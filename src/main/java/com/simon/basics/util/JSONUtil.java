package com.simon.basics.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * springboot下的jackon 处理json
 * @author fengtianying
 * @date 2018/9/4 14:57
 */
public class JSONUtil {

    protected static Logger logger  = LoggerFactory.getLogger(JSONUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();
    public static <T> T jsonToObject(String jsonString,Class<T> c){

        try {
            return mapper.readValue(jsonString,c);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(jsonString+"is not a json string",e);
            return null;
        }
    }

    public static String objectToJson(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("system error",e);
            return null;
        }
    }

    public static <T> List<T> jsonToList(String jsonString) {
        try {
            return mapper.readValue(jsonString,new TypeReference<List<T>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(jsonString+"is not a json array",e);
            return null;

        }
    }
    public static String listToJson(List list) {
        try {
            return  mapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("system error",e);
            return null;

        }
    }
    public static HashMap jsonToMap(String jsonString) {
        try {
            JavaType jvt = mapper.getTypeFactory().constructParametricType(HashMap.class,String.class,Object.class);
            return mapper.readValue(jsonString, jvt);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("system error",e);
            return null;

        }
    }
//    public static void main(String[] args) {
////        ReturnParam returnParam = new ReturnParam();
////        String json = "{\"code\":12,\"msg\":12,\"data\":213}";
////        System.out.println(objectToJson(returnParam));
////        System.out.println(jsonToObject(json,ReturnParam.class));
//        PayOrderDetail payOrderDetail = new PayOrderDetail();
//        payOrderDetail.setAmount("1");
//        payOrderDetail.setBody("22ceshishiyiong");
//        payOrderDetail.setClientIp("127.0.0.1");
//        payOrderDetail.setCurrency("cny");
//        Map map = new HashMap();
//        map.put("productId","1245678954");
//        map.put("openId","oeHXkwvzKANWbsHyl2FaZxCU8iQ8");
//        payOrderDetail.setExtra(JSONUtil.objectToJson(map));
//        payOrderDetail.setMchId("20001229");
//        payOrderDetail.setChannelId("ALIPAY_WAP");
//        payOrderDetail.setMchOrderNo(""+new SnowflakeIdWorker().nextId());
//        payOrderDetail.setSubject("ceshi");
//        payOrderDetail.setParam1("额外字段1");
//        payOrderDetail.setParam2("额外字段2");
//        payOrderDetail.setTimestamp("-1");
//        payOrderDetail.setNotifyUrl("http://pay.taichuan.com:6000/pay/aliPayNotifyRes.htm");
//        payOrderDetail.setSign("sign");
//        String eecode = MyBase64.encode(objectToJson(payOrderDetail).getBytes());
//        System.out.println(eecode);
//        System.out.println(new String(MyBase64.decode(eecode)));
//    }
}
