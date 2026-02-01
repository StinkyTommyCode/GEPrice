package com.geprice.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submission")
public class Submission {
    @Id
    @GeneratedValue
    @SequenceGenerator(name = "submission_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "user_id")
    private String userId;

    @Column(name = "item_id")
    private int itemId;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(name = "status")
    private  Status status;

    @Column(name = "value")
    private long value;

    @NonNull
    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "flagged")
    private boolean flagged;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "listed")
    private boolean listed;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    private enum Status {
        buy, sell, instant_buy, instant_sell
    }
}
