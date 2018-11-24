package com.ternnetwork.wechat.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;


/**
 * Created by huangwen on 2017/08/21.
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/wx/*")
public class WeiXinController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String WX_URL = "http://api.parllay.cn/v1.1/social/wxconfig/7UOHYCCD9T/metadata/get";
	public static final String appID ="wxbc330df0e013e079";
	public static final String secret ="1d5a471f4b31f030ff4f02c95baa7e94";
	public static final String WX_TOKEN_URL =  "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&grant_type=client_credential";
	@RequestMapping("getShareInfo.do")
	public void getShareInfo(PrintWriter out, HttpServletRequest request, HttpServletResponse response,
			String jsonpcallback, String jsonStr) {
		logger.info("/ocms/wx/getShareInfo.do in:jsonStr:" + jsonStr);
		response.setContentType("text/javascript");// 如果不这样设置 chrome浏览器无法调用
		ShareInfoReq req = JSONUtil.toObject(jsonStr, ShareInfoReq.class);
		ShareInfoRes res = getShareInfoDetail(req);
		String result = res.toString();

		String return_str = jsonpcallback + "(" + result + ")";

		logger.info("/ocms/wx/getShareInfo.do;return_str:" + return_str);
		out.print(return_str);
		logger.info("/ocms/wx/getShareInfo.do out;");
	}

	public ShareInfoRes getShareInfoDetail(ShareInfoReq req) {
		ShareInfoRes res = new ShareInfoRes();
		try {
			if (StringUtils.isBlank(req.getUrl())) {
				res.setStatus(-11);
				res.setMessage("操作失败,url不能为空！");
				return res;
			}
			String accessToken = getAccessTokenWeixinByAppId(appID,secret);
			if (null == accessToken) {
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！accessToke获取失败");
				return res;
			}
			logger.info("/ocms/wx/getShareInfo.do in1:accessToken:" + accessToken);
			String geturl =   WX_URL + "?url=" + req.getUrl() + "&callback=?" + "&access_token=" + accessToken ;
			logger.info("/ocms/wx/getShareInfo.do in1:geturl:" + geturl);
			String responseBody = HttpClientUtil.get(WX_URL + "?url=" + req.getUrl() + "&callback=?" + "&access_token=" + accessToken,
					30000, 30000, "UTF-8");
			logger.info(" responseBody HttpClientUtil.get  result  = " + responseBody);
			
			if (null != responseBody) {
//				responseBody = responseBody.substring(5, responseBody.length());
				responseBody = responseBody.substring(2, responseBody.length());
				responseBody = responseBody.substring(0, responseBody.length() - 1);
			} else {
				res.setAccessToken(accessToken);
				res.setStatus(0);// 状态 1.成功，0 失败
				res.setMessage("操做 失败！");
				return res;
			}
			logger.info("/ocms/wx/getShareInfo.do in2:responseBody:" + responseBody);
			res = JSONUtil.toObject(responseBody, ShareInfoRes.class);
			res.setAccessToken(accessToken);
			res.setStatus(1);// 状态 1.成功，0 失败
			res.setMessage("操做成功！");
			return res;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			res.setStatus(-99);
			res.setMessage("操作失败,异常");
			return res;
		}
	}
	public  String getAccessTokenWeixinByAppId(String appID,String secret) {
		String url = WX_TOKEN_URL+"&appid=" +appID + "&secret=" +secret;
	        try {
	            logger.info("请求accesstoken url:{}", url);
	            String json = HttpClientUtil.get(url, 30000, 30000, "UTF-8");
	            logger.info("获取accesstoken返回值为：{}", json);
	            Map<String, String> map = (Map<String, String>) JSON.parse(json);
	            String  errcode = map.get("errcode");
	            if (errcode != null) {
	                logger.info("获取accesstoken异常,errorCode:{},errmsg:{}", errcode, map.get("errmsg"));
	                return null;
	            }
	            return map.get("access_token");
	        } catch (Exception e) {
	            logger.error("获取accesstoken异常", e);
	        }
	        return null;
}

//	 public static void main(String[] args) {
		 
//			String accessToken = getAccessTokenWeixinByAppId(appID,secret);
//		   System.out.println(accessToken);

//	 }
}
