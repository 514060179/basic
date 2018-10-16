package com.simon.basics.qianying.pay;


import com.simon.basics.qianying.pay.tools.MD5;
import com.simon.basics.qianying.pay.tools.OuterRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class QianyingPayService {

    @Value("${QIANYING_KEY}")
    private String QIANYING_KEY;

    public String getQIANYING_KEY() {
        return QIANYING_KEY;
    }

    public void setQIANYING_KEY(String QIANYING_KEY) {
        this.QIANYING_KEY = QIANYING_KEY;
    }

    //商户 的玩家id
    @Value("${QIANYING_UUID}")
    private String QIANYING_UUID;
    @Value("${QIANYING_UID}")
    private String QIANYING_UID;
    // 订单失效，需要重新创建订单时，该地址必须是可以再互联网上访问的网址,此处仅为示例
    @Value("${QIANYING_GO_FALSE}")
    private String QIANYING_GO_FALSE;
    // 订单成功，该地址必须是可以再互联网上访问的网址,此处仅为示例
    @Value("${QIANYING_GO_TRUE}")
    private String QIANYING_GO_TRUE;
    //千应异步通知地址   该地址必须是可以再互联网上访问的网址,此处仅为示例
    @Value("${QIANYING_CALLBACK_URL}")
    private String QIANYING_CALLBACK_URL;
    // 千应api的post提交接口服务器地址
    private String QIANYING_URL = "https://www.qianyingnet.com/pay/";
    // 商户网页的编码方式【请根据实际情况自行配置】
    @Value("${QIANYING_CHARSET}")
    private String QIANYING_CHARSET;

    public void submitOrder(HttpServletResponse response, String amount, String orderid, String token, String type, String gotrue, String gofalse) {
        if (orderid == null || orderid.trim().equals("")) orderid = UUID.randomUUID().toString();
        Map<String, String[]> params = new LinkedHashMap<>();
        params.put("uid", new String[]{QIANYING_UID});

        params.put("type", new String[]{type});
        params.put("m", new String[]{amount});
        params.put("orderid", new String[]{orderid});
        params.put("callbackurl", new String[]{QIANYING_CALLBACK_URL});
        String _sign = MD5.convert("uid=" + QIANYING_UID + "&type=" + type + "&m=" + amount + "&orderid="
                + orderid + "&callbackurl=" + QIANYING_CALLBACK_URL + QIANYING_KEY);

        //uuid商户的玩家id
        params.put("uuid", new String[]{QIANYING_UUID});
        params.put("sign", new String[]{_sign});
        params.put("gofalse", new String[]{gofalse});
        params.put("gotrue", new String[]{gotrue});
        params.put("charset", new String[]{QIANYING_CHARSET});

        //token是用户自定义的字段，千应服务器会原址返回，请自定义此字段
        if (token != null)
            params.put("token", new String[]{token});
        params.put("ts", new String[]{System.currentTimeMillis() + ""});
        try {
            String urlWithParams = OuterRequestUtil.getUrlString(QIANYING_URL, params);
            response.sendRedirect(urlWithParams);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}