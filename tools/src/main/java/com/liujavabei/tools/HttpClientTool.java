package com.liujavabei.tools;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientTool {
    private static final String url = "http://10.216.42.105:8081/W_LCGL/WpmCheckOrderInterface/createWpmCheckOrder.do";
    private static final String checkOrder = "{\"typeSolu\":\"0\",\"soluName\":\"集中投诉参数实施方案\",\"orderSource\":\"集中投诉\",\"sourceSoluId\":\"ID-0601-20211122-03338\",\"userCName\":\"wy_jzts\",\"dist\":\"保定市\",\"prod\":\"华为\",\"updateSize\":\"单站\",\"moduType\":\"普通参数模板\",\"startTime\":\"2021-12-18 15:13:00\",\"expectTime\":\"2021-12-31 15:13:00\",\"paise\":\"haobosen\",\"paiseMobile\":\"13111111111\",\"requirement\":\"分公司\",\"oneLevelReason\":\"投诉处理\",\"twoLevelReason\":\"普通投诉处理\",\"soluReason\":\"xxx\",\"uploadExcel\":\"\",\"netType\":\"TDD\"}";
    private static final String checkOrderList = "[{\"listDist\":\"保定市\",\"listProd\":\"华为\",\"neType\":\"ECELL\",\"neName\":\"460-00-667180-195\",\"paraObjectName\":\"NRCELLHOEUTRANMEAGRP\",\"paraOMCName\":\"COVBASEDHOB1RSRPTHLD\",\"paraGroup\":\"\",\"paraValue\":\"-121\",\"paraNewValue\":\"-103\",\"paraCName\":\"基于覆盖的切换B1RSRP门限\",\"enbName\":\"BDZHZ3462涿州第二中心医院-HLH\",\"operateType\":\"MOD\",\"cellName\":\"BDZHZ3462涿州第二中心医院-HLH-0\"}]";

    public static void main(String[] args) throws UnsupportedEncodingException {
        String checkOrder = "{\"typeSolu\":\"0\",\"soluName\":\"集中投诉参数实施方案\",\"orderSource\":\"集中投诉\",\"sourceSoluId\":\"ID-0601-20211122-03338\",\"userCName\":\"wy_jzts\",\"dist\":\"保定市\",\"prod\":\"华为\",\"updateSize\":\"单站\",\"moduType\":\"普通参数模板\",\"startTime\":\"2021-12-18 15:13:00\",\"expectTime\":\"2021-12-31 15:13:00\",\"paise\":\"haobosen\",\"paiseMobile\":\"13111111111\",\"requirement\":\"分公司\",\"oneLevelReason\":\"投诉处理\",\"twoLevelReason\":\"普通投诉处理\",\"soluReason\":\"xxx\",\"uploadExcel\":\"\",\"netType\":\"TDD\"}";
        String checkOrderList = "[{\"listDist\":\"保定市\",\"listProd\":\"华为\",\"neType\":\"ECELL\",\"neName\":\"460-00-667180-195\",\"paraObjectName\":\"NRCELLHOEUTRANMEAGRP\",\"paraOMCName\":\"COVBASEDHOB1RSRPTHLD\",\"paraGroup\":\"\",\"paraValue\":\"-121\",\"paraNewValue\":\"-103\",\"paraCName\":\"基于覆盖的切换B1RSRP门限\",\"enbName\":\"BDZHZ3462涿州第二中心医院-HLH\",\"operateType\":\"MOD\",\"cellName\":\"BDZHZ3462涿州第二中心医院-HLH-0\"}]";
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
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            System.out.println("nameValuePairs:" + nameValuePairs);
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 获得http响应体
            HttpEntity entity = response.getEntity();
            System.out.println("entity:" + entity);
            if (entity != null) {
                // 响应的结果
                String content = EntityUtils.toString(entity, "UTF-8");
                System.out.println("content:" + content);
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
