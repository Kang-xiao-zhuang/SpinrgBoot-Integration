# SpringBoot整合Minio

### 0. 安装Minio

拉取镜像

```shell
[root@VM-16-5-centos ~]# docker pull minio/minio
Using default tag: latest
latest: Pulling from minio/minio
d46336f50433: Pull complete 
be961ec68663: Pull complete 
44173c602141: Pull complete 
a9809a6a679b: Pull complete 
df29d4a76971: Pull complete 
2b5a8853d302: Pull complete 
84f01ee8dfc1: Pull complete 
Digest: sha256:d786220feef7d8fe0239d41b5d74501dc824f6e7dd0e5a05749c502fff225bf3
Status: Downloaded newer image for minio/minio:latest
docker.io/minio/minio:latest
```

启动镜像

```shell
docker run -p 9000:9000 -p 9001:9001 --name minio -d --restart=always -e "MINIO_ACCESS_KEY=admin" -e "MINIO_SECRET_KEY=admin123" -v /home/data:/data -v /home/config:/root/.minio minio/minio server --console-address ":9000" --address ":9001" /data
```

命令解释如下：

- **-p**：**9000**是图形界面的端口，**9001**是API的端口，在使用SDK连接需要用到
- **MINIO_ACCESS_KEY**：指定图形界面的用户名
- **MINIO_SECRET_KEY**：指定图形界面的密码

按照上述两个步骤启动成功即可。



> 注意：ACCESS_KEY 长度最少3个字符，SECRET_KEY 长度最少8个字符



安装成功后直接访问地址：**http:/ip:9000/login**，如下：



![在这里插入图片描述](https://img-blog.csdnimg.cn/ea78202fa7a543589343686a970236e6.png)



### **1. 获取accessKey和secretKey**

这里的**accessKey**和**secretKey**并不是图形界面登录名和密码，获取很简单，直接在图形界面中操作，如下图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/44b2030998c24933ae4970170019c7d5.png)



### 2. 导入依赖

添加MinIO的依赖，如下：

```xml
        <!-- minio依赖 -->
        <dependency>
            <groupId>io.minio</groupId>
            <artifactId>minio</artifactId>
            <version>8.3.7</version>
        </dependency>
```

### 3. 添加配置

在**aplication.yml**配置中添加MInIO相关的配置，如下：

```yaml
minio:
  # 访问的url
  endpoint: https://101.43.21.132
  # API的端口
  port: 9001
  # 秘钥
  accessKey: U2L0FKK2ZXCBEQF6R1FO
  secretKey: 86+up6VAxQlBZ88waNUYhHVbc1r8S5YAZyBKMoRH
  secure: false
  bucket-name: test # 桶名 我这是给出了一个默认桶名
  image-size: 10485760 # 我在这里设定了 图片文件的最大大小
  file-size: 1073741824 # 此处是设定了文件的最大大小
```

###  4. 新建上传文件接口

在进行文件上传之前需要编写上传工具类和配置类

参考官方实例代码如下： https://github.com/minio/minio-java-rest-example

控制器的上传文件接口，如下：

```java
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
```

### 5.测试接口

![在这里插入图片描述](https://img-blog.csdnimg.cn/c63ad70158d843dcb053f1f47e668650.png)

上传成功，查看页面是否保存成功

![在这里插入图片描述](https://img-blog.csdnimg.cn/cb1e3665e41446ab84dd6d9e613c1b0d.png)