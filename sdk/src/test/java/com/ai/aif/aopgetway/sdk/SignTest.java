package com.ai.aif.aopgetway.sdk;

import java.util.HashMap;
import java.util.Map;

import com.ai.aif.aopgetway.sdk.utils.RSAUtils;
import com.ai.aif.aopgetway.sdk.utils.SignUtil;

public class SignTest {

	public static void main(String[] args) {
//		Map<String,String> sysParams = new HashMap<String,String>();
//		sysParams.put("slbrule", "A1");//网关接入层到分发层的软负载
//		sysParams.put("servicecode", "Asiainfo5G_IInnerProcessSV_closeFgs");//API ID
//		sysParams.put("version", "1.0");//版本号
//		sysParams.put("appId", "2003");//应用ID
//		sysParams.put("accessToken", "99380ac5-805f-4900-bdfa-156d49fa30d3");//认证token
//	//	sysParams.put("sign", "1jbwKDiBjoLGIZquIKQ6XJ3k7cWHlNAYvl8j3otL9PBij0MPe7/kGwyORnn1psAxNyRWrgHUDPSy8qOLjcmKYUprY4glM+LrbALqi");
//		
//		//sysParams.put("username", "yuxg");
//	//	sysParams.put("password", "A123456");
//		String busiParams ="{\"username\":\"yuxg\",\"password\":\"yuxg\"}";
////		String busiParams ="";
//		
//		String sign = SignUtil.generateSign(sysParams, busiParams);
//		System.out.println(sign);
		
		
		Map<String, String> sysParams = new HashMap<String, String>();
	      //sysParams.put("slbrule", "A1");
		
	      sysParams.put("servicecode", "DEMO_IDemoTestCSV_sayHello");
	      sysParams.put("version", "1.0");
	      sysParams.put("appId", "9885");
	      sysParams.put("accessToken", "b840aad4-51c8-4aa9-bcc6-9a30c8f39940");
		
		
	      //sysParams.put("appKey", "6400ce1d56efbe1fe03b7b87c66a184c");
//	    sysParams.put("name", "xieshaorong");
//	    sysParams.put("sign","1jbwKDiBjoLGIZquIKQ6XJ3k7cWHlNAYvl8j3otL9PBij0MPe7/kGwyORnn1psAxNyRWrgHUDPSy8qOLjcmKYUprY4glM+LrbALqi");
//	    sysParams.put("username", "yuxg");
//	    sysParams.put("password", "A123456");
		
		
	       String busiParams = "{\"name\":\"李晓冬\"}";
	       
	       
//	    String busiParams = "{\"<name>xieshaorong</name>\"}";
	      //String busiParams="123";
//	      String busiParams="<name>xieshaorong</name>";
	    //String busiParams = "";

	      String sign = SignUtil.generateSign(sysParams, busiParams);
	      System.out.println(sign);
	      
	      
	}

}
