package com.hamzatugrul.surl.service.impl;

import com.hamzatugrul.surl.entity.ShortUrlEntity;
import com.hamzatugrul.surl.model.ShortenerDTO;
import com.hamzatugrul.surl.repository.ShortUrlRepository;
import com.hamzatugrul.surl.service.UrlShortenerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final ShortUrlRepository shortUrlRepository;

    public UrlShortenerServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public ShortenerDTO shortener(ShortenerDTO shortenerDTO) {

        // Check if it is exist in DB
        Optional<ShortUrlEntity> existingShortUrl =
                Optional.ofNullable(shortUrlRepository.findByLongUrl(shortenerDTO.getLongUrl()));

        if (existingShortUrl.isPresent()) {
            shortenerDTO.setLongUrl(existingShortUrl.get().getLongUrl());
            return shortenerDTO;
        }


        //TODO: need to be generate Hash, save it to DB and return it.
        shortenerDTO.setShortURL("shortURL");
        return shortenerDTO;
    }
}
