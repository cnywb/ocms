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
      	 params["name"]=$("#queryKeyword").val();
      	  return params;
        },
        columns: [ {
            field: 'id',
            title: 'ID'
        },{
            field: 'code',
            title: '代码'
        },{
            field: 'name',
            title: '名称'
        },{
            field: 'domain',
            title: '域名'
        },{
            field: 'indexTemplate',
            title: '首页模版'
        },{
            field: 'createTime',
            title: '创建时间'
        },{
            field: 'statusName',
            title: '状态'
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
  	
  }
};
function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}
var siteCode="";
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
	    siteCode=obj.code;
	   getSiteIndexTemplateFileList(obj.code, "editModal",obj.indexTemplate );
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
    $('#addModal').modal('hide');
    $.ajax( {
		url :'cms/site/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			showAlertModal('注意',result.message);
			if(result.status==6){
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

function update(){
	var submitData=getParams('editModal');
	 $('#editModal').modal('hide');
	$.ajax( {
		url :'cms/site/update.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			showAlertModal('注意',result.message);
			if(result.status==6){
				refreshTable();
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	     	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}

function getSiteIndexTemplateFileList(siteCode,targetModal,selected){
	var submitData={};
	submitData["siteCode"]=siteCode;
	$.ajax( {
		url :'cms/file/getSiteIndexTemplateFileList.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			var optionHtml="";
			for(var i=0;i<result.length;i++){
				if(selected==result[i]){
					optionHtml=optionHtml+'<option selected="selected" value=\''+result[i]+'\'>'+result[i]+'</option>';
				}else{
					optionHtml=optionHtml+'<option value=\''+result[i]+'\'>'+result[i]+'</option>';
				}
			}
			$("#"+targetModal+" select[name='indexTemplate']").empty();
			$("#"+targetModal+" select[name='indexTemplate']").append(optionHtml);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	     	showAlertModal('注意','系统异常！');
     }
	});
}
//siteCode 为全局变量
