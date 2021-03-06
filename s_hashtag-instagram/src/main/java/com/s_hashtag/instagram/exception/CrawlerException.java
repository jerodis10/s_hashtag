package com.s_hashtag.instagram.exception;

//import com.s_hashtag.common.exception.CommonExceptionStatus;
//import com.s_hashtag.common.exception.HashtagMapException;
import lombok.Getter;

//public class CrawlerException extends HashtagMapException {

//    public CrawlerException(CrawlerExceptionStatus crawlerExceptionStatus) {
//        super(crawlerExceptionStatus.getMessage(), crawlerExceptionStatus.getStatusCode());
//    }
//}
@Getter
public class CrawlerException extends RuntimeException {
    public CrawlerException(CrawlerExceptionStatus crawlerExceptionStatus) {
        super(crawlerExceptionStatus.getMessage());
    }
}