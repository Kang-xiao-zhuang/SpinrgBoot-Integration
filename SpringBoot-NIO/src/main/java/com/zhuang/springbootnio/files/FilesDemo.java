package com.zhuang.springbootnio.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesDemo {
    public static void main(String[] args) {
        FilesDemo filesDemo = new FilesDemo();
        filesDemo.walkFileTree();
    }

    /**
     * 文件创建
     */
    public void createFile() {
        Path path = Paths.get("d:\\88");
        try {
            // 创建文件
            Path directories = Files.createDirectories(path);
            System.out.println("directories = " + directories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件复制
     */
    public void copyFile() {
        // 文件复制方法
        Path sourcePath = Paths.get("d:\\01.txt");
        Path destinationPath = Paths.get("d:\\88\\002.txt");
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            // 目录已经存在
        } catch (IOException e) {
            // 其他发生的异常
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile() {
        Path deletepath = Paths.get("d:\\atguigu\\001.txt");
        try {
            Files.delete(deletepath);
        } catch (IOException e) {
            // 删除文件失败
            e.printStackTrace();
        }
    }

    /**
     * 查找文件
     */
    public void walkFileTree() {
        Path rootPath = Paths.get("d:\\88");
        String fileToFind = File.separator + "001.txt";
        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    System.out.println("pathString = " + fileString);
                    if (fileString.endsWith(fileToFind)) {
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     */
    public void moveFile() {
        // 文件复制方法
        Path sourcePath = Paths.get("d:\\01.txt");
        Path destinationPath = Paths.get("d:\\88\\002.txt");
        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            // 目录已经存在
        } catch (IOException e) {
            // 其他发生的异常
            e.printStackTrace();
        }
    }
}
