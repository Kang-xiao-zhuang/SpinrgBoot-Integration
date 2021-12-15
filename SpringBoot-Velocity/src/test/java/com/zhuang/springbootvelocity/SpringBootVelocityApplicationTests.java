package com.zhuang.springbootvelocity;

import com.zhuang.springbootvelocity.entity.User;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class SpringBootVelocityApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();
        context.put("name", "zhangsan");
        //加载模板
        Template template = Velocity.getTemplate("static/vms/01-quickstart.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\01-quickstart.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }

    @Test
    void test2() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();


        User user = new User();
        user.setName("zhuangkang");
        user.setPassword("123321zk");
        context.put("user", user);
        //加载模板
        Template template = Velocity.getTemplate("static/vms/02-cite-field.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\02-cite-field.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }

    @Test
    void test3() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();

        context.put("str", "hello velocity");
        context.put("now", new Date());
        //加载模板
        Template template = Velocity.getTemplate("static/vms/03-cite-method.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\03-cite-method.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }


    @Test
    void test4() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();

        context.put("str", "hello velocity");
        context.put("now", new Date());
        //加载模板
        Template template = Velocity.getTemplate("static/vms/04-instructions-set.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\04-instructions-set.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }

    @Test
    void test5() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();

        context.put("str", "hello velocity");
        context.put("now", new Date());
        //加载模板
        Template template = Velocity.getTemplate("static/vms/05-instructions-if.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\05-instructions-if.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }

    @Test
    void test6() throws IOException {
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //创建Velocity容器
        VelocityContext context = new VelocityContext();
        String[] hobbise = {"eat", "play", "drink"};
        context.put("hobbies", hobbise);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("zk", "123"));
        userList.add(new User("zk11", "123"));
        userList.add(new User("zk22", "123"));

        context.put("userList", userList);

        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");

        context.put("map", map);

        //加载模板
        Template template = Velocity.getTemplate("static/vms/06-instructions-foreach.vm", "UTF-8");
        FileWriter fw = new FileWriter("D:\\IdeaProjects\\spinrg-boot-integration\\SpringBoot-Velocity\\src\\main\\resources\\static\\html\\06-instructions-foreach.html");
        template.merge(context, fw);

        // 释放资源
        fw.close();
    }
}
