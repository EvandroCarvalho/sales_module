package br.com.salesModule.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CaffeineCache customerCache() {
		return new CaffeineCache("customerInCache",
				Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(100).build());
	}

	@Bean
	public CaffeineCache employeeCache() {
		return new CaffeineCache("employeeInCache",
				Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(100).build());
	}

	@Bean
	public CaffeineCache productCache() {
		return new CaffeineCache("productInCache",
				Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(100).build());
	}

	@Bean
	public CaffeineCache userCache() {
		return new CaffeineCache("usersInCache",
				Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(100).build());
	}
}
