$(document).ready(function(){
	initTable();
	$('[data-toggle="popover"]').popover();
	App.init();
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
      	 params["genName"]=$("#queryKeyword").val();
      	  return params;
        },
        columns: [ {
            field: 'id',
            title: 'ID'
        },{
            field: 'genName',
            title: '名称'
        },{
            field: 'genValue',
            title: '下一个值'
        },{
            field: 'memo',
            title: '备注'
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
      '</a>  '
  ].join('');
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
	if(submitData["genName"]==""){
		showAlertModal('注意', '主键名称不能为空！');
    	return;
    }
	if(submitData["genValue"]==""){
		showAlertModal('注意', '下一个值不能为空！');
    	return;
    }
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'baseframework/config/idgenerator/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		
			if(result==0){
				showAlertModal('注意','操作失败，因为主键名已经在系统中存在，请重新填写！');
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


function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function update(){
	var submitData=getParams('editModal');
	if(submitData["genName"]==""){
		showAlertModal('注意', '主键名称不能为空！');
    	return;
    }
	if(submitData["genValue"]==""){
		showAlertModal('注意', '下一个值不能为空！');
    	return;
    }
	$('#editModal').modal('hide');

	$.ajax( {
		url :'baseframework/config/idgenerator/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			
			if(result==0){
				showAlertModal('注意','操作失败，因为主键名已经在系统中存在，请重新填写！');
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

