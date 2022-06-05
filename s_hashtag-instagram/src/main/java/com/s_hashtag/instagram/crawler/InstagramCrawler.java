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

    public CrawlingDto createCrawlingDto(String hashtagName, String body) throws IOException {
//        System.out.println(body);
//        ClassPathResource resource = new ClassPathResource("test.txt");
//        body = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        InstaCrawlingResult instaCrawlingResult = new InstaCrawlingResult(body);
        String instagramId = instaCrawlingResult.findInstagramId();
        String hashTagCount = instaCrawlingResult.findHashTagCount();
        PostDtos postDtos = instaCrawlingResult.findPostDtos();
        return CrawlingDto.of(instagramId, hashtagName, hashTagCount, postDtos);
    }

    public CrawlingDto crawler(String crawlingName) throws IOException, InterruptedException {
//        String parsedHashtagName = PlaceName  Parser.parsePlaceName(crawlingName);
        String parsedHashtagName = crawlingName.replaceAll(" ", "");
        Thread.sleep(10000);
        log.info("Proxy Host = {}, Port = {}", System.getProperty("http.proxyHost"), System.getProperty("http.proxyPort"));
        String body = crawler.crawl(String.format(INSTAGRAM_URL_FORMAT, parsedHashtagName));
        if(body != null){
            log.info("insert HashtagName = {}", parsedHashtagName);
            return createCrawlingDto(parsedHashtagName, body);
        }

        return null;
//        return createCrawlingDto(parsedHashtagName, body);
    }
}


