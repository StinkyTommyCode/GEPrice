package com.geprice.controller;

import com.geprice.Constants;
import com.geprice.Util;
import com.geprice.error.GEPrice400Error;
import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.Prices;
import com.geprice.pojo.Report;
import com.geprice.repository.ItemRepo;
import com.geprice.repository.SubmissionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(PricesController.class);
    private final SubmissionRepo submissionRepo;
    private final ItemRepo itemRepo;

    public PricesController(SubmissionRepo submissionRepo, ItemRepo itemRepo) {
        this.submissionRepo = submissionRepo;
        this.itemRepo = itemRepo;
    }

    @GetMapping("/{itemId}")
    public Prices getRecent(@PathVariable String itemId,
                            @RequestParam(value = "numDays", required = false, defaultValue = "30") String numDays) {
        int item = Util.validateIntegerParameter(itemId, Constants.ITEM_NOT_FOUND);
        if (numDays == null || !List.of("30", "90", "all").contains(numDays)) {
            throw new GEPrice400Error("Invalid number of days [30 / 90 / all]");
        }

        if (itemRepo.findById(item).isEmpty()) {
            throw new GEPrice404Error(Constants.ITEM_NOT_FOUND);
        }

        List<Report> reports = submissionRepo.findAllByItemIdAndListedAndReviewStatusNotOrderByCreatedAtDesc(item, true, "denied")
                .stream().filter(s -> "all".equals(numDays) ||
                        s.getCreatedAt().isAfter(Instant.now(Clock.systemUTC()).minus(Integer.parseInt(numDays), ChronoUnit.DAYS)))
                .map(Report::fromSubmission).toList();

        Optional<Report> lastBuyReport = reports.stream().filter(r -> List.of("buy", "instant_buy").contains(r.getTransactionType())).findFirst();
        Optional<Report> lastSellReport = reports.stream().filter(r -> List.of("sell", "instant_sell").contains(r.getTransactionType())).findFirst();
        List<Report> lastWeek = reports.stream().filter(r -> r.getDate().isAfter(Instant.now(Clock.systemUTC()).minus(7, ChronoUnit.DAYS))).toList();

        long weekChange;
        double weekChangePercentage;
        if (!lastWeek.isEmpty()) {
            Report latestReport = lastWeek.getFirst();
            Report lastWeekReport = lastWeek.getLast();

            weekChange = latestReport.getPrice() - lastWeekReport.getPrice();
            weekChangePercentage = (double) weekChange / lastWeekReport.getPrice();
        } else {
            log.warn("Recent prices for item {}xxxxxxxxxxxxxxxxxxxxx not found", itemId);
            weekChange = 0;
            weekChangePercentage = 0.0;
        }

        return Prices.builder()
                .itemId(item)
                .lastBuy(lastBuyReport.isPresent() ? lastBuyReport.get().getPrice() : null)
                .lastSell(lastSellReport.isPresent() ? lastSellReport.get().getPrice() : null)
                .weekChange(weekChange)
                .weekChangePercentage(String.format("%+.2f%%", weekChangePercentage * 100.0))
                .timeframe(numDays)
                .reports(reports)
                .build();
    }
}
