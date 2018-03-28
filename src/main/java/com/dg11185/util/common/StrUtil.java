package com.dg11185.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiesp
 * @description
 * @date 9:13 AM  11/29/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class StrUtil {



    //all integer,positive and negative
    public static final Pattern PARAMETER_INTEGER= Pattern.compile("^-?\\d+$");
    //successive whitespaces
    public static final String WHITE_SPACES_RE = "\\s+";



    public static boolean isInteger(String value){
        Matcher matcher = PARAMETER_INTEGER.matcher(value);
        return matcher.matches();
    }

    public static boolean isNullInText(String value){
        return "null".equals(value);
    }

    public static boolean isEmpty(String stirng){

        return stirng == null || stirng.equals("");
    }


    public static boolean isEmptyInText(String stirng){

        return stirng!=null && stirng.equals("");
    }
}
