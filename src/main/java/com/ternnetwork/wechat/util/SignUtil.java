package com.ternnetwork.wechat.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



/**
 * 请求校验工具类
 * 
 * @author 
 * @date 
 */
public class SignUtil {
	
	//signature=&echostr=7964850911659707359&timestamp=&nonce=1057774130
	
	
	public static void main(String[] args) {  
        
		Boolean b=checkSignature("9732f78833631fead703937b31f660a08adef3a9","1449921483","1057774130","weixinCourse"); 
	System.out.println(b);
	}  
	
	
	// 与接口配置信息中的Token要一致
	//private static String token = "weixinCourse";

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce,String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
	
    public static String checkQySignature(String msg_signature, String timestamp, String nonce,String echostr, String encodingAESKey, String token, String corpID) {
    	String sEchoStr="";
    	try {
    		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
    		
    		sEchoStr = wxcpt.VerifyURL(msg_signature, timestamp,nonce, echostr);
    		
    	} catch (AesException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	    return sEchoStr;
	}
    
 public static String decryptMsg(String msg_signature, String timestamp, String nonce,String data,String encodingAESKey,String token, String corpID) {
	 String retVal="";
	try {
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
		retVal = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, data);
	} catch (AesException e) {
		e.printStackTrace();
	}
	    return retVal;
	}
 
   public static String encryptMsg(String timestamp, String nonce,String data,String encodingAESKey,String token, String corpID) {
	 String retVal="";
	try {
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
		retVal = wxcpt.EncryptMsg(data, timestamp, nonce);
	} catch (AesException e) {
		e.printStackTrace();
	}
	    return retVal;
	}
}

