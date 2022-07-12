package com.s_hashtag.batch.reader;

import com.s_hashtag.kakaoapi.domain.dto.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.domain.rect.Rect;
import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

//@RequiredArgsConstructor
@Component
public class KakaoBatchReader implements ItemReader<KakaoPlaceDto> {

    private final KakaoApiService kakaoApiService;
    private int nextKakaoPlaceIndex;
    private List<KakaoPlaceDto> KakaoPlaceDtoList;

    public KakaoBatchReader(KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;

        Coordinate minLatitude = new Latitude(new BigDecimal("1"));
        Coordinate maxLatitude = new Latitude(new BigDecimal("1"));
        Coordinate minLongitude = new Longitude(new BigDecimal("1"));
        Coordinate maxLongitude = new Longitude(new BigDecimal("1"));

        Rect rect = new Rect(minLatitude, maxLatitude, maxLongitude, minLongitude);

        KakaoPlaceDtoList = kakaoApiService.findPlaces("FD6", rect);
//        studentData = Collections.unmodifiableList(Arrays.asList(tony, nick, ian));
        nextKakaoPlaceIndex = 0;
    }

    @Override
    public KakaoPlaceDto read() {
        KakaoPlaceDto nextKakaoPlace = new KakaoPlaceDto();

        if (nextKakaoPlaceIndex < KakaoPlaceDtoList.size()) {
            nextKakaoPlace = KakaoPlaceDtoList.get(nextKakaoPlaceIndex);
            nextKakaoPlaceIndex++;
        }
        else {
            nextKakaoPlaceIndex = 0;
        }

        return nextKakaoPlace;
    }
}
