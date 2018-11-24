// jQuery File Tree Plugin
//
// Version 1.01
//
// Cory S.N. LaViska
// A Beautiful Site (http://abeautifulsite.net/)
// 24 March 2008
//
// Visit http://abeautifulsite.net/notebook.php?article=58 for more information
//
// Usage: $('.fileTreeDemo').fileTree( options, callback )
//
// Options:  root           - root folder to display; default = /
//           script         - location of the serverside AJAX file to use; default = jqueryFileTree.php
//           folderEvent    - event to trigger expand/collapse; default = click
//           expandSpeed    - default = 500 (ms); use -1 for no animation
//           collapseSpeed  - default = 500 (ms); use -1 for no animation
//           expandEasing   - easing function to use on expand (optional)
//           collapseEasing - easing function to use on collapse (optional)
//           multiFolder    - whether or not to limit the browser to one subfolder at a time
//           loadMessage    - Message to display while initial tree loads (can be HTML)
//
// History:
//
// 1.01 - updated to work with foreign characters in directory/file names (12 April 2008)
// 1.00 - released (24 March 2008)
//
// TERMS OF USE
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
if(jQuery) (function($){
	
	$.extend($.fn, {
		fileTree: function(o, h) {
			// Defaults
			if( !o ) var o = {};
			if( o.root == undefined ) o.root = '/';
			if( o.script == undefined ) o.script = 'jqueryFileTree.php';
			if( o.folderEvent == undefined ) o.folderEvent = 'click';
			if( o.expandSpeed == undefined ) o.expandSpeed= 500;
			if( o.collapseSpeed == undefined ) o.collapseSpeed= 500;
			if( o.expandEasing == undefined ) o.expandEasing = null;
			if( o.collapseEasing == undefined ) o.collapseEasing = null;
			if( o.multiFolder == undefined ) o.multiFolder = true;
			if( o.loadMessage == undefined ) o.loadMessage = 'Loading...';
			
			$(this).each( function() {
				
				function showTree(c, t) {
					
					$(c).addClass('wait');
					$(".jqueryFileTree.start").remove();
					$.post(o.script, { dir: t }, function(data) {
						$(c).find('.start').html('');
						var dataObject=JSON.parse(data);
					    if(dataObject.status==0){
							alert(dataObject.massage);return;
						}else{
							$(c).removeClass('wait').append(createAppendHtml(dataObject));
						}
						
						if( o.root == t ) $(c).find('UL:hidden').show(); else $(c).find('UL:hidden').slideDown({ duration: o.expandSpeed, easing: o.expandEasing });
						bindTree(c);
					});
				}
				
				function createAppendHtml(data){
					var retVal="<ul class=\"jqueryFileTree\" style=\"display: none;\">";
					var fileList=data.data;
					for(var i=0;i<fileList.length;i++){
						var file=fileList[i];
						if(file.directory==true){
							if(file.showButtons==false){
								retVal=retVal+createFileFolerLi(file.ext,file.filePath,file.fileName);
							}else{
								retVal=retVal+createButtonFileFolerLi(file.ext,file.filePath,file.fileName);
							}
						}else{
							if(file.showButtons==false){
								retVal=retVal+createFileLi(file.ext,file.filePath,file.fileName);
							}else{
								retVal=retVal+createButtonFileLi(file.ext,file.filePath,file.fileName);
							}
						}
						
					}
					retVal=retVal+"</ul>";
					return retVal;
				}
				
				function createFileLi(ext,rel,file){
					return "<li class=\"file ext_" + ext + "\"><a class=\"fileName\" href=\"javascript:void(0);\" rel=\"" + rel + "\">"+file+"</a></li>";
				}
				
				function createButtonFileLi(ext,rel,file){
					return "<li class=\"file ext_" + ext + "\"><a class=\"fileName\" href=\"javascript:void(0);\" rel=\"" + rel + "\">"+ file + "</a>"+createButton(rel, "删除","red")+createButton(rel, "显示链接","black")+createFileZipButton(rel, "压缩","black")+createUnzipButton(rel, "解压","black")+createEditButton(rel, "编辑","black")+"</li>";
				}
				function createFileFolerLi(ext,rel,file){
					return "<li class=\"directory collapsed\"><a class=\"folderName\" href=\"javascript:void(0);\" rel=\"" + rel+ "/\">"+ file + "</a></li>";
				}
				
				function createButtonFileFolerLi(ext,rel,file){
					return "<li class=\"directory collapsed\"><a class=\"folderName\" href=\"javascript:void(0);\" rel=\"" + rel+ "/\">"+ file + "</a>"+createButton(rel, "删除","red")+createFolderZipButton(rel, "压缩","black")+"</li>";
				}
				
				function createButton(rel,text,color){
					return "<a href=\"javascript:void(0);\" class=\"opreateButton\" rel=\"" + rel+ " \" style=\"color:"+color+" \">"+text+"</a>";
				}
				
				function createEditButton(rel,text,color){
					if(rel.endsWith(".js")||rel.endsWith(".css")||rel.endsWith(".html")){
						return createButton(rel, text, color);
					}
			        return "";
				}
				
				function  createFolderZipButton(rel,text,color){
					if(!rel.endsWith("/")){
						rel=rel+"/";
					}
					return createButton(rel, text, color);
			  	}
				
				function createFileZipButton(rel,text,color){
					if(rel.endsWith(".zip")){
						return "";
					}
					return createButton(rel, text, color);
			  	}
				
				function createUnzipButton(rel,text,color){
					if(rel.endsWith(".zip")){
						return createButton(rel, text, color);
					}
			        return "";
				}
			   
				
				
				function bindTree(t) {
					$(t).find('LI A').bind(o.folderEvent, function() {
						if( $(this).hasClass('folderName') ) {
					//	if( $(this).parent().hasClass('directory') ) {	
							if( $(this).parent().hasClass('collapsed') ) {
								// Expand
								if( !o.multiFolder ) {
									$(this).parent().parent().find('UL').slideUp({ duration: o.collapseSpeed, easing: o.collapseEasing });
									$(this).parent().parent().find('LI.directory').removeClass('expanded').addClass('collapsed');
								}
								$(this).parent().find('UL').remove(); // cleanup
								showTree( $(this).parent(), escape($(this).attr('rel').match( /.*\// )) );
								$(this).parent().removeClass('collapsed').addClass('expanded');
							} else {
								// Collapse
								$(this).parent().find('UL').slideUp({ duration: o.collapseSpeed, easing: o.collapseEasing });
								$(this).parent().removeClass('expanded').addClass('collapsed');
							}
							
						}
						if( $(this).hasClass('folderName') ) {
							h($(this).attr('rel'),'folder',$(this).text());
						}else if( $(this).hasClass('fileName') ) {
							h($(this).attr('rel'),'file',$(this).text());
						}else if( $(this).hasClass('opreateButton') ) {
							h($(this).attr('rel'),'opreateButton',$(this).text());
						}
						
						return false;
					});
					// Prevent A from triggering the # on non-click events
					if( o.folderEvent.toLowerCase != 'click' ) $(t).find('LI A').bind('click', function() { return false; });
				}
				// Loading message
				$(this).html('<ul class="jqueryFileTree start"><li class="wait">' + o.loadMessage + '<li></ul>');
				// Get the initial file list
				showTree( $(this), escape(o.root) );
			});
		}
	});
	
})(jQuery);