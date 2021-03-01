package com.hamzatugrul.surl.controller;

import com.hamzatugrul.surl.exception.InvalidURLException;
import com.hamzatugrul.surl.exception.KeyNotFoundException;
import com.hamzatugrul.surl.model.ShortenerDTO;
import com.hamzatugrul.surl.model.StatusDTO;
import com.hamzatugrul.surl.service.UrlShortenerService;
import com.hamzatugrul.surl.validator.SUrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@RestController
public class ShortURLController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private UrlShortenerService urlShortenerService;

    @Autowired
    public ShortURLController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{surl}")
    public void redirectShortUrl(@PathVariable String surl, HttpServletRequest request, HttpServletResponse response)
            throws InvalidURLException, KeyNotFoundException
    {
        SUrlValidator.checkShortURlKey(surl);

        final String longUrl = urlShortenerService.resolve(surl);
        logger.info("Request for redirecting... ShortUrlKey={} , ResolvedLongURL={}", surl, longUrl);

        response.setHeader("Location", longUrl);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }

    @PostMapping(value = "/api/v1/shortener",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortenerDTO> urlShortener(@RequestBody @Valid @NotNull ShortenerDTO requestedShortener)
            throws IllegalArgumentException, InvalidURLException
    {
        //TODO: checkValidity of requested longURL, could be throw invalid URL exception
        URI uri = SUrlValidator.checkUrlValidityAndReturnUri(requestedShortener.getLongUrl());

        ShortenerDTO shortUrl = urlShortenerService.shortener(requestedShortener);
        return ResponseEntity.created(uri).body(shortUrl);
    }

    @GetMapping(value = "/api/v1/status/{surl}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusDTO> getStatus(@PathVariable String surl)
            throws InvalidURLException, KeyNotFoundException
    {
        SUrlValidator.checkShortURlKey(surl);
        StatusDTO status = urlShortenerService.getStatus(surl);
        logger.info("Requested Status for ShortUrlKey={} , ResolvedLongURL={}", surl, status.getLongUrl());

        return ResponseEntity.ok(status);
    }
}
