package com.geprice.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submission")
public class Submission {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "value")
    private long value;

    @Column(name = "listed")
    private boolean listed;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "review_status")
    private String reviewStatus;

    @Column(name = "flag_reason")
    private String flagReason;

    @Column(name = "flag_details")
    private String flagDetails;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;
}
