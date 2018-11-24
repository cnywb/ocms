$(document).ready(function(){
	 query();
	 $("#btn_save").click(function(){ saveMenu();});
	 $("#btn_delete").click(function(){ 
		 if(confirm("确定要删除所有菜单吗？")){
			 deleteMenu();
		 }
		 
	 });
});
var menu={};
 function query(){
		$.ajax( {
	 			url :'wechat/menu/queryQyMenu.htm',
	 			type : 'post',
	 			data: '',
	 			dataType:'json',
	 			success : function(result) {
	 		    	pharseResult(result);
	  	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	 	        	alert("网络或数据异常，操作失败！");
	 	       }
	 		});
 }
 
 
 function deleteMenu(){
	 $.ajax( {
 			url :'wechat/menu/deleteQyMenu.htm',
 			type : 'post',
 			data: '',
 			dataType:'json',
 			success : function(result) {
 				
 				if(result!=undefined&&result!=null){
 					if(result.errcode=="0"){
 						showAlertModal('注意','删除成功！');
 					}else{
 						showAlertModal('注意',result.errmsg);
 					}
 				}else{
 					showAlertModal('注意','系统错误，操作失败！');
 				}
  	        },error: function(XMLHttpRequest, textStatus, errorThrown){
 	        	
 	        	showAlertModal('注意','网络或数据异常，操作失败！');
 	       }
 		});
}
 
 
 function saveMenu(){
	 var manuCode=$("#menuCode").val();
	 
	 $.ajax( {
 			url :'wechat/menu/createQyMenu.htm',
 			type : 'post',
 			data: {'json':manuCode},
 			dataType:'json',
 			success : function(result) {
 				
 				if(result!=undefined&&result!=null){
 					if(result.errcode=="0"){
 						showAlertModal('注意','保存成功！');
 						
 					}else{
 						showAlertModal('注意',result.errmsg);
 					}
 				}else{
 					showAlertModal('注意','系统错误，操作失败！');
 				}
  	        },error: function(XMLHttpRequest, textStatus, errorThrown){
 	        	
 	        	showAlertModal('注意','网络或数据异常，操作失败！');
 	       }
 		});
}
 function pharseResult(result){
	    if(result["errcode"]!=undefined){
	    	if(result["errcode"]=='46003'){
	    		$("#menuCode").val("");
	    		showAlertModal('注意',"自定义菜单不存在，请创建！");
	    		$("#cbtn_0").html('<input type="button" class="btn btn-primary" value="添加菜单" onclick="createAddWin(0)" />');
	    		$("#cbtn_1").html('<input type="button" class="btn btn-primary" value="添加菜单" onclick="createAddWin(1)"/>');
	    		$("#cbtn_2").html('<input type="button" class="btn btn-primary" value="添加菜单" onclick="createAddWin(2)"/>');
	    	}else{
	    		showAlertModal('注意',result["errmsg"]);
	    	}
	    	return;
	    }
	    initMenu(result["menu"]);
 }
 
