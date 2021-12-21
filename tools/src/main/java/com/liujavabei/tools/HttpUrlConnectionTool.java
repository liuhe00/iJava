package com.liujavabei.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUrlConnectionTool {
    private String address;
    private String param;

    public HttpUrlConnectionTool(String address, String param) {
        this.address = address;
        this.param = param;
    }

    public static void main(String[] args) throws Exception {
        JSONObject requestBody = getRequestBody();
        String url = "http://10.216.42.105:8081/W_LCGL/WpmCheckOrderInterface/createWpmCheckOrder.do";
        //String param = "checkOrder={\"moduType\": \"普通参数模板\",\"requirement\": \"分公司\",\"orderSource\": \"集中投诉\",\"sourceSoluId\": \"ID-0601-20211122-03338\",\"userCName\": \"wy_jzts\",\"expectTime\": \"2021-12-31 15:13\",\"paiseMobile\": \"13111111111\",\"typeSolu\": \"0\",\"updateSize\": \"单站\",\"soluReason\": \"xxx\",\"startTime\": \"2021-12-18 15:13\",\"oneLevelReason\": \"投诉处理\",\"netType\": \"FDD\",\"soluName\": \"集中投诉参数实施方案\",\"paise\": \"haobosen\",\"twoLevelReason\": \"普通投诉处理\",\"prod\": \"华为\",\"dist\": \"保定市\"}&checkOrderList=[{\"neType\": \"ECELL\",\"neName\": \"460-00-667180-195\",\"paraObjectName\": \"NRCELLHOEUTRANMEAGRP\",\"paraOMCName\": \"COVBASEDHOB1RSRPTHLD\",\"paraGroup\": \"\",\"paraValue\": \"-121\",\"paraNewValue\": \"-103\",\"paraCName\": \"基于覆盖的切换B1 RSRP门限\",\"enbName\": \"BDZHZ3462涿州第二中心医院-HLH\",\"operateType\": \"MOD\",\"cellName\": \"BDZHZ3462涿州第二中心医院-HLH-0\",\"listDist\": \"保定市\",\"listProd\": \"华为\"}]";
        String paramStr ="checkOrder="+requestBody.get("checkOrder")+"&checkOrderList="+requestBody.get("checkOrderList");
        Map<String, String> requestProperty = new HashMap<String, String>();
        requestProperty.put("serviceCode", "qaz1124");
        requestProperty.put("Connection", "Keep-Alive");
        try{
            System.out.println("form表单提交...");
            System.out.println("数据："+paramStr);
            requestProperty.put("Content-Type", "application/x-www-form-urlencoded");
            String result = new HttpUrlConnectionTool(url, URLEncoder.encode(paramStr, "UTF-8")).configure().requestProperty(requestProperty).sendRequest();
            System.out.printf("响应结果：%s", result);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            System.out.println("JSON格式提交...");
            System.out.println("数据："+requestBody);
            requestProperty.put("Content-Type", "application/json");
            String result = new HttpUrlConnectionTool(url,requestBody.toString()).configure().requestProperty(requestProperty).sendRequest();
            System.out.printf("响应结果：%s", result);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public ConnectConfigure configure() {
        return new ConnectConfigure();
    }
    /**
     * 请求初始化
     */
    private class ConnectConfigure {
        // 是否输入参数
        boolean doOutput = true;
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        boolean doInput = true;
        // 是否使用缓存（Post 请求不能使用缓存）
        boolean useCaches = false;
        // 设置连接超时
        int connectTimeout = 5000;
        // 设置请求方式
        String requestMethod = "POST";
        // 设置请求属性
        Map<String, String> requestProperty;

        public ConnectConfigure doOutput(boolean doOutput) {
            this.doOutput = doOutput;
            return this;
        }

        public ConnectConfigure doInput(boolean doInput) {
            this.doInput = doInput;
            return this;
        }

        public ConnectConfigure useCaches(boolean useCaches) {
            this.useCaches = useCaches;
            return this;
        }

        public ConnectConfigure requestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public ConnectConfigure requestProperty(Map<String, String> requestProperty) {
            this.requestProperty = requestProperty;
            return this;
        }

        public ConnectConfigure connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public String sendRequest() throws Exception {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Map<String, String> requestProperty = this.requestProperty;
            if (requestProperty != null) {
                for (String key : requestProperty.keySet()) {
                    conn.setRequestProperty(key, requestProperty.get(key));
                }
            }
            conn.setRequestMethod(this.requestMethod);
            conn.setConnectTimeout(this.connectTimeout);
            conn.setDoOutput(this.doOutput);
            conn.setDoInput(this.doInput);
            conn.setUseCaches(this.useCaches);
            //此处getOutputStream会隐含的进行connect
            OutputStream output = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(output);
            //构建对象输出流，以实现输出可序列化的对象。
            dos.write(param.getBytes());
            output.flush();
            output.close();
            InputStream resInputStream;
            int status = conn.getResponseCode();
            System.out.printf("响应码：%s%n",status);
            if (status != HttpURLConnection.HTTP_OK) {
                resInputStream = conn.getErrorStream();
            } else {
                resInputStream = conn.getInputStream();
            }
            StringBuilder res;
            res = new StringBuilder(64);
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = resInputStream.read(buffer)) != -1) {
                res.append(new String(buffer, 0, i));
            }
            System.out.printf("响应结果：%s", res);
            return res.toString();
        }
    }

    public static String sendDefaultRequest(String url, String param) {
        StringBuilder result = new StringBuilder();
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection) httpUrl.openConnection();
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(), StandardCharsets.UTF_8));
            out.print(param);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String sendDefaultConfigRequest(String url, String param) throws Exception {
        return new HttpUrlConnectionTool(url, param).configure().sendRequest();
    }
    public static JSONObject getRequestBody(){
        String checkOrder ="{\"typeSolu\":\"0\",\"soluName\":\"集中投诉参数实施方案\",\"orderSource\":\"集中投诉\",\"sourceSoluId\":\"ID-0601-20211122-03338\",\"userCName\":\"wy_jzts\",\"dist\":\"保定市\",\"prod\":\"华为\",\"updateSize\":\"单站\",\"moduType\":\"普通参数模板\",\"startTime\":\"2021-12-1815:13:00\",\"expectTime\":\"2021-12-3115:13:00\",\"paise\":\"haobosen\",\"paiseMobile\":\"13111111111\",\"requirement\":\"分公司\",\"oneLevelReason\":\"投诉处理\",\"twoLevelReason\":\"普通投诉处理\",\"soluReason\":\"xxx\",\"uploadExcel\":\"\",\"netType\":\"TDD\"}";
        String checkOrderList ="[{\"listDist\":\"保定市\",\"listProd\":\"华为\",\"neType\":\"ECELL\",\"neName\":\"460-00-667180-195\",\"paraObjectName\":\"NRCELLHOEUTRANMEAGRP\",\"paraOMCName\":\"COVBASEDHOB1RSRPTHLD\",\"paraGroup\":\"\",\"paraValue\":\"-121\",\"paraNewValue\":\"-103\",\"paraCName\":\"基于覆盖的切换B1RSRP门限\",\"enbName\":\"BDZHZ3462涿州第二中心医院-HLH\",\"operateType\":\"MOD\",\"cellName\":\"BDZHZ3462涿州第二中心医院-HLH-0\"}]";
        JSONObject obj =new JSONObject();
        obj.put("checkOrder",JSON.parseObject(checkOrder));
        obj.put("checkOrderList",JSON.parseArray(checkOrderList));
        return obj;
    }
}