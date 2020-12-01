package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.Metric;
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
import java.util.stream.IntStream;

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

    public boolean isSubredditInDb(String subredditName) {
        return subredditRankingRepository.existsById(new SubredditRankingPK(subredditName, DateUtil.ago(0)));
    }

    public List<SubredditRanking> getMetricGrowth(String subredditName) {
        return subredditRankingRepository.findAllByNameOrderByDateDesc(subredditName)
                .stream().limit(31).sorted(Comparator.comparing(SubredditRanking::getDate))
                .collect(Collectors.toList());
    }

    public List<SubredditMetric> getMetricList(Metric metric, Integer fromDays, Integer limit, boolean filterByTop1000) {
        limit = limit == 0 ? 9999 : limit;
        List<SubredditMetric> toReturn;
        switch (metric) {
            case SUBSCRIBER_GROWTH:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findSubredditsGrowth(
                        DateUtil.daysAgo(0), DateUtil.daysAgo(fromDays),
                        fromDays));
                break;
            case COMMENT:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findAverageComments(
                        DateUtil.daysAgo(1), DateUtil.daysAgo(fromDays)));
                break;
            case POST:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findAveragePosts(
                        DateUtil.daysAgo(1), DateUtil.daysAgo(fromDays)));
                break;
            case SUBSCRIBER:
                toReturn = subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date()).stream()
                        .map(subreddit -> new SubredditMetric(subreddit.getName(), subreddit.getSubscribers()))
                        .collect(Collectors.toList());
                break;
            case ACTIVITY_GROWTH:
                toReturn = mapToSubredditMetric(subredditRankingRepository.findSubredditsActivityGrowth(
                        DateUtil.daysAgo(1), DateUtil.daysAgo(fromDays)));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + metric);
        }
        return toReturn.stream().filter(s -> !filterByTop1000 || top1000.contains(s.getName()))
                .limit(limit).collect(Collectors.toList());
    }

    public SubredditRankedMetric getRankedMetric(String subredditName, Metric metric, Integer days) {
        List<SubredditMetric> metricList = getMetricList(metric, days, 0, false);
        int index = IntStream.range(0, metricList.size()).filter(i -> metricList.get(i).getName().equals(subredditName))
                .findFirst().orElse(-1);
        return new SubredditRankedMetric(subredditName, metricList.get(index).getNumber(), index + 1);
    }

    private SubredditMetric mapToSubredditMetric(Tuple tuple) {
        return new SubredditMetric((String) tuple.get(0),
                tuple.get(1) == null ? 0 : ((BigDecimal) tuple.get(1)).intValue());
    }

    private List<SubredditMetric> mapToSubredditMetric(List<Tuple> tupleList) {
        return tupleList.stream().map(this::mapToSubredditMetric).collect(Collectors.toList());
    }
}
