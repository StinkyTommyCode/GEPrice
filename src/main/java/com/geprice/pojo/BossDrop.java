package com.geprice.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BossDrop {
    private @Id @GeneratedValue Long id;
    private int bossId;
    private int itemId;
}
