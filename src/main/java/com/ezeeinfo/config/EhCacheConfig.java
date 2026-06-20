package com.ezeeinfo.config;

import java.util.List;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ezeeinfo.dto.NamespaceDTO;

@Configuration
public class EhCacheConfig {

	@Bean
	public CacheManager cacheManager() {
		return CacheManagerBuilder.newCacheManagerBuilder().withCache("namespaceCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, NamespaceDTO.class, ResourcePoolsBuilder.heap(100))).withCache("namespaceListCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(10))).build(true);
	}
}
