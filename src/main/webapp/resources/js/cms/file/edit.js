var editor;
$(document).ready(function(){
	editor.setOption("theme", "blackboard");
	$("#selectTheme").val("blackboard");
});
editor=CodeMirror.fromTextArea(document.getElementById("code"), {
	   mode:$("#mode").val(),  
	   extraKeys: {"Alt-/": "autocomplete",
	          "F11": function(cm) {
	            cm.setOption("fullScreen", !cm.getOption("fullScreen"));
	          },
	          "Esc": function(cm) {
	            if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
	          }},
	  // mode: {name: "javascript", globalVars: true},
 lineNumbers: true,
 theme:"blackboard",
 styleActiveLine: true,
 matchBrackets: true
});

  var input = document.getElementById("selectTheme");
  function selectTheme() {
    var theme = input.options[input.selectedIndex].textContent;
    editor.setOption("theme", theme);
    location.hash = "#" + theme;
  }
  var choice = (location.hash && location.hash.slice(1)) ||(document.location.search && decodeURIComponent(document.location.search.slice(1)));
  if (choice) {
    input.value = choice;
    editor.setOption("theme", choice);
  }
  CodeMirror.on(window, "hashchange", function() {
	    var theme = location.hash.slice(1);
	    if (theme) { input.value = theme; selectTheme(); }
	  });
  
  
  
  var insertFile=function(fileName){
	    var replaceLine=headLine();
	    var insertHtml="";
	    if(fileName.endWith(".js")){
	    	insertHtml='\t<script type="text/javascript" th:src="@{'+fileName+'}"></script>\n';
	    }else  if(fileName.endWith(".css")){
	    	insertHtml='\t<link rel="stylesheet" th:href="@{'+fileName+'}" />\n';
	    }else  if(isImage(fileName)==true){
	    	insertHtml='\t<img  th:src="@{'+fileName+'}" />\n';
	    	replaceLine=bodyLine();
	    }else if(fileName.endWith(".html")){
	    	if($("#file").val().endWith(fileName)){//如果文件是自身
	    		 $("#fileModal").modal("hide");
	    		 showAlertModal('注意','不可以引用自己！');return;
	    	}
	    	fileName=fileName.replace(/\/template\//g, "template/")
	    	insertHtml='\t<div th:replace="'+fileName+'"></div>\n';
	    	replaceLine=bodyLine();
	    }else{
	    	insertHtml='\t<span  th:text="@{'+fileName+'}" />\n';
	    	replaceLine=bodyLine();
	    }
	    editor.replaceRange(insertHtml, replaceLine);
	    $("#fileModal").modal("hide");
	   }
  var headLine=function() {
		var count = editor.lineCount();
		var head_line = {
			line: 0,
			ch: 0
		};
		for (var i = 0; i < count; i++) {
			var line = editor.getLine(i);
			if (isNotEmpty(line)) {
				var m = line.match(/<\/head>/ig);
				if (isNotEmpty(m) && m.length == 1) {
					head_line = {
						line: i,
						ch: 0
					};
				}
			}
		}
		return head_line;
	}
  var bodyLine=function() {
		var count = editor.lineCount();
		var bodyLine = {
			line: 0,
			ch: 0
		};
		for (var i = 0; i < count; i++) {
			var line = editor.getLine(i);
			if (isNotEmpty(line)) {
				var m = line.match(/<\/body>/ig);
				if (isNotEmpty(m) && m.length == 1) {
					bodyLine = {
						line: i,
						ch: 0
					};
				}
			}
		}
		return bodyLine;
	}
  
  function isNotEmpty(str){
	  return (str!=""&&str!=undefined)?true:false;
  }
  
  function save(){
	    var submitData={};
	    submitData["code"]=editor.getValue();
	    submitData["file"]=$("#file").val();
	    $.blockUI({ message:'正在执行操作，请稍等...' });
		$.ajax( {
			url :'cms/file/save.htm',
			type : 'post',
			dataType:'json',
			data:submitData,
			success : function(result) {
			   $.unblockUI();
			   showAlertModal('注意',result.message);
		   },error: function(XMLHttpRequest, textStatus, errorThrown){
		    	 $.unblockUI();
		    	 showAlertModal('注意','系统异常！'+errorThrown);
	       }
		});
  }
  
  function showAlertModal(title,content){
		$("#alertModal").find('h4').html(title);
		$("#alertModal").find('p').html(content);
		$("#alertModal").modal('show');
 }
  
  function initFileTree(rootDir){
		$('#fileTree').fileTree({root: rootDir,script: 'cms/file/list.htm?showButtons=false'}, function(file,elementType,text) { 
			fileTreeCallBack(file,elementType,text);
		});
	   $("#fileModal").modal("show");
  }
  String.prototype.endWith=function(str){    
	  var reg=new RegExp(str+"$");    
	  return reg.test(this);       
	};
  function fileTreeCallBack(file,elementType,text){
		file=file.trim();
		if(elementType=="file"){
			insertFile(file);
		}
   }
  
  function initPluginFileTree(){
	  initFileTree("/plugin");
  }
  
 function initResouresFileTree(){
	 var siteCode=$("#siteCode").val();
	 var module=$("#module").val();
	 var moduleCode=$("#moduleCode").val();
	 initFileTree("/resources/cms/site/"+siteCode+"/"+module+"/"+moduleCode); 
 }
 function initIncludeFileTree(){
	 var siteCode=$("#siteCode").val();
	 var module=$("#module").val();
	 var moduleCode=$("#moduleCode").val();
	 initFileTree("/template/cms/site/"+siteCode+"/include/"); 
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