$(document).ready(function(){
	initTree();
	initTable();
	$("#btnAdd").click(function(){
	  $("#addModal").modal('show');
	});
	 handleJqueryTagIt();
	 $('#jquery-tagIt-primary').click(function(){
	    	$("#treeAdd_container").show();
	 });
	 handleJqueryTagItEdit();
	 $('#jquery-tagIt-primary-edit').click(function(){
	    	$("#treeEdit_container").show();
	 });
});


var handleJqueryTagIt = function() {
   $('#jquery-tagIt-primary').tagit({
        availableTags: []
    });
 };
 
 var handleJqueryTagItEdit = function() {
	   $('#jquery-tagIt-primary-edit').tagit({
	        availableTags: []
	    });
	 };
	 
var departmentTree=[];	 
function initTree(){
	$.ajax( {
		url :'wechat/qy/txl/department/query.htm',
		type : 'post',
		dataType:'json',
		success : function(result) {
			departmentTree=result;
			initZTree(result);
			initZTreeAdd(result);
			initZTreeEdit(result);
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
				    departmentId=treeNode.id;
				    refreshTable();
			}
		}
	},data);
	
	$.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
}

function initZTreeAdd(data){
	$.fn.zTree.init($("#treeAdd"),{
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
			    $('#jquery-tagIt-primary').tagit("createTag",treeNode.name,null,null,treeNode.id);
			    $("#treeAdd_container").hide();
			}
		}
	},data);
}

function initZTreeEdit(data){
	$.fn.zTree.init($("#treeEdit"),{
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
			    $('#jquery-tagIt-primary-edit').tagit("createTag",treeNode.name,null,null,treeNode.id);
			    $("#treeEdit_container").hide();
			}
		}
	},data);
	
}

var departmentId="";

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
    	 
    	 params["departmentId"]=departmentId;
    	 params["status"]=0;
    	  return params;
      },
      columns: [ {
          field: 'userid',
          title: 'ID'
      },{
          field: 'name',
          title: '名称'
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
	         '</a>  ',
	         '<a class="remove" href="javascript:void(0)" title="Remove">',
	         '<i class="glyphicon glyphicon-remove"></i>',
	         '</a>'
	     ].join('');
}

window.operateEvents = {
'click .edit': function (e, value, row, index) {
	   queryById(row.userid);
	 
   
},
'click .remove': function (e, value, row, index) {
	doDelete(row.userid);
}
};

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
	    $('#jquery-tagIt-primary-edit').tagit("removeAll");
	    var departments=obj["department"];
	    for(var i=0;i<departments.length;i++){
	    	var departmentId=departments[i];
	    	var departmentName=getDepartmentNameById(departments[i]);
	  	    $('#jquery-tagIt-primary-edit').tagit("createTag",departmentName,null,null,departmentId);
	    }
	   
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


function queryById(id){
	 $.ajax( {
			url :'wechat/qy/txl/user/queryById.htm',
			type : 'post',
			data:{"id":id},
			dataType:'json',
			success : function(result) {
				setEditParams("editModal",result);
				$('#editModal').modal('show');
 	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	
 	        	showAlertModal('注意','网络或数据异常，操作失败！');
	       }
		});
}
function save(){
	var submitData=getParams('addModal');
    submitData["department"]=getTagAdditionalValue('jquery-tagIt-primary');
	$('#addModal').modal('hide');
	$.ajax( {
		url :'wechat/qy/txl/user/create.htm',
		type : 'post',
		data:{"json":JSON.stringify(submitData)},
		dataType:'json',
		success : function(result) {
			    if(result.errcode==0){
			    	$('#addModal').modal('hide');
				    showAlertModal('注意','操作成功!');
				    refreshTable();
				}else{
					showAlertModal('注意',result.errmsg); 
				}
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}




function update(){
	var submitData=getParams('editModal');
    submitData["department"]=getTagAdditionalValue('jquery-tagIt-primary-edit');
	$('#addModal').modal('hide');
	$.ajax( {
		url :'wechat/qy/txl/user/update.htm',
		type : 'post',
		data:{"json":JSON.stringify(submitData)},
		dataType:'json',
		success : function(result) {
			    if(result.errcode==0){
			    	$('#editModal').modal('hide');
				    showAlertModal('注意','操作成功!');
				    refreshTable();
				}else{
					showAlertModal('注意',result.errmsg); 
				}
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	 showAlertModal('注意','系统异常！'+errorThrown);
     }
	});
}

function doDelete(id){
	if(!confirm("确认删除"+id+"吗？")){
		return;
	}
	$.ajax( {//提交数据
		url : 'wechat/qy/txl/user/delete.htm',
		type : 'post',
		dataType:'json',
		data:'id='+id,
		success : function(result) {
			if(result.errcode==0){
				refreshTable();
			}else{
				showAlertModal('注意',result.errmsg);
			}
       },error: function(XMLHttpRequest, textStatus, errorThrown){
    	   showAlertModal('注意',"网络异常！");
      }
	});
}


function getTagAdditionalValue(contianerId){
	var retVal=new Array();
	$("#"+contianerId+" .tagit-label").each(function(){
		retVal.push($(this).attr("additionalValue"));
	});
	return retVal;
}
function getDepartmentNameById(id){
	for(var i=0;i<departmentTree.length;i++){
		if(departmentTree[i].id==id){
			return departmentTree[i].name;
		}
	}
	return id;
}
