package com.dg11185.hnyz.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *  字节与字符串之间相互转换工具类
 *
 * @author xdweleven
 * @version 1.0
 * @since 2016-09-18
 * @Copyright 2016 全国邮政电子商务运营中心 All rights reserved.
 */
public class ByteStringConvertUtil {

    /**
     * 字符串转换为字节数据
     *
     * @param hexStr 待转换的字符串
     * @return 返回转换后的字节数组
     */
    public static byte[] hexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte result[] = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 字节数组转换为字符串
     *
     * @param buf 待转换的字节数组
     * @return 返回转换后的字符串
     */
    public static String byte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 255);
            if (hex.length() == 1)
                hex = (new StringBuilder(String.valueOf('0'))).append(hex).toString();
            sb.append(hex.toLowerCase());
        }
        return sb.toString();
    }

    public static byte[] inputStream2Bytes(InputStream inStream) {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outSteam.close();
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outSteam.toByteArray();
    }
}
