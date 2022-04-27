package com.s_hashtag.instagram.proxy;

import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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

    public CrawlingDto crawlInstagram(String hashtagNameToCrawl) throws IOException {
        try {
            proxySetter.setProxy();
            return instagramCrawler.crawler(hashtagNameToCrawl);
        } catch (CrawlerException e) {
            log.info("CrawlerException: {}", e.getMessage());
//            if (NOT_FOUND_EXCEPTION_CODE.equals(e.getErrorCode())) {
//                return Optional.empty();
//            }
            throw e;
        }
    }
}
