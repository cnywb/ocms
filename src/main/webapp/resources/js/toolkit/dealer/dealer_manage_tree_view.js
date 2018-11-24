$(document).ready(function(){
	initTree();
	initSelect2();
});



var $citySelect;
var $provinceSelect;
var $bigAreaSelect;
function initSelect2(){
	$.fn.modal.Constructor.prototype.enforceFocus=function(){};//此行代码解决select2在模态窗口无法输入的bug
	$provinceSelect=$("#provinceSelect").select2({
		  placeholder: "选择省",
		  allowClear: true
		});
	$bigAreaSelect=$("#bigAreaSelect").select2({
		  placeholder: "选择大区",
		  allowClear: true
		});
	$citySelect=$("#editDealerModal .selectpicker").select2({
		  placeholder: "选择市",
		  allowClear: true
		});
	
   $(".select2-container--default").css({"width":"100%"});
}


function initTree(){
	$.blockUI({ message:'正在加载数据，请稍等...' });
	$.ajax( {
		url :'toolkit/dealer/getZtreeList.htm',
		type : 'post',
		dataType:'json',
		data: '',
		success : function(result) {
			$.unblockUI();
			initZTree(result);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    		$.unblockUI();
      	alert('注意,系统错误！');
     }
	});
}

function searchNode() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var keyword=$("#keyword").val();
    var allNodes=zTree.getNodesByParamFuzzy("name","");
    updateNodes(false,zTree,allNodes,false);//复位所有样式
	var nodeList = zTree.getNodesByParamFuzzy("name", keyword);
    if(keyword!=""){
      updateNodes(true,zTree,nodeList,true);//高亮查找结果
    }
}
function updateNodes(highlight,zTree,nodeList,expandNode) {
	    for( var i=0, l=nodeList.length; i<l; i++) {
		nodeList[i].highlight = highlight;
		zTree.updateNode(nodeList[i]);
		if(expandNode==true){
			zTree.expandNode(nodeList[i].getParentNode(), true, true, true);
		}
	}
}
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			fontCss: getFontCss
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
//$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
}

var updateTreeNode;
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	updateTreeNode=treeNode;
	if(treeNode.id.indexOf("A_")!=-1){
		$("#provinceSelectRow").hide();
		$("#bigAreaSelectRow").hide();
		showEditModal(treeNode,"A");
	}else if(treeNode.id.indexOf("P_")!=-1){
		
		$("#provinceSelectRow").hide();
		$("#bigAreaSelectRow").show();
		showEditModal(treeNode,"P");
		$bigAreaSelect.val(treeNode["bigAreaId"]).trigger("change");
	}else if(treeNode.id.indexOf("C_")!=-1){
	   
	    $("#provinceSelectRow").show();
		$("#bigAreaSelectRow").hide();
		showEditModal(treeNode,"C");
		 $provinceSelect.val(treeNode["provinceId"]).trigger("change");
	}else{
	   
		showEditDealerModal(treeNode,"D");
		$citySelect.val(treeNode["cityId"]).trigger("change");
	}
	return false;//false表示不显示编辑状态
}
var editType="A";
function showEditModal(treeNode,type){
    editType=type;
	setEditParams('editModal', treeNode);
	$('#editModal').modal('show');
}

function showEditDealerModal(treeNode,type){
    editType=type;
	setEditParams('editDealerModal', treeNode);
	$('#editDealerModal').modal('show');
}

var removeTreeNode;
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "":"dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	removeTreeNode=treeNode;
	if(confirm("确认删除 " + treeNode.name + "节点 吗？")){
		doDelete(treeNode.realId,treeNode.id);
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
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId+ "' title='' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		    addParentTreeNode=treeNode;
		 
		    if(treeNode.id.indexOf("A_")!=-1){
				showAddModal(treeNode,"A");
			}else if(treeNode.id.indexOf("P_")!=-1){
				showAddModal(treeNode,"P");
			}else if(treeNode.id.indexOf("C_")!=-1){
				showAddDealerModal(treeNode,"D");
			}
		return false;
	});
};

