package com.liujavabei.tools;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientTool {
    private static final String url = "http://10.216.42.105:8081/W_LCGL/WpmCheckOrderInterface/createWpmCheckOrder.do";
    private static final String checkOrder = "{\"moduType\":\"普通参数模板\",\"requirement\":\"分公司\",\"orderSource\":\"集中投诉\",\"expectTime\":\"2021-12-25 14:32:00\",\"userCName\":\"wy_jzts\",\"paiseMobile\":\"18633005558\",\"typeSolu\":\"0\",\"updateSize\":\"单站\",\"soluReason\":\"参数调整测试工单\",\"startTime\":\"2021-12-24 14:32:00\",\"oneLevelReason\":\"投诉处理\",\"netType\":\"FDD\",\"soluName\":\"集中投诉参数实施方案\",\"paise\":\"郝柏森\",\"twoLevelReason\":\"紧急投诉处理\",\"sourceSoluId\":\"ID-0601-20211223-00279\",\"prod\":\"华为\",\"dist\":\"邢台市\"}";
    private static final String checkOrderList = "[{\"paraOMCName\":\"COVINTERFREQA2RSRPTHLD\",\"paraCName\":\"基于覆盖的异频A2RSRP触发门限\",\"neName\":\"460-00-1087444-2\",\"paraNewValue\":\"-105\",\"operateType\":\"MOD\",\"enbName\":\"XTSHH8168沙河杜村HB-HWH\",\"neType\":\"ECELL\",\"paraObjectName\":\"NRCELLINTERFHOMEAGRP\",\"listProd\":\"华为\",\"paraGroup\":\"0\",\"listDist\":\"邢台市\",\"paraValue\":\"-99\"},{\"paraOMCName\":\"MAXTRANSMITPOWER\",\"paraCName\":\"最大发射功率\",\"neName\":\"460-00-1087444-2\",\"paraNewValue\":\"400\",\"operateType\":\"MOD\",\"enbName\":\"XTSHH8168沙河杜村HB-HWH\",\"neType\":\"ECELL\",\"paraObjectName\":\"NRDUCELLTRP\",\"listProd\":\"华为\",\"paraGroup\":\"0\",\"listDist\":\"邢台市\",\"paraValue\":\"390\"},{\"paraOMCName\":\"TILT\",\"paraCName\":\"倾角(度)\",\"neName\":\"460-00-1087444-4\",\"paraNewValue\":\"3\",\"operateType\":\"MOD\",\"enbName\":\"XTSHH8168沙河杜村HB-HWH\",\"neType\":\"ECELL\",\"paraObjectName\":\"NRDUCELLTRPBEAM\",\"listProd\":\"华为\",\"paraGroup\":\"0\",\"listDist\":\"邢台市\",\"paraValue\":\"255\"},{\"paraOMCName\":\"PSCELLA2RSRPTHLD\",\"paraCName\":\"PSCellA2事件RSRP门限(毫瓦分贝) \",\"neName\":\"460-00-1087444-5\",\"paraNewValue\":\"-110\",\"operateType\":\"MOD\",\"enbName\":\"XTSHH8168沙河杜村HB-HWH\",\"neType\":\"ECELL\",\"paraObjectName\":\"NRCELLNSADCCONFIG\",\"listProd\":\"华为\",\"paraGroup\":\"0\",\"listDist\":\"邢台市\",\"paraValue\":\"-108\"}]";

    public static void main(String[] args) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("checkOrder", checkOrder);
        paramMap.put("checkOrderList", checkOrderList);
        send(url, paramMap);
    }

    public static String send(String url, Map<String, String> mapdata) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httpPost = new HttpPost(url);
        try {
            // 设置提交方式
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.addHeader("serviceCode", "qaz1124");
            // 添加参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String k : mapdata.keySet()) {
                String v = mapdata.get(k);// value
                nameValuePairs.add(new BasicNameValuePair(k, v));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            System.out.printf("参数nameValuePairs：%s", nameValuePairs);
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 获得http响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 响应的结果
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.printf("响应结果:%s", content);
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
