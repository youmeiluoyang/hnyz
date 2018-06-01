package com.dg11185.util.network;

import com.dg11185.hnyz.common.config.ResourceConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author xiesp
 * @description
 * @date 5:15 PM  5/4/2018
 * @copyright 全国邮政电子商务运营中心
 */
public class CertLoader {

    private static final String path = ResourceConfig.getAppConfig().getProperty("app.cert");
    public static  final SSLConnectionSocketFactory SSL_CONNECTION_SOCKET_FACTORY;
    static {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(new File(path)),
                    ResourceConfig.mchId.toCharArray());
            SSLContext sslcontext = SSLContexts.custom()
                    //忽略掉对服务器端证书的校验
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            return true;
                        }
                    })
                    .loadKeyMaterial(keyStore, ResourceConfig.mchId.toCharArray())
                    .build();
            SSL_CONNECTION_SOCKET_FACTORY = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static SSLConnectionSocketFactory getSSLConnectionSocketFactory(){
        return SSL_CONNECTION_SOCKET_FACTORY;
    }
}
