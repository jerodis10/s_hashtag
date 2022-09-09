package com.s_hashtag.instagram.service;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.repository.KakaoRepository;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InstagramSavePlace {

    private final InstagramRepository instagramRepository;
    private final KakaoRepository kakaoRepository;
    private final InstagramCrawler instagramCrawler;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePlaceByProcess(Document document) {
        kakaoRepository.kakao_document_save(document);
        CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
        CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
        if (crawlingDto != null) {
            instagramRepository.instagramSave(crawlingDto, document);
            for (PostDto postDto : crawlingDto.getPostDtoList()) {
                instagramRepository.instagramPostSave(postDto);
            }
        }
    }
}
