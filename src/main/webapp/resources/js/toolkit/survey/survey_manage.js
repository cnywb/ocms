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
    '编辑',
    '</a>  ', '<a class="result" href="javascript:void(0)" title="edit">',
    '查看结果',
    '</a>  '
].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	  setEditParams('editModal',row);
	 $('#editModal').modal('show');
   
},
'click .result': function (e, value, row, index) {
	result(row.code);
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
            if(attrName=="enable"||attrName=="multipleVote"){
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
	    
	    if(submitData["startTime"]==""){
	    	showAlertModal('注意', '活动开始时间不能为空！');
	    	return;
	    }
	    if(submitData["endTime"]==""){
	    	showAlertModal('注意', '活动结束时间不能为空！');
	    	return;
	    }
	   
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'toolkit/survey/add.htm',
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
	    if(submitData["name"]==""){
	    	showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
	    if(submitData["code"]==""){
	    	showAlertModal('注意', '代码不能为空！');
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
	  
	$('#editModal').modal('hide');

	$.ajax( {
		url :'toolkit/survey/update.htm',
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

function groupManage(){
	var surveyId=$("#surveyId").val();
	window.parent.addTabs({id: "survey_group_manage", title: "问题分组管理", url: 'toolkit/survey/group/manage.htm?surveyId='+surveyId, close: true});
}
function questionManage(){
	var surveyId=$("#surveyId").val();
	window.parent.addTabs({id: "question_group_manage", title: "问题管理", url: 'toolkit/survey/question/manage.htm?surveyId='+surveyId, close: true});
}
function result(code){
	window.parent.addTabs({id: "survey_result", title: "查看结果", url: 'toolkit/survey/result.htm?code='+code, close: true});
}
