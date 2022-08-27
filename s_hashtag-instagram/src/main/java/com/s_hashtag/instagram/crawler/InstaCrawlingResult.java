package com.s_hashtag.instagram.crawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.s_hashtag.common.domain.instagram.dto.external.PostDto;
import com.s_hashtag.common.domain.instagram.dto.external.PostDtos;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class InstaCrawlingResult {
    public static final String POST_ID = "id";
    public static final String POST_URL_KEY = "shortcode";
    public static final String DISPLAY_URL_KEY = "display_url";
    public static final String SOURCE_KEY = "edges";

    private final String body;

    public String findInstagramId() {
        return RegexPattern.INSTAGRAM_ID.extract(body);
    }

    public String findHashTagCount() {
        return RegexPattern.HASH_TAG_COUNT.extract(body);
    }

    public String findPostCount() {
        return RegexPattern.POST_COUNT.extract(body);
    }

    public String checkRobot() {
        return RegexPattern.ROBOT.extract(body);
    }

    public PostDtos findPostDtos() {
        List<PostDto> postDtos = new ArrayList<>();
        String popularPostsInfo = RegexPattern.HASHTAG_POPULAR_POSTS_INFO.extract(body);
        if(popularPostsInfo != null) {
            String instagram_id = RegexPattern.INSTAGRAM_ID.extract(body);
            JsonElement popularPostsJson = JsonParser.parseString(popularPostsInfo);
            JsonArray sources = popularPostsJson.getAsJsonObject().get(SOURCE_KEY).getAsJsonArray();
            for (JsonElement source : sources) {
                String instagram_post_id = JsonExplorer.findByKey(source, POST_ID);
                String postUrl = JsonExplorer.findByKey(source, POST_URL_KEY);
                String displayUrl = JsonExplorer.findByKey(source, DISPLAY_URL_KEY);
                postDtos.add(new PostDto(instagram_post_id, instagram_id, postUrl, displayUrl));
            }
        }

        return new PostDtos(postDtos);
    }
}
