package com.geprice.pojo;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
public class BossItemKey implements Serializable {
    public int bossId;
    public int itemId;
}