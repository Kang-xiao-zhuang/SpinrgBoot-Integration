package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Regex01
 * @Description 体验正则表达式的威力
 * @Date 2021/5/23 12:58
 * @Created by dell
 */

public class Regex01 {
    public static void main(String[] args) {
        String content = "1998 年 12 月 8 日，第二代 Java 平台的企业版 J2EE 发布。1999 年 6 月，Sun 公司发布了" +
                "第二代 Java 平台（简称为 Java2）的 3 个版本：J2ME（Java2 Micro Edition，Java2 平台的微型" +
                "版），应用于移动、无线及有限资源的环境；J2SE（Java 2 Standard Edition，Java 2 平台的" +
                "标准版），应用于桌面环境；J2EE（Java 2Enterprise Edition，Java 2 平台的企业版），应" +
                "用 3443 于基于 Java 的应用服务器。Java 2 平台的发布，是 Java 发展过程中最重要的一个" +
                "里程碑，标志着 Java 的应用开始普及 9889 ";
        
        //目标匹配所有四个数字
        // \\d表示任意一个数字
        String regStr="(\\d\\d)(\\d\\d)";
        // 创建模式对象 即 正则表达式对象
        Pattern pattern = Pattern.compile(regStr);
        // 创建匹配器 按照正则表达式的规则去匹配content字符串
        Matcher matcher = pattern.matcher(content);

        matcher.find();
        while (matcher.find()){
            System.out.println("找到->"+matcher.group(0));
            System.out.println("第一组找到的值->"+matcher.group(1));
            System.out.println("第二组找到的值->"+matcher.group(2));
        }
    }
}
