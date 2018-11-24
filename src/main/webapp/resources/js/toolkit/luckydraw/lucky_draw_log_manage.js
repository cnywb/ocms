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
    	  params["luckyDrawId"]=$("#luckyDrawId").val();
    	  params=getQueryParams("query_con",params);
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'luckyDrawCode',
          title: '活动代码'
      },{
          field: 'luckyDrawName',
          title: '活动名称'
      },{
          field: 'userName',
          title: '抽奖用户'
      },{
          field: 'createTime',
          title: '抽奖时间'
      },{
          field: 'winName',
          title: '是否中奖'
      }]
  });

}



function operateFormatter(value, row, index) {
	return [
	        '<a class="edit" href="javascript:void(0)" title="edit">',
	        '<i class="glyphicon glyphicon-edit"></i>',
	        '</a>  '
	    ].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	  setEditParams('editModal',row);
	 $('#editModal').modal('show');
   
}
};

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
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
