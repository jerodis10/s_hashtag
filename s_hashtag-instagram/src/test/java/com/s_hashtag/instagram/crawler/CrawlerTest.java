package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import com.s_hashtag.instagram.proxy.Proxies;
import com.s_hashtag.instagram.proxy.Proxy;
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

//    public String extract(String body) {
//        Matcher matcher = Pattern.matcher(body);
//        if (matcher.find()) {
//            return matcher.group(2);
//        }
////        else {
////            return "0";
////        }
//        throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_MATCH_REGEX);
//    }
}