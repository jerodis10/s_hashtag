package com.s_hashtag.batch.reader;

import com.s_hashtag.common.domain.kakao.dto.external.Coordinate;
import com.s_hashtag.common.domain.kakao.dto.external.Latitude;
import com.s_hashtag.common.domain.kakao.dto.external.Longitude;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;
import com.s_hashtag.common.domain.schedule.dto.external.ScheduleDto;
import com.s_hashtag.common.domain.schedule.repository.ScheduleRepository;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

//@RequiredArgsConstructor
@Slf4j
@Component
public class KakaoBatchReader implements ItemReader<KakaoPlaceDto> {

    private final KakaoApiService kakaoApiService;
    private final ScheduleRepository scheduleRepository;

    private int nextKakaoPlaceIndex;
    private List<KakaoPlaceDto> KakaoPlaceDtoList;

    public KakaoBatchReader(KakaoApiService kakaoApiService, ScheduleRepository scheduleRepository) {
        this.kakaoApiService = kakaoApiService;
        this.scheduleRepository = scheduleRepository;

        ScheduleDto scheduleDto = scheduleRepository.findById("KakaoScheduler");

//        Rect rect = rectFactory.CreateRect(schedule.getMinLatitude(),
//                                           schedule.getMaxLatitude(),
//                                           schedule.getMinLongitude(),
//                                           schedule.getMaxLongitude());

        Coordinate minLatitude = new Latitude(new BigDecimal(scheduleDto.getMinLatitude()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(scheduleDto.getMaxLatitude()));
        Coordinate minLongitude = new Longitude(new BigDecimal(scheduleDto.getMinLongitude()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(scheduleDto.getMaxLongitude()));

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
            log.info("nextKakaoPlaceIndex : {}", nextKakaoPlaceIndex);
        }
        else {
            nextKakaoPlaceIndex = 0;
        }

        if(nextKakaoPlaceIndex == 0){
            return null;
        } else {
            return nextKakaoPlace;
        }
//        return nextKakaoPlace;
    }
}
