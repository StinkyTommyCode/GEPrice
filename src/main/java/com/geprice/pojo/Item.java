package com.geprice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @NonNull
    @Column(name = "id")
    private Integer id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @NonNull
    @Column(name = "name_upper")
    private String nameUpper;

    @Column(name = "type")
    private String type;

    @Column(name = "icon")
    private String icon;

    @Column(name = "icon_large")
    private String iconLarge;

    @Column(name = "wiki_url")
    private String wikiUrl;

    @Column(name = "is_members")
    private boolean isMembers;
}
