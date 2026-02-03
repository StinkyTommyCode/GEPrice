package com.geprice.repository;

import com.geprice.pojo.Submission;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<@NonNull Submission, @NonNull Long> {
    List<Submission> findAllByItemIdAndListedAndReviewStatusNotOrderByCreatedAtDesc(int itemId, boolean listed, String reviewStatusNot);
}