package com.s_hashtag.instagram.dto;

import com.s_hashtag.kakaoapi.dto.external.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mapping(source = "kakao_id", target = "id")
    @Mapping(source = "place_name", target = "placeName")
    @Mapping(source = "category_group_code", target = "categoryGroupCode")
    @Mapping(source = "place_url", target = "placeUrl")
//    @Mapping(source = "category_group_name", target = "categoryGroupName")
//    @Mapping(source = "address_name", target = "addressName")
//    @Mapping(source = "road_address_name", target = "roadAddressName")
    Document toDocument(PlaceDto placeDto);
}
