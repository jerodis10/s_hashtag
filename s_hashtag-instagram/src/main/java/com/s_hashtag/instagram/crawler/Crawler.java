package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class Crawler {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public String crawl(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(HOLDING_TIME)
                    .get()
                    .body()
                    .toString();
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == NOT_FOUND) {
                throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_URL);
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
            }
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } catch (IOException e) {
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        }
    }
}
