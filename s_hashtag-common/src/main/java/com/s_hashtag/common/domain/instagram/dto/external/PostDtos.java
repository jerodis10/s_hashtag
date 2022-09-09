package com.s_hashtag.common.domain.instagram.dto.external;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDtos {
    public static final int POPULAR_POST_SIZE = 9;

    private final List<PostDto> postDtos;

    public PostDtos(List<PostDto> postDtos) {
        if (postDtos.size() > POPULAR_POST_SIZE) {
            throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_POPULAR_POST);
        }
        this.postDtos = new ArrayList<>(postDtos);
    }

    public int size() {
        return this.postDtos.size();
    }
}
