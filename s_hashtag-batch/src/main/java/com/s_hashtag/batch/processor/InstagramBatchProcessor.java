package com.s_hashtag.batch.processor;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.place.domain.model.Place;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class InstagramBatchProcessor implements ItemProcessor<Place, CrawlingDto> {

    private final InstagramCrawler instagramCrawler;

    @Override
    public CrawlingDto process(Place place) {
        try {
            CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
            return crawlerWithProxy.crawlInstagram(place.getPlaceName(), place.getKakaoId());
        } catch (CrawlerException crawlerException) {
            log.debug("CrawlerException: {}", crawlerException.getMessage());
            throw crawlerException;
        } catch (Exception e) {
            throw e;
        }
    }
}
