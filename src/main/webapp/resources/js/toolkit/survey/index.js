$(document).ready(function(){
	initTable();
	$(".form_datetime").datetimepicker({minView:1,language: 'zh-CN',format: 'yyyy-mm-dd hh:ii:ss',autoclose: true});
	$('[data-toggle="popover"]').popover();
});


function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}
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
          field: 'code',
          title: '活动代码'
      },{
          field: 'name',
          title: '活动名称'
      },{
          field: 'startTime',
          title: '活动开始时间'
      },{
          field: 'endTime',
          title: '活动结束时间'
      },{
          field: 'enableName',
          title: '是否启用'
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
    '<a class="edit" href="javascript:void(0)" >',
    '参与',
    '</a>  '
].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	goDetail(row.code);
   
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


function goDetail(code){
	window.parent.addTabs({id: "survey_detail", title: "查看详情", url: 'toolkit/survey/detail.htm?code='+code, close: true});
}


