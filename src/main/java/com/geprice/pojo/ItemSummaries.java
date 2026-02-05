package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemSummaries {
    private int id;
    private String name;
    private int categoryId;
    private long currentWeekAverage;
    private long previousWeekAverage;
    private long weeklyChange;
    private String weeklyChangePercent;
}
