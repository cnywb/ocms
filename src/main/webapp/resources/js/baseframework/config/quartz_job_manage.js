$(document).ready(function(){
	$('[data-toggle="popover"]').popover();
	initTree();
	App.init();
});
function initTree(){
	$.ajax( {
		url :'baseframework/config/job/getZtreeJSON.htm',
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
		setEditParams('editTriggerModal', treeNode);
		$('#editTriggerModal').modal('show');
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
			doDelete(treeNode.realId);
		}else{
			doDeleteTrigger(treeNode.realId);
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
		+ "' title='添加触发器' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		addParentTreeNode=treeNode;
		 $("#jobId").val(treeNode.realId); 
		 $('#addTriggerModal').modal('show');
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
	 if(submitData["group"]==""){
			showAlertModal('注意','组不能为空！');
		    return ;
	 }
	 $.ajax( {//提交数据
			url : 'baseframework/config/job/add.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==4){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#addModal').modal('hide');
			}else if(result==3){
		          showAlertModal('注意','操作失败，名称已在系统中存在！');
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
		 if(submitData["group"]==""){
				showAlertModal('注意','组不能为空！');
			    return ;
		 }
		 $.ajax( {//提交数据
				url : 'baseframework/config/job/update.htm',
				type : 'post',
				dataType:'json',
				data:submitData,
				success : function(result) {
			    if(result==4){
			    	 initTree();
					 showAlertModal('注意','操作成功！');
					 $('#addModal').modal('hide');
				}else if(result==3){
			          showAlertModal('注意','操作失败，名称已在系统中存在！');
				}
		        },error: function(XMLHttpRequest, textStatus, errorThrown){
		        	showAlertModal('注意',"网络异常！");
		       }
			});
}

function doDelete(id){
	$.ajax( {//提交数据
		url : 'baseframework/config/job/delete.htm',
		type : 'post',
		dataType:'text',
		data:'id='+id,
		success : function(result) {
	    if(result==1){
	    	initTree();
			 showAlertModal('注意','删除成功！');
		}else if(result==0){
			showAlertModal('注意','操作失败,作业不存在！');
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
			$(this).val(obj[attrName]);
		});
		 $("#"+containerId+" input[type='hidden']").each(function(){
			 var attrName=$(this).attr("name");
				if(attrName=="id"){
					$(this).val(obj["realId"]);
				}else if(attrName=="job.id"){
					$(this).val(obj["parentId"]);
				}
				
		 });
		 $("#"+containerId+" select").each(function(){
				var attrName=$(this).attr("name");
				$(this).val(obj[attrName]+"");
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

function saveTrigger(){
	 var submitData=getParams('addTriggerModal');
	 if(submitData["name"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	
	 $.ajax( {//提交数据
			url : 'baseframework/config/job/addTrigger.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==4){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#addTriggerModal').modal('hide');
			}else if(result==3){
		          showAlertModal('注意','操作失败，触发器名称已在系统中存在！');
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function updateTrigger(){
	var submitData=getParams('editTriggerModal');
	 if(submitData["name"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	 
	 $.ajax( {//提交数据
			url : 'baseframework/config/job/updateTrigger.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result==4){
		    	 initTree();
				 showAlertModal('注意','操作成功！');
				 $('#editTriggerModal').modal('hide');
			}else if(result==3){
		          showAlertModal('注意','操作失败，触发器名称已在系统中存在！');
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function doDeleteTrigger(id){
	$.ajax( {//提交数据
		url : 'baseframework/config/job/deleteTrigger.htm',
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