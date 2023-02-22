package com.superheroes.heroes.configuration;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class CacheConfig {
	  @Autowired
	  CacheManager cacheManager;
	  
	  public void refreshAllCaches() {
	    cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
	  }

	  @Scheduled(fixedRate = 120000) // 5 min
	  public void refreshAllcachesByTime() {
	    log.info("Refresh cache...");
	    refreshAllCaches();
	  }

}
