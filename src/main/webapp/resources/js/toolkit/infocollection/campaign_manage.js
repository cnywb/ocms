$(document).ready(function(){
    initTable();
    $(".form_datetime").datetimepicker({minView:1,language: 'zh-CN',format: 'yyyy-mm-dd hh:ii:ss',autoclose: true});
    $('[data-toggle="popover"]').popover();
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
		  
		  $("#btn_thumbnail_upload").click(function(){
				$("#btnc06 input[type='file']").each(function(){
					if($(this).hasClass("ke-upload-file")){
						$(this).click();
					}
				});
			});
			  $("#btnc06").hide();
			  $("#edit_btn_thumbnail_upload").click(function(){
					$("#btnc07 input[type='file']").each(function(){
						if($(this).hasClass("ke-upload-file")){
							$(this).click();
						}
					});
				});
				  $("#btnc07").hide();
}

function preview(){
	var code=$("#edit_code").val();
	$("#previewForm").attr("action","toolkit/infocollection/campaign/page/"+code+"/index.htm?preview=true");
	$("#previewForm").submit();
}
function initEditor(){
	KindEditor.ready(function(K) {
	initUploadBtn(K,'file','btn_upload_file','kv','imageSrcAdd');//新建活动KV上传按钮
	initUploadBtn(K,'file','edit_btn_upload_file','kv','imageSrcEdit');//编辑活动KV上传按钮
	initUploadBtn(K,'file','btn_thumbnail_upload_file','thumbnail','thumbnailSrcAdd');//新建活动缩略图上传按钮
	initUploadBtn(K,'file','edit_btn_thumbnail_upload_file','thumbnail','thumbnailSrcEdit');//编辑活动缩略图上传按钮
	initFileManage(K);//浏览按钮
	});
}

function initUploadBtn(K,type,id,model,inputId){
	var currentDir="/resources/campaign/"+model;
	var uploadbutton = K.uploadbutton({
		button : K('#'+id)[0],
		fieldName : 'file',
		extraParams:{'currentDir':currentDir},
		url : 'toolkit/infocollection/campaign/file/upload.htm?dir='+type,
		afterUpload : function(data) {
			alert(data.message);
			if (data.status ===1) {
			   $('#'+inputId).val(currentDir+"/"+data.data[0]);
			}
		},
		afterError : function(str) {
			alert(str);
		}
	});
	uploadbutton.fileBox.change(function(e) {
		uploadbutton.submit();
	});
}


function initFileManage(K){
	
	var editor = K.editor({
		fileManagerJson : 'toolkit/infocollection/campaign/file/listForKindEditor.htm'
	});
	K('#btn_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'kv',
				clickFn : function(url, title) {
					K('#imageSrcAdd').val(convertUrl(url));
					editor.hideDialog();
				}
			});
		});
	});
	K('#edit_btn_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'kv',
				clickFn : function(url, title) {
					K('#imageSrcEdit').val(convertUrl(url));
					editor.hideDialog();
				}
			});
		});
	});
	
	K('#btn_thumbnail_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'thumbnail',
				clickFn : function(url, title) {
					K('#thumbnailSrcAdd').val(convertUrl(url));
					editor.hideDialog();
				}
			});
		});
	});
	
	K('#edit_btn_thumbnail_file_manage').click(function() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'thumbnail',
				clickFn : function(url, title) {
					K('#thumbnailSrcEdit').val(convertUrl(url));
					editor.hideDialog();
				}
			});
		});
	});
}

function convertUrl(url){
	if(url.indexOf("http")!=-1){
		url="/"+url.replace(userPath,"");
	}else{
		url=url.replace(getWebContex(),"");
	}
	return url;
}

