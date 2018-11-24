$(document).ready(function(){
	initTable();
	initAward();
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
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'awardCode',
          title: '奖品代码'
      },{
          field: 'awardName',
          title: '奖品名称'
      },{
          field: 'awardQty',
          title: '出奖数量'
      },{
          field: 'awardDate',
          title: '出奖时间'
      },{
          field: 'remainingQty',
          title: '剩余数量'
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
            if(attrName=="award.id"){
            	$(this).val(obj["award"].id);
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
	    
	    if(parseInt(submitData["awardQty"])!=submitData["awardQty"]){
	    	showAlertModal('注意', '总数量只能为整数！');
	    	return;
	    }
	    submitData["luckyDraw.id"]=$("#luckyDrawId").val();
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/rule/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败,同一活动中规则奖品总数不能超过活动奖品总数！');
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
	    if(parseInt(submitData["awardQty"])!=submitData["awardQty"]){
	    	showAlertModal('注意', '总数量只能为整数！');
	    	return;
	    }
	    submitData["luckyDraw.id"]=$("#luckyDrawId").val();
	$('#editModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/rule/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败,同一活动中规则奖品总数不能超过活动奖品总数！');
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

function initAward(){
	   var luckyDrawId=$("#luckyDrawId").val();
	$.ajax( {
		url :'toolkit/luckydraw/award/findAllByluckyDrawId.htm',
		type : 'post',
		dataType:'json',
		data:{"luckyDrawId":luckyDrawId},
		success : function(result) {
			for(var i=0;i<result.length;i++){
				var t=result[i];
			  
			    $("select[name='award.id']").append('<option  value="'+t.id+'">'+t.name+'</option>');
				  
			 }
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	 
	    	alert('注意网络异常！');
   }
	});
}

function doDelete(delId){
	 if(!confirm("确定要进行此删除操作吗?")){
	 return;
	 }
     $.ajax( {
			url :'toolkit/luckydraw/rule/delete.htm',
			type : 'post',
			data: 'id='+delId,
			dataType:'text',
			success : function(result) {
				if(result=="1"){
					showAlertModal('注意', '操作成功！');
					refreshTable();
 				}else if(result=="0"){
 					showAlertModal('注意', '操作失败！活动还在启用中无法删除，请先停用活动。');
 				}else{
 					showAlertModal('注意', '操作失败！系统异常。');
 				}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	   showAlertModal('注意','系统错误，操作失败！');
	       }
		});
	
}

