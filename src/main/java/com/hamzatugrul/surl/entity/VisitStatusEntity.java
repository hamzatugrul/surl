package com.hamzatugrul.surl.entity;

import java.io.Serializable;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/28/2021
 */
public class VisitStatusEntity implements Serializable {

    private Integer visits;
    private Integer dayOfYear;

    public VisitStatusEntity() {
    }

    public VisitStatusEntity(Integer visits, Integer dayOfYear) {
        this.visits = visits;
        this.dayOfYear = dayOfYear;
    }

    public void incrementVisit() {
        this.visits++;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }
}
