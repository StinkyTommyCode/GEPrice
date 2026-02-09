package com.geprice.repository;

import com.geprice.pojo.Submission;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepo extends JpaRepository<@NonNull Submission, @NonNull Long> {

    @Query("select s " +
            "from Submission s " +
            "where s.listed = true " +
            "and s.reviewStatus != 'denied' " +
            "and s.itemId = ?1 " +
            "order by s.id desc ")
    List<Submission> findListedByItemId(long itemId);

    @Query("select s " +
            "from Submission s " +
            "where s.listed = true " +
            "and s.reviewStatus != 'denied' " +
            "and (?1 < 0 or s.id > ?1) " +
            "order by s.id asc " +
            "fetch first ?2 rows only")
    List<Submission> findListedOrderByIdAsc(long submissionId, long fetch);

    @Query("select s " +
            "from Submission s " +
            "where s.listed = true " +
            "and s.reviewStatus != 'denied' " +
            "and (?1 < 0 or s.id < ?1) " +
            "order by s.id desc " +
            "fetch first ?2 rows only")
    List<Submission> findListedOrderByIdDesc(long submissionId, long fetch);

    @Query("select count(s) " +
            "from Submission s " +
            "where s.listed = true " +
            "and s.reviewStatus != 'denied' " +
            "and (?1 < 0 or s.id > ?1)")
    long findListedOrderByIdAscCount(long submissionId);


    @Query("select count(s) " +
            "from Submission s " +
            "where s.listed = true " +
            "and s.reviewStatus != 'denied' " +
            "and (?1 < 0 or s.id < ?1)")
    long findListedOrderByIdDescCount(long submissionId);
}