$(document).ready(function(){
	$(".form_datetime").datetimepicker({minView:2,language: 'zh-CN',format: 'yyyy-mm-dd',autoclose: true});
	initTable();
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
        	  params=getQueryParams('query_con',params);
        	  return params;
          },
          columns: [ {
              field: 'id',
              title: 'ID'
          },{
              field: 'name',
              title: '名称'
          },{
              field: 'formatName',
              title: '格式'
          },{
              field: 'sendFrequencyName',
              title: '发送频率'
          },{
              field: 'receiveEmail',
              title: '接收邮箱',
              formatter: lengthFormatter
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

function lengthFormatter(value, row, index){
	if(value.length>20){
		return value.substring(0,20)+"..";
	}
	return value;
}

function operateFormatter(value, row, index) {
    return [
            '<a class="run" href="javascript:void(0)" title="执行">',
            '<i class="fa fa-1x fa-play-circle-o"></i>',
            '</a>  ',
        '<a class="edit" href="javascript:void(0)" title="编辑">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:void(0)" title="删除">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('');
}

window.operateEvents = {
		 'click .run': function (e, value, row, index) {
			 $('#runParamsModal').modal('show');
			 $('#runReportId').val(row.id);
	    },
    'click .edit': function (e, value, row, index) {
    	  setEditParams('editModal',row);
    	 $('#editModal').modal('show');
       
    },
    'click .remove': function (e, value, row, index) {
    	doDelete(row.id);
    }
};

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
		$("#"+containerId+" textarea").each(function(){
			var attrName=$(this).attr("name");
			
			$(this).val(obj[attrName]);
		});
		$("#"+containerId+" select").each(function(){
             var attrName=$(this).attr("name");
			$(this).val(obj[attrName]);
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
	$("#"+containerId+" textarea").each(function(){
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

function save(){
	var submitData=getParams('addModal');
	if(submitData["name"]==""){
	   showAlertModal('注意', '名称不能为空！');
 	   return;
    }
    $('#addModal').modal('hide');
    $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'toolkit/report/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			$.unblockUI();
			showAlertModal('注意',result.message);
			if(result.status==1){
				 $('#data_table').bootstrapTable("refresh");
			}
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	    	 $.unblockUI();
	 	    	showAlertModal('注意','请求错误！'+errorThrown);
       }
	});
}


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function update(){
	var submitData=getParams('editModal');
	if(submitData["name"]==""){
		   showAlertModal('注意', '名称不能为空！');
	 	   return;
	}
	 $('#editModal').modal('hide');
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'toolkit/report/update.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			$.unblockUI();
			showAlertModal('注意',result.message);
			if(result.status==1){
				 $('#data_table').bootstrapTable("refresh");
			}
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	    	 $.unblockUI();
	 	    	showAlertModal('注意','请求错误！'+errorThrown);
       }
	});
}


function doDelete(id){

	if(!confirm("确认删除该数据吗？")){
		return;
	}
	 var submitData={};
	 submitData["id"]=id;
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'toolkit/report/delete.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			$.unblockUI();
			showAlertModal('注意',result.message);
			if(result.status==1){
				 $('#data_table').bootstrapTable("refresh");
			}
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	    	   $.unblockUI();
	 	    	showAlertModal('注意','请求错误！'+errorThrown);
       }
	});
}

function run(){

	if(!confirm("确认执行该报告吗？")){
		return;
	}
	 var submitData=getParams('runParamsModal');
	 $('#runParamsModal').modal('hide');
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	$.ajax( {
		url :'toolkit/report/job/run.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			$.unblockUI();
			showAlertModal('注意',result.message);
			if(result.status==1){
				 $('#data_table').bootstrapTable("refresh");
			}
	     },error:function(XMLHttpRequest, textStatus, errorThrown){
	    	   $.unblockUI();
	 	    	showAlertModal('注意','请求错误！'+errorThrown);
       }
	});
}

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}

function reportItemManage(){
	var reportId=$("#reportId").val();
	window.parent.addTabs({id: "report_item_manage", title: "设置报告项", url: 'toolkit/report/item/manage.htm?reportId='+reportId, close: true});
}

