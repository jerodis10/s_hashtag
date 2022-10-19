package com.s_hashtag.instagram.crawler;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Crawler {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public String crawl(String url, String user_agent) {
        try {
            String proxyHost = System.getProperty("http.proxyHost") != null ?
                    System.getProperty("http.proxyHost") : "138.68.60.8";

            int proxyPort = System.getProperty("http.proxyPort") != null ?
                    Integer.parseInt(System.getProperty("http.proxyPort")) : 8080;

            Document doc= Jsoup.connect(url)
                    .userAgent(user_agent)
                    .referrer("https://www.instagram.com")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .ignoreContentType(true)
                    .cookie("auth", "token")
                    .get();
            return doc.toString();

        } catch (HttpStatusException e) {
            log.info("url = {}", url);
            if (e.getStatusCode() == NOT_FOUND) {
                return null;
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
            }
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } catch (IOException e) {
            log.info("url = {}", url);
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        }
    }
}
