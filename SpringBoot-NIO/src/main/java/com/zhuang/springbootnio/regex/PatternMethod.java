package com.zhuang.springbootnio.regex;

import java.util.regex.Pattern;

/**
 * @Classname PatternMethod
 * @Description 演示 matches 方法，用于整体匹配, 在验证输入的字符串是否满足条件使用
 * @Date 2021/5/23 14:49
 * @Created by dell
 */

public class PatternMethod {
    public static void main(String[] args) {
        String content = "hello abc hello, kang";
        //String regStr = "hello";
        String regStr = "hello.*";
        boolean matches = Pattern.matches(regStr, content);
        System.out.println("整体匹配= " + matches);
    }
}
