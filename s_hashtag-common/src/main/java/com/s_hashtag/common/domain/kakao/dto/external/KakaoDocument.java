//package com.s_hashtag.common.domain.kakao.dto.external;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.Objects;
//
//@Getter
////@Setter
//@NoArgsConstructor
////@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class KakaoDocument {
//    private String id;
//    private String placeName;
//    private String categoryName;
//    private String categoryGroupCode;
//    private String categoryGroupName;
//    private String phone;
//    private String addressName;
//    private String roadAddressName;
////    @JsonAlias("y")
//    private String latitude;
////    @JsonAlias("x")
//    private String longitude;
//    private String placeUrl;
//    private String distance;
//
////    @Generated
////    @Builder
////    public KakaoDocument(String id, String placeName, String categoryGroupCode, String roadAddressName,
////                         String latitude, String longitude, String placeUrl) {
////        this.id = id;
////        this.placeName = placeName;
////        this.categoryGroupCode = categoryGroupCode;
////        this.roadAddressName = roadAddressName;
////        this.latitude = latitude;
////        this.longitude = longitude;
////        this.placeUrl = placeUrl;
////    }
//
////    @Generated
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        KakaoDocument document = (KakaoDocument) o;
//        return Objects.equals(id, document.id)
//                && Objects.equals(placeName, document.placeName)
//                && Objects.equals(categoryName, document.categoryName)
//                && Objects.equals(categoryGroupCode, document.categoryGroupCode)
//                && Objects.equals(categoryGroupName, document.categoryGroupName)
//                && Objects.equals(phone, document.phone)
//                && Objects.equals(addressName, document.addressName)
//                && Objects.equals(roadAddressName, document.roadAddressName)
//                && Objects.equals(latitude, document.latitude)
//                && Objects.equals(longitude, document.longitude)
//                && Objects.equals(placeUrl, document.placeUrl)
//                && Objects.equals(distance, document.distance);
//    }
//
////    @Generated
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, placeName, categoryName, categoryGroupCode, categoryGroupName, phone, addressName,
//                roadAddressName, latitude, longitude, placeUrl, distance);
//    }
//}
