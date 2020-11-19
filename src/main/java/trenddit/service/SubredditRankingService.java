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
        return getSubscriberRanking(true);
    }

    public List<SubredditMetric> getSubscriberRanking(boolean filterByTop1000) {
        return filterByTop1000(subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date())
                .stream().map(subreddit -> new SubredditMetric(subreddit.getName(), subreddit.getSubscribers()))
                .collect(Collectors.toList()), filterByTop1000);
    }

    public List<SubredditRanking> getTodayMostCommented() {
        return subredditRankingRepository.findTop10ByDateOrderByCommentsDesc(DateUtil.ago(1));
    }

    public List<SubredditMetric> getAverageComments(Integer days) {
        return getAverageComments(days, true);
    }

    public List<SubredditMetric> getAverageComments(Integer days, boolean filterByTop1000) {
        return filterByTop1000(mapToSubredditMetric(subredditRankingRepository.findAverageComments(
                DateUtil.daysAgo(1), DateUtil.daysAgo(days))), filterByTop1000);
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(DateUtil.ago(1));
    }

    public List<SubredditMetric> getAveragePosted(Integer days) {
        return getAveragePosted(days, true);
    }

    public List<SubredditMetric> getAveragePosted(Integer days, boolean filterByTop1000) {
        return filterByTop1000(mapToSubredditMetric(subredditRankingRepository.findAveragePosts(
                DateUtil.daysAgo(1), DateUtil.daysAgo(days))), filterByTop1000);
    }

    public List<SubredditMetric> getSubredditsGrowth(Integer days, Integer limit) {
        return getSubredditsGrowth(days, limit, true);
    }

    public List<SubredditMetric> getSubredditsGrowth(Integer days, Integer limit, boolean filterByTop1000) {
        return filterByTop1000(mapToSubredditMetric(subredditRankingRepository.findSubredditsGrowth(
                DateUtil.daysAgo(0), DateUtil.daysAgo(days), days, limit == null ? 9999 : limit)), filterByTop1000)
                .stream().sorted((Comparator.comparing(SubredditMetric::getNumber)).reversed()).collect(Collectors.toList());
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
        List<SubredditDoubleMetric> lastMonthActivity = getSubredditsActivity(31);
        List<SubredditDoubleMetric> yesterdayActivity = getSubredditsActivity(1);

        return filterByTop1000(yesterdayActivity.stream()
                .map(s -> new SubredditMetric(s.getName(), (int) (s.getNumber() / lastMonthActivity.stream()
                        .filter(ss -> ss.getName().equals(s.getName()))
                        .findFirst()
                        .orElse(new SubredditDoubleMetric(s.getName(), 1.)).getNumber() * 100) - 100))
                .sorted(Comparator.comparing(SubredditMetric::getNumber).reversed())
                .filter(s -> !Double.isNaN(s.getNumber()))
                .collect(Collectors.toList()), true);
    }

    public SubredditDoubleMetric getSubredditAverageActivity(String subredditName) {
        return mapToSubredditDoubleMetric(subredditRankingRepository.findSubredditAverageActivity(
                subredditName, DateUtil.daysAgo(31), DateUtil.daysAgo(1)));
    }

    private List<SubredditDoubleMetric> getSubredditsActivity(Integer from) {
        return mapToSubredditDoubleMetric(subredditRankingRepository.findSubredditsActivity(
                DateUtil.daysAgo(from), DateUtil.daysAgo(1)));
    }

    private List<SubredditMetric> getMetricList(String metric, Integer days) {
        switch (metric) {
            case "growth":
                return getSubredditsGrowth(days, 9999, false);
            case "comments":
                return getAverageComments(days, false);
            case "posts":
                return getAveragePosted(days, false);
            case "subscribers":
                return getSubscriberRanking(false);
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

    private List<SubredditMetric> filterByTop1000(List<SubredditMetric> list, boolean filter) {
        List<String> top1000 = subredditRankingRepository.findTop1000ByDateOrderByCommentsDesc(DateUtil.ago(1)).stream()
                .map(SubredditRanking::getName).collect(Collectors.toList());
        return list.stream().filter(s -> !filter || top1000.contains(s.getName())).collect(Collectors.toList());
    }
}
