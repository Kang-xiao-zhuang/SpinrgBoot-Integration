package com.zhuang.springbootredis.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LRUCacheDemo
 * @Description 手写实现LRU算法
 * @Date 2023/12/21 9:22
 * @Author by Zhuang
 */
public class LRUCacheDemo {

    // map 负责查找，构建一个虚拟的双向链表，它里面装的就是一个个 Node 节点，作为数据载体

    // 1.构造一个node节点作为数据载体
    // 乘客Node
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node() {
            this.prev = this.next = null;
        }

        // 初始化节点
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }

    // 2.构建一个虚拟的双向链表,,里面安放的就是我们的Node  火车带着乘客 乘客就是Node类
    static class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            // 头后是尾
            head.next = tail;
            // 尾前是头
            tail.prev = head;
        }

        // 3.添加到头
        public void addHead(Node<K, V> node) {
            // 头后继给新节点后继
            node.next = head.next;
            // 新节点前是头
            node.prev = head;
            // 头节点的后继的前驱是新节点
            head.next.prev = node;
            // 头节点后继是新节点
            head.next = node;
        }

        // 4.删除节点
        public void removeNode(Node<K, V> node) {
            // 节点的后继的前驱指向节点的前驱
            node.next.prev = node.prev;
            // 节点的前驱的后继指向节点的后继
            node.prev.next = node.next;
            // 断掉前后的指向
            node.prev = null;
            node.next = null;
        }

        // 5.获得最后一个节点
        public Node<K, V> getLast() {
            return tail.prev;
        }
    }

    private final int cacheSize;
    Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    // 初始化LRU
    public LRUCacheDemo(int cacheSize) {
        this.cacheSize = cacheSize;//坑位
        map = new HashMap<>();//查找
        doubleLinkedList = new DoubleLinkedList<>();
    }

    //
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node<Integer, Integer> node = map.get(key);
        // 出队
        doubleLinkedList.removeNode(node);
        // 入队
        doubleLinkedList.addHead(node);

        return node.value;
    }

    // saveOrupdate
    public void put(int key, int value) {
        if (map.containsKey(key)) {  //update
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key, node);
            // 出队
            doubleLinkedList.removeNode(node);
            // 入队
            doubleLinkedList.addHead(node);
        } else {
            if (map.size() == cacheSize)  //坑位满了
            {
                Node<Integer, Integer> lastNode = doubleLinkedList.getLast();
                map.remove(lastNode.key);
                doubleLinkedList.removeNode(lastNode);
            }
            //新增一个
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            // 入队
            doubleLinkedList.addHead(newNode);
        }
    }

    public static void main(String[] args) {

        LRUCacheDemo lruCacheDemo = new LRUCacheDemo(3);

        lruCacheDemo.put(1, 1);
        lruCacheDemo.put(2, 2);
        lruCacheDemo.put(3, 3);
        System.out.println(lruCacheDemo.map.keySet());

        // 新增一个没有的值
        lruCacheDemo.put(4, 1);
        System.out.println(lruCacheDemo.map.keySet());
        // 更新值
        lruCacheDemo.put(3, 1);
        System.out.println(lruCacheDemo.map.keySet());
        // 新增一个没有的值
        lruCacheDemo.put(5, 1);
        System.out.println(lruCacheDemo.map.keySet());

    }
}

