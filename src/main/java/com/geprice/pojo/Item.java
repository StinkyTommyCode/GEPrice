package com.geprice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private @NonNull @Id Integer id;
    private @NonNull @Column(length = 255) String name;
    private @NonNull @Column(length = 255) @JsonIgnore String nameUpper;
    private @Lob String description;
    private @Column(length = 100) String type;
    private @Lob String icon;
    private @Lob String iconLarge;
    private @Lob String wikiUrl;
    private boolean isMembers;
}
