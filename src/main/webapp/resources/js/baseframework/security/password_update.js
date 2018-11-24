$(document).ready(function(){
	App.init();
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

if(submitData["newPassword"]==""){
	showAlertModal('注意','新密码不能为空！');
	return ;
}

if(submitData["newPassword"]!=submitData["confirmNewPassword"]){
	showAlertModal('注意','新密码与确认新密码不一致！');
	return ;
}
$.ajax( {
		url :'baseframework/security/user/updatePassword.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','当前密码不能为空！');
			}else if(result==1){
				showAlertModal('注意','新密码不能为空！');
			}else if(result==2){
				showAlertModal('注意','操作失败，当前密码不正确！');
			}else if(result==3){
				showAlertModal('注意','操作成功！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}

function getParams(containerId){
	var submitData={};
	$("#"+containerId+" input[type='password']").each(function(){
		submitData[$(this).attr("name")]=$(this).val();
	});
	
	return submitData;
}

