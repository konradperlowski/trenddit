package trenddit.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import trenddit.bean.*;
import trenddit.controller.bean.SubredditGrowth;
import trenddit.repository.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    private final SubredditRankingRepository subredditRankingRepository;

    private final BestSubredditDailyRepository bestSubredditDailyRepository;

    private final BestSubredditWeeklyRepository bestSubredditWeeklyRepository;

    private final BestSubredditMonthlyRepository bestSubredditMonthlyRepository;

    private final BestSubredditYearlyRepository bestSubredditYearlyRepository;

    private final BestSubredditAllTimeRepository bestSubredditAllTimeRepository;

    public TestController(SubredditRankingRepository subredditRankingRepository,
                          BestSubredditDailyRepository bestSubredditDailyRepository,
                          BestSubredditWeeklyRepository bestSubredditWeeklyRepository,
                          BestSubredditMonthlyRepository bestSubredditMonthlyRepository,
                          BestSubredditYearlyRepository bestSubredditYearlyRepository,
                          BestSubredditAllTimeRepository bestSubredditAllTimeRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
        this.bestSubredditDailyRepository = bestSubredditDailyRepository;
        this.bestSubredditWeeklyRepository = bestSubredditWeeklyRepository;
        this.bestSubredditMonthlyRepository = bestSubredditMonthlyRepository;
        this.bestSubredditYearlyRepository = bestSubredditYearlyRepository;
        this.bestSubredditAllTimeRepository = bestSubredditAllTimeRepository;
    }

    @RequestMapping(value = "/ranking", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SubredditRanking> ranking() {
        return subredditRankingRepository.findAll();
    }

    @RequestMapping(value = "/best-daily", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BestSubredditDaily> bestDaily() {
        return bestSubredditDailyRepository.findAll();
    }

    @RequestMapping(value = "/best-weekly", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BestSubredditWeekly> bestWeekly() {
        return bestSubredditWeeklyRepository.findAll();
    }

    @RequestMapping(value = "/best-monthly", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BestSubredditMonthly> bestMonthly() {
        return bestSubredditMonthlyRepository.findAll();
    }

    @RequestMapping(value = "/best-yearly", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BestSubredditYearly> bestYearly() {
        return bestSubredditYearlyRepository.findAll();
    }

    @RequestMapping(value = "/best-all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BestSubredditAllTime> bestAllTime() {
        return bestSubredditAllTimeRepository.findAll();
    }

    @RequestMapping(value = "/growth/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SubredditGrowth> growthDaily(@PathVariable Integer days) {

        List<SubredditGrowth> subredditGrowthList = new ArrayList<>();

        subredditRankingRepository.findDistinctName().forEach(subreddit -> {
            SubredditGrowth subredditGrowth = new SubredditGrowth();
            subredditGrowth.setName(subreddit);
            subredditGrowth.setGrowth(subredditRankingRepository.findSubredditGrowth(subreddit,
                    dateToString(getToday()), dateToString(daysAgo(days)), days));
            if (subredditGrowth.getGrowth() == null) subredditGrowth.setGrowth(0);
            subredditGrowthList.add(subredditGrowth);
        });

        return subredditGrowthList.stream().sorted().collect(Collectors.toList());
    }

    private Date getToday() {
        return daysAgo(0);
    }

    private Date daysAgo(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
