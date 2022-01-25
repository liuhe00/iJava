package com.ai.aif.aopgetway.sdk.sign.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.aif.aopgetway.sdk.sign.ISignEngine;
import com.ai.aif.aopgetway.sdk.utils.ZJWGSDKConfig;
import com.ai.aif.aopgetway.sdk.utils.AppkeyUtil;
import com.ai.aif.aopgetway.sdk.utils.Constants;
import com.ai.aif.aopgetway.sdk.utils.MD5Util;
import com.ai.aif.aopgetway.sdk.utils.RSAUtils;

public class RSASignEngineImpl implements ISignEngine {
	
	private static RSASignEngineImpl instance;
	
	private RSASignEngineImpl(){
	}
	
	public static RSASignEngineImpl getSingleton(){
		if (instance == null){
			synchronized (RSASignEngineImpl.class) {
				if (instance == null){
					instance = new RSASignEngineImpl();
				}
			}
		}
		
		return instance;
	}

	@Override
	public String generateSign(Map<String, String> paramsMap) throws Exception {
		//String signSecret = AIESBConfig.getRsaKey();
		String signSecret=AppkeyUtil.getAppkey();
		
		String md5Str = getMD5Str(paramsMap);
		System.out.println(md5Str);
		if (Constants.RSA_ENCRYPT_TYPE.ENCRYPT_PUBLIC.equals(ZJWGSDKConfig.getRsaEncryptType())){
			return RSAUtils.encryptByPublicKey(md5Str, signSecret);
		} else {
			return RSAUtils.encryptByPrivateKey(md5Str, signSecret);
		}
	}

	private static String getMD5Str(Map<String, String> paramMap) throws Exception{
		// 按照接口规范获得“待签名字符串”，并生成字符串的MD5值
		String[] paramArr = paramMap.keySet().toArray(new String[paramMap.size()]);
		Arrays.sort(paramArr);
		StringBuilder keyBuf = new StringBuilder();
		StringBuilder buf = new StringBuilder();
		for (String param : paramArr) {
			if (!Constants.SIGN.equals(param)) {
				String value = paramMap.get(param.trim());
				if (StringUtils.isNotBlank(value)) {
					keyBuf.append(param).append("|");
					buf.append(value.trim());
				}
			}
		}

		String md5Str = "";
		if (buf.length() > 0) {
			// 根据报文参数生成签名
			md5Str = MD5Util.MD5(buf.toString());
		}
		
		return md5Str;
	}
}
