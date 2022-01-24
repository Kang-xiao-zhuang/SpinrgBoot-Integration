package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Homework03
 * @Description 案例3
 * @Date 2021/5/23 16:08
 * @Created by dell
 */

public class Homework03 {
    public static void main(String[] args) {
        String content="http://space.bilibili.com:8080/abc/favlist";
        String regStr="^([a-zA-Z]+)://([a-zA-Z.]+):(\\d+)[\\w-/]*/([\\w.]+)$";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);

        if (matcher.matches()){
            System.out.println("匹配成功！！！");
            System.out.println("协议->"+ matcher.group(0));
            System.out.println("域名->"+ matcher.group(1));
            System.out.println("端口->"+ matcher.group(2));
            System.out.println("文件名->"+ matcher.group(3));
        }else {
            System.out.println("匹配失败！！！");
        }
    }
}
