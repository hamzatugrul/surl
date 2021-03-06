package com.hamzatugrul.surl.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */
public class ShortenerDTO {

    @NotBlank
    @Pattern(regexp = "^(https?://)((([a-z0-9][a-z0-9.-]?)*[a-z0-9]){1,63})+\\.[a-z]{2,63}(:\\d{1,5})?(/.*)?$", message = "must be a valid url")
    private String longUrl;

    private String shortURL;
    private String shortUrlKey;

    public ShortenerDTO() {
    }

    @JsonCreator
    public ShortenerDTO(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrlKey() {
        return shortUrlKey;
    }

    public void setShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    @Override
    public String toString() {
        return "ShortenerDTO{" +
                "longUrl='" + longUrl + '\'' +
                ", shortURL='" + shortURL + '\'' +
                ", shortUrlKey='" + shortUrlKey + '\'' +
                '}';
    }
}
