package com.s_hashtag.batch.reader;

import com.s_hashtag.common.schedule.model.Schedule;
import com.s_hashtag.common.schedule.repository.ScheduleRepository;
import com.s_hashtag.kakaoapi.dto.external.KakaoPlaceDto;
import com.s_hashtag.kakaoapi.rect.Rect;
import com.s_hashtag.kakaoapi.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.rect.location.Latitude;
import com.s_hashtag.kakaoapi.rect.location.Longitude;
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

        Schedule schedule = scheduleRepository.findById("KakaoScheduler");

        Coordinate minLatitude = new Latitude(new BigDecimal(schedule.getMin_latitude()));
        Coordinate maxLatitude = new Latitude(new BigDecimal(schedule.getMax_latitude()));
        Coordinate minLongitude = new Longitude(new BigDecimal(schedule.getMin_longitude()));
        Coordinate maxLongitude = new Longitude(new BigDecimal(schedule.getMax_longitude()));

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
