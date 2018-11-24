	$(document).ready(function() {
			initTree();
		});
		
		function initTree(){
			var submitData={};
			$.ajax( {
				url :'baseframework/security/resources/getUserMenuTreeJSON.htm',
				type : 'post',
				dataType:'json',
				data: submitData,
				success : function(result) {
					var menu=getByParentId(result,0);
					$("#sideBarMenu").append(menu).append('<li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li>');
					App.init();
			     },error: function(XMLHttpRequest, textStatus, errorThrown){
		      	alert('注意,系统错误！');
		     }
			});
		}
		
		function getChildrenQty(list,parentId){
			var qty=0;
			for(var i=0;i<list.length;i++){
				   if(list[i].pId==parentId){
					   qty=qty+1;
				   }
			}
			return qty;
		}

		function getByParentId(list,parentId){
			var html='';
			for(var i=0;i<list.length;i++){
				if(list[i].pId==parentId){
					  var childrenQty=getChildrenQty(list,list[i].id);
					  if( childrenQty>0){
					       html=html+'<li class="has-sub"><a href="javascript:;"> <b class="caret pull-right"></b>'+list[i].nameZh+'</a> ';
					  }else{
						   if(list[i].tabUrl!=""){
							   html=html+'<li><li><a href="javascript:;" onclick="doAddTab('+list[i].id+',\''+list[i].nameZh+'\',\''+list[i].tabUrl+'\');">'+list[i].nameZh+'</a>';
						   }else{
							   html=html+'<li><li><a href="javascript:;">'+list[i].nameZh+'</a>';
						   }
					  }
					 if( childrenQty>0){
						 html=html+'<ul class="sub-menu">';
						 html=html+ getByParentId(list,list[i].id);
						 html=html+'</ul>'; 
					 }
					 html=html+'</li>';
				}
			}
			
			return html;
	}
		
	function doAddTab(tabId,tabTitle,tabUrl){
	       addTabs({id:tabId, title: tabTitle, url: tabUrl, close: true});
    }