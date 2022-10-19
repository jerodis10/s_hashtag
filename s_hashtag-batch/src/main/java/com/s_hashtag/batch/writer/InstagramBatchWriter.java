package com.s_hashtag.batch.writer;

import com.s_hashtag.common.domain.instagram.dto.external.CrawlingDto;
import com.s_hashtag.common.domain.instagram.repository.InstagramRepository;
import com.s_hashtag.common.domain.kakao.dto.external.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InstagramBatchWriter implements ItemWriter<CrawlingDto> {

    private final InstagramRepository instagramRepository;

    @Override
    public void write(List<? extends CrawlingDto> items) {
        saveCrawlingResult((List<CrawlingDto>) items);
    }

    private void saveCrawlingResult(List<CrawlingDto> items) {
        for(CrawlingDto crawlingDto : items) {
            Document document = Document.builder()
                    .id(crawlingDto.getPlaceId())
                    .build();
            instagramRepository.instagramSave(crawlingDto, document);
        }
    }
}
