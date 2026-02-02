package com.geprice.pojo;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
public class CategoryItemKey implements Serializable {
    public int categoryId;
    public int itemId;
}
