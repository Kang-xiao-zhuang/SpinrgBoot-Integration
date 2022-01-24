package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Regex02
 * @Description 演示转义字符的使用
 * @Date 2021/5/23 13:37
 * @Created by dell
 */

public class Regex02 {
    public static void main(String[] args) {
        String content = "abc$(a.bc(123( )";
        //匹配( => \\(
        //匹配. => \\. //String regStr = "\\.";
        //String regStr = "\\d\\d\\d";
        String regStr = "\\d{3}";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到 " + matcher.group(0));
        }
    }
}
