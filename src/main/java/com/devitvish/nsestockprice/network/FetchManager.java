package com.devitvish.nsestockprice.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Optional;

public interface FetchManager {

    Optional<HttpResponse<String>> getFrom(URI endpoint, int retries) throws IOException, InterruptedException;
    Optional<HttpResponse<String>> getFrom(URI endpoint) throws IOException, InterruptedException;
    <T> Optional<T> unmarshal(String jsonString, Class<T> clazz);
}
