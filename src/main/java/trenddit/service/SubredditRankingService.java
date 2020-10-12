package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.SubredditGrowth;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditRankingService {

    private final SubredditRankingRepository subredditRankingRepository;

    public SubredditRankingService(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
    }

    public List<SubredditRanking> getSubscriberRanking() {
        return subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date());
    }

    public List<SubredditRanking> getTodayMostCommented() {
        return subredditRankingRepository.findTop10ByDateOrderByCommentsDesc(new Date());
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(new Date());
    }

    public SubredditGrowth getSubredditGrowth(String subreddit, Integer days) {
        SubredditGrowth subredditGrowth = new SubredditGrowth();
        subredditGrowth.setName(subreddit);
        subredditGrowth.setGrowth(subredditRankingRepository.findSubredditGrowth(subreddit,
                dateToString(getToday()), dateToString(daysAgo(days)), days));

        if (subredditGrowth.getGrowth() == null) subredditGrowth.setGrowth(0);

        return subredditGrowth;
    }

    public List<SubredditGrowth> getSubredditsGrowth(Integer days, Integer limit) {
        List<SubredditGrowth> subredditGrowthList = new ArrayList<>();

        subredditRankingRepository.findDistinctName()
                .forEach(subreddit -> subredditGrowthList.add(getSubredditGrowth(subreddit, days)));

        if (limit != null)
            return subredditGrowthList.stream().sorted().limit(limit).collect(Collectors.toList());
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
