package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.SubredditMetric;
import trenddit.bean.SubredditRankedMetric;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    public List<SubredditMetric> getAverageComments(Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findAverageComments(
                daysAgo(0), daysAgo(days)), "comments");
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(new Date());
    }

    public List<SubredditMetric> getAveragePosted(Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findAveragePosts(
                daysAgo(0), daysAgo(days)), "posts");
    }

    public List<SubredditMetric> getSubredditsGrowth(Integer days, Integer limit) {
        return mapToSubredditMetric(subredditRankingRepository.findSubredditsGrowth(
                daysAgo(0), daysAgo(days), days, limit == null ? 9999 : limit), "growth");
    }

    public SubredditRankedMetric getSubredditGrowthWithRank(String subredditName) {
        List<SubredditMetric> subredditsGrowth = getSubredditsGrowth(1, 9999);
        final int[] i = {1};
        List<SubredditRankedMetric> subredditsGrowthRanked = subredditsGrowth.stream()
                .map(subreddit -> new SubredditRankedMetric(
                        subreddit.getName(),
                        subreddit.getNumber(),
                        i[0]++)).collect(Collectors.toList());
        return subredditsGrowthRanked.stream()
                .filter(subreddit -> subreddit.getName().equals(subredditName))
                .findAny().orElse(null);
    }

    private List<SubredditMetric> mapToSubredditMetric(List<Tuple> tupleList, String metricName) {
        return tupleList.stream().map(
                tuple -> new SubredditMetric(
                        (String) tuple.get("name"),
                        tuple.get(metricName) == null ? 0 : ((BigDecimal) tuple.get(metricName)).intValue())
        ).collect(Collectors.toList());
    }

    private String daysAgo(Integer days) {
        return dateToString(ago(days));
    }

    private Date ago(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
