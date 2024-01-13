package com.devitvish.nsestockprice.network.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.devitvish.nsestockprice.network.FetchManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpFetchManagerImpl implements FetchManager {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String RESET_COOKIE_URL = "https://www.nseindia.com";

    private static final String HEADER_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0";

    private void resetCookie() {
        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(RESET_COOKIE_URL))
                .setHeader("User-Agent", HEADER_USER_AGENT)
                .GET()
                .build();
        log.info("resetting the cookies, calling GET to {}", RESET_COOKIE_URL);
        try {
            executeRequest(request);
        } catch (IOException | InterruptedException e) {
            log.error("Error encountered while reseting cookies", e.getMessage());
        }
    }

    private HttpRequest prepareRequest(URI endpoint) {
        return HttpRequest
                .newBuilder()
                .uri(endpoint)
                .setHeader("User-Agent", HEADER_USER_AGENT)
                .GET()
                .build();
    }

    private HttpResponse<String> executeRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        log.info("executing request method={} uri={}", httpRequest.method(), httpRequest.uri().toString());
        return this.httpClient.send(httpRequest, BodyHandlers.ofString());
    }

    @Override
    public Optional<HttpResponse<String>> getFrom(URI endpoint, int retries) throws IOException, InterruptedException {
        final HttpRequest request = prepareRequest(endpoint);
        int retryCount = 0;
        do {
            final HttpResponse<String> response = executeRequest(request);
            if (response.statusCode() == 200) {
                return Optional.of(response);
            }
            resetCookie();
            retryCount++;
            log.info("retry-{} request={}", retryCount, endpoint.toString());
        } while (retryCount < retries);
        log.info("retry count above {}, returning empty optional", retries);
        return Optional.empty();
    }

    @Override
    public Optional<HttpResponse<String>> getFrom(URI endpoint) throws IOException, InterruptedException {
        return getFrom(endpoint, 3);
    }

    @Override
    public <T> Optional<T> unmarshal(String jsonString, Class<T> clazz) {
        T obj = null;
        try {
            obj = this.objectMapper.readerFor(clazz).readValue(jsonString);
            return Optional.of(obj);
        } catch (JsonProcessingException e) {
            log.error("Error parsing json string {} to Class {}", jsonString, clazz.getSimpleName(), e);
            return Optional.empty();
        }
    }

}
