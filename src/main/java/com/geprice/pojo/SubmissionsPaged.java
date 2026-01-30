package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SubmissionsPaged {
    private List<Submission> submissions;
    private int pageNumber;
    private int pageSize;
}
