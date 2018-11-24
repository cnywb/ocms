$(document).ready(function(){
	$("#btn_save").click(function(){
		save();
	});
	$("#btn_save_publish").click(function(){
		saveAndPublish();
	});
});
function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}
function save(){
	var submitData={};
	submitData["html"]=pageHtml.getCode();
	submitData["id"]=$("#pageId").val();
	$.ajax( {
		url :'cms/page/saveHtml.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败，页面不存在！');
			}else if(result>0){
				showAlertModal('注意','操作成功！');
			}else{
				showAlertModal('注意','系统异常！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	showAlertModal('注意','系统异常！');
     }
	});
}




function saveAndPublish(){

var submitData={};
submitData["html"]=pageHtml.getCode();
submitData["id"]=$("#pageId").val();

	$.ajax( {
		url :'cms/page/saveAndPublishHtml.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败，页面不存在！');
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