function getWebContex(){
	var  webroot=document.location.href;
    webroot=webroot.substring(webroot.indexOf('//')+2,webroot.length);
    webroot=webroot.substring(webroot.indexOf('/')+1,webroot.length);
    webroot=webroot.substring(0,webroot.indexOf('/'));
    rootpath="/"+webroot;
    return rootpath;
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
          params=getQueryParams("query_con",params);
          return params;
      },
      columns: [ {
          field: 'id',
          title: 'ID'
      },{
          field: 'name',
          title: '活动名称'
      },{
          field: 'code',
          title: '活动代码'
      },{
          field: 'typeName',
          title: '活动类型'
      }/*,{
          field: 'product',
          title: '活动推广产品'
      }*/,{
          field: 'dataSendFrequencyName',
          title: '数据发送频率'
      }/*,{
          field: 'dataReceiveEmail',
          title: '数据接收邮箱'
      }*/,{
          field: 'startTime',
          title: '活动开始时间'
      },{
          field: 'endTime',
          title: '活动结束时间'
      },{
          field: 'enableName',
          title: '是否启用'
      },{
          field: 'timeCheckName',
          title: '校验活动时间'
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
     return ['<a class="page" href="javascript:void(0)" title="edit">',
             '页面管理',
             '</a>  ',
             '<a class="edit" href="javascript:void(0)" title="edit">',
             '<i class="glyphicon glyphicon-edit"></i>',
             '</a>  ',
             '<a class="remove" href="javascript:void(0)" title="Remove">',
             '<i class="glyphicon glyphicon-remove"></i>',
             '</a>'
         ].join('');
}

window.operateEvents = {
        'click .page': function (e, value, row, index) {
            	pageManage(row.id,row.code);
        },
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
        $("#"+containerId+" textarea").each(function(){
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
    $("#"+containerId+" select").each(function(){
        submitData[$(this).attr("name")]=$(this).val();
    });
    $("#"+containerId+" input[type='hidden']").each(function(){
        submitData[$(this).attr("name")]=$(this).val();
    });
    $("#"+containerId+" textarea").each(function(){
        submitData[$(this).attr("name")]=$(this).val();
    });
    return submitData;
}


function refreshTable(){
    $('#data_table').bootstrapTable("refresh");
}


function save(){
       var submitData=getParams('addModal');
        if(submitData["startTime"]==""){
            showAlertModal('注意', '活动开始时间不能为空！');
            return;
        }
        if(submitData["endTime"]==""){
            showAlertModal('注意', '活动结束时间不能为空！');
            return;
        }
   $('#addModal').modal('hide');
   $.ajax( {
        url :'toolkit/infocollection/campaign/add.htm',
        type : 'post',
        dataType:'json',
        data:submitData,
        success : function(result) {
               showAlertModal('注意',result.message);
               if(result.status==5){
                   refreshTable();
               }
         },error:function(XMLHttpRequest, textStatus, errorThrown){
                 showAlertModal('注意','请求错误！'+textStatus);
     }
    });
}

function update(){
       var submitData=getParams('editModal');
       if(submitData["startTime"]==""){
            showAlertModal('注意', '活动开始时间不能为空！');
            return;
        }
        if(submitData["endTime"]==""){
            showAlertModal('注意', '活动结束时间不能为空！');
            return;
        }
    $('#editModal').modal('hide');
    $.ajax( {
        url :'toolkit/infocollection/campaign/update.htm',
        type : 'post',
        dataType:'json',
        data:submitData,
        success : function(result) {
               showAlertModal('注意',result.message);
               if(result.status==5){
                   refreshTable();
               }
         },error:function(XMLHttpRequest, textStatus, errorThrown){
                 showAlertModal('注意','请求错误！'+textStatus);
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
            url :'toolkit/infocollection/campaign/delete.htm',
            type : 'post',
            dataType:'json',
            data:submitData,
            success : function(result) {
                   showAlertModal('注意',result.message);
                   if(result.status==2){
                       refreshTable();
                   }
             },error:function(XMLHttpRequest, textStatus, errorThrown){
                     showAlertModal('注意','请求错误！'+textStatus);
         }
        });
}


function showAlertModal(title,content){
    $("#alertModal").find('h4').html(title);
    $("#alertModal").find('p').html(content);
    $("#alertModal").modal('show');
}

function getQueryParams(containerId,submitData){
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

function pageManage(campaignId,campaignCode){
     window.parent.addTabs({id: "campaign_page_manage", title: "活动页面管理", url: 'toolkit/infocollection/campaign/page/manage.htm?campaignId='+campaignId+'&campaignCode='+campaignCode, close: true});
}