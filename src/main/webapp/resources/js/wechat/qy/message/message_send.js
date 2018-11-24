$(document).ready(function(){

	initEditor();
	$("#delegate_btn_upload_image").click(function(){
		$("#btnc01 input[type='file']").each(function(){
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
	$("#delegate_btn_upload_file").click(function(){
		$("#btnc04 input[type='file']").each(function(){
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
		url :'wechat/qy/txl/department/query.htm',
		type : 'post',
		dataType:'json',
		success : function(result) {
			
			initZTreeAdd(result);
			
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
			}else if(i==2){
				 submitData=getNewsParams();
			}else if(i==3){
				 submitData=getImageParams();
			}else if(i==4){
				submitData=getFileParams();
			}else if(i==5){
				 submitData=getVideoParams();
			}else if(i==6){
				 submitData=getVoiceParams();
			}
		}
	});
	
	 doSendMessage(submitData);
}

var handleJqueryTagIt = function() {
	var params={};
	 params["departmentId"]="";
	 params["status"]=0;
	 params["limit"]=10;
	 params["offset"]=10;
	$.ajax( {
		url :'wechat/qy/txl/user/queryByDepartmentId.htm',
		type : 'post',
		dataType:'json',
		data:params,
		success : function(result) {
			   var rows=result.rows;
			   var tags=new Array();
		       for(var i=0;i<rows.length;i++){
		    	   tags.push(rows[i].userid);
		       }
			  $('#jquery-tagIt-primary').tagit({
			        availableTags: tags
			    });
			
	     },error: function(XMLHttpRequest, textStatus, errorThrown){
      	alert('注意,系统错误！');
     }
	});
	 
};

function doSendMessage(submitData){
	 $.ajax( {
 			url :'wechat/message/sendQyMessage.htm',
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


/**
 * {
   "touser": "UserID1|UserID2|UserID3",
   "toparty": " PartyID1 | PartyID2 ",
   "totag": " TagID1 | TagID2 ",
   "msgtype": "text",
   "agentid": "1",
   "text": {
       "content": "Holiday Request For Pony(http://xxxxx)"
   },
   "safe":"0"
}
 */
function getTextParams(){
	var retVal={};
	var text={};
	text["content"]=$("#msg_content").val();
	retVal["text"]=text;
	retVal["msgtype"]="text";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["safe"]="0";
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}


/**
 * {
   "touser": "UserID1|UserID2|UserID3",
   "toparty": " PartyID1 | PartyID2 ",
   "msgtype": "image",
   "agentid": "1",
   "image": {
       "media_id": "MEDIA_ID"
   },
   "safe":"0"
}
 * @returns {___anonymous3094_3095}
 */
function getImageParams(){
	var retVal={};
	var image={};
	image["media_id"]=$("#media_image_id").val();
	retVal["image"]=image;
	retVal["msgtype"]="image";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["safe"]="0";
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}

function getVoiceParams(){
	var retVal={};
	var voice={};
	voice["media_id"]=$("#media_voice_id").val();
	retVal["voice"]=voice;
	retVal["msgtype"]="voice";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["safe"]="0";
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}

function getVideoParams(){
	var retVal={};
	var video={};
	video["media_id"]=$("#media_video_id").val();
	retVal["video"]=video;
	retVal["msgtype"]="video";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["safe"]="0";
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}
function getFileParams(){
	var retVal={};
	var file={};
	file["media_id"]=$("#media_file_id").val();
	retVal["file"]=file;
	retVal["msgtype"]="file";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["safe"]="0";
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}

function getNewsParams(){
	var retVal={};
    var news={};
    var articles=new Array();
    var article0={};
    article0["title"]=$("#news_title").val();
    article0["description"]=$("#news_description").val();
    article0["url"]=$("#news_url").val();
    article0["picurl"]=$("#news_picurl").val();
    articles.push(article0);
    news["articles"]=articles;
    retVal["news"]=news;
	retVal["msgtype"]="news";
	retVal["toparty"]=getTagPartyList();
	retVal["touser"]=getTagUserList();
	retVal["agentid"]=$("#agentid").val();
	return retVal;
}
var sessionId=getCookie("JSESSIONID");
function initEditor(){
	KindEditor.ready(function(K) {
		initUploadBtn(K,'image');
		initUploadBtn(K,'voice');
		initUploadBtn(K,'video');
		initUploadBtn(K,'file');
		
		var editor = K.editor({
			allowFileManager : true,
			uploadJson:'baseframework/util/editor/upload.htm;jsessionid='+sessionId, 
			fileManagerJson:'baseframework/util/editor/manage.htm;jsessionid='+sessionId
		});
		K('#imageSrcAddBtn').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#imageSrcAdd').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#news_picurl').val(url);
						editor.hideDialog();
					}
				});
			});
		});
	});
	
	

}

function initUploadBtn(K,type){
	var uploadbutton = K.uploadbutton({
		button : K('#btn_upload_'+type)[0],
		fieldName : 'imgFile',
		url : 'wechat/media/uploadQyMedia.htm?dir='+type,
		afterUpload : function(data) {
			if (data.error === 0) {
			 var message=JSON.parse(data.message);
				if(message.errcode==undefined){
					$("#media_"+type+"_id").val(message.media_id);
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