package com.s_hashtag.kakaoapi.factory;

import com.s_hashtag.common.domain.kakao.dto.external.Coordinate;
import com.s_hashtag.common.domain.kakao.dto.external.Latitude;
import com.s_hashtag.common.domain.kakao.dto.external.Longitude;
import com.s_hashtag.common.domain.kakao.dto.external.Rect;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RectFactory {

    public static Rect CreateRect(String p_minLatitude, String p_maxLatitude, String p_minLongitude, String p_maxLongitude) {
        Coordinate minLatitude = new Latitude(new BigDecimal(p_minLatitude));
        Coordinate maxLatitude = new Latitude(new BigDecimal(p_maxLatitude));
        Coordinate minLongitude = new Longitude(new BigDecimal(p_minLongitude));
        Coordinate maxLongitude = new Longitude(new BigDecimal(p_maxLongitude));

        return new Rect(minLatitude, maxLatitude, maxLongitude, minLongitude);
    }

    public static List<Rect> divide(Rect rect) {
        List<Rect> rects = new ArrayList<>();

        Coordinate minLatitude = rect.getMinLatitude();
        Coordinate maxLatitude = rect.getMaxLatitude();
        Coordinate minLongitude = rect.getMinLongitude();
        Coordinate maxLongitude = rect.getMaxLongitude();

        Coordinate midLatitude = new Latitude(minLatitude.makeMedian(maxLatitude));
        Coordinate midLongitude = new Longitude(minLongitude.makeMedian(maxLongitude));

        rects.add(new Rect(minLatitude, midLatitude, minLongitude, midLongitude));
        rects.add(new Rect(minLatitude, midLatitude, midLongitude, maxLongitude));
        rects.add(new Rect(midLatitude, maxLatitude, minLongitude, midLongitude));
        rects.add(new Rect(midLatitude, maxLatitude, midLongitude, maxLongitude));

        return rects;
    }
}
