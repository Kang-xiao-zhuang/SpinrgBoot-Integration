package com.zhuang.springbootminio.file;

import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioService {

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName String
     * @return boolean
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建 bucket
     *
     * @param bucketName String
     */
    void makeBucket(String bucketName);

    /**
     * 列出所有存储桶名称
     *
     * @return List<String>
     */
    List<String> listBucketName();

    /**
     * 列出所有存储桶 信息
     *
     * @return List<Bucket>
     */
    List<Bucket> listBuckets();

    /**
     * 根据桶名删除桶
     *
     * @param bucketName boolean
     */
    boolean removeBucket(String bucketName);

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName String
     * @return List<String>
     */
    List<String> listObjectNames(String bucketName);

    /**
     * 文件上传
     *
     * @param multipartFile MultipartFile
     * @param bucketName    String
     */
    String putObject(MultipartFile multipartFile, String bucketName, String fileType);

    /**
     * 文件流下载
     *
     * @param bucketName String
     * @param objectName String
     * @return InputStream
     */
    InputStream downloadObject(String bucketName, String objectName);


    /**
     * 删除文件
     *
     * @param bucketName String
     * @param objectName String
     */
    boolean removeObject(String bucketName, String objectName);


    /**
     * 批量删除文件
     *
     * @param bucketName     String
     * @param objectNameList List<String>
     * @return boolean
     */
    boolean removeListObject(String bucketName, List<String> objectNameList);

    /**
     * 获取文件路径
     *
     * @param bucketName String
     * @param objectName String
     * @return String
     */
    String getObjectUrl(String bucketName, String objectName);
}
