package com.geprice;

import com.geprice.error.GEPrice404Error;
import com.geprice.pojo.PreviousWeeklyAverage;
import com.geprice.pojo.WeeklyAverage;
import com.geprice.repository.PreviousWeeklyAverageRepo;
import com.geprice.repository.WeeklyAverageRepo;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Util {

    private static ApplicationContext context;

    public Util(ApplicationContext context) {
        Util.context = context;
    }

    public static int validateIntegerParameter(String integer, String failMessage) {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            throw new GEPrice404Error(failMessage);
        }
    }

    public static String getWeeklyAveragePercentChange(int itemId) {
        long average = getWeeklyAverage(itemId);
        long previousAverage = getPreviousWeeklyAverage(itemId);
        return getPercentChange(average, previousAverage);
    }

    public static String getPercentChange(long current, long previous) {
        if (current == 0 && previous == 0) {
            return "+0.00%";
        }
        if (current == 0) {
            return "-100.00%";
        }
        if (previous == 0) {
            return "+inf%";
        } else {
            return String.format("%+.2f%%", (double) (current - previous) / previous * 100.0);
        }
    }


    public static long getWeeklyAverageChange(int itemId) {
        long average = getWeeklyAverage(itemId);
        long previousAverage = getPreviousWeeklyAverage(itemId);
        return average - previousAverage;
    }

    public static long getWeeklyAverage(int itemId) {
        WeeklyAverageRepo repo = context.getBean(WeeklyAverageRepo.class);
        Optional<WeeklyAverage> avg = repo.findById(itemId);
        return avg.map(WeeklyAverage::getAverage).orElse(0L);
    }

    public static long getPreviousWeeklyAverage(int itemId) {
        PreviousWeeklyAverageRepo repo = context.getBean(PreviousWeeklyAverageRepo.class);
        Optional<PreviousWeeklyAverage> avg = repo.findById(itemId);
        return avg.map(PreviousWeeklyAverage::getAverage).orElse(0L);
    }
}
