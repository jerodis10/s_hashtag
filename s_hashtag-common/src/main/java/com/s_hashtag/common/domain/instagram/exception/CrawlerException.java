package com.s_hashtag.common.domain.instagram.exception;

import com.s_hashtag.common.exception.HashtagMapException;

public class CrawlerException extends HashtagMapException {

    public CrawlerException(CrawlerExceptionStatus crawlerExceptionStatus) {
        super(crawlerExceptionStatus.getMessage(), crawlerExceptionStatus.getStatusCode());
    }
}
//@Getter
//public class CrawlerException extends RuntimeException {
//    public CrawlerException(CrawlerExceptionStatus crawlerExceptionStatus) {
//        super(crawlerExceptionStatus.getMessage());
//    }
//}