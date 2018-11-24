$(document).ready(function(){
	$("#btn_save").click(function(){
		save();
	});
	
});


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}
function save(){
var submitData=getParams("content_container");
setHiddenValue(currentEditor);
submitData["sourceCode"]=$("#html").val();
submitData["javaScript"]=$("#javaScript").val();
submitData["css"]=$("#css").val();
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

var currentEditor='html';
function changeEditor(type){
	setHiddenValue(currentEditor);
	currentEditor=type;
	if(type=='html'){
		sourceCode.edit('html','html');
	}else if(type=='javaScript'){
	    sourceCode.edit('javaScript','javascript');
	}else if(type=='css'){
		sourceCode.edit('css','css');
	}
}

function setHiddenValue(currentEditor){
	if(currentEditor=='html'){
		$("#html").val(sourceCode.getCode());
	}else if(currentEditor=='javaScript'){
		$("#javaScript").val(sourceCode.getCode());
	}else if(currentEditor=='css'){
		$("#css").val(sourceCode.getCode());
	}
}
