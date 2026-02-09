package com.geprice.controller;

import com.geprice.pojo.ReportsPaged;
import com.geprice.pojo.Submission;
import com.geprice.repository.SubmissionRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PricesControllerTest {

    private static final String DEFAULT_PAGE_SIZE = "-1";
    private static final String DEFAULT_AFTER_SUBMISSION = "-1";
    private static final String DEFAULT_NEW_SUBMISSION = "true";
    private static final List<Submission> submissions = new ArrayList<>();

    @Mock
    private SubmissionRepo submissionRepo;

    @InjectMocks
    private PricesController pricesController;

    @BeforeAll
    public static void setup() {
        submissions.add(Submission.builder()
                .id(1L)
                .build());
        submissions.add(Submission.builder()
                .id(2L)
                .build());
        submissions.add(Submission.builder()
                .id(3L)
                .build());
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.lenient().when(submissionRepo.findAllByListedAndReviewStatusNotOrderByCreatedAtAsc(anyBoolean(), anyString())).thenReturn(submissions);
        Mockito.lenient().when(submissionRepo.findAllByListedAndReviewStatusNotOrderByCreatedAtDesc(anyBoolean(), anyString())).thenReturn(submissions.reversed());
    }

    @Test
    public void getAllPricesPagedDefault() {
        ReportsPaged reports = pricesController.getAllPricesPaged(DEFAULT_PAGE_SIZE, DEFAULT_AFTER_SUBMISSION, DEFAULT_NEW_SUBMISSION);
        assertEquals(3, reports.getTotalItems(), "Incorrect totalItems returned");
        assertEquals(3, reports.getReports().size(), "Incorrect report count returned");
        assertEquals(3, reports.getReports().get(0).getSubmissionId(), "Incorrect submissionId returned for submission number 0");
        assertEquals(2, reports.getReports().get(1).getSubmissionId(), "Incorrect submissionId returned for submission number 1");
        assertEquals(1, reports.getReports().get(2).getSubmissionId(), "Incorrect submissionId returned for submission number 2");
        assertEquals(-1, reports.getPageSize(), "Incorrect pageSize returned");
        assertEquals(-1, reports.getAfterSubmission(), "Incorrect afterSubmission returned");
        assertTrue(reports.isNewestFirst(), "Incorrect newestFirst returned");
    }

    @Test
    public void getAllPricesPagedWithPageSize() {
        ReportsPaged reports = pricesController.getAllPricesPaged("2", DEFAULT_AFTER_SUBMISSION, DEFAULT_NEW_SUBMISSION);
        assertEquals(3, reports.getTotalItems(), "Incorrect totalItems returned");
        assertEquals(2, reports.getReports().size(), "Incorrect report count returned");
        assertEquals(3, reports.getReports().get(0).getSubmissionId(), "Incorrect submissionId returned for submission number 0");
        assertEquals(2, reports.getReports().get(1).getSubmissionId(), "Incorrect submissionId returned for submission number 1");
        assertEquals(2, reports.getPageSize(), "Incorrect pageSize returned");
        assertEquals(-1, reports.getAfterSubmission(), "Incorrect afterSubmission returned");
        assertTrue(reports.isNewestFirst(), "Incorrect newestFirst returned");
    }

    @Test
    public void getAllPricesPagedWithLargePageSize() {
        ReportsPaged reports = pricesController.getAllPricesPaged("5", DEFAULT_AFTER_SUBMISSION, DEFAULT_NEW_SUBMISSION);
        assertEquals(3, reports.getTotalItems(), "Incorrect totalItems returned");
        assertEquals(3, reports.getReports().size(), "Incorrect report count returned");
        assertEquals(3, reports.getReports().get(0).getSubmissionId(), "Incorrect submissionId returned for submission number 0");
        assertEquals(2, reports.getReports().get(1).getSubmissionId(), "Incorrect submissionId returned for submission number 1");
        assertEquals(1, reports.getReports().get(2).getSubmissionId(), "Incorrect submissionId returned for submission number 2");
        assertEquals(5, reports.getPageSize(), "Incorrect pageSize returned");
        assertEquals(-1, reports.getAfterSubmission(), "Incorrect afterSubmission returned");
        assertTrue(reports.isNewestFirst(), "Incorrect newestFirst returned");
    }

    @Test
    public void getAllPricesPagedWithAfterSubmission() {
        ReportsPaged reports = pricesController.getAllPricesPaged(DEFAULT_PAGE_SIZE, "3", DEFAULT_NEW_SUBMISSION);
        assertEquals(2, reports.getTotalItems(), "Incorrect totalItems returned");
        assertEquals(2, reports.getReports().size(), "Incorrect report count returned");
        assertEquals(2, reports.getReports().get(0).getSubmissionId(), "Incorrect submissionId returned for submission number 1");
        assertEquals(1, reports.getReports().get(1).getSubmissionId(), "Incorrect submissionId returned for submission number 2");
        assertEquals(-1, reports.getPageSize(), "Incorrect pageSize returned");
        assertEquals(3, reports.getAfterSubmission(), "Incorrect afterSubmission returned");
        assertTrue(reports.isNewestFirst(), "Incorrect newestFirst returned");
    }

    @Test
    public void getAllPricesPagedWithSortOrder() {
        ReportsPaged reports = pricesController.getAllPricesPaged(DEFAULT_PAGE_SIZE, DEFAULT_AFTER_SUBMISSION, "false");
        assertEquals(3, reports.getTotalItems(), "Incorrect totalItems returned");
        assertEquals(3, reports.getReports().size(), "Incorrect report count returned");
        assertEquals(1, reports.getReports().get(0).getSubmissionId(), "Incorrect submissionId returned for submission number 0");
        assertEquals(2, reports.getReports().get(1).getSubmissionId(), "Incorrect submissionId returned for submission number 1");
        assertEquals(3, reports.getReports().get(2).getSubmissionId(), "Incorrect submissionId returned for submission number 2");
        assertEquals(-1, reports.getPageSize(), "Incorrect pageSize returned");
        assertEquals(-1, reports.getAfterSubmission(), "Incorrect afterSubmission returned");
        assertFalse(reports.isNewestFirst(), "Incorrect newestFirst returned");
    }
}
