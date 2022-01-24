package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname MatcherMethod
 * @Description  Matcher常用方法
 * @Date 2021/5/23 14:51
 * @Created by dell
 */

public class MatcherMethod {
    public static void main(String[] args) {
        String content = "hello edu jack kangxiaohhh hello hhkxz hello kkxxaa kxzkxz";
        String regStr = "hello";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("=================");
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            System.out.println("找到: " + content.substring(matcher.start(), matcher.end()));
        }
        //整体匹配方法，常用于，去校验某个字符串是否满足某个规则
        System.out.println("整体匹配=" + matcher.matches());
        //完成如果 content 有 kang 替换成 康小庄！！！
        regStr = "kang";
        pattern = Pattern.compile(regStr);
        matcher = pattern.matcher(content);
        //注意：返回的字符串才是替换后的字符串 原来的 content 不变化
        String newContent = matcher.replaceAll("康小庄！！！");
        System.out.println("newContent=" + newContent);
        System.out.println("content=" + content);
    }
}
