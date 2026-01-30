package com.geprice.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Boss {
    private @NonNull @Id Integer id;
    private @NonNull @Lob String name;
    private @Lob String wikiUrl;
    private @Lob String icon;
}
