package com.s_hashtag.kakaoapi.domain.rect.location;

import java.math.BigDecimal;

public abstract class Coordinate {
    protected final BigDecimal value;

    public Coordinate(BigDecimal value) {
        this.value = value;
    }

    protected static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value.compareTo(max) <= 0
                && value.compareTo(min) >= 0;
    }

    public boolean isGreater(Coordinate compare) {
        return this.getValue() > compare.getValue();
    }

    public boolean isLess(Coordinate compare) {
        return this.getValue() < compare.getValue();
    }

    public BigDecimal makeMedian(Coordinate compare) { return this.value.add(compare.value).divide(BigDecimal.valueOf(2)); }

    public double getValue() {
        return value.doubleValue();
    }

    public abstract Coordinate forward(BigDecimal offset);
}
