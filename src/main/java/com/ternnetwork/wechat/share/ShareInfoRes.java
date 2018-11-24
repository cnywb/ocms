package com.ternnetwork.wechat.share;


/**
 * Created by 阳葵 on 15/11/5.
 */
public class ShareInfoRes extends BasePageInterfaceRes  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	   private String accessToken;
	    private String appid;
		private String timestamp; 
		private String noncestr; 
		private String url;
		private String jsapi_ticket;
		private String signature; 
		
		private String responseBody;
   

	public String getAccessToken() {
			return accessToken;
		}


		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}


		public String getAppid() {
			return appid;
		}


		public void setAppid(String appid) {
			this.appid = appid;
		}


		public String getTimestamp() {
			return timestamp;
		}


		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}


		public String getNoncestr() {
			return noncestr;
		}


		public void setNoncestr(String noncestr) {
			this.noncestr = noncestr;
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}



		public String getJsapi_ticket() {
			return jsapi_ticket;
		}


		public void setJsapi_ticket(String jsapi_ticket) {
			this.jsapi_ticket = jsapi_ticket;
		}


		public String getSignature() {
			return signature;
		}


		public void setSignature(String signature) {
			this.signature = signature;
		}

		
		

	public String getResponseBody() {
			return responseBody;
		}


		public void setResponseBody(String responseBody) {
			this.responseBody = responseBody;
		}


	@Override
	public String toString() {
		return JSONUtil.objectToJson(this);
	}
}
