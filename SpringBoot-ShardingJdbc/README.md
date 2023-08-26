# ShardingJdbc学习笔记
# 什么是ShardingSphere

1、一套开源的分布式数据库中间件解决方案
2、有三个产品：Sharding-JDBC和Sharding-Proxy
3、定位为关系型数据库中间件，合理在分布式环境下使用关系型数据库操作

## 什么是分库分表

数据据库数据量是不可控的，随着时间和业务发展，造成表里面数据越来越多，如果再去对数据库表curd操作时，就会有性能问题。
解决办法：

- 方案1：从硬件上
- 方案2：分库分表

![](https://img-blog.csdnimg.cn/e55b01ded6eb4927a757bb04dbb3d392.png)

**分库分表的方式**

1、分库分表有两种方式：垂直切分和水平切分
2、垂直切分：垂直分表和垂直分库
3、水平切分：水平分表和水平分库

4、垂直分表
（1）操作数据库中某张表，把这张表中一部分字段数据存到一张新表里面，再把这张表另一部分字段数据存到另外一张表里面

垂直拆分成的两张表，每张表的数据量和原先的表的数据量相同。

![](https://img-blog.csdnimg.cn/99afcc926daa49178f6160d7fb3adb19.png)



5、垂直分库
把单一数据库按照业务进行划分，专库专表

![](https://img-blog.csdnimg.cn/69804c4603a545d2a2f27c9eb29bf630.png)



6、水平分库

![](https://img-blog.csdnimg.cn/b423f77d58084df98d0f9c35a1492ede.png)





7、水平分表

![](https://img-blog.csdnimg.cn/47c8857994f146f6b47d035109d4cf2c.png)

## 分库分表应用和问题

1、应用
（1）在数据库设计时候考虑垂直分库和垂直分表
（2）随着数据库数据量增加，不要马上考虑做水平切分，首先考虑缓存处理，读写分离，使用索引等等方式，如果这些方式不能根本解决问题了，再考虑做水平分库和水平分表
2、分库分表问题
（1）跨节点连接查询问题（分页、排序）
（2）多数据源管理问题

## ShardingJDBC 简介

![](https://img-blog.csdnimg.cn/a4bd9deae8b64958be45a5f5625ec77d.png)

1、是轻量级的java框架，是增强版的JDBC驱动
2、Sharding-JDBC
（1）主要目的是：简化对分库分表之后数据相关操作

## Sharding-JDBC 实现水平分表

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.zhuang</groupId>
    <artifactId>shardingjdbcdemo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>shardingjdbcdemo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.20</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.0.0-RC1</version>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${project.parent.version}</version>
            </plugin>
        </plugins>
    </build>

</project>
```



### 按照水平分表的方式创建数据库、数据表

1. 创建数据库 course_db。
2. 在数据库中创建两张表 course_1 和 course_2。
3. 数据存放约定规则：添加的数据id为偶数放 course_1 表中，id为奇数放 course_2 表中。

```sql
create database course_db;

use course_db;

create table course_1 (
                          `cid` bigint(20) primary key,
                          `cname` varchar(50) not null,
                          `user_id` bigint(20) not null,
                          `cstatus` varchar(10) not null
);

create table course_2 (
                          `cid` bigint(20) primary key,
                          `cname` varchar(50) not null,
                          `user_id` bigint(20) not null,
                          `cstatus` varchar(10) not null
);
```

在 application.properties 配置文件中配置

```properties
# shardingjdbc 水平分表策略
# 配置数据源，给数据源起别名
spring.shardingsphere.datasource.names=m1

# 一个实体类对应两张表，覆盖
spring.main.allow-bean-definition-overriding=true

# 配置数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/course_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=root

# 指定course表分布的情况，配置表在哪个数据库里，表的名称都是什么 m1.course_1,m1.course_2
spring.shardingsphere.sharding.tables.course.actual-data-nodes=m1.course_$->{1..2}

# 指定 course 表里面主键 cid 的生成策略 SNOWFLAKE
spring.shardingsphere.sharding.tables.course.key-generator.column=cid
spring.shardingsphere.sharding.tables.course.key-generator.type=SNOWFLAKE

# 配置分表策略    约定 cid 值偶数添加到 course_1 表，如果 cid 是奇数添加到 course_2 表
spring.shardingsphere.sharding.tables.course.table-strategy.inline.shardingcolumn=cid
spring.shardingsphere.sharding.tables.course.table-strategy.inline.algorithmexpression=course_$->{cid % 2 + 1}

# 打开 sql 输出日志
spring.shardingsphere.props.sql.show=true
```

### 编写测试代码

在test包中的测试类中进行测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    @Autowired
    private CourseMapper courseMapper;

    //添加课程
    @Test
    public void addCourse(){
        Course course = new Course();
        //cid由我们设置的策略，雪花算法进行生成（至少70年内生成的id不会重复）
        course.setCname("java");
        course.setUserId(100L);
        course.setCstatus("Normal");

        courseMapper.insert(course);
    }

    //查询课程
    @Test
    public void findCourse(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 509755853058867201L);
        courseMapper.selectOne(wrapper);
    }
}
```

插入测试

![](https://img-blog.csdnimg.cn/cea150dd71d6499bb834f855ada23d88.png)



## Sharding-JDBC 实现水平分库

![](https://img-blog.csdnimg.cn/dd66149532a44d7fb6f7fa0bdf45ba50.png)

### 创建数据库，数据表

```java
create database edu_db_1;
        create database edu_db_2;

        use edu_db_1;

        create table course_1 (
        `cid` bigint(20) primary key,
        `cname` varchar(50) not null,
        `user_id` bigint(20) not null,
        `cstatus` varchar(10) not null
        );

        create table course_2 (
        `cid` bigint(20) primary key,
        `cname` varchar(50) not null,
        `user_id` bigint(20) not null,
        `cstatus` varchar(10) not null
        );

        use edu_db_2;

        create table course_1 (
        `cid` bigint(20) primary key,
        `cname` varchar(50) not null,
        `user_id` bigint(20) not null,
        `cstatus` varchar(10) not null
        );

        create table course_2 (
        `cid` bigint(20) primary key,
        `cname` varchar(50) not null,
        `user_id` bigint(20) not null,
        `cstatus` varchar(10) not null
        );
```

### 配置水平分库策略

```properties
# shardingjdbc 水平分库分表策略
# 配置数据源，给数据源起别名
# 水平分库需要配置多个数据库
spring.shardingsphere.datasource.names=m1,m2

# 一个实体类对应两张表，覆盖
spring.main.allow-bean-definition-overriding=true

# 配置第一个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/edu_db_1?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=root

# 配置第二个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m2.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m2.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m2.url=jdbc:mysql://localhost:3306/edu_db_2?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m2.username=root
spring.shardingsphere.datasource.m2.password=root

# 指定数据库分布的情况和数据表分布的情况
# m1 m2   course_1 course_2
spring.shardingsphere.sharding.tables.course.actual-data-nodes=m$->{1..2}.course_$->{1..2}

# 指定 course 表里面主键 cid 的生成策略 SNOWFLAKE
spring.shardingsphere.sharding.tables.course.key-generator.column=cid
spring.shardingsphere.sharding.tables.course.key-generator.type=SNOWFLAKE

# 指定分库策略    约定 user_id 值偶数添加到 m1 库，如果 user_id 是奇数添加到 m2 库
# 默认写法（所有的表的user_id）
#spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=user_id
#spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=m$->{user_id % 2 + 1}
# 指定只有course表的user_id
spring.shardingsphere.sharding.tables.course.database-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.course.database-strategy.inline.algorithm-expression=m$->{user_id % 2 + 1}

# 指定分表策略    约定 cid 值偶数添加到 course_1 表，如果 cid 是奇数添加到 course_2 表
spring.shardingsphere.sharding.tables.course.table-strategy.inline.sharding-column=cid
spring.shardingsphere.sharding.tables.course.table-strategy.inline.algorithm-expression=course_$->{cid % 2 + 1}

# 打开 sql 输出日志
spring.shardingsphere.props.sql.show=true
```

### 编写测试代码

```java
	//添加课程
@Test
public void addCourseDb(){
        Course course = new Course();
        //cid由我们设置的策略，雪花算法进行生成（至少70年内生成的id不会重复）
        course.setCname("javademo");
        //分库根据user_id
        course.setUserId(100L);
        course.setCstatus("Normal");
        courseMapper.insert(course);

        course.setCname("javademo2");
        course.setUserId(111L);
        courseMapper.insert(course);
        }

//查询课程
@Test
public void findCourseDb(){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置user_id的值
        wrapper.eq("user_id", 100L);
        //设置cid的值
        wrapper.eq("cid", 509771111076986881L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
        }
```

插入测试

![](https://img-blog.csdnimg.cn/719869ba194d4c05ba874a6866bf92a1.png)

## Sharding-JDBC 实现垂直分库

![](https://img-blog.csdnimg.cn/d682c00bfc8a471db8f47020ab4f5bc2.png)

需要查询用户信息的时候，不需要查到课程信息。

```sql
create database user_db;

use user_db;

create table t_user(
                       `user_id` bigint(20) primary key,
                       `username` varchar(100) not null,
                       `ustatus` varchar(50) not null
);
```

### 配置垂直分库策略

```properties
# shardingjdbc 垂直分库策略
# 配置数据源，给数据源起别名
# 水平分库需要配置多个数据库
# m0为用户数据库
spring.shardingsphere.datasource.names=m1,m2,m0

# 一个实体类对应两张表，覆盖
spring.main.allow-bean-definition-overriding=true

# 配置第一个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/edu_db_1?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=root

# 配置第二个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m2.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m2.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m2.url=jdbc:mysql://localhost:3306/edu_db_2?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m2.username=root
spring.shardingsphere.datasource.m2.password=root

# 配置user数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m0.url=jdbc:mysql://localhost:3306/user_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m0.username=root
spring.shardingsphere.datasource.m0.password=root
# 配置user_db数据库里面t_user  专库专表
spring.shardingsphere.sharding.tables.t_user.actual-data-nodes=m0.t_user
# 配置主键的生成策略
spring.shardingsphere.sharding.tables.t_user.key-generator.column=user_id
spring.shardingsphere.sharding.tables.t_user.key-generator.type=SNOWFLAKE
# 指定分表策略
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.algorithm-expression=t_user

# 打开 sql 输出日志S
spring.shardingsphere.props.sql.show=true
```

### 编写测试代码

```java
    //添加用户
@Test
public void addUserDb(){
        User user = new User();
        user.setUsername("张三");
        user.setUstatus("a");
        userMapper.insert(user);
        }

@Test
//查询用户
public void findUserDb(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 509793334663839745L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
        }
```

插入测试

![](https://img-blog.csdnimg.cn/ec9d39b2d90b4729ae9e7f01a366f836.png)

查询测试

![](https://img-blog.csdnimg.cn/e332375a62ad4170b821668d16f0a153.png)

## Sharding-JDBC 公共表

### 公共表概念

1. 存储固定数据的表，表数据很少发生变化，查询时经常要进行关联。
2. 在每个数据库中都创建出相同结构公共表。
3. 操作公共表时，同时操作添加了公共表的数据库中的公共表，添加记录时，同时添加，删除时，同时删除。

### 在多个数据库中创建相同结构的公共表

```sql
use user_db;
#use edu_db_1;
#use edu_db_2;

create table t_udict(
                        `dictid` bigint(20) primary key,
                        `ustatus` varchar(100) not null,
                        `uvalue` varchar(100) not null
);
```

### 公共表配置

```properties
# shardingjdbc 垂直分库策略
# 配置数据源，给数据源起别名
# 水平分库需要配置多个数据库
# m0为用户数据库
spring.shardingsphere.datasource.names=m1,m2,m0

# 一个实体类对应两张表，覆盖
spring.main.allow-bean-definition-overriding=true

# 配置第一个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/edu_db_1?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=root

# 配置第二个数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m2.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m2.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m2.url=jdbc:mysql://localhost:3306/edu_db_2?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m2.username=root
spring.shardingsphere.datasource.m2.password=root

# 配置user数据源的具体内容，包含连接池，驱动，地址，用户名，密码
spring.shardingsphere.datasource.m0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m0.url=jdbc:mysql://localhost:3306/user_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m0.username=root
spring.shardingsphere.datasource.m0.password=root
# 配置user_db数据库里面t_user  专库专表
spring.shardingsphere.sharding.tables.t_user.actual-data-nodes=m0.t_user
# 配置主键的生成策略
spring.shardingsphere.sharding.tables.t_user.key-generator.column=user_id
spring.shardingsphere.sharding.tables.t_user.key-generator.type=SNOWFLAKE
# 指定分表策略
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.algorithm-expression=t_user

# 公共表配置
spring.shardingsphere.sharding.broadcast-tables=t_udict
# 配置主键的生成策略
spring.shardingsphere.sharding.tables.t_udict.key-generator.column=dictid
spring.shardingsphere.sharding.tables.t_udict.key-generator.type=SNOWFLAKE

# 打开 sql 输出日志
spring.shardingsphere.props.sql.show=true
```

### 编写测试代码

```java
    //添加
@Test
public void addDict(){
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
        }

//删除
@Test
public void deleteDict(){
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        wrapper.eq("dictid", 509811689974136833L);
        udictMapper.delete(wrapper);
        }
```

插入测试

![](https://img-blog.csdnimg.cn/08b44d49d5e64f6a86a07be757cccf13.png)

## Sharding-JDBC 实现读写分离

![](https://img-blog.csdnimg.cn/b09083479fe14a2d8fa19bfa9dca0a5e.png)

**读写原理**

主从复制：当主服务器写入时（insert/update/delete）语句时候，从服务器自动回去

读写分离：insert/update/delete语句操作一台服务器，select操作另一台服务器

![](https://img-blog.csdnimg.cn/9196494f5492428fb8e3093f2279b019.png)



### Sharding-JDBC 读写分离

Sharding-JDBC通过sql语句语义分析，当sql语句有insert、update、delete时，Sharding-JDBC就把这次操作在主数据库上执行；当sql语句有select时，就会把这次操作在从数据库上执行，从而实现读写分离过程。但Sharding-JDBC并不会做数据同步，数据同步是配置MySQL后由MySQL自己完成的。

参考：https://blog.csdn.net/qq_36903261/article/details/108457759

镜像更换：

```shell
$ mv /etc/apt/sources.list /etc/apt/sources.list.bak
 
$ echo  "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ buster main contrib non-free" >/etc/apt/sources.list
$ echo  "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ buster-updates main contrib non-free" >>/etc/apt/sources.list
$ echo  "deb http://mirrors.tuna.tsinghua.edu.cn/debian/ buster-backports main contrib non-free" >>/etc/apt/sources.list
echo  "deb http://mirrors.tuna.tsinghua.edu.cn/debian-security buster/updates main contrib non-free" >>/etc/apt/sources.list


$ apt-get update

$ apt-get install -y vim
```

### MySQL 一主一从读写分离配置

因为记录的文件名以及位点每次重启或刷新都会改变，所以以下命令放在这里，方便查看。

主mysql：

```sql
#确认位点 记录下文件名以及位点（重启或者刷新都会改变）
show master status;
```

从mysql：

```sql
#先停止同步
STOP SLAVE;

#修改从库指向到主库，使用上一步记录的文件名以及位点
# master_host docker容器linux的ip地址
# master_port 主mysql暴露的端口
# master_user 主mysql的用户名
# master_password 主mysql的密码
#（最后两项修改成刚刚从主mysql查到的，主mysql每次刷新权限或者重启时，这两个值都会改变，所以每次都需要查看是否相同）
CHANGE MASTER TO
master_host = '10.211.55.26',
master_port = 33060,
master_user = 'db_sync',
master_password = 'db_sync',
master_log_file = 'mysql-bin.000001',
master_log_pos = 823;

#启动同步
START SLAVE;

#查看Slave_IO_Runing和Slave_SQL_Runing字段值都为Yes，表示同步配置成功。
show slave status \G;
```

#### Sharding-JDBC 操作

##### 配置读写分离策略

也使用docker的，复制的时候记得改一下ip地址

```properties
# 配置数据源，给数据源起别名
# m0为用户数据库
spring.shardingsphere.datasource.names=m0,s0

# 一个实体类对应两张表，覆盖
spring.main.allow-bean-definition-overriding=true

#user_db 主服务器
spring.shardingsphere.datasource.m0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m0.url=jdbc:mysql://101.43.21.132:33061/user_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m0.username=root
spring.shardingsphere.datasource.m0.password=123456

#user_db 从服务器
spring.shardingsphere.datasource.s0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.s0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.s0.url=jdbc:mysql://101.43.21.132:33061/user_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.s0.username=root
spring.shardingsphere.datasource.s0.password=123456

# 主库从库逻辑数据源定义 ds0 为 user_db
spring.shardingsphere.sharding.master-slave-rules.ds0.master-data-source-name=m0
spring.shardingsphere.sharding.master-slave-rules.ds0.slave-data-source-names=s0

# 配置user_db数据库里面t_user  专库专表
#spring.shardingsphere.sharding.tables.t_user.actual-data-nodes=m0.t_user
# t_user 分表策略，固定分配至 ds0 的 t_user 真实表
spring.shardingsphere.sharding.tables.t_user.actual-data-nodes=ds0.t_user

# 配置主键的生成策略
spring.shardingsphere.sharding.tables.t_user.key-generator.column=user_id
spring.shardingsphere.sharding.tables.t_user.key-generator.type=SNOWFLAKE
# 指定分表策略
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.t_user.table-strategy.inline.algorithm-expression=t_user

# 打开 sql 输出日志
spring.shardingsphere.props.sql.show=true
```

查询操作

![](https://img-blog.csdnimg.cn/3e13ff8166bf419b83086925fe0ea99c.png)

插入操作

![](https://img-blog.csdnimg.cn/ea5f1fb4f2da4f08b2126278732db17b.png)

