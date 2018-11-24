package com.ternnetwork.wechat.service.media;



import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
	public String uploadDyMedia(MultipartFile file,String mediaType);
	public String downloadDyMedia(String mediaId,String savePath);
	public String uploadQyMedia(MultipartFile file,String mediaType);
	public String uploadDyNews(String json);
	public String uploadDyVideo(String json);
	public String downloadQyMedia(String mediaId,String savePath);
	public String uploadFwMedia(MultipartFile file, String mediaType);
	public String downloadFwMedia(String mediaId, String savePath);
	public String uploadFwVideo(String json);
	public String uploadFwNews(String json);
}
