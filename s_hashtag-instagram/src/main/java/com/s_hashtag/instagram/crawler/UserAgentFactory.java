package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.crawler.Crawler;
import com.s_hashtag.instagram.proxy.Proxies;
import com.s_hashtag.instagram.proxy.Proxy;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class UserAgentFactory {
    private static final String USER_AGENT_LIST_URL = "https://www.useragentstring.com/pages/All/";
    private static final String USER_AGENT_REGEX = "(Mozilla\\/.*)(?=<\\/a>)";
//    private static final String USER_AGENT_REGEX = "(^Mozilla\\/.*)";
    private final Random random = new Random();

    List<String> userAgentList = new ArrayList<>();

    public List<String> create() {
        Crawler crawler = new Crawler();
        String body = crawler.crawl(USER_AGENT_LIST_URL);
        Pattern pattern = Pattern.compile(USER_AGENT_REGEX);
        Matcher matcher = pattern.matcher(body);
//        List<String> userAgentList = new ArrayList<>();
        while (matcher.find()) {
            userAgentList.add(matcher.group(1));
        }
        return userAgentList;
    }

    public String getUserAgent(List<String> userAgentList) {
        int size = userAgentList.size();
        return userAgentList.get(random.nextInt(size));
    }
}
