package com.s_hashtag.batch.writer;

import com.s_hashtag.instagram.repository.InstagramRepository;
import com.s_hashtag.kakaoapi.domain.dto.Document;
import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.repository.KakaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KakaoBatchWriter implements ItemWriter<KakaoPlaceDto> {

    private final KakaoRepository kakaoRepository;

    @Override
    public void write(List<? extends KakaoPlaceDto> items) {
        saveKakaoResult((List<KakaoPlaceDto>) items);
    }

    private void saveKakaoResult(List<KakaoPlaceDto> items) {
        for(KakaoPlaceDto kakaoPlaceDto : items) {
            for(Document document : kakaoPlaceDto.getDocuments()){
                kakaoRepository.kakao_document_save(document);
            }
        }
    }
}
