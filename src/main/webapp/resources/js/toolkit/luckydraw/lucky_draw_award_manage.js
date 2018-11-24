$(document).ready(function(){
	initTable();
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
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'code',
          title: '奖品代码'
      },{
          field: 'name',
          title: '奖品名称'
      },{
          field: 'grade',
          title: '奖品级别'
      },{
          field: 'quantity',
          title: '总数量'
      },{
          field: 'remainingQty',
          title: '剩余数量'
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
	        '</a>  ',
	        '<a class="remove" href="javascript:void(0)" title="Remove">',
	        '<i class="glyphicon glyphicon-remove"></i>',
	        '</a>'
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
            if(attrName=="enable"){
            	$(this).val(obj[attrName]+"");
            }else{
            	$(this).val(obj[attrName]);
            }
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
	    if(submitData["name"]==""){
	    	showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
	    if(submitData["code"]==""){
	    	showAlertModal('注意', '代码不能为空！');
	    	return;
	    }
	    if(parseInt(submitData["quantity"])!=submitData["quantity"]){
	    	showAlertModal('注意', '总数量只能为整数！');
	    	return;
	    }
	    submitData["luckyDraw.id"]=$("#luckyDrawId").val();
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/award/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败,奖品代码在系统中已经存在，请用其它代码！');
			}else if(result==1){
				showAlertModal('注意','操作成功！');
				refreshTable();
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	 	    	showAlertModal('注意','系统异常！');
     }
	});
}

function update(){
	   var submitData=getParams('editModal');
	    if(submitData["name"]==""){
	    	showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
	    if(submitData["code"]==""){
	    	showAlertModal('注意', '代码不能为空！');
	    	return;
	    }
	    if(parseInt(submitData["quantity"])!=submitData["quantity"]){
	    	showAlertModal('注意', '总数量只能为整数！');
	    	return;
	    }
	    submitData["luckyDraw.id"]=$("#luckyDrawId").val();
	$('#editModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/award/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		    if(result==0){
				showAlertModal('注意','操作失败,活动代码在系统中已经存在，请用其它代码！');
			}else if(result==1){
				showAlertModal('注意','操作成功！');
				refreshTable();
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


 function doDelete(delId){
	   if(!confirm("删除奖品会册除其对应的规则，确定要进行此操作吗?")){
	     return;
	   }
       $.ajax( {
 			url :'toolkit/luckydraw/award/delete.htm',
 			type : 'post',
 			data: 'id='+delId,
 			dataType:'text',
 			success : function(result) {
 				if(result=="2"){
 					showAlertModal('注意','操作成功！');
 					refreshTable();
 				}else if(result=="0"){
 					showAlertModal('注意', '操作失败！活动还在启用中无法删除，请先停用活动。');
 				}else if(result=="1"){
 					showAlertModal('注意', '操作失败！已有中奖记录无法删除该奖品。');
 				}else{
 					showAlertModal('注意', '操作失败！系统异常。');
 				}
 	        },error:function(jqXHR, textStatus, errorThrown){
 	 	      alert('注意:请求异常！状态码'+textStatus);
 		    }
 		});
 	
 }