function initMenu(data){
    menu=data;
    $("#menuCode").val(JSON.stringify(menu,null,4));
	var btns=menu.button;
	if(btns.length==0){
		$("#cbtn_0").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(0)" />');
		$("#cbtn_1").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(1)"/>');
		$("#cbtn_2").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(2)"/>');
	}else if(btns.length==1){
		$("#cbtn_1").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(1)" />');
		$("#cbtn_2").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(2)" />');
	}else if(btns.length==2){
		$("#cbtn_2").html('<input type="button" class="btn btn-default col-md-4" value="添加菜单" onclick="createAddWin(2)" />');
	}
		for(var i=0;i<btns.length;i++){//遍历菜单
			var btn=btns[i];
			$("#btn_"+i).html(wrapButton(btn.name,i));
			var subBtns=btn.sub_button;
			if(subBtns.length<5){
				$("#cbtn_"+i).html('<input type="button" class="btn btn-success col-md-4" value="添加菜单项" onclick="createAddItemWin('+i+')" />');	
			}
			for(var k=0;k<subBtns.length;k++){//遍历菜单项
				var subBtn=subBtns[k];
				$("#btn_"+i+"_sub_btn_"+k).html(wrapSubButton(subBtn.name, i, k));
			}
		}
} 

 var menuIdx=0;
 function createAddWin(menuIndex){
	 clearParams('win_add');
	 menuIdx=menuIndex;
	 $("#win_add .modal-title").html("添加第"+(menuIndex+1)+"个菜单");
	 $("#win_add").modal("show");
}
 
 
 var addMenuIndex=0;
 function createAddItemWin(menuIndex){
	 clearParams('win_add_item');
	 addMenuIndex=menuIndex;
	 $("#win_add_item .modal-title").text("添加第"+(menuIndex+1)+"个菜单的菜单项");
	 $("#win_add_item").modal("show");
}
 
 
 
 var updateMenuIdx=0;
 function createUpdateWin(menuIndex){
	  clearParams('win_update');
	  setEditParams('win_update',menuIndex);
	 updateMenuIdx=menuIndex;
	 $("#win_update .modal-title").text("更新第"+(menuIndex+1)+"个菜单");
	 $("#win_update").modal("show");
}
 
 
 var updateMenuItem_menuIndex=0;
 var updateMenuItem_menuItemIndex=0;
 function createUpdateItemWin(menuIndex,menuItemIndex){
	  clearParams('win_update_item');
	  setEditItemParams('win_update_item',menuIndex,menuItemIndex);
	  updateMenuItem_menuIndex=menuIndex;
	  updateMenuItem_menuItemIndex=menuItemIndex;
	 $("#win_update_item .modal-title").text("更新第"+(menuIndex+1)+"个菜单的第"+(menuItemIndex+1)+"个菜单项");
	 $("#win_update_item").modal("show");
	
}
 
 
 /**
  * 更新菜单button
  */
 function updateMenu(){
		var btn=getAddParams('win_update');
		if(btn["type"]=="click"&&(btn["key"]==""||btn["key"]==undefined)){
			showAlertModal('注意','菜单类型为点击推事件时键不能为空！');
			return;
		}
		if(btn["type"]=="view"&&(btn["url"]==""||btn["url"]==undefined)){
			showAlertModal('注意','菜单类型为跳转URL时链接不能为空！');
			return;
		}
		var oldBtns=menu["button"];
		for(var i=0;i<oldBtns.length;i++){
			if(i==updateMenuIdx){
				var oldBtn=oldBtns[i];
				oldBtn["type"]=btn["type"];
				oldBtn["name"]=btn["name"];
				oldBtn["key"]=btn["key"];
				oldBtn["url"]=btn["url"];
				oldBtns[i]=oldBtn;
			}
		}
		menu["button"]=oldBtns;
		$("#menuCode").val(JSON.stringify(menu,null,4));
		$('#win_update').modal("hide");
		$("#btn_"+updateMenuIdx).html(wrapButton(btn["name"],updateMenuIdx));
	}
 
 /**
  * 更新菜单项目sub_button
  */
 function updateMenuItem(){
		var btn=getAddParams('win_update_item');
		if(btn["type"]=="click"&&(btn["key"]==""||btn["key"]==undefined)){
			showAlertModal('注意','菜单类型为点击推事件时键不能为空！');
			return;
		}
		if(btn["type"]=="view"&&(btn["url"]==""||btn["url"]==undefined)){
			showAlertModal('注意','菜单类型为跳转URL时链接不能为空！');
			return;
		}
		var oldBtns=menu["button"];
		for(var i=0;i<oldBtns.length;i++){
			if(i==updateMenuItem_menuIndex){
				var oldBtn=oldBtns[i];
				var oldBtnSubBtns=oldBtn["sub_button"];
				  for(var k=0;k<oldBtnSubBtns.length;k++){
					  
					  if(k==updateMenuItem_menuItemIndex){
						  var oldBtnSubBtn=oldBtnSubBtns[k];
						  oldBtnSubBtn["type"]=btn["type"];
						  oldBtnSubBtn["name"]=btn["name"];
						  oldBtnSubBtn["key"]=btn["key"];
						  oldBtnSubBtn["url"]=btn["url"];
						  oldBtnSubBtns[k]=oldBtnSubBtn;
						  oldBtn["sub_button"]=oldBtnSubBtns;//刷新sub_button
					  }
				  }
				oldBtns[i]=oldBtn;
			}
		}
		menu["button"]=oldBtns;//刷新button
		$("#menuCode").val(JSON.stringify(menu,null,4));
		$('#win_update_item').modal("hide");
		$("#btn_"+updateMenuItem_menuIndex+"_sub_btn_"+updateMenuItem_menuItemIndex).html(wrapSubButton(btn["name"],updateMenuItem_menuIndex,updateMenuItem_menuItemIndex));
	}
 function setEditParams(id,menuIndex){
	    var btn=getButton(menuIndex);
	    doSetParams(id, btn);
}
 function doSetParams(id,btn){
	 $("#"+id+" input[type='text']").each(function(){
			var attrName=$(this).attr("name");
			$(this).val(btn[attrName]);
		});
		$("#"+id+" select").each(function(){
			var attrName=$(this).attr("name");
			$(this).val(btn[attrName]);
		});
 }
 
 //设置菜单项初始数据
