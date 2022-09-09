package com.s_hashtag.instagram.crawler;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDtos;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.instagram.util.PlaceNameParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstagramCrawler {
//    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?hl=ko";
//    private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?__a=1";
private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?__a=1&__d=dis";

    private final Crawler crawler;
//    private final PlaceNameParser placeNameParser;

//    public InstagramCrawler(Crawler crawler) {
//        this.crawler = crawler;
//    }

    public CrawlingDto createCrawlingDto(String hashtagName, String body, String kakaoId) {
        try {
            //        ClassPathResource resource = new ClassPathResource("test.txt");
            //        body = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            InstaCrawlingResult instaCrawlingResult = new InstaCrawlingResult(body);
            //        String robot = instaCrawlingResult.checkRobot();
            String instagramId = instaCrawlingResult.findInstagramId();
            BigDecimal hashTagCount = new BigDecimal(instaCrawlingResult.findHashTagCount());
            if (hashTagCount.equals(BigDecimal.ZERO))
                throw new CrawlerException(CrawlerExceptionStatus.NOT_ENOUGH_HASHTAG_COUNT);
            PostDtos postDtos = instaCrawlingResult.findPostDtos();
            return CrawlingDto.of(instagramId, kakaoId, hashtagName, hashTagCount, postDtos);
        } catch (CrawlerException crawlerException) {
            log.info(crawlerException.getMessage());
            throw crawlerException;
        }
    }

    public CrawlingDto crawler(String crawlingName, String kakaoId) {
        try {
              String parsedHashtagName = PlaceNameParser.parsePlaceName(crawlingName);
//              String parsedHashtagName = placeNameParser.parsePlaceName(crawlingName);
//            String parsedHashtagName = crawlingName.replaceAll(" ", "");
//            Thread.sleep(10000);
            log.info("Proxy Host = {}, Port = {}", System.getProperty("http.proxyHost"), System.getProperty("http.proxyPort"));

            UserAgentFactory userAgentFactory = new UserAgentFactory();
            String user_agent = userAgentFactory.getUserAgent(userAgentFactory.create());

            String body = crawler.crawl(String.format(INSTAGRAM_URL_FORMAT, parsedHashtagName), user_agent);
            if (body != null) {
                log.info("before crawling HashtagName = {}", parsedHashtagName);
                return createCrawlingDto(parsedHashtagName, body, kakaoId);
            }

            return null;
            //        return createCrawlingDto(parsedHashtagName, body);
        } catch (CrawlerException crawlerException) {
            throw crawlerException;
        }
    }
}






