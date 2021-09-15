package com.pilsa.place.framework.ehcache;

import com.pilsa.place.biz.vo.response.KeywordResponse;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

/**
 * 인기 키워드, API 메타 에서 Cache를 사용한다.
 * The type Ehcache config.
 *
 * @author pilsa_home1
 * @since 2021 -09-12 오전 12:40
 */
@Configuration
@EnableCaching
public class  EhcacheConfig {

    @Autowired
    private Environment environment;


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

    /** ======================================================================================
     * 최초 캐시 저장 이후부터 지정시간이 되면 캐시를 삭제 하는 시간 : 5초
     * application.yml 에서 정의한다.
    ======================================================================================= */
    private javax.cache.configuration.Configuration<String, KeywordResponse> popularKeywordCache() {
        int liveSeconds = environment.getProperty("system.keyword-cache.live-seconds",Integer.class,10);

        CacheConfigurationBuilder<String, KeywordResponse> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                String.class,
                KeywordResponse.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .offheap(3, MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(liveSeconds)));

        return Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

    }

    /** ======================================================================================
     * 마지막 캐시 요청 이후 지정 시간동안 재 요청이 없다면 삭제 하는 시간 : 1일
    ======================================================================================= */
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
