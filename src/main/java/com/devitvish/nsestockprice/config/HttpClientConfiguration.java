package com.devitvish.nsestockprice.config;

import java.net.CookieManager;
import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mizosoft.methanol.Methanol;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Configuration
public class HttpClientConfiguration {

    @Value("${httpclient.timeout.second:10}")
    private Long timeoutInSeconds;

    private static final String BASE_URI = "https://www.nseindia.com";
    private static final String HEADER_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0";
    private static final String HEADER_AUTHORITY = "www.nseindia.com";
    private static final String HEADER_ACCEPT = "*/*";
    private static final String HEADER_ACCEPT_LANG = "en-US,en;q=0.9";
    private static final String HEADER_DNT = "1";
    private static final String HEADER_UPGRADE_INSECURE_REQUESTS = "1";

    @Bean
    public HttpClient httpClient(){
        log.info("httpclient.timeout.second={}", timeoutInSeconds);  
        final HttpClient baseClient = HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(timeoutInSeconds))
            .cookieHandler(new CookieManager())
            .build();

        return Methanol.newBuilder(baseClient)
            .autoAcceptEncoding(true)
            .baseUri(BASE_URI)
            .userAgent(HEADER_USER_AGENT)
            .defaultHeader("authority", HEADER_AUTHORITY)
            .defaultHeader("accept", HEADER_ACCEPT)
            .defaultHeader("accept-language", HEADER_ACCEPT_LANG)
            .defaultHeader("dnt", HEADER_DNT)
            .defaultHeader("upgrade-insecure-requests", HEADER_UPGRADE_INSECURE_REQUESTS)
            .build();
    }

}
