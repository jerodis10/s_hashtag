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
public class InstagramPostEntity {

    @Id
    @GeneratedValue
    @Column(name = "INSTAGRAM_POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTAGRAM_ID")
    private InstagramEntity instagramEntity;

//    @Column(name = "POST_URL")
    private String postUrl;

//    @Column(name = "IMAGE_URL")
    private String imageUrl;

    //==연관관계 메서드==//
    public void setInstagram(InstagramEntity instagramEntity) {
        this.instagramEntity = instagramEntity;
        instagramEntity.getInstagramPostEntitys().add(this);
    }

}
