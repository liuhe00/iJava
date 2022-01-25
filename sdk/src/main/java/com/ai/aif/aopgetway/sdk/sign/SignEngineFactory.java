package com.ai.aif.aopgetway.sdk.sign;

import java.util.HashMap;
import java.util.Map;

import com.ai.aif.aopgetway.sdk.sign.impl.RSASignEngineImpl;
import com.ai.aif.aopgetway.sdk.sign.impl.SHASignEngineImpl;
import com.ai.aif.aopgetway.sdk.utils.ZJWGSDKConfig;
import com.ai.aif.aopgetway.sdk.utils.Constants;

public class SignEngineFactory {

	private static Map<String, ISignEngine> signEngineMap = new HashMap<String, ISignEngine>();
	
	public static ISignEngine getSignEngine(){
		String signMethod = ZJWGSDKConfig.getSignMethod();
		ISignEngine signEngine = signEngineMap.get(signMethod);
		if (signEngine != null){
			return signEngine;
		}
		
		if (Constants.SIGN_METHOD.RSA.equalsIgnoreCase(signMethod)){
			signEngine = RSASignEngineImpl.getSingleton();
		}else if (Constants.SIGN_METHOD.SHA.equalsIgnoreCase(signMethod)){
			signEngine = SHASignEngineImpl.getSingleton();
		}
		signEngineMap.put(signMethod, signEngine);
		return signEngine;
	}
}
