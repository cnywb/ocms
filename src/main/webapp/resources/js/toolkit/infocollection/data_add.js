function save(){
	var submitData=getParams('add_content');
	var jsonObj={};
	jsonObj["campaignCode"]=submitData["campaignCode"];//活动代码，需要预先从后台设置，然后在页面写死
	jsonObj["channelCode"]=submitData["channelCode"];//活动渠道代码，需要预先从后台设置，然后从url中获取或写死
	delete submitData.campaignCode;
	delete submitData.channelCode;
	var dataList=new Array();//活动数据列表
	dataList.push(submitData);//放放一条数据
	jsonObj["dataList"]=dataList;//设置活动数据列表
	var jsonStr=JSON.stringify(jsonObj);
	alert("将要提交的数据:"+jsonStr);
	$.ajax( {
		url :'toolkit/infocollection/data/addJSONP.htm',
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