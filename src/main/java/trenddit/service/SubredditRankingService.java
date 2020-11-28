package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.Metric;
import trenddit.bean.SubredditDoubleMetric;
import trenddit.bean.SubredditMetric;
import trenddit.bean.SubredditRankedMetric;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;
import trenddit.entity.SubredditRankingPK;
import trenddit.util.DateUtil;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditRankingService {

    private final SubredditRankingRepository subredditRankingRepository;

    private final List<String> top1000;

    public SubredditRankingService(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
        top1000 = subredditRankingRepository.findTop1000ByDateOrderByCommentsDesc(DateUtil.ago(1)).stream()
                .map(SubredditRanking::getName).collect(Collectors.toList());
    }

    public SubredditMetric getSubredditGrowth(String subredditName, Integer days) {
        return mapToSubredditMetric(subredditRankingRepository.findSubredditGrowth(
                subredditName, DateUtil.daysAgo(0), DateUtil.daysAgo(days), days));
    }

    public SubredditRankedMetric getMetricRankedList(String subredditName, Metric metric, Integer days) {
        List<SubredditMetric> subredditsGrowth = getMetricList(metric, days, 0, false);
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
        return subredditRankingRepository.findAllByNameOrderByDateDesc(subredditName)
                .stream().limit(limit).sorted(Comparator.comparing(SubredditRanking::getDate))
                .collect(Collectors.toList());
    }

    public boolean isSubredditInDb(String subredditName) {
        return subredditRankingRepository.existsById(new SubredditRankingPK(subredditName, DateUtil.ago(0)));
    }

    public List<SubredditMetric> getSubredditsActivityGrowth() {
        List<SubredditDoubleMetric> lastMonthActivity = mapToSubredditDoubleMetric(
                subredditRankingRepository.findSubredditsActivity(DateUtil.daysAgo(31), DateUtil.daysAgo(1)));
        List<SubredditDoubleMetric> yesterdayActivity = mapToSubredditDoubleMetric(
                subredditRankingRepository.findSubredditsActivity(DateUtil.daysAgo(1), DateUtil.daysAgo(1)));

        return yesterdayActivity.stream()
                .map(s -> new SubredditMetric(s.getName(), (int) (s.getNumber() / lastMonthActivity.stream()
                        .filter(ss -> ss.getName().equals(s.getName()))
                        .findFirst()
                        .orElse(new SubredditDoubleMetric(s.getName(), 1.)).getNumber() * 100) - 100))
                .sorted(Comparator.comparing(SubredditMetric::getNumber).reversed())
                .filter(s -> !Double.isNaN(s.getNumber()))
                .filter(s -> top1000.contains(s.getName()))
                .collect(Collectors.toList());
    }

    public SubredditDoubleMetric getSubredditAverageActivity(String subredditName) {
        return mapToSubredditDoubleMetric(subredditRankingRepository.findSubredditAverageActivity(
                subredditName, DateUtil.daysAgo(31), DateUtil.daysAgo(1)));
    }

    public List<SubredditMetric> getMetricList(Metric metric, Integer fromDays,
                                               Integer limit, boolean filterByTop1000) {
        limit = limit == 0 ? 9999 : limit;
        List<SubredditMetric> toReturn;
        switch (metric) {
            case GROWTH:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findSubredditsGrowth(
                        DateUtil.daysAgo(0), DateUtil.daysAgo(fromDays), fromDays, limit)).stream()
                        .sorted((Comparator.comparing(SubredditMetric::getNumber)).reversed())
                        .collect(Collectors.toList());
                break;
            case COMMENT:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findAverageComments(
                        DateUtil.daysAgo(1), DateUtil.daysAgo(fromDays), limit));
                break;
            case POST:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findAveragePosts(
                        DateUtil.daysAgo(1), DateUtil.daysAgo(fromDays), limit));
                break;
            case SUBSCRIBER:
                toReturn = subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date()).stream()
                        .map(subreddit -> new SubredditMetric(subreddit.getName(), subreddit.getSubscribers()))
                        .collect(Collectors.toList());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + metric);
        }
        return toReturn.stream()
                .filter(s -> !filterByTop1000 || top1000.contains(s.getName())).collect(Collectors.toList());
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
