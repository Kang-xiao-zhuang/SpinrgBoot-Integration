package com.zhuang.springbootnio.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dell
 * @Classname Regex09
 * @Description 判断URL的表达式
 * @Date 2021/5/23 14:42
 * @Created by dell
 */

public class Regex09 {
    public static void main(String[] args) {
        //String content = "https://www.bilibili.com/video/BV1fh411y7R8?from=search&seid=1831060912083761326";
        String content ="https://www.runoob.com/regexp/regexp-syntax.html";
        //注意：[. ? *]表示匹配就是.本身
        String regStr = "^((http|https)://)?([\\w-]+\\.)+[\\w-]+(\\/[\\w-?=&/%.#]*)?$";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()) {
            System.out.println("满足格式");
        } else {
            System.out.println("不满足格式");
        }
        //这里如果使用 Pattern 的 matches 整体匹配 比较简洁
        System.out.println(Pattern.matches(regStr, content));//
    }
}