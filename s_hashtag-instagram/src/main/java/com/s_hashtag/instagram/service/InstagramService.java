package com.s_hashtag.instagram.service;
import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.DocumentMapper;
import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import com.s_hashtag.instagram.repository.InstagramRepository;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.repository.KakaoRepository;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private final DocumentMapper documentMapper;

    @Transactional(rollbackFor = Exception.class)
    public void saveCrawlingResults(Rect rect) {
        try {
            List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect);

            List<Document> list_documonet = new ArrayList<>();
            List<Document> list_documonet2 = new ArrayList<>();


            for (KakaoPlaceDto page : kakaoPlaceDto_FD6) {
                for (Document document : page.getDocuments()) {
                    list_documonet.add(document);
                }
            }

            List<PlaceDto> list_placeDto = instagramRepository.getHashtag("FD6", rect);

            for (PlaceDto placeDto : list_placeDto) {
                list_documonet2.add(documentMapper.toDocument(placeDto));
            }

            List<Document> remove_list = new ArrayList<>();
            for (Document document : list_documonet) {
                for (Document document2 : list_documonet2) {
                    if (document.getId() != null && document2.getId() != null) {
                        if (document.getId().equals(document2.getId())) {
                            remove_list.add(document);
                        }
                    }
                }
            }

            list_documonet.removeAll(remove_list);

            int count = 0;
            for (Document document : list_documonet) {
                kakaoRepository.kakao_document_save(document);
                System.out.println(count++);
                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
                if (crawlingDto != null) {
                    instagramRepository.instagram_save(crawlingDto, document);
                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
                        instagramRepository.instagram_post_save(postDto);
                    }
                }
            }

            List<KakaoPlaceDto> kakaoPlaceDto_CE7 = kakaoApiService.findPlaces("CE7", rect);

            list_documonet = new ArrayList<>();
            list_documonet2 = new ArrayList<>();

            for (KakaoPlaceDto page : kakaoPlaceDto_CE7) {
                for (Document document : page.getDocuments()) {
                    list_documonet.add(document);
                }
            }

            list_placeDto = instagramRepository.getHashtag("CE7", rect);

            for (PlaceDto placeDto : list_placeDto) {
                list_documonet2.add(documentMapper.toDocument(placeDto));
            }

            remove_list = new ArrayList<>();
            for (Document document : list_documonet) {
                for (Document document2 : list_documonet2) {
                    if (document.getId() != null && document2.getId() != null) {
                        if (document.getId().equals(document2.getId())) {
                            remove_list.add(document);
                        }
                    }
                }
            }

            list_documonet.removeAll(remove_list);
            for (Document document : list_documonet) {
                kakaoRepository.kakao_document_save(document);
                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName(), document.getId());
                if (crawlingDto != null) {
                    instagramRepository.instagram_save(crawlingDto, document);
                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
                        instagramRepository.instagram_post_save(postDto);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
