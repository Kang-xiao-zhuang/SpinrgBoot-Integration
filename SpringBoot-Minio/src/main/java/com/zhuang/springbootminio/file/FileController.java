package com.zhuang.springbootminio.file;

import com.zhuang.springbootminio.utils.FileTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    // 通过构造注入业务层对象
    private final MinioService minioService;

    /**
     * 上传
     *
     * @param file       文件
     * @param bucketName 存储桶名称
     * @return {@link String} 文件的访问路径
     */
    @PostMapping("upload")
    public String upload(MultipartFile file, String bucketName) {
        if (Objects.isNull(file)) {
            return "上传的文件不能为空!";
        }
        // 继续上传文件
        String type = FileTypeUtils.getFileType(file);
        return minioService.putObject(file, bucketName, type);
    }

    /**
     * 列出来存储桶中的所有文件
     *
     * @param bucketName bucket名称
     * @return {@link String} 所有文件
     */
    @GetMapping("list")
    public List<String> list(String bucketName) {
        return minioService.listObjectNames(bucketName);
    }
}

