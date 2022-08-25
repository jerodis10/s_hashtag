package com.s_hashtag.common.instagram.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "INSTAGRAM_POST")
@Entity
public class InstagramPost {

    @Id
    @GeneratedValue
    @Column(name = "INSTAGRAM_POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTAGRAM_ID")
    private Instagram instagram;

    private String postUrl;

    private String imageUrl;

    //==연관관계 메서드==//
    public void setInstagram(Instagram instagram) {
        this.instagram = instagram;
        instagram.getInstagramPosts().add(this);
    }

}
