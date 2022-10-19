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
import java.util.Optional;

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
            return null;
        } catch (CrawlerException crawlerException) {
            log.debug("CrawlerException: {}", crawlerException.getMessage());
            throw crawlerException;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isOnline() {
        HttpURLConnection httpURLConnection = null;
        try {
            proxySetter.setProxy();
            httpURLConnection = (HttpURLConnection) new URL("https://www.instagram.com/").openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();
            httpURLConnection.disconnect();
            return true;
        } catch (MalformedURLException e) {
            log.info("MalformedURLException: {}", e.getMessage());
        } catch (IOException e) {
            log.info("IOException: {}", e.getMessage());
        }
        return true;
    }
}
