package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Regex10
 * @Description 案例
 * @Date 2021/5/23 15:26
 * @Created by dell
 */

public class Regex10 {
    public static void main(String[] args) {
        String content="h23423288888856666 jackangdaxxxiiit6886  65432-999888777";
        // 匹配连续两个相同的数字
       // String regStr="(\\d)\\1";
       // 匹配连续五个相同的数字
       // String regStr="(\\d)\\1{4}";
       //匹配 个位和千位相同 十位和百位相同的数字
       //   String regStr="(\\d)(\\d)\\2\\1";
        // 匹配 形如12321-333666999 这样的号码满足前面一个五位数
//        然后一个- 然后是一个九位数，连续的每三位要相同
        String regStr="\\d{5}-(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}";
       Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            System.out.println("找到->"+matcher.group(0));
        }
    }
}
