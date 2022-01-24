package com.zhuang.springbootnio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetDemo {

    public static void main(String[] args) throws CharacterCodingException {
        // 获取Charset对象
        Charset charset = Charset.forName("UTF-8");
        // 获得编码器对象
        CharsetEncoder charsetEncoder = charset.newEncoder();
        // 创建缓冲区
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("zk哈哈哈哈");
        charBuffer.flip();
        // 编码
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }
        // 获取解码器对象
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        // 解码
        CharBuffer charBuffer1 = charsetDecoder.decode(byteBuffer);
        System.out.println("解码之后结果");
        System.out.println(charBuffer1.toString());

        // 使用GBK编码
        Charset charset1 = Charset.forName("GBK");
        byteBuffer.flip();
        CharBuffer charBuffer2 = charset1.decode(byteBuffer);
        System.out.println("使用其他编码进行：");
        System.out.println(charBuffer2.toString());
    }


}
