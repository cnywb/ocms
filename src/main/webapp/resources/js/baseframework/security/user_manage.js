$(document).ready(function(){
	initTable();
	$('[data-toggle="popover"]').popover();
	initTree();
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
          field: 'name',
          title: '用户名'
      },{
          field: 'realName',
          title: '真实姓名'
      },{
          field: 'genderName',
          title: '性别'
      },{
          field: 'email',
          title: '电子邮箱'
      },{
          field: 'mobilePhone',
          title: '手机号码'
      },{
          field: 'roles',
          title: '角色'
      },{
          field: 'departmentName',
          title: '部门'
      },{
          field: 'accountLockedName',
          title: '是否锁定'
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

			$(this).val(obj[attrName]+"");
		});
	    $("#"+containerId+" input[type='hidden']").each(function(){
var attrName=$(this).attr("name");
			
			$(this).val(obj[attrName]);
		});
	   
	    setRoles(obj["roleIds"]);//所有字段都要小写
	   
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
	$("#"+containerId+" input[type='password']").each(function(){
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

function save(){
	var submitData=getParams('addModal');
	if(submitData["name"]==""){
		showAlertModal('注意', '用户名不能为空！');
    	return;
    }
	if(submitData["password"]==""){
		showAlertModal('注意', '登录密码不能为空！');
    	return;
    }
	if(submitData["password"]!=submitData["confirmPassword"]){
		showAlertModal('注意', '确认密码不能为空！');
    	return;
    }
	 var roleIds=splitCheckBoxValue('role_chk');
	 var departmentId=$("#departmentId").val();
	 if(departmentId==""){
		 showAlertModal('注意','部门不能为空！');
		 return ;
	 }
    if(roleIds==""){
    	showAlertModal('注意','请点选角色！');
		 return ;
	 }
     submitData["department.id"]=departmentId;
     submitData["roleIds"]=roleIds;
	  $('#addModal').modal('hide');

	$.ajax( {
		url :'baseframework/security/user/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
		
			if(result==0){
				showAlertModal('注意','操作失败，用户名已经在系统中存在，请重新填写！');
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
	if(submitData["password"]!=submitData["confirmPassword"]){
		showAlertModal('注意', '确认密码不能为空！');
    	return;
    }
	 var roleIds=splitCheckBoxValue('edit_role_chk');
	 var deleteRoleIds=splitUnCheckBoxValue('edit_role_chk');
	 var departmentId=$("#edit_departmentId").val();
	 if(departmentId==""){
		 showAlertModal('注意','部门不能为空！');
		 return ;
	 }
    if(roleIds==""){
    	showAlertModal('注意','请点选角色！');
		 return ;
	 }
     submitData["department.id"]=departmentId;
     submitData["roleIds"]=roleIds;
     submitData["deleteRoleIds"]=deleteRoleIds;
	$('#editModal').modal('hide');

	$.ajax( {
		url :'baseframework/security/user/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
            if(result>0){
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

   	function showMenu() {
		$("#menuContent").slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	
	function hideEditMenu() {
		$("#edit_menuContent").fadeOut("fast");
		$("body").unbind("mousedown", editOnBodyDown);
	}
	function editOnBodyDown(event) {
		if (!(event.target.id == "edit_menuContent" || $(event.target).parents("#edit_menuContent").length>0)) {
			hideEditMenu();
		}
	}
function initTree(){
	$("#menuContent").hide();
	$("#department").click(function(){
		showMenu();
	});
	$("#edit_menuContent").hide();
	$("#edit_department").click(function(){
		$("#edit_menuContent").slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	});
	
	$.ajax( {
		url :'baseframework/org/department/getZTreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: '',
		success : function(result) {
			initAddTree(result);
			initEditTree(result);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}


function initAddTree(data){
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
				//var check = (treeNode && !treeNode.isParent);
				//if (!check) alert("只能选择最小类别");
				$("#menuContent").fadeOut("fast");
				$("#departmentId").val(treeNode.id);
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				nodes = zTree.getSelectedNodes(),
				v = "";
				nodes.sort(function compare(a,b){return a.id-b.id;});
				for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[i].name + ",";
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				var cityObj = $("#department");
				cityObj.attr("value", v);
			}
		}
	},data);
}

function initEditTree(data){
	$.fn.zTree.init($("#edit_tree"),{
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
				//var check = (treeNode && !treeNode.isParent);
				//if (!check) alert("只能选择最小类别");
				$("#edit_menuContent").fadeOut("fast");
				$("#edit_departmentId").val(treeNode.id);
				return true;
			},
			onClick: function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("edit_tree"),
				nodes = zTree.getSelectedNodes(),
				v = "";
				nodes.sort(function compare(a,b){return a.id-b.id;});
				for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[i].name + ",";
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				var cityObj = $("#edit_department");
			    cityObj.prop("value", v);//bootstrap的原因只能用prop方法了
			}
		}
	},data);
}