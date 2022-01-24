package com.zhuang.springbootnio.regex;

/**
 * @Classname Homework01
 * @Description 案例1
 * @Date 2021/5/23 16:08
 * @Created by dell
 */

public class Homework01 {
    public static void main(String[] args) {
        String content="itkxz@qq.com";
        String regStr="[\\w-_]+@([a-zA-Z]+\\.)+[a-zA-Z]+";

        if (content.matches(regStr)){
            System.out.println("匹配成功！！！");
        }else {
            System.out.println("匹配失败！！！");
        }
    }
}
