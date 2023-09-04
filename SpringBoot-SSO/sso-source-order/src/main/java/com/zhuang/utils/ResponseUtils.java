package com.zhuang.utils;

import com.zhuang.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 响应工具类
 */
public class ResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * 向浏览器响应一个json字符串
     *
     * @param response 响应对象
     * @param status   状态码
     * @param msg      响应信息
     */
    public static void write(HttpServletResponse response, int status, String msg) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(status);
            PrintWriter out = response.getWriter();
            out.write(JsonUtils.toString(new Result(status, msg, null)));
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("响应出错：" + msg, e);
        }
    }
}