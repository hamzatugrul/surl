package com.hamzatugrul.surl.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@Document(collection = "shorturl")
public class ShortUrlEntity implements Serializable {

    private static final long serialVersionUID = 6503444006709955915L;

    @Id
    private String id;
    @Indexed
    private String shortUrlKey;
    @Indexed
    private String longUrl;

    private LocalDate createdDate;
    private LocalDate lastAccessDate;

    private List<VisitStatusEntity> visitStatusEntities = new ArrayList<>();

    public ShortUrlEntity() {
    }

    public ShortUrlEntity(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortUrlKey() {
        return shortUrlKey;
    }

    public void setShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public List<VisitStatusEntity> getVisitStatusEntities() {
        return visitStatusEntities;
    }

    public void setVisitStatusEntities(List<VisitStatusEntity> visitStatusEntities) {
        this.visitStatusEntities = visitStatusEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShortUrlEntity that = (ShortUrlEntity) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.shortUrlKey, that.shortUrlKey) &&
                Objects.equals(this.longUrl, that.longUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortUrlKey, longUrl);
    }
}