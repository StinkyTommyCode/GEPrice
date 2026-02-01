package com.geprice.controller;

import com.geprice.Util;
import com.geprice.pojo.GEPriceError;
import com.geprice.pojo.Item;
import com.geprice.pojo.Submission;
import com.geprice.pojo.SubmissionsPaged;
import com.geprice.repository.ItemRepo;
import com.geprice.repository.SubmissionRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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

    public SubmissionController(SubmissionRepo submissionRepo, ItemRepo itemRepo) {
        this.submissionRepo = submissionRepo;
        this.itemRepo = itemRepo;
    }

    @GetMapping(path = "/recent", produces = { MediaType.APPLICATION_JSON_VALUE })
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

    @GetMapping(path = "/flagged", produces = { MediaType.APPLICATION_JSON_VALUE })
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

    @GetMapping(path = "/pending", produces = { MediaType.APPLICATION_JSON_VALUE })
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

    @PostMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String create(@RequestBody Submission submission, HttpServletResponse response) {
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

            return Util.toJson(submissionRepo.save(created));
        } else {
            Util.log.warn("Item with id {} not found", submission.getItemId());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Item not found").build());
        }
    }

    @PatchMapping(path = "/{id}/approve", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String approve(@PathVariable String id, HttpServletResponse response) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return Util.toJson(submissionRepo.save(submission.get().toBuilder()
                    .approved(true)
                    .updatedAt(Instant.now()).build()));
        } else {
            Util.logMissingSubmission(id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Submission not found").build());
        }
    }

    @PatchMapping(path = "/{id}/flag", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String flag(@PathVariable String id, HttpServletResponse response) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return Util.toJson(submissionRepo.save(submission.get().toBuilder()
                    .flagged(true)
                    .updatedAt(Instant.now()).build()));
        } else {
            Util.logMissingSubmission(id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Submission not found").build());
        }
    }

    @PatchMapping(path = "/{id}/unflag", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String unflag(@PathVariable String id, HttpServletResponse response) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return Util.toJson(submissionRepo.save(submission.get().toBuilder()
                    .flagged(false)
                    .updatedAt(Instant.now()).build()));
        } else {
            Util.logMissingSubmission(id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Submission not found").build());
        }
    }

    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public String delete(@PathVariable String id, HttpServletResponse response) {
        Optional<Submission> submission = submissionRepo.findById(Long.parseLong(id));

        if(submission.isPresent()) {
            return Util.toJson(submissionRepo.save(submission.get().toBuilder()
                    .listed(false)
                    .updatedAt(Instant.now()).build()));
        } else {
            Util.logMissingSubmission(id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return Util.toJson(GEPriceError.builder().error("Submission not found").build());
        }
    }
}
