$(document).ready(function(){
	initTable();
	$("#btnAdd").click(function(){
		window.parent.addTabs({id: "page_component_add", title: "新建组件", url: 'cms/page/component/add.htm', close: true});
	});
	
});

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
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
      	 params["name"]=$("#queryKeyword").val();
      	  return params;
        },
        columns: [ {
            field: 'id',
            title: 'ID'
        },{
            field: 'name',
            title: '名称'
        },{
            field: 'code',
            title: '代码'
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

window.operateEvents = {
  'click .edit': function (e, value, row, index) {
  	   var id=row.id;
 	window.parent.addTabs({id: "page_component_edit", title: "编辑组件", url: 'cms/page/component/edit.htm?id='+id, close: true});

     
  },
  'click .remove': function (e, value, row, index) {
  	doDelete(row.id);
  }
};

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}
function doDelete(id){

	if(!confirm("确认删除该条数据码？")){
		return;
	}
	
	var submitData={};
	submitData["id"]=id;

	
		$.ajax( {
			url :'cms/page/component/delete.htm',
			type : 'post',
			dataType:'text',
			data:submitData,
			success : function(result) {
				if(result==0){
					showAlertModal('注意','操作失败，组件不存在！');
				}else if(result>0){
					showAlertModal('注意','操作成功！');
					refreshTable();
				}else{
					showAlertModal('注意','系统异常！');
				}
		     },error: function(XMLHttpRequest, textStatus, errorThrown){
		    	
		    	 showAlertModal('注意','系统异常！'+errorThrown);
	     }
		});
	}




