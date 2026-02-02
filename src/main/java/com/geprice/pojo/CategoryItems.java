package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CategoryItems {
    private Category category;
    private List<Item> items;
}
