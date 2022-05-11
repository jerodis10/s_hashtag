package com.s_hashtag.instagram.controller;

import com.s_hashtag.instagram.crawler.InstagramCrawler;
import com.s_hashtag.instagram.dto.CrawlingDto;
import com.s_hashtag.instagram.dto.PlaceDto;
import com.s_hashtag.instagram.dto.PostDto;
import com.s_hashtag.instagram.proxy.CrawlerWithProxy;
import com.s_hashtag.instagram.proxy.ProxiesFactory;
import com.s_hashtag.instagram.proxy.ProxySetter;
import com.s_hashtag.instagram.repository.InstagramRepository;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Controller
public class InstagramController {

    private final KakaoApiService kakaoApiService;
    private final InstagramCrawler instagramCrawler;
    private final InstagramRepository instagramRepository;

    @PostMapping("/kakaoMap")
    @ResponseBody
    public List<KakaoPlaceDto> kakaoMap(@RequestParam Map<String, Object> param) throws IOException {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));

//        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()).setScale(5, RoundingMode.HALF_UP));
//        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()).setScale(5, RoundingMode.HALF_UP));
//        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("ha").toString()).setScale(5, RoundingMode.HALF_UP));

        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
        List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect);
//        List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect, new ArrayList<>());

        List<Document> list_documonet = new ArrayList<>();
        List<Document> list_documonet2 = new ArrayList<>();

        for(KakaoPlaceDto page : kakaoPlaceDto_FD6){
            for(Document document : page.getDocuments()) {
                list_documonet.add(document);
            }
        }

        List<PlaceDto> list_placeDto = instagramRepository.getHashtag("FD6", rect);

        for(PlaceDto placeDto : list_placeDto) {
            Document document = new Document();
            document.setId(placeDto.getKakao_id());
            document.setPlaceName(placeDto.getPlace_name());
            document.setCategoryGroupName(placeDto.getCategory_group_name());
            document.setCategoryGroupCode(placeDto.getCategory_group_code());
            document.setPhone(placeDto.getPhone());
            document.setAddressName(placeDto.getAddress_name());
            document.setRoadAddressName(placeDto.getRoad_address_name());
            document.setLatitude(String.valueOf(placeDto.getLatitude()));
            document.setLongitude(String.valueOf(placeDto.getLongitude()));
            document.setPlaceUrl(placeDto.getPlace_url());
            document.setDistance(placeDto.getDistance());

            list_documonet2.add(document);
        }

//        list_documonet.removeAll(list_documonet2);

        for(int i = 0; i < list_documonet.size(); i++){
            for (int j = 0; j < list_documonet2.size(); j++) {
                if(list_documonet.get(i).getId() != null && list_documonet2.get(j).getId() != null) {
                    if (list_documonet.get(i).getId().equals(list_documonet2.get(j).getId())) {
                        list_documonet.remove(i);
                    }
                }
            }
        }

//        for(KakaoPlaceDto page : kakaoPlaceDto_FD6){
//            for(Document document : page.getDocuments()){
        for(Document document : list_documonet){
//                list_documonet.add(document);

                instagramRepository.kakao_document_save(document);

//                CrawlingDto crawlingDto = instagramCrawler.crawler(document.getPlaceName());
//                list_crawlingDto.add(crawlingDto);
//                CrawlingDto crawlingDto = instagramCrawler.crawler(document.getPlaceName());
                CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
                CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName());
                if(crawlingDto != null) {
                    instagramRepository.instagram_save(crawlingDto, document);
                    for (PostDto postDto : crawlingDto.getPostDtoList()) {
                        instagramRepository.instagram_post_save(postDto);
                    }
                }
