package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.instagram.proxy.Proxies;
import com.s_hashtag.instagram.proxy.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CrawlerTest {
    @DisplayName("인자로 넘긴 인덱스에 해당하는 프록시로 잘 설정되는지 테스트")
    @Test
    void setHostAndPort() {
        Proxies proxies = new Proxies(Arrays.asList(
                new Proxy("123.123.123.1", "11"),
                new Proxy("123.123.123.2", "12"),
                new Proxy("123.123.123.3", "13")
        ));

        proxies.setHostAndPort(1);
        String actualHost = System.getProperty("http.proxyHost");
        String actualPort = System.getProperty("http.proxyPort");

        assertAll(
                () -> assertThat(actualHost).isEqualTo("123.123.123.2"),
                () -> assertThat(actualPort).isEqualTo("12")
        );
    }

    @DisplayName("인스타그램 아이디 정규식 테스트")
    @Test
    void patternTest() throws IOException {

//        FileInputStream fis = new FileInputStream("src/test/resources/stringtoolong.txt");
//        String stringTooLong = IOUtils.toString(fis, "UTF-8");

        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/test.txt")));

        String extracted = RegexPattern.INSTAGRAM_ID.extract(body);

        assertThat(extracted).isEqualTo("17842213252071142");
    }

    @DisplayName("인스타그램 크롤링 테스트")
    @Test
    void crawlingTest() throws IOException {

//        Document doc= Jsoup.connect("https://www.instagram.com")
        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?hl=ko")
//        Document doc= Jsoup.connect("https://stackoverflow.com/questions/32623199/log-into-instagram-with-java")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.57 Safari/537.36")
                .get();

        assertThat(doc).isNotNull();
    }
}