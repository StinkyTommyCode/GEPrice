package com.geprice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {
    private @Id @GeneratedValue Long id;
    private @NonNull @Lob String userId;
    private int itemId;
    private @NonNull Status status;
    private long value;
    private Boolean flagged;
    private Boolean approved;
    private Boolean listed;
    private Instant createdAt;

    private enum Status {
        buy, sell, instant_buy, instant_sell
    }
}
