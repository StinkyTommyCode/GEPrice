package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Prices {

    private int itemId;
    private Long lastBuy;
    private Long lastSell;
    private Long weekChange;
    private String weekChangePercentage;
    private String timeframe;

    private List<Report> reports;
}
