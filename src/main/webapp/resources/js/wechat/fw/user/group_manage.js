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
              field: 'id',
              title: 'ID'
          },{
              field: 'name',
              title: '名称'
          },{
              field: 'count',
              title: '用户数'
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

window.operateEvents = { 'click .edit': function (e, value, row, index) {
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
	var group={"group":submitData};
	var json=JSON.stringify(group);
	$('#addModal').modal('hide');
	$.ajax( {
		url :'wechat/user/createFwGroup.htm',
		type : 'post',
		dataType:'json',
		data:{"json":json},
		success : function(result) {
			    if(result.errcode==undefined){
					showAlertModal('注意','操作成功!');
					refreshTable();
				}else{
					showAlertModal('注意',result.errmsg); 
				}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	  	    	 showAlertModal('注意','系统异常！'+errorThrown);
	     }
        
	});
}

function update(){
	var submitData=getParams('editModal');
	if(submitData["name"]==""){
	   showAlertModal('注意', '名称不能为空！');
 	   return;
    }
	var group={"group":submitData};
	var json=JSON.stringify(group);
	$('#editModal').modal('hide');
	$.ajax( {
		url :'wechat/user/updateFwGroup.htm',
		type : 'post',
		dataType:'json',
		data:{"json":json},
		success : function(result) {
			    if(result.errcode==0){
					showAlertModal('注意','操作成功!');
					refreshTable();
				}else{
					showAlertModal('注意',result.errmsg); 
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



function doDelete(id){
	if(!confirm("确认删除"+id+"吗？")){
		return;
	}
	var group={"group":{"id":id}};
	var json=JSON.stringify(group);
	$.ajax( {//提交数据
		url : 'wechat/user/deleteFwGroup.htm',
		type : 'post',
		dataType:'json',
		data:{"json":json},
		success : function(result) {
			if(result.errcode==0){
				showAlertModal('注意',"操作成功！");
				refreshTable();
			}else{
				showAlertModal('注意',result.errmsg);
			}
       },error: function(XMLHttpRequest, textStatus, errorThrown){
    	   showAlertModal('注意',"网络异常！");
      }
	});
}

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}
