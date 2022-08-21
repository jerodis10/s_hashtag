package com.s_hashtag.batch.processor;

import com.s_hashtag.common.place.domain.model.Place;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.dto.external.CrawlingDto;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class InstagramBatchProcessor implements ItemProcessor<Place, CrawlingDto> {

    private final InstagramCrawler instagramCrawler;

    @Override
    public CrawlingDto process(Place place) throws IOException {
        CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
        return crawlerWithProxy.crawlInstagram(place.getPlaceName(), place.getKakaoId());
    }
}
