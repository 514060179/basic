package com.simon.basics.util.wx;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simon.basics.util.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

/**
 * @author fengtianying
 * @date 2018/11/26 11:46
 */
public class WxApi {

    private static Logger _log = LoggerFactory.getLogger(WxApi.class);

    //网页授权OAuth2.0获取token
    private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //网页授权OAuth2.0获取code
    private static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";


    //获取OAuth2.0 Token
    public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret, String code) {
        OAuthAccessToken token = null;
        String tockenUrl = getOAuthTokenUrl(appId, appSecret, code);
        String ret = httpsRequest(tockenUrl, HttpMethod.GET.toString(), null);
        HashMap jsonObject = JSONUtil.jsonToMap(ret);
        _log.warn("获取token返回：token=",jsonObject);
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            try {
                token = new OAuthAccessToken();
                token.setAccessToken((String)jsonObject.get("access_token"));
                token.setExpiresIn((int)jsonObject.get("expires_in"));
                token.setOpenid((String)jsonObject.get("openid"));
                token.setScope((String)jsonObject.get("scope"));
            } catch (JSONException e) {
                _log.error("json转换异常！",e);
                token = null;//获取token失败
            }
        }else if(null != jsonObject){
            token = new OAuthAccessToken();
            token.setErrcode((int)jsonObject.get("errcode"));
            _log.error("获取token返回：token=",ret);
        }
        return token;
    }
    //网页授权OAuth2.0获取token
    public static String getOAuthTokenUrl(String appId ,String appSecret ,String code ){
        return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
    }

    //网页授权OAuth2.0获取code
    public static String getOAuthCodeUrl(String appId ,String redirectUrl ,String scope ,String state){
        return String.format(GET_OAUTH_CODE, appId, urlEnodeUTF8(redirectUrl), "code", scope, state);
    }

    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        String respone = null;
        try {
            _log.info("发送HTTPS请求,requestMethod={},requestUrl={},outputStr={}", requestMethod, requestUrl, outputStr);
            TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            _log.info("响应数据,rtn={}", buffer);
            respone = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respone;
    }
}

class JEEWeiXinX509TrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}