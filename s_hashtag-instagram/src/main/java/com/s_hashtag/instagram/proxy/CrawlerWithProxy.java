package com.s_hashtag.instagram.proxy;

import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.common.instagram.dto.external.CrawlingDto;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class CrawlerWithProxy {
    private static final String NOT_FOUND_EXCEPTION_CODE = CrawlerExceptionStatus.NOT_FOUND_URL.getStatusCode();

    private final ProxySetter proxySetter;
    private final InstagramCrawler instagramCrawler;

    public CrawlerWithProxy(ProxySetter proxySetter, InstagramCrawler instagramCrawler) {
        this.proxySetter = proxySetter;
        this.instagramCrawler = instagramCrawler;
    }

    public CrawlingDto crawlInstagram(String hashtagNameToCrawl, String kakaoId) throws IOException {
        try {
//            proxySetter.setProxy();
//            return instagramCrawler.crawler(hashtagNameToCrawl);
//            Proxy proxy = new Proxy(System.getProperty("http.proxyHost"), System.getProperty("http.proxyPort"));
            if(isOnline()) return instagramCrawler.crawler(hashtagNameToCrawl, kakaoId);
            else return null;
        } catch (CrawlerException e) {
            log.debug("CrawlerException: {}", e.getMessage());
//            if (NOT_FOUND_EXCEPTION_CODE.equals(e.getErrorCode())) {
//                return Optional.empty();
//            }
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isOnline() {
//        proxy.setHostAndPort();
        HttpURLConnection httpURLConnection = null;
        try {
            proxySetter.setProxy();
            httpURLConnection = (HttpURLConnection) new URL("https://www.instagram.com/").openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();
//            boolean isOnline = httpURLConnection.usingProxy();
            httpURLConnection.disconnect();
//            proxy.clearProperty();
//            return isOnline;
            return true;
        } catch (MalformedURLException e) {
            log.info("CrawlerException: {}", e.getMessage());
        } catch (IOException e) {
            log.info("CrawlerException: {}", e.getMessage());
        }
        return true;
    }
}
