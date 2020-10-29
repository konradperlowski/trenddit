package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.SubredditMetric;
import trenddit.dao.*;
import trenddit.entity.*;
import trenddit.util.DateUtil;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, List<SubredditMetric>> getForAnalysis() {
        Map<String, List<SubredditMetric>> result = new LinkedHashMap<>();
        DateUtil.periodOfTime(30, 0)
                .forEach(date -> result.put(DateUtil.dateToString(date), getRankedBestForDate(date)));
        result.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        return result;
    }

    private List<SubredditMetric> getRankedBestForDate(Date date) {
        return changeNumberToRank(subredditDailyRepository.findByDateOrderByNumberDesc(date)
                .stream().limit(5).collect(Collectors.toList()))
                .stream().map(s -> new SubredditMetric(s.getName(), s.getNumber())).collect(Collectors.toList());
    }

    private List<BestSubredditDaily> changeNumberToRank(List<BestSubredditDaily> bestSubredditDailyList) {
        for (int i = 1; i <= bestSubredditDailyList.size(); i++) {
            bestSubredditDailyList.get(i - 1).setNumber(i);
        }
        return bestSubredditDailyList;
    }
}
