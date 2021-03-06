package com.s_hashtag.instagram.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Crawler {
    private static final String USER_AGENT =
//            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.104 Whale/3.13.131.36 Safari/537.36";
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.57 Safari/537.36";
//            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.19582";
                "Dooble/0.07 (de_CH) WebKit";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

//    private Date time = new Timestamp(System.currentTimeMillis());

//    Proxy proxy = new Proxy(Proxy.Type.HTTP,
//            new InetSocketAddress("127.0.0.1", 1080));

    public String crawl(String url) {
        try {
//            if(url.equals("https://free-proxy-list.net/")) {
//                Document doc= Jsoup.connect(url)
//                        .userAgent(USER_AGENT)
//                        .get();
//
//                return doc.toString();
//            }

//            Connection.Response initial=Jsoup.connect("https://www.instagram.com/accounts/login/")
//                    .method(Connection.Method.GET)
//                    .execute();
//            Document key=initial.parse();
//            String csrf=initial.cookies().get("csrftoken");
//
//            Connection.Response login=Jsoup.connect("https://www.instagram.com/accounts/login/")
//                    .cookies(initial.cookies())
//                    .data("id","1", "password", "1")
//
//                    .data("auto", "false", "csrftoken", csrf)
//
//                    // add other values
//
//                    .method(Connection.Method.POST)
////                    .method(Connection.Method.GET)
//
//                    .timeout(5000)
//
//                    .execute();
//
//            Document doc=Jsoup.connect(url)
//
//                    .cookies(login.cookies())
//
//                    .timeout(3000000).get();
//
//            return doc.toString();



//            Connection.Response loginPageResponse = Jsoup.connect("https://www.instagram.com/accounts/login")
//                    .timeout(3000)
//                    .header("Origin", "https://www.instagram.com/")
//                    .header("Referer", "https://www.instagram.com/accounts/login")
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .header("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7")
//                    .method(Connection.Method.GET)
//                    .execute();
//
//            // ????????? ??????????????? ?????? ??????
//            Map<String, String> loginTryCookie = loginPageResponse.cookies();
//
//            // ????????? ??????????????? ???????????? ?????? ???????????? ?????? ????????????
////            Document loginPageDocument = loginPageResponse.parse();
//
////            String ofp = loginPageDocument.select("input.ofp").val();
////            String nfp = loginPageDocument.select("input.nfp").val();
//
//            // ????????? ??? ?????????
//            Map<String, String> data = new HashMap<>();
//            Date time = new Timestamp(System.currentTimeMillis());
//            data.put("loginId", "jerodis10");
//            data.put("enc_password", "#PWD_INSTAGRAM_BROWSER:0:" + time.getTime() + ":" + "b84g9f156@");
//            data.put("queryParams", "{}");
//            data.put("optIntoOneTap", "false");
//
//            // ?????????(POST)
//            Connection.Response response = Jsoup.connect("https://www.instagram.com/accounts/login")
//                    .userAgent(USER_AGENT)
//                    .timeout(3000)
//                    .header("Origin", "https://www.instagram.com/")
//                    .header("Referer", "https://www.instagram.com/accounts/login/")
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .header("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7")
//
////                    .header("X-Requested-With", "XMLHttpRequest")
////                    .header("x-csrftoken","all")
//                    .cookies(loginTryCookie)
//                    .data(data)
//                    .method(Connection.Method.POST)
//                    .execute();
//
//            Map<String, String> loginCookie = response.cookies();
//
//            Document hashTagePageDocument = Jsoup.connect(url)
//                    .userAgent(USER_AGENT)
////                    .header("Referer", "https://www.instagram.com/")
//                    .header("Referer", "https://www.instagram.com/accounts/login/")
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .header("Accept-Encoding", "gzip, deflate, sdch")
//                    .header("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7")
//                    .cookies(loginCookie) // ????????? ?????? '????????? ???' ??????
//                    .get();
//
//            return hashTagePageDocument.toString();



            Document doc= Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .header("Referer", "https://www.tistory.com")
                    .get();
            return doc.toString();

        } catch (HttpStatusException e) {
            if (e.getStatusCode() == NOT_FOUND) {
//                throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_URL);
                return null;
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
            }
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } catch (IOException e) {
            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
        } finally {

        }

//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }
}
