package com.geprice.controller;

import com.geprice.pojo.Submission;
import com.geprice.pojo.SubmissionsPaged;
import com.geprice.repository.SubmissionRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionRepo submissionRepo;

    public SubmissionController(SubmissionRepo submissionRepo) {
        this.submissionRepo = submissionRepo;
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
        Submission created = Submission.builder()
                .userId(submission.getUserId())
                .itemId(submission.getItemId())
                .status(submission.getStatus())
                .value(submission.getValue())
                .flagged(false)
                .approved(false)
                .listed(true)
                .createdAt(Instant.now(Clock.systemUTC()))
                .build();

        return submissionRepo.save(created);
    }

    @PatchMapping("/{id}/approve")
    public Submission approve(@PathVariable String id) {
        Submission submission = submissionRepo.getReferenceById(Long.parseLong(id));
        return submissionRepo.save(submission.toBuilder().approved(true).build());
    }

    @PatchMapping("/{id}/flag")
    public Submission flag(@PathVariable String id) {
        Submission submission = submissionRepo.getReferenceById(Long.parseLong(id));
        return submissionRepo.save(submission.toBuilder().flagged(true).build());
    }

    @DeleteMapping("/{id}")
    public Submission delete(@PathVariable String id) {
        Submission submission = submissionRepo.getReferenceById(Long.parseLong(id));
        return submissionRepo.save(submission.toBuilder().listed(false).build());
    }
}
