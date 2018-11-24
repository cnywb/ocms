$(document).ready(function(){
 findChannelBySite();//
});
function findChannelBySite(){
$.ajax( {
		url :'cms/channel/findChannelBySite.htm',
		type : 'post',
		dataType:'json',
		data:'',
		success : function(result) {
			var root= getRootChannel(result);
			if(root!=null){
				var t=getByParentId(result,root.id);
				   $("#grid_container .nav").html(t);
			       $("#selfDefComponent .view .nav").html(t);
			       $('#selfDefComponent .dropdown').hover(function(){
                     $(this).addClass("open");
                   },function(){
                	  $(this).removeClass("open");
                   });
			}
	  },error:function(){
		  alert('注意，网络异常！');
     }
	});
}

function getRootChannel(list){
	for(var i=0;i<list.length;i++){
	   if(list[i].parentId==0){
		   return list[i];
	   }
	}
	return null;
}

function getChildrenQty(list,parentId){
	var qty=0;
	for(var i=0;i<list.length;i++){
		   if(list[i].parentId==parentId){
			   qty=qty+1;
		   }
	}
	return qty;
}

function getByParentId(list,parentId){
	var html='';
	for(var i=0;i<list.length;i++){
		if(list[i].parentId==parentId){
			  var childrenQty=getChildrenQty(list,list[i].id);
			  if( childrenQty>0){
			       html=html+'<li class="dropdown"> <a data-toggle="dropdown" class="dropdown-toggle" href="'+list[i].pageUrl+'">'+list[i].nameZh+'<strong class="caret"></strong></a> ';
			  }else{
				   html=html+'<li > <a  href="'+list[i].pageUrl+'">'+list[i].nameZh+'</a> ';
						  
			  }
			 if( childrenQty>0){
				 html=html+'<ul class="dropdown-menu">';
				 html=html+ getByParentId(list,list[i].id);
				 html=html+'</ul>'; 
			 }
			 html=html+'</li>';
		}
	}
	return html;
}
            
            