package com.ternnetwork.baseframework.service.impl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import com.ternnetwork.baseframework.service.util.HashService;





@Service("hashService")
public class HashServiceImpl implements HashService {

	public String encryptSHA(String data) throws NoSuchAlgorithmException {
		MessageDigest alga= MessageDigest.getInstance("SHA-1");
		alga.update(data.getBytes());
		byte[] digesta = alga.digest();
		return new String(new Hex().encode(digesta));
	}

}
