package com.devitvish.nsestockprice.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.Data;
import lombok.NonNull;

@Data
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public Caffeine<Object, Object> buildCaffeineCache(){
        // The NSE server is near real-time by delay of 2-4 minutes
        // Total stocks listsed in NSE are under 2.2k. 3k max entries is a good measure.
        return Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(2*60)).maximumSize(3000).initialCapacity(10);
    }

    @Bean
    public CacheManager cacheManager(@NonNull Caffeine<Object,Object> caffeine){
        final CaffeineCacheManager cm = new CaffeineCacheManager();
        cm.setCaffeine(caffeine);
        return cm;
    }

}
