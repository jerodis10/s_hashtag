package com.s_hashtag.instagram.exception;

import com.s_hashtag.exception.HashtagMapException;

public class CrawlerException extends HashtagMapException {

    public CrawlerException(CrawlerExceptionStatus crawlerExceptionStatus) {
        super(crawlerExceptionStatus.getMessage(), crawlerExceptionStatus.getStatusCode());
    }
}