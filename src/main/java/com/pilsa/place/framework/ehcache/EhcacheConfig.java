package com.pilsa.place.framework.ehcache;

import com.pilsa.place.biz.vo.response.KeywordResponse;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

/**
 * The type Ehcache config.
 *
 * @author pilsa_home1
 * @since 2021 -09-12 오전 12:40
 */
@Configuration
@EnableCaching
public class  EhcacheConfig {

    /**
     * Eh cache manager cache manager.
     *
     * @return the cache manager
     */
    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        cacheManager.createCache("popularKeywordCache", popularKeywordCache());
        cacheManager.createCache("apiMetaCache", apiCache());

        return cacheManager;

    }

    private javax.cache.configuration.Configuration<String, KeywordResponse> popularKeywordCache() {

        CacheConfigurationBuilder<String, KeywordResponse> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                KeywordResponse.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .offheap(3, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)));

        return Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

    }

    private javax.cache.configuration.Configuration<String, List> apiCache() {

        CacheConfigurationBuilder<String, List> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                List.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .offheap(1, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofDays(1)));

        return Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

    }
}
