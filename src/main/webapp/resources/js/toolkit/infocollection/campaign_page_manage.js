$(document).ready(function(){
	initTable();
	$('[data-toggle="popover"]').popover();
});



function initTable(){
	  $('#data_table').bootstrapTable({
      cache: false,
      height: 400,
      striped: true,
      pagination: true,
      pageSize: 10,
      pageList: [10,20,50,100],
      //search: true,
     // showColumns: true,
      //showRefresh: true,
      minimumCountColumns: 1,
      clickToSelect: true,
      queryParams: function (params) {
    	  params=getQueryParams("query_con",params);
    	  params["campaignId"]=$("#campaignId").val();
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'code',
          title: '代码'
      },{
          field: 'name',
          title: '名称'
      },{
          field: 'template',
          title: '关联模版'
      },{
          field: 'url',
          title: '访问路径',
          formatter: urlFormatter
      },{
          field: 'createTime',
          title: '创建时间'
      },{
          field: 'operate',
          title: '操作',
          align: 'center',
          events: operateEvents,
          formatter: operateFormatter
      }]
  });

}



function operateFormatter(value, row, index) {
	 return [
	         '<a class="edit" href="javascript:void(0)" title="edit">',
	         '<i class="glyphicon glyphicon-edit"></i>',
	         '</a>  ',
	         '<a class="remove" href="javascript:void(0)" title="Remove">',
	         '<i class="glyphicon glyphicon-remove"></i>',
	         '</a>'
	     ].join('');
}

function urlFormatter(value, row, index) {
	 return '<a  href="'+value+'" title="点击浏览" target="_blank">'+value+'</a>';
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	  setEditParams('editModal',row);
	 $('#editModal').modal('show');
   
},
'click .remove': function (e, value, row, index) {
	doDelete(row.id);
}
};

function setEditParams(containerId,obj){
	    clearEditParams(containerId);
		$("#"+containerId+" input[type='text']").each(function(){
			var attrName=$(this).attr("name");
			$(this).val(obj[attrName]);
		});
		$("#"+containerId+" select").each(function(){
            var attrName=$(this).attr("name");
          
            $(this).val(obj[attrName]+"");
            
		});
	    $("#"+containerId+" input[type='hidden']").each(function(){
            var attrName=$(this).attr("name");
			$(this).val(obj[attrName]);
		});
	  
	   
}

function clearEditParams(containerId){
		$("#"+containerId+" input[type='text']").each(function(){
			$(this).val("");
		});
		$("#"+containerId+" select").each(function(){
			$(this).val("");
		});
	    $("#"+containerId+" input[type='hidden']").each(function(){
	    	$(this).val("");
		});
	    $("#"+containerId+" textarea").each(function(){
	    	$(this).val("");
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


function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}


function save(){
	   var submitData=getParams('addModal');
	   submitData["campaign.id"]=$("#campaignId").val();
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'toolkit/infocollection/campaign/page/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			   showAlertModal('注意',result.message);
		       if(result.status==5){
		    	   refreshTable();
		       }
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	showAlertModal('注意','请求错误！'+textStatus);
     }
	});
}




function update(){
	   
	var submitData=getParams('editModal');
	 submitData["campaign.id"]=$("#campaignId").val();
	$('#editModal').modal('hide');
	$.ajax( {
		url :'toolkit/infocollection/campaign/page/update.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			   showAlertModal('注意',result.message);
		       if(result.status==5){
		    	   refreshTable();
		       }
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	 	    	showAlertModal('注意','请求错误！'+textStatus);
     }
	});
}

function doDelete(id){

	if(!confirm("确认删除该条数据吗？")){
		return;
	}
	
	var submitData={};
	submitData["id"]=id;
	$.ajax( {
			url :'toolkit/infocollection/campaign/page/delete.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   showAlertModal('注意',result.message);
			       if(result.status==1){
			    	   refreshTable();
			       }
		     },error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	showAlertModal('注意','请求错误！'+textStatus);
	     }
		});
}


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function getQueryParams(containerId,submitData){
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

function templateManage(campaignCode){
	window.parent.addTabs({id: "campaign_template_manage", title: "模版文件管理", url: 'toolkit/infocollection/campaign/file/manage.htm?type=template&&campaignCode='+campaignCode, close: true});
}
function resourcesManage(campaignCode){
	window.parent.addTabs({id: "campaign_resources_manage", title: "资源文件管理", url: 'toolkit/infocollection/campaign/file/manage.htm?type=resources&campaignCode='+campaignCode, close: true});
}
