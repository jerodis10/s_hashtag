package com.s_hashtag.kakaoapi.factory;//package com.s_hashtag.kakaoapi.domain.factory;

import com.s_hashtag.kakaoapi.rect.Rect;
import com.s_hashtag.kakaoapi.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.rect.location.Latitude;
import com.s_hashtag.kakaoapi.rect.location.Longitude;

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
