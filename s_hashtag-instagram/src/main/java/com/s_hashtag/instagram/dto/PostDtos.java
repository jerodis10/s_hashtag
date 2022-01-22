package com.s_hashtag.instagram.dto;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDtos {
    public static final int POPULAR_POST_SIZE = 9;

    private final List<PostDto> postDtos;

    public PostDtos(List<PostDto> postDtos) {
        if (postDtos.size() != POPULAR_POST_SIZE) {
            throw new CrawlerException(CrawlerExceptionStatus.NOT_ENOUGH_POPULAR_POST);
        }
        this.postDtos = new ArrayList<>(postDtos);
    }

    public int size() {
        return this.postDtos.size();
    }
}
