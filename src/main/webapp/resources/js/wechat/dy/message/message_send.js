$(document).ready(function(){
	initEditor();
	$("#delegate_btn_upload_image").click(function(){
		$("#btnc01 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	
	$("#delegate_btn_upload_image_2").click(function(){
		$("#btnc04 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	$("#delegate_btn_upload_voice").click(function(){
		$("#btnc02 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	$("#delegate_btn_upload_video").click(function(){
		$("#btnc03 input[type='file']").each(function(){
			if($(this).hasClass("ke-upload-file")){
				$(this).click();
			}
		});
	});
	
   $("#btnc01,#btnc02,#btnc03,#btnc04").hide();
     handleJqueryTagIt();
     initTree();
     $("#chooseDepartmentBtn").click(function(){
    	 $("#treeModal").modal("show");
     });
});

function initTree(){
	$.ajax( {
		url :'wechat/user/queryDyGroup.htm',
		type : 'post',
		dataType:'json',
		success : function(result) {
			var data=result.rows;
			for(var i=0;i<data.length;i++){
				data[i]["pId"]=-1;
			}
			initZTreeAdd(data);
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
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
			    
			}
		}
	},data);
}

function sendMessage(){
	 var submitData={};
	 var i=0;
	 $(".nav li").each(function(){
			i=i+1;
			if($(this).hasClass("active")){
				if(i==1){
					 submitData=getTextParams();
					 doSendMessage(submitData);
				}else if(i==2){
					 submitData=getNewsParams();
					 uploadDyNews(submitData);//上传图文消息
				}else if(i==3){
					 submitData=getImageParams();
					 doSendMessage(submitData);
				}else if(i==4){
					 uploadDyVideo();
				}else if(i==5){
					 submitData=getVoiceParams();
					 doSendMessage(submitData);
				}
			}
		});
		
	
}

var handleJqueryTagIt = function() {
	 $('#jquery-tagIt-primary').tagit({
	        availableTags: []
	    });
	 
};

function doSendMessage(submitData){
	 $.ajax( {
 			url :'wechat/message/sendDyMessage.htm',
 			type : 'post',
 			data:{"json":JSON.stringify(submitData)},
 			dataType:'json',
 			success : function(result) {
 				if(result.errcode==0){
 					showAlertModal('注意','发送成功!');
 				}else{
 					showAlertModal('注意',result.errmsg); 
 				}
  	        },error: function(XMLHttpRequest, textStatus, errorThrown){
 	        	showAlertModal('注意','网络或数据异常，操作失败！');
 	       }
 		});
}


//图文消息比较怪异的地方就是要先上传得到media_id然后再发送
function uploadDyNews(submitData){
	 $.ajax( {
			url :'wechat/media/uploadDyNews.htm',
			type : 'post',
			data:{"json":JSON.stringify(submitData)},
			dataType:'json',
			success : function(result) {
				if(result.errcode==0){
					var messageData=getNewsParams(result.media_id);
					doSendMessage(messageData);//实际发送
				}else{
					showAlertModal('注意',result.errmsg); 
				}
 	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意','网络或数据异常，操作失败！');
	       }
		});
}


function uploadDyVideo(){
	var submitData={};
	    submitData["media_id"]=$("#media_video_id").val();
	    submitData["title"]="";
	    submitData["description"]="";
	 $.ajax( {
			url :'wechat/media/uploadDyVideo.htm',
			type : 'post',
			data:{"json":JSON.stringify(submitData)},
			dataType:'json',
			success : function(result) {
				if(result.errcode==0){
					var messageData=getVideoParams(result.media_id);
					doSendMessage(messageData);//实际发送
				}else{
					showAlertModal('注意',result.errmsg); 
				}
	        },error: function(XMLHttpRequest, textStatus, errorThrown){
	        	showAlertModal('注意','网络或数据异常，操作失败！');
	       }
		});
}


function getUploadNewsParams(){
	var retVal={};
    var news={};
    var articles=new Array();
    var article0={};
    article0["thumb_media_id"]=$("#media_image_id_2").val();
    article0["author"]=$("#author").val();
    article0["title"]=$("#title").val();
    article0["content_source_url"]=$("#content_source_url").val();
    article0["content"]=$("#content").val();
    article0["show_cover_pic"]=$("#show_cover_pic").val();
    article0["digest"]=$("#digest").val();
    articles.push(article0);
    news["articles"]=articles;
  	return retVal;
}
var sessionId=getCookie("JSESSIONID");
function initEditor(){
	KindEditor.ready(function(K) {
		initUploadBtn(K,'btn_upload_image','media_image_id','image');
		initUploadBtn(K,'btn_upload_image_2','media_image_id_2','image');
		initUploadBtn(K,'btn_upload_voice','media_voice_id','voice');
		initUploadBtn(K,'btn_upload_video','media_video_id','video');
		
	});
	
	

}

function initUploadBtn(K,buttonId,hiddenId,type){
	var uploadbutton = K.uploadbutton({
		button : K('#'+buttonId)[0],
		fieldName : 'imgFile',
		url : 'wechat/media/uploadDyMedia.htm?dir='+type,
		afterUpload : function(data) {
			if (data.error === 0) {
			 var message=JSON.parse(data.message);
				if(message.errcode==undefined){
					$("#"+hiddenId).val(message.media_id);
					    showAlertModal('注意','上传成功！');
					}else{
						showAlertModal('注意',message.errmsg);
					}
			}else{
				showAlertModal('注意',data.message);
			}
		},
		afterError : function(str) {
			
		}
	});
	uploadbutton.fileBox.change(function(e) {
		uploadbutton.submit();
	});
}

function showAlertModal(title,content){
	$("#alertModal").find('h4').html(title);
	$("#alertModal").find('p').html(content);
	$("#alertModal").modal('show');
}

function getTagPartyList(){
	var retVal="";
	$("#jquery-tagIt-primary .tagit-label").each(function(){
		if($(this).attr("additionalValue")!="undefined"&&$(this).attr("additionalValue")!=""){
		   if(retVal==""){
			   retVal=retVal+$(this).attr("additionalValue");
		   }else{
			   retVal=retVal+"|"+$(this).attr("additionalValue");
		   }
		}
	});
	return retVal;
}

function getTagUserList(){
	var retVal="";
	$("#jquery-tagIt-primary .tagit-label").each(function(){
		if($(this).attr("additionalValue")=="undefined"||$(this).attr("additionalValue")==""){
		   if(retVal==""){
			   retVal=retVal+$(this).text();
		   }else{
			   retVal=retVal+"|"+$(this).text();
		   }
		}
	});
	return retVal;
}


function getFilter(){
	var filter={};
	var tagList=getTagPartyList();
	if(tagList==""){
		filter["is_to_all"]=true;
	}else{
		filter["is_to_all"]=false;
		filter["group_id"]=tagList;
	}
	return filter;
}

function getTextParams(){
	var retVal={};
	var text={};
	retVal["filter"]=getFilter();
	text["content"]=$("#msg_content").val();
	retVal["text"]=text;
	retVal["msgtype"]="text";
	return retVal;
}



function getImageParams(){
	var retVal={};
	retVal["filter"]=getFilter();
    retVal["msgtype"]="image";
    var media={};
    media["media_id"]=$("#media_image_id").val();
    retVal["image"]=media;
	return retVal;
}

function getVoiceParams(){
	var retVal={};
	retVal["filter"]=getFilter();
    retVal["msgtype"]="voice";
    var media={};
    media["media_id"]=$("#media_voice_id").val();
    retVal["voice"]=media;
}

function getVideoParams(media_id){
	var retVal={};
	retVal["filter"]=getFilter();
    retVal["msgtype"]="mpvideo";
    var media={};
    media["media_id"]=media_id;
    retVal["mpvideo"]=media;
}

function getNewsParams(media_id){
	var retVal={};
	retVal["filter"]=getFilter();
    retVal["msgtype"]="mpnews";
    var media={};
    media["media_id"]=media_id;
    retVal["mpnews"]=media;
}