package com.devitvish.nsestockprice.config;

import java.net.CookieManager;
import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Configuration
public class HttpClientConfiguration {

    @Value("${httpclient.timeout.second:10}")
    private Long timeoutInSeconds;

    @Bean
    public HttpClient httpClient(){
        log.info("httpclient.timeout.second={}", timeoutInSeconds);  
        return HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(timeoutInSeconds))
            .cookieHandler(new CookieManager())
            .build();
    }

}
