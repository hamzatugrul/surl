package com.hamzatugrul.surl.controller;

import com.hamzatugrul.surl.exception.InvalidURLException;
import com.hamzatugrul.surl.exception.KeyNotFoundException;
import com.hamzatugrul.surl.model.BaseResponse;
import com.hamzatugrul.surl.model.ShortenerDTO;
import com.hamzatugrul.surl.service.UrlShortenerService;
import com.hamzatugrul.surl.validator.SUrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@RestController
public class ShortURLController {

    private static final Logger logger = LoggerFactory.getLogger(ShortURLController.class);

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public ShortURLController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{surl}")
    public ModelAndView redirectShortUrl(@PathVariable String surl) throws InvalidURLException, KeyNotFoundException {
        SUrlValidator.checkShortURlKey(surl);

        final String longUrl = urlShortenerService.resolve(surl);
        logger.info("Request for redirecting... ShortUrlKey={0} , ResolvedLongURL={1}", surl, longUrl);

        return new ModelAndView(String.format("redirect:%s", longUrl));
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
    public ResponseEntity<BaseResponse> getStatus(@PathVariable String surl) {
        //TODO: getStatus
        return null;
    }
}