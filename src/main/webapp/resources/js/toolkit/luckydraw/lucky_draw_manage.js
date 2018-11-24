$(document).ready(function(){
	initTable();
	$(".form_datetime").datetimepicker({minView:1,language: 'zh-CN',format: 'yyyy-mm-dd hh:ii:ss',autoclose: true});
	$('[data-toggle="popover"]').popover();
});

function uncheck(){
	$("input[name='edit_role_chk']").prop("checked",false);
}
function setRoles(roleIds){
	uncheck();
	var result=(roleIds+"").split(",");
	for(var i=0;i<result.length;i++){
		$("#edit_role_"+result[i]).prop("checked",true);
	}
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
          field: 'chanceQty',
          title: '每个用户最大抽奖次数'
      },{
          field: 'awardQty',
          title: '每个用户最大中奖次数'
      },{
          field: 'awardRate',
          title: '中奖机率'
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
	    setRoles(obj["participantRules"])
	   
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
	    submitData["participantRules"]=splitCheckBoxValue('role_chk');
	
	    if(submitData["name"]==""){
	    	showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
	    if(submitData["code"]==""){
	    	showAlertModal('注意', '代码不能为空！');
	    	return;
	    }
	    if(parseInt(submitData["chanceQty"])!=submitData["chanceQty"]){
	    	showAlertModal('注意', '最大抽奖次数只能为整数！');
	    	return;
	    }
	  
	    if(parseInt(submitData["chanceQty"])<0){
	    	showAlertModal('注意', '最大抽奖次数不能小于0！');
	    	return;
	    }
	    if(parseInt(submitData["awardQty"])!=submitData["awardQty"]){
	    	showAlertModal('注意', '最大中奖次数只能为整数！');
	    	return;
	    }
	   
	    if(parseInt(submitData["awardQty"])<0){
	    	showAlertModal('注意', '最大中奖次数不能小于零！');
	    	return;
	    }
	    if(parseInt(submitData["awardRate"])!=submitData["awardRate"]){
	    	showAlertModal('注意', '中奖机率只能为整数！');
	    	return;
	    }
	    if(parseInt(submitData["awardRate"])<1){
	    	showAlertModal('注意', '中奖机率必须大于1！');
	    	return;
	    }
	    
	    if(submitData["startTime"]==""){
	    	showAlertModal('注意', '活动开始时间不能为空！');
	    	return;
	    }
	    if(submitData["endTime"]==""){
	    	showAlertModal('注意', '活动结束时间不能为空！');
	    	return;
	    }
	    if(submitData["participantRules"]==""){
	    	showAlertModal('注意', '请选择参与角色！');
	    	return;
	    }
	    
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败，活动开始时间必须小于活动结束时间！');
			}else if(result==1){
				showAlertModal('注意','操作失败,活动代码在系统中已经存在，请用其它代码！');
			}else if(result==2){
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
	   submitData["participantRules"]=splitCheckBoxValue('edit_role_chk');
		
	    if(submitData["name"]==""){
	    	showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
	    if(submitData["code"]==""){
	    	showAlertModal('注意', '代码不能为空！');
	    	return;
	    }
	    if(parseInt(submitData["chanceQty"])!=submitData["chanceQty"]){
	    	showAlertModal('注意', '最大抽奖次数只能为整数！');
	    	return;
	    }
	  
	    if(parseInt(submitData["chanceQty"])<0){
	    	showAlertModal('注意', '最大抽奖次数不能小于0！');
	    	return;
	    }
	    if(parseInt(submitData["awardQty"])!=submitData["awardQty"]){
	    	showAlertModal('注意', '最大中奖次数只能为整数！');
	    	return;
	    }
	   
	    if(parseInt(submitData["awardQty"])<0){
	    	showAlertModal('注意', '最大中奖次数不能小于零！');
	    	return;
	    }
	    if(parseInt(submitData["awardRate"])!=submitData["awardRate"]){
	    	showAlertModal('注意', '中奖机率只能为整数！');
	    	return;
	    }
	    if(parseInt(submitData["awardRate"])<1){
	    	showAlertModal('注意', '中奖机率必须大于1！');
	    	return;
	    }
	    
	    if(submitData["startTime"]==""){
	    	showAlertModal('注意', '活动开始时间不能为空！');
	    	return;
	    }
	    if(submitData["endTime"]==""){
	    	showAlertModal('注意', '活动结束时间不能为空！');
	    	return;
	    }
	    if(submitData["participantRules"]==""){
	    	showAlertModal('注意', '请选择参与角色！');
	    	return;
	    }
	$('#editModal').modal('hide');

	$.ajax( {
		url :'toolkit/luckydraw/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result==0){
				showAlertModal('注意','操作失败，活动开始时间必须小于活动结束时间！');
			}else if(result==1){
				showAlertModal('注意','操作失败,活动代码在系统中已经存在，请用其它代码！');
			}else if(result==2){
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

function awardManage(){
	var luckyDrawId=$("#luckyDrawId").val();
	window.parent.addTabs({id: "lucky_draw_award_manage", title: "奖品设置", url: 'toolkit/luckydraw/award/manage.htm?luckyDrawId='+luckyDrawId, close: true});
}
function ruleManage(){
	var luckyDrawId=$("#luckyDrawId").val();
	window.parent.addTabs({id: "lucky_draw_rule_manage", title: "规则设置", url: 'toolkit/luckydraw/rule/manage.htm?luckyDrawId='+luckyDrawId, close: true});
}

function logManage(){
	var luckyDrawId=$("#luckyDrawId").val();
	window.parent.addTabs({id: "lucky_draw_log_manage", title: "抽奖记录", url: 'toolkit/luckydraw/log/manage.htm?luckyDrawId='+luckyDrawId, close: true});
}

function resultManage(){
	var luckyDrawId=$("#luckyDrawId").val();
	window.parent.addTabs({id: "lucky_draw_result_manage", title:"中奖记录", url: 'toolkit/luckydraw/result/manage.htm?luckyDrawId='+luckyDrawId, close: true});
}