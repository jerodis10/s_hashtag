package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDtos;
import com.s_hashtag.instagram.util.PlaceNameParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// @Component 나중에 지워야함
@Service
//@RequiredArgsConstructor
public class InstagramCrawler {
    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?hl=ko";

    private final Crawler crawler;

    public InstagramCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public CrawlingDto createCrawlingDto(String hashtagName, String body) {
        InstaCrawlingResult instaCrawlingResult = new InstaCrawlingResult(body);
        String hashTagCount = instaCrawlingResult.findHashTagCount();
        PostDtos postDtos = instaCrawlingResult.findPostDtos();
        return CrawlingDto.of(hashtagName, hashTagCount, postDtos);
    }

    public CrawlingDto crawler(String crawlingName) {
//        String parsedHashtagName = PlaceName  Parser.parsePlaceName(crawlingName);
        String parsedHashtagName = crawlingName.replaceAll(" ", "");
        String body = crawler.crawl(String.format(INSTAGRAM_URL_FORMAT, parsedHashtagName));
        return createCrawlingDto(parsedHashtagName, body);
    }
}
