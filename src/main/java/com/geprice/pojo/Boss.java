package com.geprice.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boss")
public class Boss {
    @Id
    @NonNull
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "wiki_url")
    private String wikiUrl;

    @Column(name = "icon")
    private String icon;

    @Column(name = "created_at")
    private Instant createdAt;
}
