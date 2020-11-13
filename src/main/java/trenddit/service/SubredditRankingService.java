package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.SubredditDoubleMetric;
import trenddit.bean.SubredditMetric;
import trenddit.bean.SubredditRankedMetric;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;
import trenddit.entity.SubredditRankingPK;
import trenddit.util.DateUtil;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditRankingService {

    private final SubredditRankingRepository subredditRankingRepository;

    public SubredditRankingService(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
    }

    public List<SubredditMetric> getSubscriberRanking() {
        return subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date())
                .stream().map(subreddit -> new SubredditMetric(subreddit.getName(), subreddit.getSubscribers()))
                .collect(Collectors.toList());
    }

    public List<SubredditRanking> getTodayMostCommented() {
        return subredditRankingRepository.findTop10ByDateOrderByCommentsDesc(DateUtil.ago(1));
    }

    public List<SubredditMetric> getAverageComments(Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findAverageComments(
                DateUtil.daysAgo(1), DateUtil.daysAgo(days)));
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(DateUtil.ago(1));
    }

    public List<SubredditMetric> getAveragePosted(Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findAveragePosts(
                DateUtil.daysAgo(1), DateUtil.daysAgo(days)));
    }

    public List<SubredditMetric> getSubredditsGrowth(Integer days, Integer limit) {
        return mapToSubredditMetric(subredditRankingRepository.findSubredditsGrowth(
                DateUtil.daysAgo(0), DateUtil.daysAgo(days), days, limit == null ? 9999 : limit));
    }

    public SubredditMetric getSubredditGrowth(String subreddit, Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findSubredditGrowth(
                subreddit, DateUtil.daysAgo(0), DateUtil.daysAgo(days), days));
    }

    public SubredditRankedMetric getSubredditRankedList(String subredditName, String metric) {
        return getSubredditRankedList(subredditName, metric, 1);
    }

    public SubredditRankedMetric getSubredditRankedList(String subredditName, String metric, Integer days) {
        List<SubredditMetric> subredditsGrowth = getMetricList(metric, days);
        if (subredditsGrowth == null) return null;
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

    public List<SubredditRanking> getSubredditMetricGrowthOverTime(String subredditName, Integer limit) {
        if (limit == null) return subredditRankingRepository.findAllByNameOrderByDateDesc(subredditName);
        return subredditRankingRepository.findAllByNameOrderByDateDesc(subredditName)
                .stream().limit(limit).sorted(Comparator.comparing(SubredditRanking::getDate))
                .collect(Collectors.toList());
    }

    public List<SubredditDoubleMetric> getSubredditsActivity(Integer from, Integer to) {
        return mapToSubredditDoubleMetric(subredditRankingRepository.findSubredditsActivity(
                DateUtil.daysAgo(from), DateUtil.daysAgo(to)));
    }

    public boolean isSubredditInDb(String subredditName) {
        return subredditRankingRepository.existsById(new SubredditRankingPK(subredditName, DateUtil.ago(0)));
    }

    public List<SubredditMetric> getFirst15SubredditsActivityGrowth() {
        return getSubredditsActivityGrowth().stream().limit(10).collect(Collectors.toList());
    }

    public List<SubredditMetric> getLast15SubredditsActivityGrowth() {
        List<SubredditMetric> toReturn = getSubredditsActivityGrowth();
        Collections.reverse(toReturn);
        return toReturn.stream().limit(15)
                .map(s -> new SubredditMetric(s.getName(), -s.getNumber())).collect(Collectors.toList());
    }

    public List<SubredditMetric> getSubredditsActivityGrowth() {
        List<SubredditDoubleMetric> lastMonthActivity = getSubredditsActivity(31, 1);
        List<SubredditDoubleMetric> yesterdayActivity = getSubredditsActivity(1, 1);

        return yesterdayActivity.stream()
                .map(s -> new SubredditMetric(s.getName(), (int) (s.getNumber() / lastMonthActivity.stream()
                        .filter(ss -> ss.getName().equals(s.getName()))
                        .findFirst()
                        .orElse(new SubredditDoubleMetric(s.getName(), 1.)).getNumber() * 100) - 100))
                .sorted(Comparator.comparing(SubredditMetric::getNumber).reversed())
                .filter(s -> !Double.isNaN(s.getNumber()))
                .collect(Collectors.toList());
    }

    private List<SubredditMetric> getMetricList(String metric, Integer days) {
        switch (metric) {
            case "growth":
                return getSubredditsGrowth(days, 9999);
            case "comments":
                return getAverageComments(days);
            case "posts":
                return getAveragePosted(days);
            case "subscribers":
                return getSubscriberRanking();
            default:
                return null;
        }
    }

    private SubredditMetric mapToSubredditMetric(Tuple tuple) {
        return new SubredditMetric(
                (String) tuple.get(0),
                tuple.get(1) == null ? 0 : ((BigDecimal) tuple.get(1)).intValue());
    }

    private List<SubredditMetric> mapToSubredditMetric(List<Tuple> tupleList) {
        return tupleList.stream().map(this::mapToSubredditMetric).collect(Collectors.toList());
    }

    private SubredditDoubleMetric mapToSubredditDoubleMetric(Tuple tuple) {
        return new SubredditDoubleMetric(
                (String) tuple.get(0),
                tuple.get(1) == null ? 0. : ((BigDecimal) tuple.get(1)).doubleValue());
    }

    private List<SubredditDoubleMetric> mapToSubredditDoubleMetric(List<Tuple> tupleList) {
        return tupleList.stream().map(this::mapToSubredditDoubleMetric).collect(Collectors.toList());
    }
}
