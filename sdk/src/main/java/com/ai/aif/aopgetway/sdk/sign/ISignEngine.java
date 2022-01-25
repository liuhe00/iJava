package com.ai.aif.aopgetway.sdk.sign;

import java.util.Map;

public interface ISignEngine {

	public String generateSign(Map<String, String> paramsMap) throws Exception;
}
