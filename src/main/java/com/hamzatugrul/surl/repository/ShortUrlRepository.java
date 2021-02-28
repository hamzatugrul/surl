package com.hamzatugrul.surl.repository;

import com.hamzatugrul.surl.config.RedisCacheConfig;
import com.hamzatugrul.surl.entity.ShortUrlEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/28/2021
 */

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrlEntity, String> {

    ShortUrlEntity findByLongUrl(String longUrl);

    @Cacheable(value = RedisCacheConfig.CACHE_SURL)
    ShortUrlEntity findByShortUrlKey(String shortUrlKey);

    @CachePut(value = RedisCacheConfig.CACHE_SURL, key = "#shortUrlEntity.shortUrlKey")
    ShortUrlEntity save(ShortUrlEntity shortUrlEntity);
}
