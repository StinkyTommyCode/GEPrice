package com.geprice.controller;

import com.geprice.Constants;
import com.geprice.Util;
import com.geprice.error.GEPrice400Error;
import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.*;
import com.geprice.repository.ItemRepo;
import com.geprice.repository.SubmissionRepo;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@ControllerAdvice
@RequestMapping("/api/prices")
public class PricesController {

    private final SubmissionRepo submissionRepo;
    private final ItemRepo itemRepo;

    public PricesController(SubmissionRepo submissionRepo, ItemRepo itemRepo) {
        this.submissionRepo = submissionRepo;
        this.itemRepo = itemRepo;
    }

    @GetMapping("/{itemId}")
    public Prices getRecent(@PathVariable String itemId,
                            @RequestParam(value = "numDays", required = false, defaultValue = "30") String numDays,
                            @RequestParam(value = "subId", required = false, defaultValue = "false") String includeSubmissionId) {
        int item = Util.validateIntegerParameter(itemId, Constants.ITEM_NOT_FOUND);
        if (numDays == null || !List.of("30", "90", "all").contains(numDays)) {
            throw new GEPrice400Error("Invalid number of days [30 / 90 / all]");
        }

        Optional<Item> itemOptional = itemRepo.findById(item);
        if (itemOptional.isEmpty()) {
            throw new GEPrice404Error(Constants.ITEM_NOT_FOUND);
        }

        List<Report> reports = submissionRepo.findListedByItemId(item)
                .stream().filter(s -> "all".equals(numDays) ||
                        s.getCreatedAt().isAfter(Instant.now(Clock.systemUTC()).minus(Integer.parseInt(numDays), ChronoUnit.DAYS)))
                .map(s -> Report.fromSubmission(s, Boolean.parseBoolean(includeSubmissionId))).toList();

        Optional<Report> lastBuyReport = reports.stream().filter(r -> List.of("buy", "instant_buy").contains(r.getTransactionType())).findFirst();
        Optional<Report> lastSellReport = reports.stream().filter(r -> List.of("sell", "instant_sell").contains(r.getTransactionType())).findFirst();


        return Prices.builder()
                .itemId(item)
                .itemName(itemOptional.get().getName())
                .lastBuy(lastBuyReport.isPresent() ? lastBuyReport.get().getPrice() : null)
                .lastSell(lastSellReport.isPresent() ? lastSellReport.get().getPrice() : null)
                .weekChange(Util.getWeeklyAverageChange(item))
                .weekChangePercentage(Util.getWeeklyAveragePercentChange(item))
                .timeframe(numDays)
                .reports(reports)
                .build();
    }

    @GetMapping("/all")
    public ReportsPaged getAllPricesPaged(@RequestParam(value = "pageSize", required = false, defaultValue = "-1") String pageSizeParam,
                                          @RequestParam(value = "afterSubmission", required = false, defaultValue = "-1") String afterSubmissionParam,
                                          @RequestParam(value = "newestFirst", required = false, defaultValue = "true") String newestFirstParam) {
        int afterSubmission = Util.validateIntegerParameter(afterSubmissionParam, "Invalid submission number");
        int pageSize = Util.validateIntegerParameter(pageSizeParam, "Invalid page size");
        boolean newestFirst = Boolean.parseBoolean(newestFirstParam);

        List<Report> reports;
        long totalItems;
        if (newestFirst) {
            totalItems = submissionRepo.findListedOrderByIdDescCount(afterSubmission);
            reports = submissionRepo.findListedOrderByIdDesc(afterSubmission, pageSize < 0 ? Integer.MAX_VALUE : pageSize)
                    .stream()
                    .map(s -> Report.fromSubmission(s, true))
                    .toList();
        } else {
            totalItems = submissionRepo.findListedOrderByIdAscCount(afterSubmission);
            reports = submissionRepo.findListedOrderByIdAsc(afterSubmission, pageSize < 0 ? Integer.MAX_VALUE : pageSize)
                    .stream()
                    .map(s -> Report.fromSubmission(s, true))
                    .toList();
        }

        return ReportsPaged.builder()
                .totalItems(totalItems)
                .pageSize(pageSize)
                .afterSubmission(afterSubmission)
                .newestFirst(newestFirst)
                .reports(reports.subList(0, pageSize < 0 || pageSize > reports.size() ? reports.size() : pageSize))
                .build();
    }
}
