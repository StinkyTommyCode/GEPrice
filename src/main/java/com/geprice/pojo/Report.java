package com.geprice.pojo;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Report {
    private Instant date;
    private long price;
    private String transactionType;
    private String reporter;

    private Report() {

    }

    public static Report fromSubmission(Submission submission) {
        Report report = new Report();
        report.date = submission.getCreatedAt();
        report.price = submission.getValue();
        report.transactionType = submission.getTransactionType();
        report.reporter = submission.getUserId();
        return report;
    }
}
