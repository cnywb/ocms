function save(){
	var submitData=getParams('add_content');
	
	var campaign={"code":submitData["campaignCode"]};
	var channel={"code":submitData["channelCode"]};
	
	submitData["campaign"]=campaign;
	submitData["channel"]=channel;
	
	delete submitData.campaignCode;
	delete submitData.channelCode;
	
	var jsonStr=JSON.stringify(submitData);
	alert("将要提交的数据:"+jsonStr);
	$.ajax( {
		url :'toolkit/infocollection/data/addForBpJSONP.htm',
		type : 'post',
		dataType:'jsonp',
		jsonp:"jsonpcallback",
		data:{"jsonStr":jsonStr},
		success : function(result) {
			   showAlertModal('注意',result.message);
		},error:function(XMLHttpRequest, textStatus, errorThrown){
	 	       showAlertModal('注意','请求错误！'+textStatus);
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

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}