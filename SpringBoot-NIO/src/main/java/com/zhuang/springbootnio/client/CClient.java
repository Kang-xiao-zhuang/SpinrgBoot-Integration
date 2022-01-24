package com.zhuang.springbootnio.client;

import java.io.IOException;

public class CClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("tom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
