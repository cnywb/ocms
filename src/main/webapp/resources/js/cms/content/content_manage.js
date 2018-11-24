$(document).ready(function(){
	initTree();
	initTable();
	$("#siteId").change(function(){
		initTree();
	});
	$("#btnAdd").click(function(){
		var siteId=$("#siteId").val();
		var siteCode=$("#siteId").find("option:selected").attr("code");
		window.parent.addTabs({id: "tab_content_add", title: "新建内容", url: 'cms/content/add.htm?siteId='+siteId+'&siteCode='+siteCode, close: true});
	});
});

function initTree(){
	var siteId=$("#siteId").val();
	$.ajax( {
		url :'cms/channel/getZTreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: {"siteId":siteId},
		success : function(result) {
			initZTree(result);
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}

function initZTree(data){
	$.fn.zTree.init($("#treeDemo"),{
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
					$('#treeModal').modal('hide');
				    
			}
		}
	},data);
	
	$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
}

function clearChannel(){
	channelId="";
	$("#channelName").val("");
	$('#treeModal').modal('hide');
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
      	 params["title"]=$("#queryKeyword").val();
      	 params["channelId"]=channelId;
      	  return params;
        },
        columns: [ {
            field: 'id',
            title: 'ID'
        },{
            field: 'title',
            title: '标题'
        },{
            field: 'author',
            title: '作者'
        },{
            field: 'channelName',
            title: '栏目'
        },{
            field: 'categoryName',
            title: '类别'
        },{
            field: 'template',
            title: '模版'
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


function urlFormatter(value, row, index) {
	 return '<a  href="'+value+'" title="点击浏览" target="_blank">'+value+'</a>';
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
	   var id=row.id;
	   var siteId=$("#siteId").val();
	   var siteCode=$("#siteId").find("option:selected").attr("code");
		window.parent.addTabs({id: "tab_content_edit", title: "编辑内容", url: 'cms/content/edit.htm?id='+id+"&siteId="+ siteId+'&siteCode='+siteCode, close: true});
  },
  'click .remove': function (e, value, row, index) {
  	
  }
};

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}

function templateManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "content_template_manage", title: "内容模版文件管理", url: 'cms/file/manage.htm?type=template&siteCode='+siteCode+'&module=content&moduleCode=', close: true});
}
function resourcesManage(){
	var siteCode=$("#siteId").find("option:selected").attr("code");
	window.parent.addTabs({id: "content_resources_manage", title: "内容资源文件管理", url: 'cms/file/manage.htm?type=resources&siteCode='+siteCode+'&module=content&moduleCode=', close: true});
}

