package com.s_hashtag.common.domain.kakao.dto.external;

import com.s_hashtag.common.domain.kakao.exception.KakaoApiException;
import com.s_hashtag.common.domain.kakao.exception.KakaoApiExceptionStatus;

import java.math.BigDecimal;

// 경도, x
public class Longitude extends Coordinate {
    private static final BigDecimal MIN_LONGITUDE = BigDecimal.valueOf(124);
    private static final BigDecimal MAX_LONGITUDE = BigDecimal.valueOf(132);
//    private static final BigDecimal MIN_LONGITUDE = BigDecimal.valueOf(0);
//    private static final BigDecimal MAX_LONGITUDE = BigDecimal.valueOf(20000);

    public Longitude(double longitude) {
        super(validateRange(BigDecimal.valueOf(longitude)));
    }

    public Longitude(BigDecimal longitude) {
        super(validateRange(longitude));
    }

    protected static BigDecimal validateRange(BigDecimal longitude) {
        if (isBetween(longitude, MIN_LONGITUDE, MAX_LONGITUDE)) {
            return longitude;
        }
        String detailMessage = String.format("잘못된 경도 범위(%s)입니다.", longitude);
        throw new KakaoApiException(KakaoApiExceptionStatus.INVALID_LONGITUDE, detailMessage);
//        return new BigDecimal("0");
    }

    @Override
    public Coordinate forward(BigDecimal offset) {
        return new Longitude(super.value.subtract(offset));
    }
}
