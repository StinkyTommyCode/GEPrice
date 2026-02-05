package com.geprice.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Report {
    private Instant date;
    private int itemId;
    private long price;
    private String transactionType;
    private String reporter;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long submissionId;

    private Report() {

    }

    public static Report fromSubmission(Submission submission, boolean includeSubmissionId) {
        Report report = new Report();
        report.itemId = submission.getItemId();
        report.date = submission.getCreatedAt();
        report.price = submission.getValue();
        report.transactionType = submission.getTransactionType();
        report.reporter = submission.getUsername();
        report.submissionId = includeSubmissionId ? submission.getId() : null;
        return report;
    }
}
