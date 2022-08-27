package com.s_hashtag.common.domain.instagram.dto.external;

import lombok.Getter;

@Getter
public class PostDto {
    public static final String POST_URL_FORMAT = "https://www.instagram.com/p/%s";

    private final String instagram_post_id;
    private final String instagram_document_id;
    private final String postUrl;
    private final String imageUrl;

    public PostDto(String instagram_post_id, String instagram_document_id, String postUrlKey, String imageUrl) {
        this.instagram_post_id = instagram_post_id;
        this.instagram_document_id = instagram_document_id;
        this.postUrl = String.format(POST_URL_FORMAT, postUrlKey);
        this.imageUrl = imageUrl;
    }
}
