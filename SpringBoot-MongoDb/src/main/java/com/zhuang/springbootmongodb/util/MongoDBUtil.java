package com.zhuang.springbootmongodb.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

/**
 * description: MongoDBUtil
 * date: 2023/4/11 14:09
 * author: Zhuang
 * version: 1.0
 */
@Slf4j
public class MongoDBUtil {

    public static final String DB_NAME = "school";
    public static final String COLLECTION_NAME = "student";

    public static void main(String[] args) {
        getCollection(DB_NAME, COLLECTION_NAME);
        // insert();
        find();
        // update();
        delete();
        find();
    }

    private static MongoClient mongoClient;

    static {
        System.out.println("===============MongoDBUtil初始化========================");
        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("192.168.18.129", 27017);
        adds.add(serverAddress);
        List<MongoCredential> credentials = new ArrayList<>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("myroot", DB_NAME, "123456".toCharArray());
        credentials.add(mongoCredential);
        //通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(adds, credentials);
        System.out.println("==========Connect to MongoDB successfully================");
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }


    private MongoDBUtil() {

    }

    // 获取连接
    public static MongoCollection<Document> getCollection(String dbname, String collectionname) {
        MongoClient mongoClient = new MongoClient("192.168.18.129", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbname);
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionname);
        log.info("链接对象为：" + collection);
        return collection;
    }

    // 插入数据
    public static void insert() {
        try {
            MongoCollection<Document> collection = getCollection(DB_NAME, COLLECTION_NAME);
            Document doc1 = new Document("sname", "Jerry").append("sage", 26);
            Document doc2 = new Document("sname", "Tom").append("sage", 22);
            List<Document> documents = new ArrayList<>();
            documents.add(doc1);
            documents.add(doc2);
            collection.insertMany(documents);
            log.info("插入成功");
        } catch (Exception e) {
            log.error(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    // 查询数据
    public static void find() {
        try {
            MongoCollection<Document> collection = getCollection(DB_NAME, COLLECTION_NAME);
            for (Document document : collection.find()) {
                log.info(document.toJson());
            }
        } catch (Exception e) {
            log.error(e.getClass().getName() + ":" + e.getMessage());

        }
    }

    // 更新数据
    public static void update() {
        try {
            MongoCollection<Document> collection = getCollection(DB_NAME, COLLECTION_NAME);
            collection.updateMany(Filters.eq("sname", "Mary"), new Document("$set", new Document("sage", 22)));
            log.info("更新成功");
        } catch (Exception e) {
            log.error(e.getClass().getName() + ":" + e.getMessage());
        }
    }


    // 删除数据
    public static void delete() {
        try {
            MongoCollection<Document> collection = getCollection(DB_NAME, COLLECTION_NAME);// 数据库名：School 集合名：student
            // 删除符合条件的第一个文档
            collection.deleteMany(Filters.eq("sname", "Bob"));
            // 删除所有符合条件的文档
            // collection.deleteMany(Filters.eq("sname","Bob"));
            log.info("删除成功");
        } catch (Exception e) {
            log.error(e.getClass().getName() + ":" + e.getMessage());
        }
    }
}
