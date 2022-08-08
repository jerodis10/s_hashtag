package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PostDtos;
import com.s_hashtag.instagram.util.PlaceNameParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
//@RequiredArgsConstructor
@Slf4j
public class InstagramCrawler {
    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?hl=ko";
//    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?__a=1";
//private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?__a=1&__d=dis";

    private final Crawler crawler;

    public InstagramCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public CrawlingDto createCrawlingDto(String hashtagName, String body, String kakaoId) throws IOException {
//        System.out.println(body);
//        ClassPathResource resource = new ClassPathResource("test.txt");
//        body = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        InstaCrawlingResult instaCrawlingResult = new InstaCrawlingResult(body);
//        String robot = instaCrawlingResult.checkRobot();
        String instagramId = instaCrawlingResult.findInstagramId();
//        if(instagramId == null) return null;
//        String hashTagCount = instaCrawlingResult.findHashTagCount();
        String hashTagCount = instaCrawlingResult.findPostCount();
        PostDtos postDtos = instaCrawlingResult.findPostDtos();
        return CrawlingDto.of(instagramId, kakaoId, hashtagName, hashTagCount, postDtos);
    }

    public CrawlingDto crawler(String crawlingName, String kakaoId) throws IOException, InterruptedException {
//        String parsedHashtagName = PlaceName  Parser.parsePlaceName(crawlingName);
        String parsedHashtagName = crawlingName.replaceAll(" ", "");
        Thread.sleep(30000);
        log.info("Proxy Host = {}, Port = {}", System.getProperty("http.proxyHost"), System.getProperty("http.proxyPort"));
        String body = crawler.crawl(String.format(INSTAGRAM_URL_FORMAT, parsedHashtagName));
        if(body != null){
            log.info("before crawling HashtagName = {}", parsedHashtagName);
            return createCrawlingDto(parsedHashtagName, body, kakaoId);
        }

        return null;
//        return createCrawlingDto(parsedHashtagName, body);
    }
}






