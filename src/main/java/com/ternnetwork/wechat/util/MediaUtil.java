package com.ternnetwork.wechat.util;


import org.springframework.web.multipart.MultipartFile;
import com.ternnetwork.wechat.model.auth.AccessToken;

public class MediaUtil {

		
		public static String qy_media_upload_url="https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=MEDIA_TYPE";
		
		public static String qy_media_download_url="https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
        public static String gz_media_upload_url="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=MEDIA_TYPE";
		
        public static String gz_media_upload_news_url="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";

        public static String gz_media_download_url="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		
        public static String gz_madia_upload_video_url=" https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
		
		
		public static String uploadDyMedia(String appid,String appsecret,MultipartFile file,String mediaType){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appid, appsecret);
			String url = gz_media_upload_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("MEDIA_TYPE",mediaType);
			String json = HttpConnectionUtil.uploadDyMedia(url, file);
			return json;
	    }
		
		public static String uploadDyNews(String appid,String appsecret,String json){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appid, appsecret);
			String url = gz_media_upload_news_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String retJson = HttpConnectionUtil.httpRequest(url,"post",json);
			return retJson;
	    }
		
		public static String uploadDyVideo(String appid,String appsecret,String json){
			AccessToken at = AccessTokenUtil.getDyAccessToken(appid, appsecret);
			String url = gz_madia_upload_video_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String retJson = HttpConnectionUtil.httpRequest(url,"post",json);
			return retJson;
	    }
		
		
		
		
		
		public static String uploadFwMedia(String appid,String appsecret,MultipartFile file,String mediaType){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appid, appsecret);
			String url = gz_media_upload_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("MEDIA_TYPE",mediaType);
			String json = HttpConnectionUtil.uploadDyMedia(url, file);
			return json;
	    }
		
		public static String uploadFwNews(String appid,String appsecret,String json){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appid, appsecret);
			String url = gz_media_upload_news_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String retJson = HttpConnectionUtil.httpRequest(url,"post",json);
			return retJson;
	    }
		
		public static String uploadFwVideo(String appid,String appsecret,String json){
			AccessToken at = AccessTokenUtil.getFwAccessToken(appid, appsecret);
			String url = gz_madia_upload_video_url.replace("ACCESS_TOKEN", at.getAccess_token());
			String retJson = HttpConnectionUtil.httpRequest(url,"post",json);
			return retJson;
	    }
		
		
		
		public static String uploadQyMedia(String corpId,String corpSecret,MultipartFile file,String mediaType){
			AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
			String url = qy_media_upload_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("MEDIA_TYPE",mediaType);
			String json = HttpConnectionUtil.uploadQyMedia(url, file);
			return json;
	    }
		
		 public static String downloadMedia(String accessToken,String savePath,String mediaId){
				String url = gz_media_download_url.replace("ACCESS_TOKEN",accessToken).replace("MEDIA_ID",mediaId);
				String retVal = HttpConnectionUtil.downloadMedia( url, mediaId, savePath);
				return retVal;
		 }
		
		 public static String downloadDyMedia(String appid,String appsecret,String savePath,String mediaId){
				AccessToken at = AccessTokenUtil.getDyAccessToken(appid, appsecret);
				return downloadMedia(at.getAccess_token(), savePath, mediaId);
		 }
		 
		 public static String downloadFwMedia(String appid,String appsecret,String savePath,String mediaId){
				AccessToken at = AccessTokenUtil.getFwAccessToken(appid, appsecret);
				return downloadMedia(at.getAccess_token(), savePath, mediaId);
		 }
		
		
		 public static String downloadQyMedia(String corpId,String corpSecret,String savePath,String mediaId){
				AccessToken at = AccessTokenUtil.getQyAccessToken(corpId, corpSecret);
				String url = qy_media_download_url.replace("ACCESS_TOKEN", at.getAccess_token()).replace("MEDIA_ID",mediaId);
				String retVal = HttpConnectionUtil.downloadQyMedia(url, mediaId, savePath);
				return retVal;
		 }
}