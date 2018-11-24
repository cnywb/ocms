$(document).ready(function(){
	$("#btn_save").click(function(){
		save();
	});
    initFileTree();
	initEditor();
	
	$("#btn_upload").click(function(){
		$("#btnc04 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
   $("#btn_delete").click(function(){
	   deleteConfrim();
	});
	
  $("#btnc04").hide();
	
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
		url :'cms/page/component/update.htm',
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



function showDeleteModal(title,content){
	$("#deleteModal").find('h4').html(title);
	$("#deleteModal").find('p').html(content);
	$("#deleteModal").modal('show');
}

function deleteConfrim(){
	var deleteFile=$("#deleteFile").val();
	if(deleteFile==""){
		showAlertModal('注意','请点击选择要删除的文件或目录！');
		return;
	}
	showDeleteModal("注意，确定要删除如下文件或目录吗？",deleteFile);
}
function initFileTree(){
	var rootDir=$("#currentDir").val();
	$('#fileTree').fileTree({ root: rootDir, script: 'resources/file/listTree.htm' }, function(file) { 
		$("#deleteFile").val(file);
	
	});
	$("#deleteFile").val('');
}

function initEditor(){
	KindEditor.ready(function(K) {
	initUploadBtn(K,'file');
	});
}

function initUploadBtn(K,type){
	var uploadbutton = K.uploadbutton({
		button : K('#btn_upload_'+type)[0],
		fieldName : 'imgFile',
		extraParams:{'currentDir':$("#currentDir").val()},
		url : 'resources/uploadResourcesFile.htm?dir='+type,
		afterUpload : function(data) {
			if (data.error === 0) {
			   showAlertModal('注意','上传成功！');
			   initFileTree();
			}else{
				showAlertModal('注意',data.message);
			}
		},
		afterError : function(str) {
			
		}
	});
	uploadbutton.fileBox.change(function(e) {
		//uploadbutton["options"].extraParams={'currentDir':$("#currentDir").val()};
		uploadbutton.submit();
	});
}


function doDelete(){
	var deleteFile=$("#deleteFile").val();
	
	var submitData={"file":deleteFile};
   $('#deleteModal').modal('hide');

	$.ajax( {
		url :'resources/file/delete.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		    if(result=="1"){
				showAlertModal('注意','操作失败，不能删除根目录！');
			}else  if(result=="2"){
				showAlertModal('注意','操作成功！');
				 initFileTree();
			}else  if(result=="3"){
				showAlertModal('注意','操作失败，文件或目录不存在！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}
