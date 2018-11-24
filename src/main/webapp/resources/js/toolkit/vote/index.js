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
          field: 'multipleVoteName',
          title: '是否可多选'
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
    '<a class="edit" href="javascript:void(0)" title="edit">',
    '投票',
    '</a>  '
].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	result(row.id);
}
};
function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
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
function result(voteId){
	window.parent.addTabs({id: "vote_result", title: "投票结果", url: 'toolkit/vote/result.htm?voteId='+voteId, close: true});
}

