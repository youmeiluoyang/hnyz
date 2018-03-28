package com.dg11185.dao;

import java.util.regex.Pattern;

/**
 * @author xiesp
 * @description
 * @date 4:46 PM  11/28/2017
 * @copyright 全国邮政电子商务运营中心
 */
public class Constants {

    //no whitespaces,no ","
    public static final Pattern PARAMETER_PATTERN = Pattern.compile("#[^{\\s,}]+#");


    //arithmetic operator key words
    public static final String ARITHMETIC_OPERATORS_RE = ">|<|!=|==|>=|<=";

    //logic operator key words
    public static final String LOGIC_OPERATORS_RE = "and|or|AND|OR";
}
