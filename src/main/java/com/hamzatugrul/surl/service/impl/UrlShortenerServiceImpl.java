package com.hamzatugrul.surl.service.impl;

import com.hamzatugrul.surl.entity.ShortUrlEntity;
import com.hamzatugrul.surl.entity.VisitStatusEntity;
import com.hamzatugrul.surl.exception.KeyNotFoundException;
import com.hamzatugrul.surl.model.ShortenerDTO;
import com.hamzatugrul.surl.model.StatusDTO;
import com.hamzatugrul.surl.repository.ShortUrlRepository;
import com.hamzatugrul.surl.service.KeyGenerator;
import com.hamzatugrul.surl.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.LongSummaryStatistics;
import java.util.Optional;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    @Value("${surl.service.host}")
    private String serviceHost;

    private ShortUrlRepository shortUrlRepository;
    private KeyGenerator keyGenerator;

    @Autowired
    public UrlShortenerServiceImpl(ShortUrlRepository shortUrlRepository,
            KeyGenerator keyGenerator)
    {
        this.shortUrlRepository = shortUrlRepository;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public ShortenerDTO shortener(ShortenerDTO shortenerDTO) {
        logger.info("Checking short url is already exist in DB");
        Optional<ShortUrlEntity> existingShortUrl =
                Optional.ofNullable(shortUrlRepository.findByLongUrl(shortenerDTO.getLongUrl()));

        if (existingShortUrl.isPresent()) {
            logger.info("Short url is exist in DB and returning it. LongURL:" + shortenerDTO.getLongUrl());
            shortenerDTO.setShortUrlKey(existingShortUrl.get().getShortUrlKey());
            shortenerDTO.setShortURL(serviceHost + existingShortUrl.get().getShortUrlKey());
            shortenerDTO.setLongUrl(existingShortUrl.get().getLongUrl());
            return shortenerDTO;
        }

        String key = generateUniqueKey(shortenerDTO.getLongUrl());
        shortenerDTO.setShortUrlKey(key);
        shortenerDTO.setShortURL(serviceHost + key);

        saveShortURL(key, shortenerDTO);
        return shortenerDTO;
    }

    @Override
    public String resolve(String surl) throws KeyNotFoundException {
        ShortUrlEntity shortUrlEntity = findByShortUrl(surl);

        //TODO: This could be done in parallel thread in background
        updateStatus(shortUrlEntity);

        return shortUrlEntity.getLongUrl();
    }

    protected ShortUrlEntity findByShortUrl(String surl) throws KeyNotFoundException {
        return Optional.ofNullable(shortUrlRepository.findByShortUrlKey(surl)).orElseThrow(KeyNotFoundException::new);

/*        Optional<ShortUrlEntity> shortUrlEntity = Optional.ofNullable(shortUrlRepository.findByShortUrlKey(surl));

        if (!shortUrlEntity.isPresent()) {
            throw new KeyNotFoundException("Given Key did not found in Cache and DB.");
        }

        return shortUrlEntity.get();*/
    }

    @Override
    public StatusDTO getStatus(String surl) throws KeyNotFoundException {
        final ShortUrlEntity shortUrlEntity = findByShortUrl(surl);

        LongSummaryStatistics statistics = shortUrlEntity.getVisitStatusEntities()
                .stream()
                .mapToLong(s -> s.getVisits())
                .summaryStatistics();

        StatusDTO status = new StatusDTO();
        status.setShortUrlKey(shortUrlEntity.getShortUrlKey());
        status.setShortURL(serviceHost + shortUrlEntity.getShortUrlKey());
        status.setLongUrl(shortUrlEntity.getLongUrl());
        status.setLastAccessDate(shortUrlEntity.getLastAccessDate());
        status.setDailyAverage(statistics.getAverage());
        status.setMax(statistics.getMax());
        status.setMin(statistics.getMin());
        status.setTotalPerYear(statistics.getSum());

        return status;
    }

    private void updateStatus(ShortUrlEntity shortUrlEntity) {
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        shortUrlEntity.getVisitStatusEntities().stream()
                .filter(statusEntity -> statusEntity.getDayOfYear() == dayOfYear)
                .findFirst()
                .map(statusEntity -> {
                    statusEntity.incrementVisit();
                    return statusEntity;
                })
                .orElseGet(() -> {
                    VisitStatusEntity statusEntity = new VisitStatusEntity(1, dayOfYear);
                    shortUrlEntity.getVisitStatusEntities().add(statusEntity);
                    return statusEntity;
                });

        shortUrlEntity.setLastAccessDate(LocalDate.now());
        shortUrlRepository.save(shortUrlEntity);
    }

    private void saveShortURL(String key, ShortenerDTO shortenerDTO) {
        logger.info("Shortened URL was saving to DB. " + shortenerDTO.toString());
        ShortUrlEntity shortUrl = new ShortUrlEntity();
        shortUrl.setShortUrlKey(key);
        shortUrl.setLongUrl(shortenerDTO.getLongUrl());
        shortUrl.setCreatedDate(LocalDate.now());

        shortUrlRepository.save(shortUrl);
    }

    private String generateUniqueKey(String longUrl) {
        String key = keyGenerator.generateKey(longUrl);

        //TODO: even it is rear case to happen, it could be check whether it is exist in DB.
        // If it is exist, can generate new hash with adding timespan or etc.
        // And Retry Mechanism can be added for checking newly generated value whether it is exist in DB
        // Until getting unique key.
        // OR if master-slave structure will be added, can be try different hash mechanism with CDN.
        //shortUrlRepository.findByShortUrlKey(key);

        logger.info("Unique Key was generated:" + key);
        return key;
    }
}
