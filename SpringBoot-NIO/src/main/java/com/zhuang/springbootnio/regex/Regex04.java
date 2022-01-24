package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Regex04
 * @Description 选择匹配符
 * @Date 2021/5/23 14:04
 * @Created by dell
 */

public class Regex04 {
    public static void main(String[] args) {
        String content = "kangxiaozhuang 康 扛抗";
        String regStr = "kang|康|扛";
        Pattern pattern = Pattern.compile(regStr/*, Pattern.CASE_INSENSITIVE*/);
        Matcher matcher =pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到 " + matcher.group(0));
        }
    }
}
