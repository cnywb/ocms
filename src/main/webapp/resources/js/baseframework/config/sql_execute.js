$(document).ready(function(){
	
});



function bulkUpdate(){
	var sql=$("#sql").val();
	var submitData={"sql":sql};
	    if(sql==""){
	    	showAlertModal('注意','请输入执行语句！');
	    	return ;
	    }
	    $.blockUI({ message:'正在执行操作，请稍等...' });
    $.ajax( {
		url :'baseframework/config/sql/execute/bulkUpdate.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			$.unblockUI();
			showAlertModal('注意',result);
		},error: function(XMLHttpRequest, textStatus, errorThrown){
			$.unblockUI();
	    	showAlertModal('注意','系统异常！');
  }
	});
}


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}






