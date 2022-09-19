package com.s_hashtag.common.domain.instagram.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto {
    public static final String POST_URL_FORMAT = "https://www.instagram.com/p/%s";

    private String instagramPostId;
    private String instagramDocumentId;
    private String instagramPostDocumentId;
    private String postUrl;
    private String imageUrl;
    private String placeUrl;

    public PostDto(String instagramPostId, String instagramDocumentId, String instagramPostDocumentId, String postUrlKey, String imageUrl) {
        this.instagramPostId = instagramPostId;
        this.instagramDocumentId = instagramDocumentId;
        this.instagramPostDocumentId = instagramPostDocumentId;
        this.postUrl = String.format(POST_URL_FORMAT, postUrlKey);
        this.imageUrl = imageUrl;
    }
}
