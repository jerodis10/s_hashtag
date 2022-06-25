//package com.s_hashtag.batch.processor;
//
//import com.s_hashtag.common.place.domain.model.Place;
//import com.s_hashtag.instagram.dto.CrawlingDto;
//import com.songpapeople.hashtagmap.place.domain.model.Place;
//import com.songpapeople.hashtagmap.service.CrawlingResult;
//import com.songpapeople.hashtagmap.service.InstagramCrawlingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class InstagramBatchProcessor implements ItemProcessor<Place, CrawlingDto> {
//    private final InstagramCrawlingService instagramCrawlingService;
//
//    @Override
//    public CrawlingResult process(Place place) {
//        return instagramCrawlingService.createCrawlingResult(place).orElse(null);
//    }
//}
