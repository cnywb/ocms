$(document).ready(function(){
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
        	
        	  return params;
          },
          columns: [ {
              field: 'openid',
              title: 'OpenID'
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

window.operateEvents = { 'click .edit': function (e, value, row, index) {
	
 	 $('#editModal').modal('show');
    
 },
   'click .remove': function (e, value, row, index) {
    	doDelete(row.id);
    }
};

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}
