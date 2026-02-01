package com.geprice.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boss_item")
@IdClass(BossItemKey.class)
public class BossItem {
    @Id
    @Column(name = "boss_id")
    private int bossId;

    @Id
    @Column(name = "item_id")
    private int itemId;
}
