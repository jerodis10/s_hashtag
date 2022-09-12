package com.s_hashtag.instagram.proxy;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
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

    public CrawlingDto crawlInstagram(String hashtagNameToCrawl, String kakaoId) {
        try {
            if(isOnline()) return instagramCrawler.crawler(hashtagNameToCrawl, kakaoId);
            else return null;
        } catch (CrawlerException crawlerException) {
            log.debug("CrawlerException: {}", crawlerException.getMessage());
//            if (NOT_FOUND_EXCEPTION_CODE.equals(e.getErrorCode())) {
//                return Optional.empty();
//            }
            throw crawlerException;
        } catch (Exception e) {
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
            log.info("MalformedURLException: {}", e.getMessage());
        } catch (IOException e) {
            log.info("IOException: {}", e.getMessage());
        }
        return true;
    }
}
