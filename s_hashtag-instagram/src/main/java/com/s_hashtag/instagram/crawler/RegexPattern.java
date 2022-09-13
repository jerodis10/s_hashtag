package com.s_hashtag.instagram.crawler;

import com.s_hashtag.common.domain.instagram.exception.CrawlerException;
import com.s_hashtag.common.domain.instagram.exception.CrawlerExceptionStatus;
import lombok.Getter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
enum RegexPattern {
    INSTAGRAM_ID(Pattern.compile("(\"hashtag\":\\{\"id\":\")([0-9]+)(\")")),
    HASH_TAG_COUNT(Pattern.compile("(\"edge_hashtag_to_media\":\\{\"count\"):([0-9]+)")),
    HASHTAG_POPULAR_POSTS_INFO(Pattern.compile("(\"edge_hashtag_to_top_posts\":)(.*)(,\"edge_hashtag_to_content_advisory\")")),
    ROBOT(Pattern.compile("(robots)")),
    LIKE_COUNT(Pattern.compile("(\"like_count\":\\{\"count\"):([0-9]+)")),
    POST_COUNT(Pattern.compile("meta content=\"게시물 ([0-9]+)(\\W)"));

    private final Pattern pattern;

    RegexPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String extract(String body) {
        Matcher matcher = this.pattern.matcher(body);
        if (matcher.find()) {
            if(matcher.group(2).equals("개") || matcher.group(2).equals("."))
                return matcher.group(1);
            else
                return matcher.group(2);
        }

        throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_MATCH_REGEX);
    }

    public String roBotExtract(String body) {
        Matcher matcher = this.pattern.matcher(body);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new CrawlerException(CrawlerExceptionStatus.BOT_DETECTION);
    }
}
