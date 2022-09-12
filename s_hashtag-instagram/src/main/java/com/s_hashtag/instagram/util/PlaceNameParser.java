package com.s_hashtag.instagram.util;

import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class PlaceNameParser {

    public static String parsePlaceName(String placeName) {
        String parsedPlaceName = placeName.replaceAll(" ", "");
        Optional<PlaceNameType> optionalPlaceNameType = PlaceNameType.find(parsedPlaceName);
        return optionalPlaceNameType
                .map(placeNameType -> placeNameType.parsePlaceName(parsedPlaceName))
                .orElseGet(() -> parsedPlaceName);
    }
}
