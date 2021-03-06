package com.s_hashtag.instagram.proxy;

import lombok.Getter;

@Getter
public class Proxy {
    private final String host;
    private final String port;

    public Proxy(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public void setHostAndPort() {
        System.setProperty("http.proxyHost", this.host);
        System.setProperty("http.proxyPort", this.port);
    }

    public void clearProperty() {
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
    }
}
