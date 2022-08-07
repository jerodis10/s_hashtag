package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.proxy.Proxies;
import com.s_hashtag.instagram.proxy.Proxy;
import com.s_hashtag.kakaoapi.service.KakaoApiService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@Slf4j
class CrawlerTest {

//    @Autowired
//    private KakaoApiService kakaoApiService;

//    @Autowired
//    private InstagramCrawler instagramCrawler;

    @Autowired
    private InstaCrawlingResult instaCrawlingResult;

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

    @DisplayName("인스타그램 아이디 정규식 테스트2")
    @Test
    void patternTest2() throws IOException {

//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?hl=ko")
        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/삼겹살/?hl=ko")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.19582")
                .get();

        String counnt = RegexPattern.POST_COUNT.extract(doc.toString());

        assertThat(counnt).isEqualTo("578");
    }

    @DisplayName("인스타그램 크롤링 테스트")
    @Test
    void crawlingTest() throws IOException {

//        System.setProperty("http.proxyHost", "107.182.135.43");
//        System.setProperty("http.proxyPort", "8089");

//        Document doc= Jsoup.connect("https://www.instagram.com")
        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?hl=ko")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/양평해장국/?hl=ko")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/삼겹살/?hl=ko")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?__a=1&__d=dis")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?__a")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/버거킹인천서창SK점/?hl=ko")
//        Document doc= Jsoup.connect("https://stackoverflow.com/questions/32623199/log-into-instagram-with-java")
//                .userAgent("Dooble/0.07 (de_CH) WebKit")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.19582")
                .get();
        String hashtagCount = RegexPattern.HASH_TAG_COUNT.extract(doc.toString());
//        String likeCount = RegexPattern.LIKE_COUNT.extract(doc.toString());
        assertThat(doc).isNotNull();
    }

    @DisplayName("인스타그램 크롤링 봇탐지 테스트")
    @Test
    void crawlingBotTest() throws IOException {
        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?hl=ko")
//        Document doc= Jsoup.connect("https://www.instagram.com/explore/tags/버거킹인천서창SK점/?hl=ko")
                .userAgent("Dooble/0.07 (de_CH) WebKit")
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.19582")
                .get();
        String robot = RegexPattern.ROBOT.extract(doc.toString());
//        String robot
        assertThat(robot).isEqualTo("robots");
//        assertThat(doc).isNotNull();
    }

//    @DisplayName("인스타그램 크롤링 해시태그 개수 테스트")
//    @Test
//    void crawlingHashTagCountTest() throws IOException {
//
////        System.setProperty("http.proxyHost", "107.182.135.43");
////        System.setProperty("http.proxyPort", "8089");
//
////        Document doc= Jsoup.connect("https://www.instagram.com")
//        String body = Jsoup.connect("https://www.instagram.com/explore/tags/도로시마켓/?hl=ko")
////        Document doc= Jsoup.connect("https://stackoverflow.com/questions/32623199/log-into-instagram-with-java")
//                .userAgent("Dooble/0.07 (de_CH) WebKit")
//                .get().toString();
//
//        String hashTagName =  instagramCrawler.createCrawlingDto("도로시마켓", body, null).getHashtagName();
//        assertThat(hashTagName).isGreaterThan("0");
//    }

    @DisplayName("프록시 이용한 URL 테스트")
    @Test
    void urlTest() throws IOException {

        System.setProperty("http.proxyHost", "107.182.135.43");
        System.setProperty("http.proxyPort", "8089");

//        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://www.instagram.com/explore/tags/%EB%8F%84%EB%A1%9C%EC%8B%9C%EB%A7%88%EC%BC%93/").openConnection();
//        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://www.instagram.com/").openConnection();
//        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://www.naver.com/").openConnection();
//        httpURLConnection.setConnectTimeout(5000);
//        httpURLConnection.setReadTimeout(5000);
//        httpURLConnection.connect();
//        boolean isOnline = httpURLConnection.usingProxy();
//        httpURLConnection.disconnect();

         URL url = new URL("http://www.naver.com");
         URLConnection urlConn = url.openConnection();
//        boolean isOnline = URLConnection.usingProxy();

        assertThat("doc").isNotNull();
//        assertThat(isOnline).isEqualTo(true);
    }

//    @DisplayName("kakao api를 통해 수집한 가게 가져오기")
//    @Test
//    void PlaceCount() throws IOException {
//
//        Coordinate minLatitude = new Latitude(new BigDecimal("37.41847533960485"));
//        Coordinate maxLatitude = new Latitude(new BigDecimal("37.46625487247741"));
//        Coordinate minLongitude = new Longitude(new BigDecimal("126.75578831035362"));
//        Coordinate maxLongitude = new Longitude(new BigDecimal("126.8051487382762"));
//        Rect rect = new Rect(minLatitude, maxLatitude, minLongitude, maxLongitude);
//
//        List<KakaoPlaceDto> kakaoPlaceDto_FD6 = kakaoApiService.findPlaces("FD6", rect);
//
//        assertThat(kakaoPlaceDto_FD6).isNotNull();
//    }
}
















