package com.dg11185.hnyz.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 加密工具类：MD5、SHA256、SHA-1等加密以及BASE64编码与解码。
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class EncryptUtil {

    private final static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    public static final String DEFAULT_CHARSET = "UTF-8";


    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};



    protected EncryptUtil() {}


    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String sha1(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5加密
     *
     * @param data 待加密的字符串
     * @return 返回经过MD5加密后的后密文
     */
    public static String MD5Digest(String data) {
        if(StringUtils.isBlank(data)){
            return null;
        }
        try {
            return DigestUtils.md5Hex(data.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error("MD5加密失败："+e.fillInStackTrace());
            return null;
        }
    }

    /**
     * BASE64编码
     *
     * @param data 待编码的明文字符串
     * @return 返回经过base64编码后的字符串
     */
    public static String base64Encode(String data, String charSet) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        try {
            return (new BASE64Encoder()).encodeBuffer(
                    data.getBytes(StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET)).trim();
        } catch (Exception e) {
            logger.error("base64Encode加密失败：" + e.fillInStackTrace());
            return null;
        }

    }

    /**
     * 解码BASE64
     *
     * @param key 待解码的字符串
     * @return 返回解码后的明文字符串
     */
    public static String base64Decode(String key, String charSet) {
        try {
            return new String((new BASE64Decoder()).decodeBuffer(key),
                    StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("base64Decode解密失败：" + e.fillInStackTrace());
            return null;
        }
    }

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param data 要加密的数据
     * @param encryptType 加密类型
     * @return 返回16进制形式的密文字符串
     */
    public static String encrypt(String data, String encryptType, String charSet) {
        try {
            byte[] bt = data.getBytes(StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET);
            if (encryptType == null || encryptType.equals("")) {
                encryptType = "SHA-256";
            }
            MessageDigest md = MessageDigest.getInstance(encryptType);
            md.update(bt);
            return bytes2Hex(md.digest());
        } catch (Exception e) {
            logger.error("[ "+encryptType+" ]加密失败：" + e.fillInStackTrace());
            return null;
        }
    }

    /**
     * 将字符数组转换为16进制
     *
     * @param bts 待转换的字符数组
     * @return 返回16进制形式的字符串
     */
    public static String bytes2Hex(byte[] bts) {
        StringBuilder desBuf = new StringBuilder();
        for (int i = 0; i < bts.length; i++) {
            String tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                desBuf.append("0");
            }
            desBuf.append(tmp);
        }
        return desBuf.toString();
    }


    /**
     * 根据键值进行DES加密
     *
     * @param data 待加密的明文
     * @param key 加密键byte数组
     * @return 返回加密后的密文
     */
    public static String desEncrypt(String data, String key, String charSet){
        try {
            byte[] bt = desEncrypt0(data.getBytes(StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET),
                    key.getBytes(StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET));
            return new BASE64Encoder().encode(bt);
        } catch (Exception e) {
            logger.error("DES加密失败：" + e.fillInStackTrace());
            return null;
        }
    }

    /**
     * 根据键值进行DES解密
     *
     * @param secret 待解密的密文
     * @param key 加密键byte数组
     * @return 返回解密后的明文
     */
    public static String desDecrypt(String secret, String key, String charSet) {
        try {
            if (secret == null){
                return null;
            }
            byte[] buf = new BASE64Decoder().decodeBuffer(secret);
            byte[] bt = desDecrypt0(buf, key.getBytes(StringUtils.isNotBlank(charSet) ?
                    charSet : DEFAULT_CHARSET));
            return new String(bt, StringUtils.isNotBlank(charSet) ? charSet : DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("DES解密失败：" + e.fillInStackTrace());
            return null;
        }
    }

    /**
     * 根据键值进行DES加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] desEncrypt0(byte[] data, byte[] key) throws Exception{
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }


    /**
     * 根据键值进行DES解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] desDecrypt0(byte[] data, byte[] key) throws Exception{
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    private static final String AESTYPE ="AES/ECB/PKCS5Padding";


    /**
     * 加密
     * @param keyStr 密钥
     * @param plainText 要加密的内容
     * @return
     */
    public static String AES_Encrypt(String keyStr, String plainText) {
        byte[] encrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(encrypt);
    }

    private static Key generateKey(String key) {
        return new SecretKeySpec(key.getBytes(), "AES");
    }


    public static void main(String[] args) throws Exception{
//        平安H5支付

//        明文串：
//        String mingwenStr = dataSource + customerName + businessNo + currencyNo + amount + KEY;
//        常量KEY：OPENAPI
//        密文串：
//        String signMsg = MD5Util.md5Hex(mingwenStr);
        String mingwenStr = "68" + "18103083489" + "42000088000007890857" + "RMB" + "6919.8" + "OPENAPI";
        System.out.println(EncryptUtil.encrypt(mingwenStr, "MD5", "UTF-8"));

//        平安H5支付回调

//        明文串：
//        String mingwenStr = paymentDate + documentNo + paymentState + remark + paymentSum
//                  + bankOrderNo + businessNo + errorMsg + 配置的key;
//
//        密文串：
//        String signMsg = StringUtils.upperCase(MD5Util.md5Hex(mingwenStr));
        mingwenStr = "20160524150746" + "" + "1" + "openApi,http://121.40.109.149:12301/trafficService/insurance/paic/fileChanged.do"
            + "" + "39161000000000977070" + "42000088000007878107" + "���׳ɹ�" + "OPENAPI";
        System.out.println(EncryptUtil.encrypt(mingwenStr, "MD5", "UTF-8").toUpperCase());

        System.out.println(URLEncoder.encode("n2Hj5z++Mw2ocU4dq0/6AzRJazG8CxIPaxiftyg7hplgApXvXm+Y10W8KM2Mu24L", "UTF-8"));

        String str = "bkdy/salesmanCard/" + "111111113";

        System.out.println(EncryptUtil.base64Encode(str, "UTF-8").trim());

    }

}
