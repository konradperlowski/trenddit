package trenddit.logic;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.*;
import net.dean.jraw.pagination.DefaultPaginator;
import org.springframework.stereotype.Component;
import trenddit.bean.Metric;
import trenddit.bean.SubredditInfo;
import trenddit.bean.SubredditPost;
import trenddit.service.SubredditRankingService;
import trenddit.util.DateUtil;

import java.util.stream.Collectors;

@Component
public class RedditOperations {

    private final RedditClient redditClient;

    private final SubredditRankingService subredditService;

    public RedditOperations(RedditClient redditClient, SubredditRankingService subredditService) {
        this.redditClient = redditClient;
        this.subredditService = subredditService;
    }

    public SubredditInfo getSubredditInfo(String subredditName) {
        Subreddit subreddit;
        try {
            subreddit = redditClient.subreddit(subredditName).about();
            if (!subredditService.isSubredditInDb(subredditName))
                return null;
        } catch (Exception e) {
            return null;
        }
        SubredditInfo subredditInfo = new SubredditInfo();
        subredditInfo.setName(subreddit.getName());
        subredditInfo.setUrl(subreddit.getUrl());
        subredditInfo.setDescription(subreddit.getPublicDescription());
        subredditInfo.setCreatedAt(subreddit.getCreated());

        subredditInfo.setSubscribers(subredditService.getRankedMetric(subreddit.getName(), Metric.SUBSCRIBER, 1));
        subredditInfo.setComments(subredditService.getRankedMetric(subreddit.getName(), Metric.COMMENT, 30));
        subredditInfo.setPosts(subredditService.getRankedMetric(subreddit.getName(), Metric.POST, 30));
        subredditInfo.setGrowth(subredditService.getRankedMetric(subreddit.getName(), Metric.SUBSCRIBER_GROWTH, 1));

        subredditInfo.setGrowthWeek(subredditService.getSubredditGrowth(subreddit.getName(), 7).getNumber());
        subredditInfo.setGrowthMonth(subredditService.getSubredditGrowth(subreddit.getName(), 30).getNumber());

        subredditInfo.setSubredditMetricGrowth(
                subredditService.getMetricGrowth(subreddit.getName()).stream()
                        .filter(subredditRanking ->
                                !DateUtil.dateToString(subredditRanking.getDate()).equals(DateUtil.daysAgo(0)))
                        .collect(Collectors.toList()));
        subredditInfo.setAverageCommentsToPosts((int) subredditInfo.getSubredditMetricGrowth().stream()
                .mapToDouble(s -> s.getPosts() == 0 ? s.getComments() : (double) s.getComments() / s.getPosts())
                .average().orElse(0.0));

        DefaultPaginator<Submission> hotPaginator = redditClient.subreddit(subreddit.getName()).posts()
                .sorting(SubredditSort.HOT).build();

        subredditInfo.setBestSubmissions(hotPaginator.next().stream().limit(5)
                .map(s -> new SubredditPost(s.getTitle(), s.getPermalink(), s.getScore()))
                .collect(Collectors.toList()));
        return subredditInfo;
    }
}
