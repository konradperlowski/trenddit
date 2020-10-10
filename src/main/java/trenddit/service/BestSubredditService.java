package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.dao.*;
import trenddit.entity.*;

import java.util.Date;
import java.util.List;

@Service
public class BestSubredditService {

    private final BestSubredditDailyRepository subredditDailyRepository;

    private final BestSubredditWeeklyRepository subredditWeeklyRepository;

    private final BestSubredditMonthlyRepository subredditMonthlyRepository;

    private final BestSubredditYearlyRepository subredditYearlyRepository;

    private final BestSubredditAllTimeRepository subredditAllTimeRepository;

    public BestSubredditService(BestSubredditDailyRepository subredditDailyRepository,
                                BestSubredditAllTimeRepository subredditAllTimeRepository,
                                BestSubredditYearlyRepository subredditYearlyRepository,
                                BestSubredditWeeklyRepository subredditWeeklyRepository,
                                BestSubredditMonthlyRepository subredditMonthlyRepository) {
        this.subredditDailyRepository = subredditDailyRepository;
        this.subredditAllTimeRepository = subredditAllTimeRepository;
        this.subredditYearlyRepository = subredditYearlyRepository;
        this.subredditWeeklyRepository = subredditWeeklyRepository;
        this.subredditMonthlyRepository = subredditMonthlyRepository;
    }

    public List<BestSubredditAllTime> getBestAllTime() {
        return subredditAllTimeRepository.findByOrderByNumberDesc();
    }

    public List<BestSubredditYearly> getBestYearly() {
        return subredditYearlyRepository.findByOrderByNumberDesc();
    }

    public List<BestSubredditMonthly> getBestMonthly() {
        return subredditMonthlyRepository.findByOrderByNumberDesc();
    }

    public List<BestSubredditWeekly> getBestWeekly() {
        return subredditWeeklyRepository.findByOrderByNumberDesc();
    }

    public List<BestSubredditDaily> getBestDaily(Date date) {
        return subredditDailyRepository.findByDateOrderByNumberDesc(date);
    }
}
