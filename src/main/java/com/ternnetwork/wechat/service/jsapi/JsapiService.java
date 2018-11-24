package com.ternnetwork.wechat.service.jsapi;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import com.ternnetwork.wechat.model.auth.JsapiConfig;

public interface JsapiService {
    public JsapiConfig getQyJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException;
    public JsapiConfig getDyJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException;
    public JsapiConfig getFwJsapiConfig(HttpServletRequest request) throws NoSuchAlgorithmException;
}
