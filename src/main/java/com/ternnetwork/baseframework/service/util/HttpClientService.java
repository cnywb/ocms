package com.ternnetwork.baseframework.service.util;

import java.io.InputStream;

public interface HttpClientService {
	
	public String executeGetRequestWithResponse(String url) throws Exception;
	public InputStream getRemoteImg(String url);

}
