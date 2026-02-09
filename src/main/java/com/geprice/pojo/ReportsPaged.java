package com.geprice.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportsPaged {
    private List<Report> reports;
    private long totalItems;
    private int pageSize;
    private long afterSubmission;
    private boolean newestFirst;
}
