package com.zhuang.springbootelasticsearch.utils;

import com.zhuang.springbootelasticsearch.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        List<Content> list = parseJd("python");
        list.forEach(System.out::println);
    }

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
            String src = "https://" + img;
            list.add(new Content(src, price, name));
/*            System.out.println(src);
            System.out.println(price);
            System.out.println(name);*/
        }
        return list;
    }

}
