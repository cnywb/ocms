$(document).ready(function(){
	initTable();
	App.init();
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
    	
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'nameZh',
          title: '角色名称'
      },{
          field: 'nameEn',
          title: '角色代码'
      },{
          field: 'description',
          title: '备注'
      },{
          field: 'typeName',
          title: '角色类型'
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
	if(submitData["nameZh"]==""){
		showAlertModal('注意', '名称不能为空！');
    	return;
    }
	if(submitData["nameEn"]==""){
		showAlertModal('注意', '代码不能为空！');
    	return;
    }
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'baseframework/security/role/add.htm',
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




function update(){
	var submitData=getParams('editModal');
	if(submitData["nameZh"]==""){
		showAlertModal('注意', '名称不能为空！');
    	return;
    }
	if(submitData["nameEn"]==""){
		showAlertModal('注意', '代码不能为空！');
    	return;
    }
	$('#editModal').modal('hide');

	$.ajax( {
		url :'baseframework/security/role/update.htm',
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
