package com.s_hashtag.kakaoapi.domain.rect;

import com.s_hashtag.kakaoapi.domain.rect.location.Coordinate;
import com.s_hashtag.kakaoapi.domain.rect.location.Latitude;
import com.s_hashtag.kakaoapi.domain.rect.location.Longitude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RectDivider {
    public static List<Rect> divide(Rect rect) {
        Coordinate minLatitude = rect.getMinLatitude();
        Coordinate maxLatitude = rect.getMaxLatitude();
        Coordinate minLongitude = rect.getMinLongitude();
        Coordinate maxLongitude = rect.getMaxLongitude();
        List<Rect> rects = new ArrayList<>();

        Coordinate midLatitude = new Latitude(minLatitude.makeMedian(maxLatitude));
        Coordinate midLongitude = new Longitude(minLongitude.makeMedian(maxLongitude));

        rects.add(new Rect(minLatitude, midLatitude, minLongitude, midLongitude));
        rects.add(new Rect(minLatitude, midLatitude, midLongitude, maxLongitude));
        rects.add(new Rect(midLatitude, maxLatitude, minLongitude, midLongitude));
        rects.add(new Rect(midLatitude, maxLatitude, midLongitude, maxLongitude));

//        for (Coordinate y = maxLongitude; y.isGreater(minLongitude); y = y.forward(offset)) {
//            for (Coordinate x = minLatitude; x.isLess(maxLatitude); x = x.forward(offset)) {
//                rects.add(Rect.byOffset(x, y, offset));
//            }
//        }
        return rects;
    }
}
