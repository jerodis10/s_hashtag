package com.s_hashtag.instagram.dto;

import lombok.Getter;

@Getter
public class PostDto {
    public static final String POST_URL_FORMAT = "https://www.instagram.com/p/%s";

    private final String postUrl;
    private final String imageUrl;

    public PostDto(String postUrlKey, String imageUrl) {
        this.postUrl = String.format(POST_URL_FORMAT, postUrlKey);
        this.imageUrl = imageUrl;
    }
}
