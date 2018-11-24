 	$(document).ready( function() {
		initFileTree();
		initEditor();
		$("#btn_upload").click(function(){
			$("#btnc04 input[type='file']").each(function(){
				if($(this).hasClass("ke-upload-file")){
					$(this).click();
				}
			});
		});
   	  $("#btnc04").hide();
	 
	});
  
  
	function initFileTree(){
		var rootDir=$("#rootDir").val();
		$("#currentDir").val(rootDir);//设置当前文件夹路径
		$("#currentDirAlert").text("当前目录："+rootDir);
		$('#fileTree').fileTree({root: rootDir,script: 'cms/file/list.htm'}, function(file,elementType,text) { 
			fileTreeCallBack(file,elementType,text);
		});
	}
	
	String.prototype.endWith=function(str){    
		  var reg=new RegExp(str+"$");    
		  return reg.test(this);       
		};
	
	function fileTreeCallBack(file,elementType,text){
		file=file.trim();
		if(elementType=="folder"){
			$("#currentDirAlert").text("当前目录："+file);
			$("#currentDir").val(file);//设置当前文件夹路径
			$(".ke-inline-block").each(function(){//设置当前文件夹路径让上传文件使用
					$(this).find("input[type='hidden']").eq(0).val(file);
			});
		}else if(elementType=="file"){
			if(file.endWith(".zip")){
				window.location.href=file.substring(1,file.length);
			}else if(isImage(file)){
				window.open(file.substring(1,file.length));
			}
		
		}
		if(elementType=="opreateButton"&&text=="删除"){
			deleteConfrim(file);
		}else if(elementType=="opreateButton"&&text=="压缩"){
			zipConfrim(file);
		}else if(elementType=="opreateButton"&&text=="解压"){
		    unZipConfrim(file);
		}else if(elementType=="opreateButton"&&text=="编辑"){
			if(file.endWith(".html")){
				templateEdit(file);
			}else{
				resourcesEdit(file);
			}
			
		}else if(elementType=="opreateButton"&&text=="显示链接"){
			showUrlModel(file);
		}
		
	}
	
	function addFolder(){
		var submitData=getParams('addFolderModal');
		if(submitData["forderName"]==""){
			showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
		$('#addFolderModal').modal('hide');
		$.ajax( {
			url :'cms/file/folder/add.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   showAlertModal('注意',result.message);
				   if(result.status==2){
					   initFileTree();
				   }
		     },error: function(XMLHttpRequest, textStatus, errorThrown){
		  	       showAlertModal('注意','系统异常！'+errorThrown);
	     }
		});
	}
	
	function addFile(){
		var submitData=getParams('addFileModal');
		if(submitData["fileName"]==""){
			showAlertModal('注意', '名称不能为空！');
	    	return;
	    }
		$('#addFileModal').modal('hide');
		$.ajax( {
			url :'cms/file/add.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				   showAlertModal('注意',result.message);
				   if(result.status==3){
					   initFileTree();
				   }
		     },error: function(XMLHttpRequest, textStatus, errorThrown){
		  	       showAlertModal('注意','系统异常！'+errorThrown);
	     }
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
	
	function showAlertModal(title,content){
		$("#alertModal").find('h4').html(title);
		$("#alertModal").find('p').html(content);
		$("#alertModal").modal('show');
	}
	
	function showDeleteModal(title,content){
		$("#deleteModal").find('h4').html(title);
		$("#deleteModal").find('p').html(content);
		$("#deleteModal").modal('show');
	}
	function showAddFileModal(){
		$("#addFileModal input[name='currentDir']").val($("#currentDir").val());
		$("#addFileModal").modal('show');
	}
	
	
	var delFile;
	function deleteConfrim(deleteFile){
		delFile=deleteFile;
		if(deleteFile==""){
			showAlertModal('注意','请点击选择要删除的文件或目录！');
			return;
		}
		showDeleteModal("注意，确定要删除如下文件或目录吗？",deleteFile);
	}
	
	function initEditor(){
		KindEditor.ready(function(K) {
		initUploadBtn(K,'file');
		});
	}

	function initUploadBtn(K,type){
		var uploadbutton = K.uploadbutton({
			button : K('#btn_upload_'+type)[0],
			fieldName : 'file',
			extraParams:{'currentDir':$("#currentDir").val()},
			url : 'cms/file/upload.htm?dir='+type,
			afterUpload : function(data) {
				showAlertModal('注意',data.message);
				if (data.status ===1) {
				   initFileTree();
				}
			},
			afterError : function(str) {
				showAlertModal('注意',str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			//uploadbutton["options"].extraParams={'currentDir':$("#currentDir").val()};
			uploadbutton.submit();
		});
	}
	
	
	function doDelete(){
	   var submitData={"file":delFile};
	   $('#deleteModal').modal('hide');
	   $.blockUI({ message:'正在执行操作，请稍等...' });
		$.ajax( {
			url :'cms/file/delete.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
				$.unblockUI();
				showAlertModal('注意',result.message);
				if(result.status==2){
					initFileTree();
				}
		 },error: function(XMLHttpRequest, textStatus, errorThrown){
		    	 $.unblockUI();
		    	 showAlertModal('注意','系统异常！'+errorThrown);
	     }
		});
	}
	
	function zipAll(){
		zipConfrim($("#rootDir").val());
	}
	function zipConfrim(zipSrcFile){
		$("#zipSrcFile").val(zipSrcFile);
		$("#zipModal").modal('show');
	}
	
	   function doZip(){
		    var submitData=getParams("zipModal");
		    submitData["destFile"]=$("#rootDir").val()+submitData["destFile"]+".zip";
		    $('#zipModal').modal('hide');
		    $.blockUI({ message:'正在执行操作，请稍等...' });
			$.ajax( {
				url :'cms/file/zip.htm',
				type : 'post',
				dataType:'json',
				data:submitData,
				success : function(result) {
				   $.unblockUI();
				   showAlertModal('注意',result.message);
				   if(result.status==2){
					   initFileTree();
				   }
			     },error: function(XMLHttpRequest, textStatus, errorThrown){
			    	 $.unblockUI();
			    	 showAlertModal('注意','系统异常！'+errorThrown);
		     }
			});
		}
	   
	    function unZipConfrim(zipSrcFile){
			$("#unZipSrcFile").val(zipSrcFile);
			$("#unZipModal").modal('show');
		}
	   
	   function doUnZip(){
		    var submitData=getParams("unZipModal");
		    submitData["destFile"]=$("#rootDir").val()+submitData["destFile"];
		    $('#unZipModal').modal('hide');
		    $.blockUI({ message:'正在执行操作，请稍等...' });
			$.ajax( {
				url :'cms/file/unZip.htm',
				type : 'post',
				dataType:'json',
				data:submitData,
				success : function(result) {
				   $.unblockUI();
				   showAlertModal('注意',result.message);
				   if(result.status==2){
					   initFileTree();
				   }
			     },error: function(XMLHttpRequest, textStatus, errorThrown){
			    	 $.unblockUI();
			    	 showAlertModal('注意','系统异常！'+errorThrown);
		     }
			});
		}
	   
	   function appendParams(file){
		     var siteCode=$("#siteCode").val();
			 var module=$("#module").val();
			 var moduleCode=$("#moduleCode").val();
		 return file+'&siteCode='+siteCode+'&module='+module+'&moduleCode='+moduleCode;
	   }
	   function templateEdit(file){
		    file=encodeURI(file);
		    file=appendParams(file);
			window.parent.addTabs({id: "template_file_edit", title: "编辑模版文件", url: 'cms/file/edit.htm?file='+file, close: true});
	   }
	   
	   function resourcesEdit(file){
		   file=encodeURI(file);
		   file=appendParams(file);
			window.parent.addTabs({id: "resources_file_edit", title: "编辑资源文件", url: 'cms/file/edit.htm?file='+file, close: true});
	   }
	   
	   function showUrlModel(url){
		    $("#showUrlModal input[name='url']").val(url);
			$("#showUrlModal").modal('show');
	   }
	   
	   function isImage(file){
		   var imageTypes=["gif","jpg","jpeg","png","bmp","GIF","JPG","JPEG","PNG","BMP"];
		   for(var i=0;i<imageTypes.length;i++){
			   if(file.endWith(imageTypes[i])){
				   return true;
			   }
		   }
		   return false;
	   }
	   
	   