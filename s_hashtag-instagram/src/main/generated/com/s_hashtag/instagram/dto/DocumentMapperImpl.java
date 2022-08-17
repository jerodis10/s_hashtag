package com.s_hashtag.instagram.dto;

import com.s_hashtag.kakaoapi.dto.external.Document;
import com.s_hashtag.kakaoapi.dto.external.Document.DocumentBuilder;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-17T20:31:33+0900",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_342 (Amazon.com Inc.)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public Document toDocument(PlaceDto placeDto) {
        if ( placeDto == null ) {
            return null;
        }

        DocumentBuilder document = Document.builder();

        document.id( placeDto.getKakao_id() );
        document.placeName( placeDto.getPlace_name() );
        document.categoryGroupCode( placeDto.getCategory_group_code() );
        document.placeUrl( placeDto.getPlace_url() );
        document.latitude( String.valueOf( placeDto.getLatitude() ) );
        document.longitude( String.valueOf( placeDto.getLongitude() ) );

        return document.build();
    }
}
