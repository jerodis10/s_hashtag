package com.s_hashtag.common.factory;

import com.s_hashtag.common.domain.kakao.dto.external.Coordinate;
import com.s_hashtag.common.domain.kakao.dto.external.Latitude;
import com.s_hashtag.common.domain.kakao.dto.external.Longitude;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;

import java.math.BigDecimal;

public class RectFactory {

    public Rect CreateRect(String p_minLatitude, String p_maxLatitude, String p_minLongitude, String p_maxLongitude) {
        Coordinate minLatitude = new Latitude(new BigDecimal(p_minLatitude));
        Coordinate maxLatitude = new Latitude(new BigDecimal(p_maxLatitude));
        Coordinate minLongitude = new Longitude(new BigDecimal(p_minLongitude));
        Coordinate maxLongitude = new Longitude(new BigDecimal(p_maxLongitude));

        return new Rect(minLatitude, maxLatitude, maxLongitude, minLongitude);
    }
}
