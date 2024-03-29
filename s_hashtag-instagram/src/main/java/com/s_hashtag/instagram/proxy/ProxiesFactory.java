package com.s_hashtag.instagram.proxy;

import com.s_hashtag.instagram.crawler.Crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxiesFactory {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
    private static final String FREE_PROXY_LIST_URL = "https://free-proxy-list.net/";
    private static final String PROXY_REGEX = "(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)";
    private static final int HOST_INDEX = 1;
    private static final int PORT_INDEX = 2;

    private ProxiesFactory() {
    }

    public static Proxies create() {
        Crawler crawler = new Crawler();
        String body = crawler.crawl(FREE_PROXY_LIST_URL, USER_AGENT);
        Pattern pattern = Pattern.compile(PROXY_REGEX);
        Matcher matcher = pattern.matcher(body);
        List<Proxy> proxies = new ArrayList<>();
        while (matcher.find()) {
            proxies.add(new Proxy(matcher.group(HOST_INDEX), matcher.group(PORT_INDEX)));
        }
        return new Proxies(proxies);
    }
}
