package com.zhuang.utils;

import org.junit.jupiter.api.Test;

public class RsaUtilsTest {
    private static final String publicFile = "D:\\auth_key\\rsa_key.pub";
    private static final String privateFile = "D:\\auth_key\\rsa_key";
    private static final String secret = "KangXiaoZhuangSecret";

    @Test
    public void generateKey() throws Exception {
        RsaUtils.generateKey(publicFile, privateFile, secret, 2048);
    }
}