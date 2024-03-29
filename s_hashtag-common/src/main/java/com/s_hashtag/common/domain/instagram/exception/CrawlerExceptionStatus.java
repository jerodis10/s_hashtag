package com.s_hashtag.common.domain.instagram.exception;

import com.s_hashtag.common.domain.instagram.dto.external.PostDtos;
import lombok.Getter;

@Getter
public enum CrawlerExceptionStatus {
    URL_NOT_CONNECT("연결할 수 없는 URL 입니다.", "CRAWLER_1001"),
    NOT_ENOUGH_POPULAR_POST(String.format("인기게시물이 %d개 미만입니다.", PostDtos.POPULAR_POST_SIZE), "CRAWLER_1002"),
    NOT_FOUND_MATCH_REGEX("크롤링 데이터에서 원하는 내용을 찾지 못했습니다.", "CRAWLER_1003"),
    ILLEGAL_PROXY("잘못된 프록시 설정입니다.", "CRAWLER_1004"),
    NOT_FOUND_URL("해당 URL을 찾을 수 없습니다.", "CRAWLER_1005"),
    TOO_MANY_REQUEST("너무 많은 요청으로 오류가 발생했습니다.", "CRAWLER_1006"),
    BOT_DETECTION("크롤링 중 봇 탐지로 인한 오류가 발생했습니다.", "CRAWLER_1007"),
    NOT_ENOUGH_HASHTAG_COUNT("해시태그가 존재하지 않습니다.", "CRAWLER_1008"),
    TOO_MANY_POPULAR_POST(String.format("인기게시물이 %d개로 초과입니다.", PostDtos.POPULAR_POST_SIZE), "CRAWLER_1009");

    private final String message;
    private final String statusCode;

    CrawlerExceptionStatus(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
