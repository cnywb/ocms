$(document).ready(function(){
	$('[data-toggle="popover"]').popover();
	initTree();
	App.init();
});
function initTree(){
	$.ajax( {
		url :'baseframework/org/department/getZTreeJSON.htm',
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
	setEditParams('editModal', treeNode);
	$('#editModal').modal('show');
	return false;//false表示不显示编辑状态
}
var removeTreeNode;
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	removeTreeNode=treeNode;
	if(confirm("确认删除 " + treeNode.name + "节点 吗？")){
		doDelete(treeNode.id);
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
	if(treeNode.id==1){
		return false;
	}
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
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		addParentTreeNode=treeNode;
		 $("#parentId").val(treeNode.id); 
		 $('#addModal').modal('show');
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
	 if(submitData["nameZh"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	  if(submitData["parentId"]==""){
			 submitData["parentId"]=0;
	   }
	 $.ajax( {//提交数据
			url : 'baseframework/org/department/add.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result.status==1){
		    	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(addParentTreeNode,{id:result.id, pId:result.parentId, name:result.nameZh});
				 $('#addModal').modal('hide');
				 showAlertModal('注意','添加成功！');
			}else{
				showAlertModal('注意',result.message);
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}


function update(){
	var submitData=getParams('editModal');
	 if(submitData["nameZh"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
	  if(submitData["parentId"]==""){
			 submitData["parentId"]=0;
	   }
	 $.ajax( {//提交数据
			url : 'baseframework/org/department/update.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
		    if(result.status==1){
		    	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		    	updateTreeNode.name=result.nameZh;
		    	updateTreeNode.nameEn=result.nameEn;
		    	updateTreeNode.seqNum=result.seqNum;
				zTree.updateNode(updateTreeNode);
				 $('#editModal').modal('hide');
				 showAlertModal('注意','操作成功！');
			}else{
				showAlertModal('注意',result.message);
			}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function doDelete(id){
	$.ajax( {//提交数据
		url : 'baseframework/org/department/delete.htm',
		type : 'post',
		dataType:'text',
		data:'id='+id,
		success : function(result) {
	    if(result=="3"){
	    	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
             zTree.removeNode(removeTreeNode);
			 showAlertModal('注意','删除成功！');
		}else if(result=="2"){
			showAlertModal('注意','该部门已经被用户引用，无法删除！');
		}else if(result=="1"){
			showAlertModal('注意','该部门不存在！');
		}else if(result=="0"){
			showAlertModal('注意','该部门存在子部门，请先删除子部门再进行操作！');
		}else if(result=="1"){
			showAlertModal('注意','操作失败,不能删除根节点！');
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
			if(attrName=="nameZh"){
				$(this).val(obj["name"]);
			}else if(attrName=="nameEn"){
				$(this).val(obj["nameEn"]);
			}else if(attrName=="seqNum"){
				$(this).val(obj["seqNum"]);
			}
			
		});
		 $("#"+containerId+" input[type='hidden']").each(function(){
			 var attrName=$(this).attr("name");
				if(attrName=="id"){
					$(this).val(obj["id"]);
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