package com.geprice.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
