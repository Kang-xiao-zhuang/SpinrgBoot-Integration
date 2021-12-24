package com.zhuang.springbootelasticsearch.utils;

import com.zhuang.springbootelasticsearch.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname HtmlParseUtil
 * @Description HtmlParseUtil工具类，用户京东爬虫
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
@Component
public class HtmlParseUtil {

    /**
     * 爬取京东数据展示
     *
     * @param keyword 关键词
     * @return 集合
     * @throws IOException IO异常
     */
    public static List<Content> parseJd(String keyword) throws IOException {
        ArrayList<Content> list = new ArrayList<>();
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String name = el.getElementsByClass("p-name").eq(0).text();
            list.add(new Content(img, price, name));
        }
        return list;
    }

}
