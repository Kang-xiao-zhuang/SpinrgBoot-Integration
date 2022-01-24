package com.zhuang.springbootnio.path;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo1 {
    public static void main(String[] args) {
        // 创建绝对路径
        Path path = Paths.get("d:\\01.txt");
        String originalPath = "d:\\zk\\projects\\..\\yygh-project";

        Path path1 = Paths.get(originalPath);
        System.out.println("path1 = " + path1);
        // Path 接口的 normalize()方法可以使路径标准化
        Path path2 = path1.normalize();
        System.out.println("path2 = " + path2);
    }
}
