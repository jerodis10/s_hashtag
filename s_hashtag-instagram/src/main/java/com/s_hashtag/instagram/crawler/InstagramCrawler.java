package com.s_hashtag.instagram.crawler;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDtos;
import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.instagram.util.PlaceNameParser;
import com.s_hashtag.instagram.util.UserAgentFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstagramCrawler {
private static final String INSTAGRAM_URL_FORMAT = "https://www.instagram.com/explore/tags/%s/?__a=1&__d=dis";

    private final Crawler crawler;

    public CrawlingDto createCrawlingDto(String hashtagName, String body, String kakaoId) {
        try {
            InstaCrawlingResult instaCrawlingResult = new InstaCrawlingResult(body);
            String instagramId = instaCrawlingResult.findInstagramId();
            BigDecimal hashTagCount = new BigDecimal(instaCrawlingResult.findHashTagCount());
            if (hashTagCount.equals(BigDecimal.ZERO))
                throw new CrawlerException(CrawlerExceptionStatus.NOT_ENOUGH_HASHTAG_COUNT);
            PostDtos postDtos = instaCrawlingResult.findPostDtos();

            return CrawlingDto.of(instagramId, kakaoId, hashtagName, hashTagCount, postDtos);
        } catch (CrawlerException crawlerException) {
            log.info(crawlerException.getMessage());
            throw crawlerException;
        } catch (Exception e) {
            throw e;
        }
    }

    public CrawlingDto crawler(String crawlingName, String kakaoId) {
        try {
            String parsedHashtagName = PlaceNameParser.parsePlaceName(crawlingName);
            Thread.sleep(5000);
            log.info("Proxy Host = {}, Port = {}", System.getProperty("http.proxyHost"), System.getProperty("http.proxyPort"));

            UserAgentFactory userAgentFactory = new UserAgentFactory();
            String user_agent = userAgentFactory.getUserAgent(userAgentFactory.create());

            String body = crawler.crawl(String.format(INSTAGRAM_URL_FORMAT, parsedHashtagName), user_agent);
            if (body != null) {
                log.info("before crawling HashtagName = {}", parsedHashtagName);
                return createCrawlingDto(parsedHashtagName, body, kakaoId);
            }

            return null;
        }
        catch (InterruptedException e) {
            return null;
        }
        catch (CrawlerException crawlerException) {
            throw crawlerException;
        } catch (Exception e) {
            throw e;
        }
    }
}






