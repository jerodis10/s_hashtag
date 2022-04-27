package com.s_hashtag.instagram.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Crawler {
    private static final String USER_AGENT =
//            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.104 Whale/3.13.131.36 Safari/537.36";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

//    private Date time = new Timestamp(System.currentTimeMillis());

//    Proxy proxy = new Proxy(Proxy.Type.HTTP,
//            new InetSocketAddress("127.0.0.1", 1080));

    public String crawl(String url) {
        try {

//            System.setProperty("http.proxyHost", "183.168.5.1");
//            System.setProperty("http.proxyPort", "1010");
//
//            System.out.println(System.getProperty("http.proxyHost"));
//            System.out.println(System.getProperty("http.proxyPort"));

            Document doc= Jsoup.connect(url)
//                    .proxy("127.0.0.1", 8081)
//                    .proxy(proxy)
                    .userAgent(USER_AGENT)
                    .get();
//                    .body()

//            if(doc.hasText()) return doc.toString();
//            else return null;
            return doc.toString();

        } catch (HttpStatusException e) {
            if (e.getStatusCode() == NOT_FOUND) {
                throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_URL);
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
            }
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } catch (IOException e) {
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } finally {

        }

//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }
}
