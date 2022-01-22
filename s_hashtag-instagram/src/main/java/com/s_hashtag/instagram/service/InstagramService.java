//package com.s_hashtag.instagram.service;
//
//import com.s_hashtag.instagram.crawler.InstagramCrawler;
//import com.s_hashtag.instagram.proxy.ProxiesFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class InstagramService {
//    private final BlackListRepository blackListRepository;
//    private final InstagramCrawler instagramCrawler;
//
//    public Optional<CrawlingResult> createCrawlingResult(Place place) {
//        if (isSkipPlace(place)) {
//            return Optional.empty();
//        }
//        String hashtagNameToCrawl = findHashtagNameToCrawl(place);
//        CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
//        return crawlerWithProxy.crawlInstagram(place, hashtagNameToCrawl);
//    }
//
//    private String findHashtagNameToCrawl(Place place) {
//        return blackListRepository.findByKakaoId(place.getKakaoId())
//                .map(BlackList::getReplaceName)
//                .orElseGet(place::getPlaceName);
//    }
//
//    private boolean isSkipPlace(Place place) {
//        return blackListRepository.findByKakaoId(place.getKakaoId())
//                .map(BlackList::getIsSkipPlace)
//                .orElse(false);
//    }
//}
