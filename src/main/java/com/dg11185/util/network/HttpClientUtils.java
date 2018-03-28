package com.dg11185.util.network;


import com.dg11185.hnyz.util.ByteStringConvertUtil;
import com.dg11185.hnyz.util.LogUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 说明：HTTP请求客户端类
 *
 * @author 谢德文
 * @version 1.0
 * @Copyright 2014 东莞市邮政局   All rights reserved.
 */
public class HttpClientUtils {

    private HttpClientUtils() {}


    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private final static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(5 * 1000);
        // 设置读取超时
        configBuilder.setSocketTimeout(12 * 1000);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(5 * 1000);
        // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }



    /**
     * get方式
     *
     * @param urlStr url地址
     * @return 返回数据
     */
    public static String getRequest(String urlStr) {
        HttpGet get = new HttpGet(urlStr);
        String resultStr =  HttpClientUtils.handleRequest(get,false);
        return resultStr;
    }

    /**
     * 发送Post请求
     *
     * @param url    请求链接
     * @param params 请求参数
     * @return
     */
    public static String postRequest(String url, String params) {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(params, "UTF-8");
        entity.setContentType("text/plain; charset=utf-8");
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        String resultStr =  HttpClientUtils.handleRequest(post,false);
        return resultStr;
    }

    /**
     * 发送Post请求
     *
     * @param url    请求链接
     * @param params 请求参数
     * @return
     */
    public static String postRequest(String url, Map<String,Object> params) {

        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(
                        entry.getKey(), String.valueOf(entry.getValue()));
                valuePairs.add(nameValuePair);
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "utf-8");
            post.setEntity(formEntity);
            String resultStr = handleRequest(post,false);
            return resultStr;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * post https请求,json格式的参数
     */
    public static String postByHttps(String urlStr,String params){
        HttpPost post = new HttpPost(urlStr);
        StringEntity entity = new StringEntity(params, "UTF-8");
        entity.setContentType("text/plain; charset=utf-8");
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        String resultStr = HttpClientUtils.handleRequest(post,true);
        return  resultStr;
    }


    /**
     * get https请求
     */
    public static String getByHttps(String urlStr){
        HttpGet get = new HttpGet(urlStr);
        String resultStr =  HttpClientUtils.handleRequest(get,true);
        return resultStr;
    }

    public static String postFileByFilePath(String url, String filePath){
        File file = new File(filePath);
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addPart("media", new FileBody(file, ContentType.create("image/jpeg"), file.getName()))
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(httpEntity);
        return handleRequest(httpPost, true);
    }

    public static String postFileByUrl(String toUrl, String fromUrl) {
        String responseStr = null;
        HttpGet httpGet = new HttpGet(fromUrl);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (200 == status) {
                // 获取响应对象
                HttpEntity resEntity = response.getEntity();
                // 读取响应内容
                byte[] bytes = ByteStringConvertUtil.inputStream2Bytes(resEntity.getContent());
                // 获得文件拓展名
                String contentType = resEntity.getContentType().getValue();
                // 拼接文件名
                String fileName = "cpwxyx." + contentType.substring(contentType.indexOf("/") + 1);
                // 销毁
                EntityUtils.consume(resEntity);

                // 构造请求体
                HttpEntity httpEntity = MultipartEntityBuilder.create()
                        .addPart("media", new ByteArrayBody(bytes, ContentType.create(contentType),fileName))
                        .build();
                HttpPost httpPost = new HttpPost(toUrl);
                httpPost.setEntity(httpEntity);
                // 开始上传
                responseStr = handleRequest(httpPost, true);
            }
        } catch (Exception e) {
            log.error("[HTTPCLIENTS]:网络请求发生错误:"+ LogUtil.getTrace(e));
        } finally {
            try {
                if(response !=null && response instanceof CloseableHttpClient){
                    CloseableHttpResponse closeableHttpResponse = (CloseableHttpResponse)response;
                    closeableHttpResponse.close();
                }
                if(httpClient instanceof CloseableHttpClient){
                    CloseableHttpClient closeableHttpClient = (CloseableHttpClient)httpClient;
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                log.error("[HTTPCLIENTS]:关闭HTTPCLIECNTS发生错误:"+LogUtil.getTrace(e));
            }
        }
        return responseStr;
    }

    //当请求完成的时候,做一些清理工作
    private static String handleRequest(HttpUriRequest request, boolean isHttps){
        long start = System.currentTimeMillis();
        String resultStr = "";
        //TODO 生产环境出现问题,解决的时候在换位HTTPS把
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();//isHttps?HttpClientUtils.getHttpsClient() :HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            // 打印响应状态
            int status = response.getStatusLine().getStatusCode();
            if (200 == status) {
                // 获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    resultStr = EntityUtils.toString(resEntity, "UTF-8");
                }
                // 销毁
                EntityUtils.consume(resEntity);
            }
            long end = System.currentTimeMillis();
            if(end -start >=3000){
                log.warn("[HTTPCLIENTS]:网络请求时间太长,一共耗时:{}毫秒请注意",end-start);
            }
        } catch (Exception e) {
            log.error("[HTTPCLIENTS]:网络请求发生错误:"+LogUtil.getTrace(e));
        } finally {
            try {
                if(response !=null){
                    response.close();
                }
                httpClient.close();

            } catch (IOException e) {
                log.error("[HTTPCLIENTS]:关闭HTTPCLIECNTS发生错误:"+LogUtil.getTrace(e));
            }
        }
        return resultStr;
    }


    /**
     * 创建SSLContext
     * @return
     */
    public static SSLContext getSSLContext(){
        SSLContext sslCtx = null;
        try{
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new NoopX509TrustManager() };
            sslCtx = SSLContext.getInstance("SSL", "SunJSSE");
            sslCtx.init(null, tm, new java.security.SecureRandom());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sslCtx;
    }

    /**
     * @return
     */
    public static SSLConnectionSocketFactory createSSLConnSocketFactory(){
        SSLContext sslContext = HttpClientUtils.getSSLContext();
        HostnameVerifier hnv = new HostnameVerifier() {

            public  boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[] {"TLSv1"}, null,hnv);
        return sslsf;
    }


    /**
     * 返回一个可以执行https请求的Client
     * @return
     */
    public static CloseableHttpClient getHttpsClient(){
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(HttpClientUtils.createSSLConnSocketFactory()).build();
        return httpclient;
    }

}

class NoopX509TrustManager implements X509TrustManager {

    // 检查客户端证书
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    // 检查服务器端证书
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    // 返回受信任的X509证书数组
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
