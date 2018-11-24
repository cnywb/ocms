package com.ternnetwork.baseframework.service.util;

import java.security.NoSuchAlgorithmException;

public interface HashService {
	String encryptSHA(String data) throws NoSuchAlgorithmException;
}
