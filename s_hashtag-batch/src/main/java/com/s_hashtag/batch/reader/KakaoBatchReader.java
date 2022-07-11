package com.s_hashtag.batch.reader;

import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KakaoBatchReader implements ItemReader<List<KakaoPlaceDto>> {

    private final KakaoApiService kakaoApiService;

    @Override
    public List<KakaoPlaceDto> read() {
        Coordinate minLatitude = new Latitude(new BigDecimal("1"));
        Coordinate maxLatitude = new Latitude(new BigDecimal("1"));
        Coordinate minLongitude = new Longitude(new BigDecimal("1"));
        Coordinate maxLongitude = new Longitude(new BigDecimal("1"));

        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
        return kakaoApiService.findPlaces("FD6", rect);
    }
}
