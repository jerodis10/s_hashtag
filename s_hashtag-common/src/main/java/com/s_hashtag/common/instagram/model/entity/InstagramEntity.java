package com.s_hashtag.common.instagram.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "INSTAGRAM")
@Entity
public class InstagramEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "INSTAGRAM_ID")
    private String instagramId;

    @Column(name = "PLACE_ID")
    private String placeId;

    @Column(name = "HASHTAG_NAME")
    private String hashtagName;

    @Column(name = "HASHTAG_COUNT")
    private String hashtagCount;
}
