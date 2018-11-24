$(document).ready(function(){
	initTable();
	$(".form_datetime").datetimepicker({minView:1,language: 'zh-CN',format: 'yyyy-mm-dd hh:ii:ss',autoclose: true});
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
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'campaignName',
          title: '活动名称'
      },{
          field: 'subject',
          title: '主题'
      },{
          field: 'channelName',
          title: '渠道'
      },{
          field: 'buyerName',
          title: '车主姓名'
      },{
          field: 'buyerMobilePhoneNo',
          title: '车主手机号'
      },{
          field: 'potentialBuyerName',
          title: '潜客姓名'
      },{
          field: 'potentialBuyerMobilePhoneNo',
          title: '潜客手机'
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
'  <a class="edit" href="javascript:void(0)" title="edit">',
'详情','</a>',
	         '  <a class="remove" href="javascript:void(0)" title="Remove">',
	         '<i class="glyphicon glyphicon-remove"></i>',
	         '</a>'
	     ].join('');
}

window.operateEvents = { 'click .edit': function (e, value, row, index) {
	  setEditParams('editModal',row);
	  $('#editModal').modal('show');
   
},

'click .remove': function (e, value, row, index) {
	doDelete(row.id);
}
};





function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}



function doDelete(id){
	if(!confirm("确认删除该条数据吗？")){
		return;
	}
	var submitData={};
	submitData["id"]=id;
	$.ajax( {
			url :'toolkit/infocollection/data/delete.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   showAlertModal('注意',result.message);
			       if(result.status==2){
			    	   refreshTable();
			       }
		     },error:function(XMLHttpRequest, textStatus, errorThrown){
		 	    	showAlertModal('注意','请求错误！'+textStatus);
	     }
		});
}


function exportExcel(){
	var submitData={"limit":"10","offset":"0","order":"asc"};
	submitData=getQueryParams("query_con",submitData);
	$.ajax( {
			url :'toolkit/infocollection/data/query.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				 if(result.total*1>10000){
					 showAlertModal('注意','每次导出条数不能超过10000条！');
				 }else{
					 $('#queryForm').submit();
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

function setEditParams(containerId,obj){
    clearEditParams(containerId);
	$("#"+containerId+" input[type='text']").each(function(){
		var attrName=$(this).attr("name");
		$(this).val(obj[attrName]);
	});
	$("#"+containerId+" select").each(function(){
        var attrName=$(this).attr("name");
        if(attrName=="enable"){
        	$(this).val(obj[attrName]+"");
        }else{
        	$(this).val(obj[attrName]);
        }
	});
    $("#"+containerId+" input[type='hidden']").each(function(){
        var attrName=$(this).attr("name");
		$(this).val(obj[attrName]);
	});
  
   
}

function clearEditParams(containerId){
	clearForm(containerId);
}

function clearForm(containerId){
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

