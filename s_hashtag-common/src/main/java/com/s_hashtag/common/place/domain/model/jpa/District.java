//package com.s_hashtag.common.place.domain.model.jpa;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AttributeOverride(name = "id", column = @Column(name = "DISTRICT_ID"))
//@Table(uniqueConstraints = @UniqueConstraint(name = "UK_DISTRICT_NAME", columnNames = "DISTRICT_NAME"))
//@Entity
//public class District extends BaseEntity {
//    @Column(name = "DISTRICT_NAME")
//    private String districtName;
//
//    public District(final String districtName) {
//        this.districtName = districtName.trim();
//    }
//
//    public void update(final String districtName) {
//        if (!StringUtils.isEmpty(districtName)) {
//            this.districtName = districtName;
//        }
//    }
//}
