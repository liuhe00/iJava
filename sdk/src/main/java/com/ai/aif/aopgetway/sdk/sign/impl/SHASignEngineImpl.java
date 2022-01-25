package com.ai.aif.aopgetway.sdk.sign.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ai.aif.aopgetway.sdk.sign.ISignEngine;
import com.ai.aif.aopgetway.sdk.utils.AppkeyUtil;
import com.ai.aif.aopgetway.sdk.utils.Constants;
import com.ai.aif.aopgetway.sdk.utils.SecurityUtils;

public class SHASignEngineImpl implements ISignEngine {

    private static SHASignEngineImpl instance;
	
	private SHASignEngineImpl(){
	}
	
	public static SHASignEngineImpl getSingleton(){
		if (instance == null){
			synchronized (SHASignEngineImpl.class) {
				if (instance == null){
					instance = new SHASignEngineImpl();
				}
			}
		}
		
		return instance;
	}
	
	@Override
	public String generateSign(Map<String, String> paramsMap) throws Exception {
		String appKey = AppkeyUtil.getAppkey();
		String[] paramArr = paramsMap.keySet().toArray(new String[paramsMap.size()]);
		Arrays.sort(paramArr);
		StringBuilder buf = new StringBuilder();
		buf.append(appKey);
		for (String param : paramArr) {
			if (!Constants.SIGN.equals(param)) {
				String value = paramsMap.get(param.trim());
				if (StringUtils.isNotBlank(value)) {
					buf.append(param).append(value.trim());
				}
			}
		}
		buf.append(appKey);

		String signStr = "";
		if (buf.length() > 0) {
			// 根据报文参数生成签名
			signStr = SecurityUtils.encodeHmacSHA256HexUpper(buf.toString(),SecurityUtils.decodeHexUpper(appKey));
		}
		
		return signStr;
	}

}
