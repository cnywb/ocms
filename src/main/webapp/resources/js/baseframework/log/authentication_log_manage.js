$(document).ready(function(){
	initTable();
	$(".form_datetime").datetimepicker({minView:2,language: 'zh-CN',format: 'yyyy-mm-dd',autoclose: true});
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
              field: 'address',
              title: 'IP地址'
          },{
              field: 'userName',
              title: '登录账号',
              events: operateEvents,
              formatter:operateFormatter
          },{
              field: 'createTime',
              title: '登录时间'
          },{
              field: 'resultName',
              title: '登录结果'
          },{
              field: 'updateTime',
              title: '注销时间'
          }]
      });

}

function operateFormatter(value, row, index) {
    return [
        value+'  <a class="edit" href="javascript:void(0)" title="edit">',
        '查看用户信息',
        '</a>  '
    ].join('');
}

window.operateEvents = {
    'click .edit': function (e, value, row, index) {
    	if(row.user==null){
    		alert("非系统用户，无法查看信息！");
    		return;
    	}
     	  setEditParams('editModal',row.user);
    	 $('#editModal').modal('show');
       
    }
};



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
function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}


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
	   
	    $("#"+containerId+" img").each(function(){
			$(this).attr("src",obj["photo"]);
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
	    $("#"+containerId+" img").each(function(){
			$(this).attr("src","");
		});
}
