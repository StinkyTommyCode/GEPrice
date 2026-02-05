package com.geprice.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "previous_week_averages")
public class PreviousWeeklyAverage {
    @Id
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "avg")
    private Long average;

    public long getAverage() {
        return average != null ? average : 0;
    }
}
