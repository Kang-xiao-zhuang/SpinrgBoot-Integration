package com.zhuang.springbootnio.regex;

/**
 * @Classname Homework02
 * @Description 案例2
 * @Date 2021/5/23 16:08
 * @Created by dell
 */

public class Homework02 {
    public static void main(String[] args) {
        String content="-0.58";
        String regStr="^[+-]?([1-9]\\d*|0)(\\.\\d+)?$";

        if (content.matches(regStr)){
            System.out.println("匹配成功 是正数或者小数！！！");
        }else {
            System.out.println("匹配失败！！！");
        }
    }
}
