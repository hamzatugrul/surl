package com.hamzatugrul.surl.model;

import java.time.LocalDate;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 28/02/2021
 */
public class StatusDTO extends ShortenerDTO {

    private LocalDate lastAccessDate;
    private Long max;
    private Long min;
    private Double dailyAverage;
    private Long totalPerYear;

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Double getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Double dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public Long getTotalPerYear() {
        return totalPerYear;
    }

    public void setTotalPerYear(Long totalPerYear) {
        this.totalPerYear = totalPerYear;
    }
}