function showAddModal(treeNode,type){
    editType=type;
	$('#addModal').modal('show');
}
function showAddDealerModal(treeNode,type){
    editType=type;
	$('#addDealerModal').modal('show');
}
function showAddBigAreaModal(){
    editType="BA";
	$('#addModal').modal('show');
}

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
    if(submitData["title"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
   
    if(isNaN(submitData["latitude"])){
		 showAlertModal('注意',"纬度必须为数字!");return;
	}
	if(isNaN(submitData["longitude"])){
		 showAlertModal('注意',"经度必须为数字!");return;
	}
	
     var url="";
	 if(editType=="A"){
		 url="toolkit/dealer/province/add.htm";
		 submitData["bigArea.id"]=addParentTreeNode.realId;
	 }else if(editType=="P"){
		 url="toolkit/dealer/city/add.htm";
		 submitData["province.id"]=addParentTreeNode.realId;
	 }else if(editType="BA"){
		 url="toolkit/dealer/bigarea/add.htm";
	 }
	 $('#addModal').modal('hide');
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	 $.ajax( {//提交数据
			url : url,
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				$.unblockUI();
		    if(result.status==1){
		    	initTree();
		    }
		    showAlertModal('注意',result.message);
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	$.unblockUI();
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}

function saveDealer(){
    var submitData=getParams('addDealerModal');
    if(submitData["dealerCode"]==""){
		showAlertModal('注意','代码不能为空！');
	    return ;
	 }
    if(submitData["dealerName"]==""){
		showAlertModal('注意','名称不能为空！');
	    return ;
	 }
    if(isNaN(submitData["dealerServiceCode"])){
		 showAlertModal('注意',"服务代码必须为数字!");return;
	}
    if(isNaN(submitData["latitude"])){
		 showAlertModal('注意',"纬度必须为数字!");return;
	}
	if(isNaN(submitData["longitude"])){
		 showAlertModal('注意',"经度必须为数字!");return;
	}
     var url="toolkit/dealer/add.htm";
     submitData["city.id"]=addParentTreeNode.realId;
	 $('#addDealerModal').modal('hide');
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	 $.ajax( {//提交数据
			url : url,
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				$.unblockUI();
		    if(result.status==1){
		    	initTree();
		    }
		    showAlertModal('注意',result.message);
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	$.unblockUI();
	        	showAlertModal('注意',"网络异常！");
	       }
		});
}


function update(){
	     var submitData=getParams('editModal');
	     if(submitData["title"]==""){
			showAlertModal('注意','名称不能为空！');
		    return ;
		 }
	     if(isNaN(submitData["latitude"])){
			 showAlertModal('注意',"纬度必须为数字!");return;
		}
		if(isNaN(submitData["longitude"])){
			 showAlertModal('注意',"经度必须为数字!");return;
		}
	     var url="";
		 if(editType=="A"){
			 delete submitData["province.id"];
			 delete submitData["bigArea.id"];
			 url="toolkit/dealer/bigarea/update.htm";
		 }else if(editType=="P"){
			 delete submitData["province.id"];
			 url="toolkit/dealer/province/update.htm";
		 }else if(editType=="C"){
			 delete submitData["bigArea.id"];
			 url="toolkit/dealer/city/update.htm";
		 }
		 $('#editModal').modal('hide');
		 $.blockUI({ message:'正在执行操作，请稍等...' });
		 $.ajax( {//提交数据
				url : url,
				type : 'post',
				dataType:'json',
				data:submitData,
				success : function(result) {
					$.unblockUI();
			    if(result.status==1){
			    	initTree();
			    }
			    showAlertModal('注意',result.message);
		        },error: function(XMLHttpRequest, textStatus, errorThrown){
		        	$.unblockUI();
		        	showAlertModal('注意',"网络异常！");
		       }
			});
}

function updateDealer(){
	     var submitData=getParams('editDealerModal');
	     if(submitData["dealerCode"]==""){
	 		showAlertModal('注意','代码不能为空！');
	 	    return ;
	 	 }
	     if(isNaN(submitData["dealerServiceCode"])){
			 showAlertModal('注意',"服务代码必须为数字!");return;
		 }
	     if(submitData["dealerName"]==""){
	 		showAlertModal('注意','名称不能为空！');
	 	    return ;
	 	 }
	     if(isNaN(submitData["latitude"])){
			 showAlertModal('注意',"纬度必须为数字!");return;
		}
		if(isNaN(submitData["longitude"])){
			 showAlertModal('注意',"经度必须为数字!");return;
		}
	     var url="toolkit/dealer/update.htm";
	     $('#editDealerModal').modal('hide');
	     $.blockUI({ message:'正在执行操作，请稍等...' });
		 $.ajax( {//提交数据
				url : url,
				type : 'post',
				dataType:'json',
				data:submitData,
				success : function(result) {
					$.unblockUI();
			    if(result.status==1){
			    	initTree();
			    }
			    showAlertModal('注意',result.message);
		        },error: function(XMLHttpRequest, textStatus, errorThrown){
		        	$.unblockUI();
		        	showAlertModal('注意',"网络异常！");
		       }
			});
}


function doDelete(realId,id){
	 var url="";
	 if(id.indexOf("A_")!=-1){
		 url="toolkit/dealer/bigarea/delete.htm";
	 }else if(id.indexOf("P_")!=-1){
		 url="toolkit/dealer/province/delete.htm";
	 }else if(id.indexOf("C_")!=-1){
		 url="toolkit/dealer/city/delete.htm";
	 }else if(id.indexOf("D_")!=-1){
		 url="toolkit/dealer/delete.htm";
	 }
	 $.blockUI({ message:'正在执行操作，请稍等...' });
	 $.ajax( {//提交数据
			url : url,
			type : 'post',
			dataType:'json',
			data:{"id":realId},
			success : function(result) {
				$.unblockUI();
		    if(result.status==1){
		    	initTree();
		    }
		    showAlertModal('注意',result.message);
		    $('#addModal').modal('hide');
	       },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	    $.unblockUI();
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
				}else if(attrName=="area.id"){
					$(this).val(obj["parentId"]);
				}
				
		 });
		 $("#"+containerId+" select").each(function(){
				var attrName=$(this).attr("name");
				if(attrName=="province.id"){
					$(this).val(obj["provinceId"]+"");
				}else if(attrName=="bigArea.id"){
					$(this).val(obj["bigAreaId"]+"");
				}else if(attrName=="city.id"){
					$(this).val(obj["cityId"]+"");
				}else{
					$(this).val(obj[attrName]+"");
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


function editModalSearchAddress(){
	var city=$("#editModal_title").val();
	var address=$("#editModal_title").val();
	createMap(city,address,"editModal_map","editModal_latitude","editModal_longitude");
}
function editDealerModalSearchAddress(){
	var city=$("#editDealerModal_fordBrandShopAddress").val();
	var address=$("#editDealerModal_fordBrandShopAddress").val();
	createMap(city,address,"editDealerModal_map","editDealerModal_latitude","editDealerModal_longitude");
}
function addModalSearchAddress(){
	var city=$("#addModal_title").val();
	var address=$("#addModal_title").val();
	createMap(city,address,"addModal_map","addModal_latitude","addModal_longitude");
}
function addDealerModalSearchAddress(){
	var city=$("#addDealerModal_title").val();
	var address=$("#addDealerModal_title").val();
	createMap(city,address,"addDealerModal_map","addDealerModal_latitude","addDealerModal_longitude");
}
function createMap(city,address,container,latitudeId,longitudeId){
	var map = new BMap.Map(container);
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(address, function(point){
		if (point) {
			map.centerAndZoom(point, 16);
			map.addOverlay(new BMap.Marker(point));
			$("#"+latitudeId).val(point.lat);
			$("#"+longitudeId).val(point.lng);
		}else{
			alert("您选择地址没有解析到结果!");
		}
	}, city);
}