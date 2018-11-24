$(document).ready(function(){
	$('[data-toggle="popover"]').popover();
	initTree();
	App.init();
});
function initTree(){
	$.ajax( {
		url :'baseframework/config/cluster/getZtreeJSON.htm',
		type : 'post',
		dataType:'json',
		data: '',
		success : function(result) {
			initZTree(result);
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
}

var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRemove: onRemove,
			onRename: onRename
		}
	};

var  className = "dark";
function initZTree(data){
$.fn.zTree.init($("#treeDemo"),setting,data);
$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
}

var updateTreeNode;
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	updateTreeNode=treeNode;
	if(treeNode.isParent==true){
		setEditParams('editModal', treeNode);
		$('#editModal').modal('show');
	}else{
		setEditParams('editWebAppInstanceModal', treeNode);
		$('#editWebAppInstanceModal').modal('show');
	}
	return false;//false表示不显示编辑状态
}
var removeTreeNode;
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	removeTreeNode=treeNode;
	if(confirm("确认删除 " + treeNode.name + "节点 吗？")){
		if(treeNode.isParent==true){
			doDelete(treeNode.id);
		}else{
			doDeleteWebAppInstance(treeNode.realId);
		}
		
	}
	return false;
}
function onRemove(e, treeId, treeNode) {
	//alert("remove finished");
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "":"dark");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	
}
/**
 * 是否显示删除按钮
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function showRemoveBtn(treeId, treeNode) {

	return true;
}
/**
 * 是否显示编辑按钮
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function showRenameBtn(treeId, treeNode) {
	return true;
}


var addParentTreeNode;
function addHoverDom(treeId, treeNode) {
	if(treeNode.isParent==false){
		return false;
	}
	
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加应用实例' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		addParentTreeNode=treeNode;
		 $("#serverNodeId").val(treeNode.id); 
		 $('#addWebAppInstanceModal').modal('show');
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

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
function save(){
	var submitData=getParams('addModal');
	 if(submitData["name"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	 if(submitData["ip"]==""){
			showAlertModal('注意','IP不能为空！');
		    return ;
	 }
	 $.ajax( {//提交数据
			url : 'baseframework/config/cluster/addServerNode.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==1){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#addModal').modal('hide');
			}else if(result==0){
		          showAlertModal('注意','操作失败，IP已在系统中存在！');
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}


function update(){
	var submitData=getParams('editModal');
	 if(submitData["name"]==""){
			showAlertModal('注意','名称不能为空！');
		    return ;
		 }
		 if(submitData["ip"]==""){
				showAlertModal('注意','IP不能为空！');
			    return ;
		 }
	 $.ajax( {//提交数据
			url : 'baseframework/config/cluster/updateServerNode.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   if(result==1){
				    	 initTree();
						 showAlertModal('注意','操作成功！');
						 $('#editModal').modal('hide');
					}else if(result==0){
				          showAlertModal('注意','操作失败，IP已在系统中存在！');
					}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function doDelete(id){
	$.ajax( {//提交数据
		url : 'baseframework/config/cluster/deleteServerNode.htm',
		type : 'post',
		dataType:'text',
		data:'id='+id,
		success : function(result) {
	    if(result==1){
	    	initTree();
			 showAlertModal('注意','删除成功！');
		}else if(result==0){
			showAlertModal('注意','操作失败,节点不存在！');
		}
       },error: function(XMLHttpRequest, textStatus, errorThrown){
    	   showAlertModal('注意',"网络异常！");
      }
	});
}

function setEditParams(containerId,obj){
	   clearEditParams(containerId);
		$("#"+containerId+" input[type='text']").each(function(){
			var attrName=$(this).attr("name");
			if(attrName=="name"){
				$(this).val(obj["name"]);
			}else if(attrName=="ip"){
				$(this).val(obj["ip"]);
			}else{
				$(this).val(obj[attrName]);
			}
		});
		 $("#"+containerId+" input[type='hidden']").each(function(){
			 var attrName=$(this).attr("name");
				if(attrName=="id"){
					$(this).val(obj["realId"]);
				}else if(attrName=="serverNode.id"){
					$(this).val(obj["parentId"]);
				}
				
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
function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function saveWebAppInstance(){
	var submitData=getParams('addWebAppInstanceModal');
	 if(submitData["name"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	 if(submitData["ftpPort"]==""){
			showAlertModal('注意','FTP端口不能为空！');
		    return ;
	 }
	 if(submitData["ftpPassword"]==""){
			showAlertModal('注意','FTP密码不能为空！');
		    return ;
	 }
	 $.ajax( {//提交数据
			url : 'baseframework/config/cluster/addWebAppInstance.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==1){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#addWebAppInstanceModal').modal('hide');
			}else if(result==0){
		          showAlertModal('注意','操作失败，实例名称已在系统中存在！');
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function updateWebAppInstance(){
	var submitData=getParams('editWebAppInstanceModal');
	 if(submitData["name"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	 if(submitData["ftpPort"]==""){
			showAlertModal('注意','FTP端口不能为空！');
		    return ;
	 }
	 if(submitData["ftpPassword"]==""){
			showAlertModal('注意','FTP密码不能为空！');
		    return ;
	 }
	 $.ajax( {//提交数据
			url : 'baseframework/config/cluster/updateWebAppInstance.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==1){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#editWebAppInstanceModal').modal('hide');
			}else if(result==0){
		          showAlertModal('注意','操作失败，实例名称已在系统中存在！');
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function doDeleteWebAppInstance(id){
	$.ajax( {//提交数据
		url : 'baseframework/config/cluster/deleteWebAppInstance.htm',
		type : 'post',
		dataType:'text',
		data:'id='+id,
		success : function(result) {
	    if(result==1){
	    	initTree();
			 showAlertModal('注意','删除成功！');
		}else if(result==0){
			showAlertModal('注意','操作失败,节点不存在！');
		}
       },error: function(XMLHttpRequest, textStatus, errorThrown){
    	   showAlertModal('注意',"网络异常！");
      }
	});
}