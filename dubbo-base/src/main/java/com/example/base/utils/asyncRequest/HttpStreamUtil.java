package com.example.base.utils.asyncRequest;

import com.example.base.utils.StringUtils;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpStreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(
        HttpStreamUtil.class);

    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 30000;// 30秒
    // 读数据超时时间
    private static final int READ_DATA_TIMEOUT = 30000;// 30秒
    // default  encoding
    private static final String D_ENCODING = "UTF-8";// 30秒

    /**
     *
     * @param url eg: https://www.baidu.com
     * @param param 请求参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> param) throws IOException {
        return post(url, param, D_ENCODING);
    }

    /**
     *
     * @param url eg: https://www.baidu.com
     * @param param 请求参数
     * @param encoding 编码格式
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> param,String encoding) throws IOException {
        return post(url, new HashMap<>(), param, encoding);
    }

    /**
     *
     * @param url eg: https://www.baidu.com
     * @param headers 头信息
     * @param param 请求参数
     * @param encoding 编码格式
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> headers,
        Map<String, String> param, String encoding) throws IOException {
        return post(url, HttpStreamUtil.CONNECTION_TIMEOUT, HttpStreamUtil.READ_DATA_TIMEOUT, headers, param,encoding);
    }



    public static String get(String url, Map<String, String> param) throws IOException {
        return get(url, param, D_ENCODING);
    }

    public static String get(String url, Map<String, String> param,String encoding) throws IOException {
        return get(url, new HashMap<>(), param, encoding);
    }

    public static String get(String url, Map<String, String> headers,
        Map<String, String> param, String encoding) throws IOException {
        return get(url, headers, param, CONNECTION_TIMEOUT, READ_DATA_TIMEOUT,  encoding);
    }

    private static String get(String url, Map<String, String> headers, Map<String, String> param,
        int connectTimeout, int readTimeout, String encoding) throws IOException {
        if (null != param && !param.isEmpty()) {
            String paramStr = mapToQueryStr(param);
            url = url + "?" + paramStr;
        }

        HttpGet requestGet = new HttpGet(url);

        // 配置
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(readTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectTimeout)
            .setExpectContinueEnabled(false).build();
        requestGet.setConfig(requestConfig);

        // 头信息
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                requestGet.addHeader(e.getKey(), e.getValue());
            }
        }

        // send request
        CloseableHttpResponse response = wrapClient(url).execute(requestGet);

        // get response
        return getHttpEntityStr(response,encoding);
    }

    /**
     *
     * @param url eg: https://www.baidu.com
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取
     * @param headers 头信息
     * @param param 请求参数
     * @param encoding 编码格式
     * @return
     * @throws IOException
     */
    private static String post(String url, int connectTimeout, int readTimeout,
        Map<String, String> headers, Map<String, String> param, String encoding)
        throws IOException {

        HttpPost requestPost = new HttpPost(url);

        // 配置
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(readTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectTimeout)
            .setExpectContinueEnabled(false).build();
        requestPost.setConfig(requestConfig);

        // 头信息
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                requestPost.addHeader(new BasicHeader(e.getKey(), e.getValue()));
            }
        }

        // 请求数据
        if (null != param && !param.isEmpty()) {
            List<NameValuePair> formparams = new ArrayList<>();
            for (String key : param.keySet()) {
                formparams.add(new BasicNameValuePair(key, param.get(key)));
            }

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                formparams, encoding);
            requestPost.setEntity(formEntity);
        }

        // send request
        CloseableHttpResponse response = wrapClient(url).execute(requestPost);

        // get response
        return getHttpEntityStr(response, encoding);
    }

    public static String invokePost(String url, Map<String, String> headers,String content) throws IOException{
        return invokePost(url,CONNECTION_TIMEOUT,READ_DATA_TIMEOUT,headers, content,D_ENCODING);
    }

    private static String invokePost(String url, int connectTimeout, int readTimeout,
        Map<String, String> headers, String content, String encoding)
        throws IOException {
        HttpPost requestPost = new HttpPost(url);

        // 配置
        RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(readTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectTimeout)
            .setExpectContinueEnabled(false).build();
        requestPost.setConfig(requestConfig);

        // 头信息
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                requestPost.addHeader(new BasicHeader(e.getKey(), e.getValue()));
            }
        }

        // 请求数据
        if (!StringUtils.isNullOrEmpty(content)) {
            // validate json
            try {
                new JsonParser().parse(content);
            } catch (JsonParseException e) {
                logger.error("bad content: " + content + "not json");
            }
            HttpEntity httpEntity = EntityBuilder.create()
                .setText(content)
                .setContentType(ContentType.APPLICATION_JSON)
                .setContentEncoding(encoding).build();

            requestPost.setEntity(httpEntity);
        }

        // send request
        CloseableHttpResponse response = wrapClient(url).execute(requestPost);

        return getHttpEntityStr(response, encoding);
    }

    /**
     * 获取返回 结果
     * @param response
     * @param encoding
     * @return 返回结果
     * @throws IOException
     */
    private static String getHttpEntityStr(CloseableHttpResponse response, String encoding) throws IOException  {
        HttpEntity entity = response.getEntity();
        String str = null;
        try {
            if (entity != null) {
                str = EntityUtils.toString(entity, encoding);
                return str;
            }
        } finally {
            if (entity != null) {
                entity.getContent().close();
            }
            if (response != null) {
                response.close();
            }
        }
        return str;
    }

    private static String mapToQueryStr(Map<String, String> paramMap) {
        StringBuffer strBuff = new StringBuffer();
        for (String key : paramMap.keySet()) {
            strBuff.append(key).append("=").append(paramMap.get(key))
                .append("&");
        }
        return strBuff.substring(0, strBuff.length() - 1);
    }

    /**
     * 获取 HttpClient
     * @param url
     * @return
     */
    private static CloseableHttpClient wrapClient(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        if (!StringUtils.isNullOrEmpty(url) && url.startsWith("https://")) {
            httpClient = sslClient();
        }

        return httpClient;
    }

    /**
     * HttpClient 支持https
     * @return
     */
    private static CloseableHttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override public void checkClientTrusted(X509Certificate[] xcs, String str) {}
                @Override public void checkServerTrusted(X509Certificate[] xcs, String str) {}
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[] { trustManager }, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(
                    Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https",socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }



}
