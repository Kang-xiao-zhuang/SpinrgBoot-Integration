package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Regex06
 * @Description 演示定位符的使用
 * @Date 2021/5/23 14:24
 * @Created by dell
 */

public class Regex06 {
    public static void main(String[] args) {
        String content = "kangxiaozhuang spkang nnkang";
    //String content = "123-abc";
    //以至少 1 个数字开头，后接任意个小写字母的字符串
    //String regStr = "^[0-9]+[a-z]*";
    //以至少 1 个数字开头, 必须以至少一个小写字母结束
    //String regStr = "^[0-9]+\\-[a-z]+$";
    //表示匹配边界的 han[这里的边界是指：被匹配的字符串最后, // 也可以是空格的子字符串的后面]
    String regStr = "kang\\b";
    //和\\b 的含义刚刚相反
//        String regStr = "kang\\B";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到=" + matcher.group(0));
        }
    }
}
