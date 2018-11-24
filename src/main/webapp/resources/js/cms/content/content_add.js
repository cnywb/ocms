$(document).ready(function(){
	initTree();
	initChannelTree();
	initEditor();
	$("#contentCategory").click(function(){
		showMenu();
	});
	$("#channel").click(function(){
		showChannelMenu();
	});
	 $("#btn_save").click(function(){
		 save();
	 });
	
});

function initTree(){
	var siteId=$("#siteId").val();
	$.ajax( {
		url :'cms/content/category/getZTreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: {"siteId":siteId},
		success : function(result) {
			initZTree(result);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}

function showMenu() {
	$("#menuContent").slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
function initZTree(data){
	$("#menuContent").hide();
	$.fn.zTree.init($("#treeDemo"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				$("#contentCategoryId").val(treeNode.id);
				$("#contentCategory").val(treeNode.name);
			
				 hideMenu();
			}
		}
	},data);
	
	$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
}




function showChannelMenu() {
	$("#channelMenuContent").slideDown("fast");
	$("body").bind("mousedown", onBodyDownForChannel);
}
function hideChannelMenu() {
	$("#channelMenuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownForChannel);
}
function onBodyDownForChannel(event) {
	if (!(event.target.id == "channelMenuContent" || $(event.target).parents("#channelMenuContent").length>0)) {
		hideChannelMenu();
	}
}


function initChannelTree(){
	$("#channelMenuContent").hide();
	var siteId=$("#siteId").val();
	$.ajax( {
		url :'cms/channel/getZTreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: {"siteId":siteId},
		success : function(result) {
			initChannelZTree(result);
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}

function initChannelZTree(data){
	$.fn.zTree.init($("#channelTree"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				
				$("#channelId").val(treeNode.id);
				$("#channel").val(treeNode.name);
			
				hideChannelMenu();
				
			}
		}
	},data);
	
	$.fn.zTree.getZTreeObj("channelTree").expandAll(true);
}




var editor;
function initEditor(){

	var sessionId=getCookie("JSESSIONID");
   
	var cssPathes=new Array();
   
	cssPathes.push('../kindeditor/plugins/code/prettify.css');
	 var scriptPathes=new Array();
   
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


function save(){
	var submitData=getParams('content_container');
	var content=editor.html();
	submitData["tempContentString"]=content;
	if(submitData["title"]==""){
		showAlertModal('注意','标题不能为空！');
		return ;
	}
	if(submitData["channel.id"]==""){
		showAlertModal('注意','栏目不能为空！');
		return ;
	}
	if(submitData["contentCategory.id"]==""){
		showAlertModal('注意','类别不能为空！');
		return ;
	}
	if(submitData["content"]==""){
		showAlertModal('注意','内容不能为空！');
		return ;
	}
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'cms/content/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			 $.unblockUI();
			showAlertModal('注意',result.message);
		 },error: function(XMLHttpRequest, textStatus, errorThrown){
			 $.unblockUI();
	       showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}
	
function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').text(content);
	$("#alertModal").modal('show');
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
