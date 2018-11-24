package com.ternnetwork.wechat.service.impl.media;



import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ternnetwork.baseframework.service.config.SystemParameterService;
import com.ternnetwork.wechat.service.media.MediaService;
import com.ternnetwork.wechat.util.MediaUtil;



@Service("mediaService")
public class MediaServiceImpl implements MediaService {
	
	@Resource
	private SystemParameterService systemParameterService;
	public String uploadQyMedia(MultipartFile file,String mediaType){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return MediaUtil.uploadQyMedia(corpId, corpSecret, file, mediaType);
	}
	
	public String downloadQyMedia(String mediaId,String savePath){
		String corpId=systemParameterService.getValueByKey("WECHAT_QY_CORP_ID");
		String corpSecret=systemParameterService.getValueByKey("WECHAT_QY_CORP_SECRET");
		return MediaUtil.downloadQyMedia(corpId, corpSecret, savePath, mediaId);
	}

	@Override
	public String uploadDyMedia(MultipartFile file, String mediaType) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadDyMedia(appId, appSecret, file, mediaType);
	}

	
	public String uploadDyNews(String json) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadDyNews(appId, appSecret, json);
	}
	
	public String uploadDyVideo(String json) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadDyVideo(appId, appSecret, json);
	}
	@Override
	public String downloadDyMedia(String mediaId, String savePath) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.downloadDyMedia(appId, appSecret, savePath, mediaId);
	}
	
	
	
	@Override
	public String uploadFwMedia(MultipartFile file, String mediaType) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadFwMedia(appId, appSecret, file, mediaType);
	}

	
	public String uploadFwNews(String json) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadFwNews(appId, appSecret, json);
	}
	
	public String uploadFwVideo(String json) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.uploadFwVideo(appId, appSecret, json);
	}
	@Override
	public String downloadFwMedia(String mediaId, String savePath) {
		String appId=systemParameterService.getValueByKey("WECHAT_DY_APP_ID");
		String appSecret=systemParameterService.getValueByKey("WECHAT_DY_APP_SECRET");
		return MediaUtil.downloadFwMedia(appId, appSecret, savePath, mediaId);
	}
	
	
	
	
}
