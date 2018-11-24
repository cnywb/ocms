$(document).ready(function(){
	queryAllComponent();
	$('#saveToSelfComponent').click(function(){
		$('#editorModal').modal('hide');
		$('#saveToSelfComponentModal').modal('show');
		currenteditor.html(editor.html());//将编辑器里的代码存到公共变量中，保存时会取它
	});
});

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

var editor;
function initEditor(){
  	var sessionId=getCookie("JSESSIONID");
    var cssPathes=new Array();
    cssPathes.push('../kindeditor/plugins/code/prettify.css');
	KindEditor.ready(function(K) {
		 editor= K.create('#contenteditor', {
  			cssPath:cssPathes,
			//scriptPath:scriptPathes,
			filterMode : false,
			uploadJson:'baseframework/util/editor/upload.htm;jsessionid='+sessionId, 
			fileManagerJson:'baseframework/util/editor/manage.htm;jsessionid='+sessionId,
			allowFileManager : true
		 });
		 
   	});
  
}

function savePageLayout(){
	var submitData={};
	submitData["html"]=downloadLayoutSrc();
	submitData["id"]=$("#pageId").val();
	$.ajax( {
		url :'cms/page/saveLayout.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		    if(result==0){
				showAlertModal('注意','操作失败，页面不存在！');
			}else if(result==1){
				showAlertModal('注意','操作失败，当前页面不属于bootstrap布局，请用源码编辑功能设制布局容器！');
			}else if(result==2){
				showAlertModal('注意','操作成功！');
			}else{
				showAlertModal('注意','系统异常！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}



function saveAndPublishPageLayout(){
	var submitData={};
	submitData["html"]=downloadLayoutSrc();
	submitData["id"]=$("#pageId").val();
	$.ajax( {
		url :'cms/page/saveAndPublishLayout.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		    if(result==0){
				showAlertModal('注意','操作失败，页面不存在！');
			}else if(result==1){
				showAlertModal('注意','操作失败，当前页面不属于bootstrap布局，请用源码编辑功能设制布局容器！');
			}else if(result==2){
				showAlertModal('注意','操作成功！');
			}else{
				showAlertModal('注意','系统异常！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}


/**
 * 加载所有用户自定义组件
 */
function queryAllComponent(){
	var submitData={};
	$.ajax( {
		url :'cms/page/component/queryAll.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			var html='';
		    for(var i=0;i<result.length;i++){
		    	html=html+result[i].sourceCode;
		    }
		    $("#selfDefComponent").html(html);
		    for(var i=0;i<result.length;i++){//加载组件所需的js,css文件
		    	//$("head").append('<script src="/resources/component/'+result[i].id+'/'+result[i].code+'.js"></script>');
		    	$("head").append('<link rel="stylesheet" href="/resources/component/'+result[i].id+'/'+result[i].code+'.css" />');
		    }
		    initBoxDrag();//初始化组件可拖动
		    reverseLayout();//将原码还原成可视化布局
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}
function addPageComponent(){

var submitData=getParams("saveToSelfComponentModal");

submitData["sourceCode"]=currenteditor.parent().parent().html();

if(submitData["name"]==""){
	showAlertModal('注意','名称不能为空！');
	return ;
}

if(submitData["code"]==""){
	showAlertModal('注意','代码不能为空！');
	return ;
}

if(submitData["sourceCode"]==""){
	showAlertModal('注意','源码不能为空！');
	return ;
}
	$.ajax( {
		url :'cms/page/component/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败，组件代码在系统中已经存在，请重新输入！');
			}else if(result>0){
				showAlertModal('注意','操作成功！');
				queryAllCqueryAllComponent();
			}else{
				showAlertModal('注意','系统异常！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}

function getParams(containerId){
	var submitData={};
	$("#"+containerId+" input[type='text']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" select").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	$("#"+containerId+" input[type='hidden']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	return submitData;
}
