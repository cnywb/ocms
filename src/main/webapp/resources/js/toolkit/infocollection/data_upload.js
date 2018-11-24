 	$(document).ready( function() {
		
		initEditor();
		$("#btn_upload").click(function(){
			$("#btnc04 input[type='file']").each(function(){
				if($(this).hasClass("ke-upload-file")){
					$(this).click();
				}
			});
		});
   	  $("#btnc04").hide();
   	  initFileTree();
	});
  
 	  function initFileTree(){
 			$('#fileTree').fileTree({root: "/resources/campaign/bpdata/",script: 'cms/file/list.htm?showButtons=false'}, function(file,elementType,text) { 
 				fileTreeCallBack(file,elementType,text);
 			});
 		  
 	}
 	String.prototype.endWith=function(str){    
 		  var reg=new RegExp(str+"$");    
 		  return reg.test(this);       
 		};
 	function fileTreeCallBack(file,elementType,text){
 		file=file.trim();
		if(elementType=="file"){
			if(file.endWith(".xls")){
				window.location.href=file.substring(1,file.length);
			}
		}
 	 }

	
	String.prototype.endWith=function(str){    
		  var reg=new RegExp(str+"$");    
		  return reg.test(this);       
		};
	
	
	
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
			url : 'toolkit/infocollection/data/upload.htm?dir='+type,
			afterUpload : function(data) {
				showAlertModal('注意',data.message);
				initFileTree();
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
