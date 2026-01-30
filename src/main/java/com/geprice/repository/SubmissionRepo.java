package com.geprice.repository;

import com.geprice.pojo.Submission;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<@NonNull Submission, @NonNull Long> {
    List<Submission> findAllByApproved(boolean approved, Pageable pageable);
    List<Submission> findAllByApprovedAndListed(boolean approved, boolean listed, Pageable pageable);

    List<Submission> findAllByFlagged(boolean flagged, Pageable pageable);
}