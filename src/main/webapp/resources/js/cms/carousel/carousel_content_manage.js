$(document).ready(function(){
	initTable();
	initEditor();
	initEditorButton();
});

function initEditorButton(){
	$("#btn_upload").click(function(){
		$("#btnc04 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	  $("#btnc04").hide();
	   $("#edit_btn_upload").click(function(){
			$("#btnc05 input[type='file']").each(function(){
				if($(this).hasClass("ke-upload-file")){
					$(this).click();
				}
			});
		});
	$("#btnc05").hide();
}

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
        	 params["carouselId"]=$("#carouselId").val();
        	  return params;
          },
          columns: [ {
              field: 'id',
              title: 'ID'
          },{
              field: 'imageSrc',
              title: '图片路径',
              formatter:imageFormatter
          },{
              field: 'url',
              title: '链接'
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

function titleFormatter(value, row, index){
	return '<a target="_blank" href="content/'+row.channel.id+'/'+row.id+'.htm">'+row.title+'</a>';
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
		$("#"+containerId+" textarea").each(function(){
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
	$("#"+containerId+" textarea").each(function(){
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
	if(submitData["imageSrc"]==""){
	   showAlertModal('注意', '图片路径不能为空！');
 	   return;
    }
	submitData["carousel.id"]=$("#carouselId").val();
	$('#addModal').modal('hide');

	$.ajax( {
		url :'cms/carousel/content/add.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			if(result>0){
				showAlertModal('注意','操作成功！');
				$('#data_table').bootstrapTable("refresh");
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

function update(){
	  var submitData=getParams('editModal');
	  if(submitData["imageSrc"]==""){
		   showAlertModal('注意', '图片路径不能为空！');
	 	   return;
	    }
		submitData["carousel.id"]=$("#carouselId").val();
	  $('#editModal').modal('hide');

	$.ajax( {
		url :'cms/carousel/content/update.htm',
		type : 'post',
		dataType:'text',
		data:submitData,
		success : function(result) {
			
			if(result==0){
				showAlertModal('注意','操作失败，代码已经在系统中存在，请重新填写！');
			}else if(result>0){
				showAlertModal('注意','操作成功！');
				$('#data_table').bootstrapTable("refresh");
			}else{
				showAlertModal('注意','系统异常！');
			}
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
	    	
	    	 showAlertModal('注意','系统异常！'+errorThrown);
  }
	});
}


function doDelete(id){

	if(!confirm("确认删除该条数据吗？")){
		return;
	}
	
	var submitData={};
	submitData["id"]=id;
	$.ajax( {
			url :'cms/carousel/content/delete.htm',
			type : 'post',
			dataType:'text',
			data:submitData,
			success : function(result) {
				if(result==0){
					showAlertModal('注意','操作失败，对象不存在！');
				}else if(result>0){
					showAlertModal('注意','操作成功！');
					$('#data_table').bootstrapTable("refresh");
				}else{
					showAlertModal('注意','系统异常！');
				}
		     },error: function(XMLHttpRequest, textStatus, errorThrown){
		    	
		    	 showAlertModal('注意','系统异常！'+errorThrown);
	     }
		});
	}

function refreshTable(){
	$('#data_table').bootstrapTable("refresh");
}

var sessionId=getCookie("JSESSIONID");
function initEditor(){
	KindEditor.ready(function(K) {
	initUploadBtn(K,'file','btn_upload_file','carousel','imageSrcAdd');//新建活动KV上传按钮
	initUploadBtn(K,'file','edit_btn_upload_file','carousel','imageSrcEdit');//编辑活动KV上传按钮
	initFileManage(K);//浏览按钮
	});
}

function initUploadBtn(K,type,id,model,inputId){
	var currentDir="/resources/cms/"+model;
	var uploadbutton = K.uploadbutton({
		button : K('#'+id)[0],
		fieldName : 'file',
		extraParams:{'currentDir':currentDir},
		url : 'cms/file/upload.htm?dir='+type,
		afterUpload : function(data) {
			showAlertModal('注意',data.message);
			if (data.status ===1) {
			   $('#'+inputId).val(currentDir+"/"+data.data[0]);
			}
		},
		afterError : function(str) {
			
		}
	});
	uploadbutton.fileBox.change(function(e) {
		uploadbutton.submit();
	});
}


function initFileManage(K){
	
	var editor = K.editor({
		fileManagerJson : 'cms/file/listForKindEditor.htm'
	});
	K('#btn_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'carousel',
				clickFn : function(url, title) {
					K('#imageSrcAdd').val(url.replace(getWebContex(),""));
					editor.hideDialog();
				}
			});
		});
	});
	K('#edit_btn_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'carousel',
				clickFn : function(url, title) {
					K('#imageSrcEdit').val(url.replace(getWebContex(),""));
					editor.hideDialog();
				}
			});
		});
	});
	
}

function getWebContex(){
	var  webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    rootpath="/"+webroot;
    return rootpath;
}

function imageFormatter(value, row, index){
	return '<a href="javascript:void(0);" class="thumbnail"><img width="60" src="'+getWebContex()+row.imageSrc+'" alt=""></a>';
}