package com.s_hashtag.common.domain.instagram.model.entity;

import com.s_hashtag.common.model.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "INSTAGRAM_POST")
@Entity
public class InstagramPost extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "INSTAGRAM_POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTAGRAM_ID")
    private Instagram instagram;

    private String instagramDocumentId;

    private String instagramPostDocumentId;

    private String postUrl;

    private String imageUrl;

    //==연관관계 메서드==//
    public void setInstagram(Instagram instagram) {
        this.instagram = instagram;
        instagram.getInstagramPosts().add(this);
    }

    @Builder
    public InstagramPost(Instagram instagram, String instagramDocumentId, String instagramPostDocumentId, String postUrl, String imageUrl) {
        this.instagram = instagram;
        this.instagramDocumentId = instagramDocumentId;
        this.instagramPostDocumentId = instagramPostDocumentId;
        this.postUrl = postUrl;
        this.imageUrl = imageUrl;
    }

    public void changeInstagramPost(Instagram instagram, String postUrl, String imageUrl) {
        this.instagram = instagram;
        this.postUrl = postUrl;
        this.imageUrl = imageUrl;
    }
}
