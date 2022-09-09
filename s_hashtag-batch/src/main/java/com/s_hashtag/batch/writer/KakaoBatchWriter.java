package com.s_hashtag.batch.writer;

import com.s_hashtag.common.domain.kakao.dto.external.Document;
import com.s_hashtag.common.domain.kakao.repository.KakaoRepository;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoBatchWriter implements ItemWriter<KakaoPlaceDto> {

    private final KakaoRepository kakaoRepository;

    @Override
    public void write(List<? extends KakaoPlaceDto> items) {
        log.info("KakaoBatchWriter");
        saveKakaoResult((List<KakaoPlaceDto>) items);
    }

    private void saveKakaoResult(List<KakaoPlaceDto> items) {
        for(KakaoPlaceDto kakaoPlaceDto : items) {
            if(kakaoPlaceDto.getDocuments() != null && kakaoPlaceDto.getDocuments().size() > 0) {
                for (Document document : kakaoPlaceDto.getDocuments()) {
                    kakaoRepository.kakao_document_save(document);
                }
            }
        }
    }
}
