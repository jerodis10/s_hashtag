package com.s_hashtag.instagram.crawler;

import com.s_hashtag.instagram.exception.CrawlerException;
import com.s_hashtag.instagram.exception.CrawlerExceptionStatus;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Crawler {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public String crawl(String url) {
        try {
            Connection.Response initial=Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute();
            Document key=initial.parse();
            String csrf=initial.cookies().get("csrftoken");
            //get csrf token from cookies

//            Map<String, String> data = new HashMap<>();
//            data.put("id", "jerodis10");
//            data.put("password", "b84g9f156@");
//            data.put("redirect", "/");

            Connection.Response login=Jsoup.connect("https://www.instagram.com/accounts/login/")
                    .userAgent(USER_AGENT)
                    .cookies(initial.cookies())
                    .data("id","jerodis10", "password", "b84g9f156@")
                    .data("auto", "false", "csrftoken", csrf)
//                    .data(data)

                    // add other values

                    .method(Connection.Method.POST)
                    .timeout(5000)
                    .execute();

            Document doc=Jsoup.connect(url)
                    .cookies(login.cookies())
                    .timeout(3000000).get();

            return "1";

//            return Jsoup.connect(url)
//                    .cookies(login.cookies())
//                    .timeout(3000000)
//                    .get()
//                    .toString();

//            return Jsoup.connect(url)
//                    .userAgent(USER_AGENT)
//                    .timeout(HOLDING_TIME)
//                    .get()
//                    .body()
//                    .toString();

//            return "ss";

//            String URL = "https://heodolf.tistory.com/18";
//            Connection conn = Jsoup.connect(URL);
//            Document html = conn.get();
//            return html.toString();
//        } catch (HttpStatusException e) {
//            if (e.getStatusCode() == NOT_FOUND) {
//                throw new CrawlerException(CrawlerExceptionStatus.NOT_FOUND_URL);
//            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
//                throw new CrawlerException(CrawlerExceptionStatus.TOO_MANY_REQUEST);
//            }
//            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
//        } catch (IOException e) {
//            throw new CrawlerException(CrawlerExceptionStatus.URL_NOT_CONNECT);
//        }

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
