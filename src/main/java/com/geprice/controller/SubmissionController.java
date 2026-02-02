package com.geprice.controller;

import com.geprice.Constants;
import com.geprice.Util;
import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.Item;
import com.geprice.pojo.Submission;
import com.geprice.pojo.SubmissionsPaged;
import com.geprice.repository.ItemRepo;
import com.geprice.repository.SubmissionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@ControllerAdvice
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionRepo submissionRepo;
    private final ItemRepo itemRepo;

    private static final Logger log = LoggerFactory.getLogger(SubmissionController.class);

    public SubmissionController(SubmissionRepo submissionRepo, ItemRepo itemRepo) {
        this.submissionRepo = submissionRepo;
        this.itemRepo = itemRepo;
    }

    @GetMapping("/recent")
    public SubmissionsPaged getRecent(@RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize,
                                      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") String pageNumber) {
        List<Submission> submissions = submissionRepo.findAllByApprovedAndListed(true, true, PageRequest.of(Integer.parseInt(pageNumber),
                Integer.parseInt(pageSize),
                Sort.by("createdAt").descending()));
        return SubmissionsPaged.builder()
                .submissions(submissions)
                .pageNumber(Integer.parseInt(pageNumber))
                .pageSize(Integer.parseInt(pageSize))
                .build();
    }

    @GetMapping("/flagged")
    public SubmissionsPaged getFlagged(@RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize,
                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") String pageNumber) {
        List<Submission> submissions = submissionRepo.findAllByFlagged(true, PageRequest.of(Integer.parseInt(pageNumber),
                Integer.parseInt(pageSize)));
        return SubmissionsPaged.builder()
                .submissions(submissions)
                .pageNumber(Integer.parseInt(pageNumber))
                .pageSize(Integer.parseInt(pageSize))
                .build();
    }

    @GetMapping("/pending")
    public SubmissionsPaged getPending(@RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize,
                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") String pageNumber) {
        List<Submission> submissions = submissionRepo.findAllByApproved(false, PageRequest.of(Integer.parseInt(pageNumber),
                                                                                              Integer.parseInt(pageSize)));
        return SubmissionsPaged.builder()
                .submissions(submissions)
                .pageNumber(Integer.parseInt(pageNumber))
                .pageSize(Integer.parseInt(pageSize))
                .build();
    }

    @PostMapping
    public Submission create(@RequestBody Submission submission) {
        Optional<Item> item = itemRepo.findById(submission.getItemId());
        if (item.isPresent()) {
            Submission created = Submission.builder()
                    .userId(submission.getUserId())
                    .itemId(submission.getItemId())
                    .status(submission.getStatus())
                    .value(submission.getValue())
                    .timestamp(submission.getTimestamp())
                    .flagged(false)
                    .approved(false)
                    .listed(true)
                    .createdAt(Instant.now(Clock.systemUTC()))
                    .updatedAt(Instant.now(Clock.systemUTC()))
                    .build();

            return submissionRepo.save(created);
        } else {
            log.warn("Item with id {} not found", submission.getItemId());
            throw new GEPrice404Error(Constants.ITEM_NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/approve")
    public Submission approve(@PathVariable String id) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));
        if(submission.isPresent()) {
            return submissionRepo.save(submission.get().toBuilder()
                    .approved(true)
                    .updatedAt(Instant.now()).build());
        } else {
            Util.logMissingSubmission(id);
            throw new GEPrice404Error(Constants.SUBMISSION_NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/flag")
    public Submission flag(@PathVariable String id) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return submissionRepo.save(submission.get().toBuilder()
                    .flagged(true)
                    .updatedAt(Instant.now()).build());
        } else {
            Util.logMissingSubmission(id);
            throw new GEPrice404Error(Constants.SUBMISSION_NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/unflag")
    public Submission unflag(@PathVariable String id) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return submissionRepo.save(submission.get().toBuilder()
                    .flagged(false)
                    .updatedAt(Instant.now()).build());
        } else {
            Util.logMissingSubmission(id);
            throw new GEPrice404Error(Constants.SUBMISSION_NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Submission delete(@PathVariable String id) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return submissionRepo.save(submission.get().toBuilder()
                    .listed(false)
                    .updatedAt(Instant.now()).build());
        } else {
            Util.logMissingSubmission(id);
            throw new GEPrice404Error(Constants.SUBMISSION_NOT_FOUND);
        }
    }
}
