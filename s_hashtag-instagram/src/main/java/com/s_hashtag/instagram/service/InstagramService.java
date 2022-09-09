package com.s_hashtag.instagram.service;

import com.s_hashtag.common.domain.kakao.repository.KakaoRepository;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.dto.external.PlaceDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InstagramService {

    private final KakaoApiService kakaoApiService;
    private final InstagramCrawler instagramCrawler;
    private final InstagramRepository instagramRepository;
    private final KakaoRepository kakaoRepository;
    private final InstagramSavePlace instagramSavePlace;
//    private final DocumentMapper documentMapper;

    @Transactional
    public void saveCrawlingResults(Rect rect) {
//        try {
            savePlace(rect, "FD6");
//            savePlace(rect, "CE7");

////            List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect);
//
//            List<KakaoPlaceDto> result = new ArrayList<>();
//            List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect, result);
//
//            List<Document> list_documonet = new ArrayList<>();
//            List<Document> list_documonet2 = new ArrayList<>();
//
//
//            for (KakaoPlaceDto page : kakaoPlaceDto_FD6) {
//                for (Document document : page.getDocuments()) {
//                    list_documonet.add(document);
//                }
//            }
//
//            List<PlaceDto> list_placeDto = instagramRepository.findAll("FD6", rect);
//
//            for (PlaceDto placeDto : list_placeDto) {
//                Document document = new Document();
//                BeanUtils.copyProperties(document, placeDto);
//                list_documonet2.add(document);
////                list_documonet2.add(documentMapper.toDocument(placeDto));
//            }
//
//            List<Document> remove_list = new ArrayList<>();
//            for (Document document : list_documonet) {
//                for (Document document2 : list_documonet2) {
//                    if (document.getId() != null && document2.getId() != null) {
//                        if (document.getId().equals(document2.getId())) {
//                            remove_list.add(document);
//                        }
//                    }
//                }
//            }
//
//            list_documonet.removeAll(remove_list);
//
//            int count = 0;
//            for (Document document : list_documonet) {
//                kakaoRepository.kakao_document_save(document);
//                log.info("crawling count : {}", count++);
//                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
//                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
//                if (crawlingDto != null) {
//                    instagramRepository.instagramSave(crawlingDto, document);
//                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
//                        instagramRepository.instagramPostSave(postDto);
//                    }
//                }
//            }
//
////            List<KakaoPlaceDto> kakaoPlaceDto_CE7 = kakaoApiService.findPlaces("CE7", rect);
//
//            result = new ArrayList<>();
//            List<KakaoPlaceDto> kakaoPlaceDto_CE7 = kakaoApiService.findPlaces("CE7", rect, result);
//
//            list_documonet = new ArrayList<>();
//            list_documonet2 = new ArrayList<>();
//
//            for (KakaoPlaceDto page : kakaoPlaceDto_CE7) {
//                for (Document document : page.getDocuments()) {
//                    list_documonet.add(document);
//                }
//            }
//
//            list_placeDto = instagramRepository.findAll("CE7", rect);
//
//            for (PlaceDto placeDto : list_placeDto) {
//                Document document = new Document();
//                BeanUtils.copyProperties(document, placeDto);
//                list_documonet2.add(document);
////                list_documonet2.add(documentMapper.toDocument(placeDto));
//            }
//
//            remove_list = new ArrayList<>();
//            for (Document document : list_documonet) {
//                for (Document document2 : list_documonet2) {
//                    if (document.getId() != null && document2.getId() != null) {
//                        if (document.getId().equals(document2.getId())) {
//                            remove_list.add(document);
//                        }
//                    }
//                }
//            }
//
//            list_documonet.removeAll(remove_list);
//            for (Document document : list_documonet) {
//                kakaoRepository.kakao_document_save(document);
//                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
//                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
//                if (crawlingDto != null) {
//                    instagramRepository.instagramSave(crawlingDto, document);
//                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
//                        instagramRepository.instagramPostSave(postDto);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
    }

    @Transactional
    public void savePlace(Rect rect, String category) {
        try {
            List<KakaoPlaceDto> result = new ArrayList<>();
            List<Document> documentList = new ArrayList<>();

            List<KakaoPlaceDto> kakaoPlaceDto = kakaoApiService.findPlaces(category, rect, result);

            for (KakaoPlaceDto page : kakaoPlaceDto) {
                for (Document document : page.getDocuments()) {
                    instagramSavePlace.savePlaceByProcess(document);
//                    documentList.add(document);
                }
            }

//            for (Document document : documentList) {
//                kakaoRepository.kakao_document_save(document);
//                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
//                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
//                if (crawlingDto != null) {
//                    instagramRepository.instagramSave(crawlingDto, document);
//                    log.info("crawling count : {}", count++);
//                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
//                        instagramRepository.instagramPostSave(postDto);
//                    }
//                }
//            }

        } catch (CrawlerException crawlerException) {
            log.debug("CrawlerException: {}", crawlerException.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void savePlaceByProcess(Document document) {
//        kakaoRepository.kakao_document_save(document);
//        CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
//        CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
//        if (crawlingDto != null) {
//            instagramRepository.instagramSave(crawlingDto, document);
//            for (PostDto postDto : crawlingDto.getPostDtoList()) {
//                instagramRepository.instagramPostSave(postDto);
//            }
//        }
//    }
}
