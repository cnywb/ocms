$(document).ready(function(){
	initTree();
	initTable();
	$("#siteId").change(function(){
		initTree();
	});
	
	$('[data-toggle="popover"]').popover();
	
	$("#editChannelName").click(function(){
		$("#menuContent").show();
	});
});




function getIndextPageUrlId(id){
	for(var i=0;i<treeData.length;i++){
		var t=treeData[i];
		if(t.id==id){
			return t.pageUrl;
		}
	}
	return "";
}
var treeData=[];
function initTree(){
	var siteId=$("#siteId").val();
	$.ajax( {
		url :'cms/channel/getZTreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: {"siteId":siteId},
		success : function(result) {
			treeData=result;
			initZTree(result);
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}

function initZTree(data){
	$.fn.zTree.init($("#channelTree"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
					channelId=treeNode.id;
				    $("#channelName").val(treeNode.name);
				    $("#channelId").val(treeNode.id);
				    $('#treeModal').modal('hide');
			}
		}
	},data);
	
	$.fn.zTree.getZTreeObj("channelTree").expandAll(true);
	
	
	$.fn.zTree.init($("#addChannelTree"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				   $("#addModal input[name='channelName']").val(treeNode.name);
				   $("#addModal input[name='channel.id']").val(treeNode.id);
				   $("#addChannelTreeModal").modal("hide");
	               getChannelTemplateFileList(treeNode.code, "addModal","");
	    	}
		}
	},data);
	
	$.fn.zTree.init($("#editChannelTree"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				   $("#editModal input[name='channelName']").val(treeNode.name);
				   $("#editModal input[name='channel.id']").val(treeNode.id);
				   $("#editChannelTreeModal").modal("hide");
				   getChannelTemplateFileList(treeNode.code, "editModal","");
	    	}
		}
	},data);
	
	$.fn.zTree.init($("#fileChannelTree"),{
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: function(treeId,treeNode) {
				
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				  if(fileType=='template'){
					  templateManage(treeNode.code)
				  }else if(fileType=='resources'){
					  resourcesManage(treeNode.code);
				  }
				  
	    	}
		}
	},data);
	
	
}
var channelId="";

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
    	 params["code"]=$("#pageCode").val();
    	 params["channelId"]=channelId;
    	  return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'name',
          title: '名称'
      },{
          field: 'code',
          title: '代码'
      },{
          field: 'template',
          title: '模版'
      },{
          field: 'channelName',
          title: '所属栏目'
      },{
          field: 'url',
          title: '访问路径',
          formatter: urlFormatter
      },{
          field: 'statusName',
          title: '状态'
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
	
}
};
function urlFormatter(value, row, index) {
	 return '<a  href="'+value+'" title="点击浏览" target="_blank">'+value+'</a>';
}
function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}


function setEditParams(containerId,obj){
	     clearEditParams(containerId);
		 $("#"+containerId+" input[type='text']").each(function(){
			var attrName=$(this).attr("name");
			if(attrName=="channelName"){
				$(this).val(obj["channel"]["nameZh"]);
			}
			$(this).val(obj[attrName]);
		});
		$("#"+containerId+" select").each(function(){
            var attrName=$(this).attr("name");
			$(this).val(obj[attrName]);
		});
	    $("#"+containerId+" input[type='hidden']").each(function(){
            var attrName=$(this).attr("name");
		    if(attrName=="channel.id"){
		    	$(this).val(obj.channel.id);
		    	
		    }else{
		    	$(this).val(obj[attrName]);
		    }
			
		});
	    getChannelTemplateFileList(obj.channelCode, "editModal",obj.template);
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
	submitData["channel.site.id"]=$("#siteId").val();
	$('#addModal').modal('hide');
	$.ajax( {
		url :'cms/page/add.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			showAlertModal('注意',result.message);
			if(result.status==3){
				refreshTable();
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	     	showAlertModal('注意','系统异常！');
     }
	});
}




function update(){
	var submitData=getParams('editModal');
	submitData["channel.site.id"]=$("#siteId").val();
	$('#editModal').modal('hide');
	$.ajax( {
		url :'cms/page/update.htm',
		type : 'post',
		dataType:'json',
		data:submitData,
		success : function(result) {
			showAlertModal('注意',result.message);
			if(result.status==3){
				refreshTable();
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	     	showAlertModal('注意','系统异常！');
     }
	});
}

function getChannelTemplateFileList(channelCode,targetModal,selected){
	var submitData={};
	var siteCode=$("#siteId").find("option:selected").attr("code");
	submitData["siteCode"]=siteCode;
	submitData["channelCode"]=channelCode;
	$.ajax( {
		url :'cms/file/getChannelTemplateFileList.htm',
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
			$("#"+targetModal+" select[name='template']").empty();
			$("#"+targetModal+" select[name='template']").append(optionHtml);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	     	showAlertModal('注意','系统异常！');
     }
	});
}

function clearChannel(){
	channelId="";
	$("#channelName").val("");
	$('#treeModal').modal('hide');
}
var fileType;
function showFileChannelTreeModal(type){
	$("#fileChannelTreeModal").modal("show");
	fileType=type;
}

function templateManage(moduleCode){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "channel_template_manage", title: "栏目模版管理", url: 'cms/file/manage.htm?type=template&siteCode='+siteCode+'&module=channel&moduleCode='+moduleCode, close: true});
}
function resourcesManage(moduleCode){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "channel_resources_manage", title: "栏目资源管理", url: 'cms/file/manage.htm?type=resources&siteCode='+siteCode+'&module=channel&moduleCode='+moduleCode, close: true});
}

function indexTemplateManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "index_template_manage", title: "首页模版管理", url: 'cms/file/manage.htm?type=template&siteCode='+siteCode+'&module=index&moduleCode=', close: true});
}
function indexResourcesManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "index_resources_manage", title: "首页资源管理", url: 'cms/file/manage.htm?type=resources&siteCode='+siteCode+'&module=index&moduleCode=', close: true});
}
function includeTemplateManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "include_template_manage", title: "代码片段模版管理", url: 'cms/file/manage.htm?type=template&siteCode='+siteCode+'&module=include&moduleCode=', close: true});
}
function includeResourcesManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "include_resources_manage", title: "代码片段资源管理", url: 'cms/file/manage.htm?type=resources&siteCode='+siteCode+'&module=include&moduleCode=', close: true});
}