function setEditItemParams(id,menuIndex,menuItemIndex){
	    var btn=getSubButton(menuIndex, menuItemIndex);
	    doSetParams(id, btn);
}
function addMenuItem(){
	var btn=getAddParams('win_add_item');
	if(btn["type"]=="click"&&(btn["key"]==""||btn["key"]==undefined)){
		showAlertModal('注意','菜单类型为点击推事件时键不能为空！');
		return;
	}
	if(btn["type"]=="view"&&(btn["url"]==""||btn["url"]==undefined)){
		showAlertModal('注意','菜单类型为跳转URL时链接不能为空！');
		return;
	}
	
	var btns=menu["button"];
	var currentMenuItemIdx=0;
	var addNewItemFlag=false;
	for(var i=0;i<btns.length;i++){
		if(i==addMenuIndex){
			if(btns[i]["sub_button"]==undefined){
				var subbtns=new Array();
				subbtns.push(btn);
				btns[i]["sub_button"]=subbtns;
				addNewItemFlag=true;
			}else{
				if(btns[i]["sub_button"].length<5){//每个菜单项不能超过五个
					var subbtns=btns[i]["sub_button"];
					subbtns.push(btn);
					btns[i]["sub_button"]=subbtns;
					addNewItemFlag=true;
				}
				
			}
			currentMenuItemIdx=btns[i]["sub_button"].length-1;
		}
	}
	if(addNewItemFlag==true){
	menu["button"]=btns;
	$("#menuCode").val(JSON.stringify(menu,null,4));
	$('#win_add_item').modal("hide");
	$("#btn_"+addMenuIndex+"_sub_btn_"+currentMenuItemIdx).html(wrapSubButton(btn["name"], addMenuIndex, currentMenuItemIdx));
	}
}


function addMenu(){
	var btn=getAddParams('win_add');
	if(btn["type"]=="click"&&(btn["key"]==""||btn["key"]==undefined)){
		showAlertModal('注意','菜单类型为点击推事件时键不能为空！');
		return;
	}
	if(btn["type"]=="view"&&(btn["url"]==""||btn["url"]==undefined)){
		showAlertModal('注意','菜单类型为跳转URL时链接不能为空！');
		return;
	}
	if(menu["button"]==undefined){
		var btns=new Array();
		btns.push(btn);
		menu["button"]=btns;
	}else{
		var btns=menu["button"];
		btns.push(btn);
		menu["button"]=btns;
	}
	$("#menuCode").val(JSON.stringify(menu,null,4));
	$('#win_add').modal("hide");
	$("#btn_"+menuIdx).html(wrapButton(btn["name"],menuIdx));
	$("#cbtn_"+menuIdx).html('<input type="button" class="btn btn-success col-md-4" value="添加菜单项" onclick="createAddItemWin('+menuIdx+')" />');
}
function getAddParams(id){
	var submitData={};
	$("#"+id+" input[type='text']").each(function(){
        if($(this).val()!==""){
        	submitData[$(this).attr("name")]=$(this).val();
		}
	});
	$("#"+id+" select").each(function(){
		if($(this).val()!==""){
			submitData[$(this).attr("name")]=$(this).val();
		}
	});
	return submitData;
}

function clearParams(id){
	$("#"+id+" input[type='text']").each(function(){
        if($(this).val()!==""){
         $(this).val("");
		}
	});
}

//得到菜单button
function getButton(menuIndex){
	var btns=menu["button"];
	for(var i=0;i<btns.length;i++){
		if(i==menuIndex){
			return btns[i];
		}
	}
}
//得到菜单项sub_button元素
function getSubButton(menuIndex,menuItemIndex){
	var btns=menu["button"];
	for(var i=0;i<btns.length;i++){
		if(i==menuIndex){
			    var subbtns=btns[i]["sub_button"];
			    for(var k=0;k<subbtns.length;k++){
				    	if(k==menuItemIndex){
			    		return subbtns[k];
			    	}
			    }
		}
	}
	return null;
}


function deleteMenuButton(){
	var btns=menu["button"];
	btns=delButtonByIndex(btns,updateMenuIdx);
	menu["button"]=btns;
	resetMenu();
	initMenu(menu);
}

function deleteMenuSubButton(){
	var btns=menu["button"];
	for(var i=0;i<btns.length;i++){
		if(i==updateMenuItem_menuIndex){
			var btn=btns[i];
			var subBtns=btn["sub_button"];
			subBtns=delButtonByIndex(subBtns, updateMenuItem_menuItemIndex);
			btn["sub_button"]=subBtns;
			btns[i]=btn;
		}
	}
	menu["button"]=btns;
	resetMenu();
	initMenu(menu);
}
function wrapButton(name,menuIndex){
	return '<input type="button" class="btn btn-info col-md-4"   onclick="createUpdateWin('+menuIndex+')"   value="'+name+'"/>';
}

function wrapSubButton(name,menuIndex,menuItemIndex){
	return '<input type="button" class="btn btn-info col-md-4" onclick="createUpdateItemWin('+menuIndex+','+menuItemIndex+')" value="'+name+'" />';
}

function resetMenu(){
$("#nav-tab-1 .col-md-4").each(function(){
	$(this).html("");
});
$("#nav-tab-1 .col-md-4").each(function(){
	$(this).html("");
});
}

function delButtonByIndex(btns,btnIndex){
	var retVal=new Array();
	for(var i=0;i<btns.length;i++){
		if(i!=btnIndex){
			retVal.push(btns[i]);
		}
	}
	return retVal;
}

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}