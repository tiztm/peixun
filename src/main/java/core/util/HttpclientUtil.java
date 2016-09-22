package core.util;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Httpclient 4.0 工具包装类
 *
 * @author NilsZhang
 */
@SuppressWarnings("all")
public class HttpclientUtil {

    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String CHARSET_GBK = "GBK";
    private static final String SSL_DEFAULT_SCHEME = "https";
    private static final int SSL_DEFAULT_PORT = 443;


    public static DefaultHttpClient getDefaultHttpClient() {
        return getDefaultHttpClient(null);
    }

    /**
     * 获取DefaultHttpClient实例
     *
     * @param charset 参数编码集, 可空
     * @return DefaultHttpClient 对象
     */
    public static DefaultHttpClient getDefaultHttpClient(final String charset) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        //模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
        httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset == null ? CHARSET_GBK : charset);
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 600);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 600);
        httpclient.setHttpRequestRetryHandler(requestRetryHandler);
        return httpclient;
    }

    public static String get(String url) throws Exception {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> params) throws Exception {
        return get(url, params, null, null);
    }

    public static String get(String url, Map<String, String> params, String charset) throws Exception {
        return get(url, params, charset, null);
    }

    public static String get(String url, Map<String, String> params, String charset, HttpClient httpclient) throws Exception {
        return get(url, params, charset, httpclient, null);
    }


    public static String post(String url, Map<String, String> params) throws Exception {
        return post(url, params, null);
    }

    public static String post(String url, Map<String, String> params, String charset) throws Exception {
        return post(url, params, charset, null);
    }

    public static String post(String url, Map<String, String> params, String charset, HttpClient httpclient) throws Exception {
        return post(url, params, charset, httpclient, null);
    }


    public static String get(String url, Map<String, String> params, String charset, HttpClient httpclient, Map<String, String> headMap) throws Exception {

        return request(url, params, charset, httpclient, headMap, "get");
    }


    public static String post(String url, Map<String, String> params, String charset, HttpClient httpclient, Map<String, String> headMap) throws Exception {

        return request(url, params, charset, httpclient, headMap, "post");
    }


    /**
     * 使用get/POST的方式访问指定的url，返回响应信息
     *
     * @param url           待访问的url
     * @param params        参数的map
     * @param charset       字符集
     * @param httpclient    特地的hc
     * @param headMap       指定的头部
     * @param requestMethod GET or POST
     * @return 响应信息
     * @throws Exception
     */
    private static String request(String url, Map<String, String> params, String charset, HttpClient httpclient, Map<String, String> headMap, String requestMethod) throws Exception {

        if (url == null || StringUtil.isEmpty(url)) {
            return null;
        }

        // 创建HttpClient实例
        boolean isDefaultHc = false;
        if (httpclient == null) {
            httpclient = getDefaultHttpClient(null);
            isDefaultHc = true;
        }
        HttpGet hg = null;
        HttpPost hp = null;
        if (requestMethod.equals("get")) {
            List<NameValuePair> qparams = getParamsList(params);
            if (qparams != null && qparams.size() > 0) {
                charset = (charset == null ? CHARSET_GBK : charset);
                String formatParams = URLEncodedUtils.format(qparams, charset);
                url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
                        .substring(0, url.indexOf("?") + 1) + formatParams);
            }

            hg = new HttpGet(url);

        } else if (requestMethod.equals("post")) {
            UrlEncodedFormEntity formEntity = null;

            if (params != null) {
                try {
                    if (charset == null || StringUtil.isEmpty(charset)) {
                        formEntity = new UrlEncodedFormEntity(getParamsList(params), CHARSET_UTF8);
                    } else {
                        formEntity = new UrlEncodedFormEntity(getParamsList(params), charset);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new Exception("不支持的编码集", e);
                }
            }

            hp = new HttpPost(url);
            hp.setEntity(formEntity);

        }

        if (headMap != null) {
            Set<String> strings = headMap.keySet();
            for (String s : strings) {
                if (hg != null) {
                    hg.setHeader(s, headMap.get(s));
                } else if (hp != null) {
                    hp.setHeader(s, headMap.get(s));
                }
            }
        }


        // 发送请求，得到响应
        String responseStr = null;
        try {
            if (hg != null) {
                responseStr = httpclient.execute(hg, responseHandler);
            } else if (hp != null) {
                responseStr = httpclient.execute(hp, responseHandler);
            }
        } catch (ClientProtocolException e) {
            throw new Exception("客户端连接协议错误", e);
        } catch (IOException e) {
            throw new Exception("IO操作异常", e);
        } finally {
            if (isDefaultHc) {
                abortConnection(hg, httpclient);
            }
        }
        return responseStr;


    }


    /**
     * 释放HttpClient连接
     *
     * @param hrb        请求对象
     * @param httpclient client对象
     */
    private static void abortConnection(final HttpRequestBase hrb, final HttpClient httpclient) {
        if (hrb != null) {
            hrb.abort();
        }
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
        }
    }


    /**
     * 将传入的键/值对参数转换为NameValuePair参数集
     *
     * @param paramsMap 参数集, 键/值对
     * @return NameValuePair参数集
     */
    private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
        }
        return params;
    }


    /**
     * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
     */
    private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        // 自定义的恢复策略
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            // 设置恢复策略，在发生异常时候将自动重试3次
            if (executionCount >= 3) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // Retry if the server dropped connection on us
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                // Do not retry on SSL handshake exception
                return false;
            }
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };


    /**
     * Post方式提交Body
     *
     */
    public static String postBody(String url, String body, String charset,HttpClient httpclient) throws Exception {
        boolean isDefaultHc = false;
        if (url == null || StringUtil.isEmpty(url)) {
            return null;
        }
        // 创建HttpClient实例
        if(httpclient==null)
        {
            httpclient = getDefaultHttpClient(null);
            isDefaultHc = true;
        }



        HttpPost hp = new HttpPost(url);
        try {
            hp.setEntity(new StringEntity(body));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // 发送请求，得到响应
        String responseStr = null;
        try {
            responseStr = httpclient.execute(hp, responseHandler);
        } catch (Exception e) {
            throw e;
        } finally {
            if(isDefaultHc)
            {
                abortConnection(hp, httpclient);
            }
        }
        return responseStr;
    }



    /**
     * 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
     */
    private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        // 自定义响应处理
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_GBK : EntityUtils.getContentCharSet(entity);
                return new String(EntityUtils.toByteArray(entity), charset);
            } else {
                return null;
            }
        }
    };

}
