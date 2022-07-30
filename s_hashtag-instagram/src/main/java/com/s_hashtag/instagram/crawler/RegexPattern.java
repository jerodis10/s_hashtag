package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
enum RegexPattern {
    INSTAGRAM_ID(Pattern.compile("(\"hashtag\":\\{\"id\":\")([0-9]+)(\")")),
    HASH_TAG_COUNT(Pattern.compile("(\"edge_hashtag_to_media\":\\{\"count\"):([0-9]+)")),
    HASHTAG_POPULAR_POSTS_INFO(Pattern.compile("(\"edge_hashtag_to_top_posts\":)(.*)(,\"edge_hashtag_to_content_advisory\")")),
    ROBOT(Pattern.compile("(robots)"));
//    ROBOT(Pattern.compile("(\"name\"=\\{\"robots\")"));

    private final Pattern pattern;

    RegexPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String extract(String body) {
        Matcher matcher = this.pattern.matcher(body);
        if (matcher.find()) {
            if(matcher.groupCount() == 1) throw new CrawlerException(CrawlerExceptionStatus.BOT_DETECTION);
            else return matcher.group(2);
        }

//        throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_MATCH_REGEX);
        return null;
    }

//    public String roBotExtract(String body) {
//        Matcher matcher = this.pattern.matcher(body);
//        if (matcher.find()) {
//            return matcher.group();
//        }
//        throw new CrawlerException(CrawlerExceptionStatus.BOT_DETECTION);
//    }
}