//            }
        }

        List<KakaoPlaceDto> kakaoPlaceDto_CE7 = kakaoApiService.findPlaces("CE7", rect);

        list_documonet = new ArrayList<>();
        list_documonet2 = new ArrayList<>();

        for(KakaoPlaceDto page : kakaoPlaceDto_FD6){
            for(Document document : page.getDocuments()) {
                list_documonet.add(document);
            }
        }

        list_placeDto = instagramRepository.getHashtag("FD6", rect);

        for(PlaceDto placeDto : list_placeDto) {
            Document document = new Document();
            document.setId(placeDto.getKakao_id());
            document.setPlaceName(placeDto.getPlace_name());
            document.setCategoryGroupName(placeDto.getCategory_group_name());
            document.setCategoryGroupCode(placeDto.getCategory_group_code());
            document.setPhone(placeDto.getPhone());
            document.setAddressName(placeDto.getAddress_name());
            document.setRoadAddressName(placeDto.getRoad_address_name());
            document.setLatitude(String.valueOf(placeDto.getLatitude()));
            document.setLongitude(String.valueOf(placeDto.getLongitude()));
            document.setPlaceUrl(placeDto.getPlace_url());
            document.setDistance(placeDto.getDistance());

            list_documonet2.add(document);
        }

        for(int i = 0; i < list_documonet.size(); i++){
            for (int j = 0; j < list_documonet2.size(); j++) {
                if(list_documonet.get(i).getId() != null && list_documonet2.get(j).getId() != null) {
                    if (list_documonet.get(i).getId().equals(list_documonet2.get(j).getId())) {
                        list_documonet.remove(i);
                    }
                }
            }
        }

//        for(KakaoPlaceDto page : kakaoPlaceDto_CE7){
//            for(Document document : page.getDocuments()){
        for(Document document : list_documonet){
            instagramRepository.kakao_document_save(document);

//          CrawlingDto crawlingDto = instagramCrawler.crawler(document.getPlaceName());
            CrawlerWithProxy crawlerWithProxy = new CrawlerWithProxy(new ProxySetter(ProxiesFactory.create()), instagramCrawler);
            CrawlingDto crawlingDto = crawlerWithProxy.crawlInstagram(document.getPlaceName());
            if(crawlingDto != null) {
                instagramRepository.instagram_save(crawlingDto, document);
                for (PostDto postDto : crawlingDto.getPostDtoList()) {
                    instagramRepository.instagram_post_save(postDto);
                }
            }
//          }
        }

        return kakaoPlaceDto_FD6;
    }

    @GetMapping("/getHashtag")
    @ResponseBody
    public List<PlaceDto> getHashtag(@RequestParam HashMap<String, Object> param) {
        List<Map<String, Object>> list = new ArrayList<>();
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

//        return instagramRepository.getHashtag(Arrays.asList((String) param.get("category_list[]")), rect);
        return instagramRepository.getHashtag((String) param.get("category_list"), rect);
    }

    @GetMapping("/getHashtagByKeyword")
    @ResponseBody
    public List<PlaceDto> getHashtagByKeyword(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        List<PlaceDto> placeList = new ArrayList<>();

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        for(String category : categoryList) {
            List<KakaoPlaceDto> kakaoPlaceByKeywordList = kakaoApiService.findPlacesByKeyword(category, rect, param.get("searchText").toString(), new ArrayList<>());

            List<String> KeywordStringList = new ArrayList<>();
            for (KakaoPlaceDto kakaoPlaceDto : kakaoPlaceByKeywordList) {
                for (Document document : kakaoPlaceDto.getDocuments()) {
                    KeywordStringList.add(document.getId());
                }
            }

            placeList.addAll(instagramRepository.getHashtagByKeyword(category, KeywordStringList));
        }

        return placeList;
    }

    @GetMapping("/getHashtagByCount")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        List<PlaceDto> placeList = instagramRepository.getHashtagByCount(categoryList, param.get("check").toString());

        return placeList;
    }

    @GetMapping("/getHashtagByCount2")
    @ResponseBody
    public List<PlaceDto> getHashtagByCount2(@RequestParam HashMap<String, Object> param) {
        Coordinate minLatitude = new Latitude(new BigDecimal(param.get("pa").toString()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(param.get("qa").toString()));
        Coordinate minLongitude = new Longitude(new BigDecimal(param.get("oa").toString()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(param.get("ha").toString()));
        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);

        String temp = (String) param.get("category_list");
        String[] categoryList = temp.split(",");

        Map<String, Object> hashtag_count_param = new HashMap<>();
        hashtag_count_param.put("check1", param.get("check1"));
        hashtag_count_param.put("check2", param.get("check2"));
        hashtag_count_param.put("check3", param.get("check3"));
        hashtag_count_param.put("check4", param.get("check4"));
        hashtag_count_param.put("check5", param.get("check5"));

        List<PlaceDto> placeList = instagramRepository.getHashtagByCount2(categoryList, hashtag_count_param);

        return placeList;
    }
}
