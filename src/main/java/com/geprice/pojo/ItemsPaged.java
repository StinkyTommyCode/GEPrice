package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ItemsPaged {
    private List<Item> items;
    private String query;
    private int pageNumber;
    private int pageSize;
}
