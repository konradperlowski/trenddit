package trenddit.logic;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.*;
import net.dean.jraw.pagination.DefaultPaginator;
import org.springframework.stereotype.Component;
import trenddit.bean.Metric;
import trenddit.bean.SubredditInfo;
import trenddit.bean.SubredditPost;
import trenddit.bean.SubredditRankedMetric;
import trenddit.service.SubredditRankingService;
import trenddit.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedditOperations {

    private final RedditClient redditClient;

    private final SubredditRankingService subredditRankingService;

    public RedditOperations(RedditClient redditClient, SubredditRankingService subredditRankingService) {
        this.redditClient = redditClient;
        this.subredditRankingService = subredditRankingService;
    }

    public SubredditInfo getSubredditInfo(String subredditName) {
        Subreddit subreddit;
        try {
            subreddit = redditClient.subreddit(subredditName).about();
            if (!subredditRankingService.isSubredditInDb(subredditName))
                return null;
        } catch (Exception e) {
            return null;
        }
        SubredditInfo subredditInfo = new SubredditInfo();
        subredditInfo.setName(subreddit.getName());
        subredditInfo.setUrl(subreddit.getUrl());
        subredditInfo.setDescription(subreddit.getPublicDescription());
        subredditInfo.setCreatedAt(subreddit.getCreated());
        subredditInfo.setAverageCommentsToPosts(
                subredditRankingService.getSubredditAverageActivity(subreddit.getName()).getNumber());

        SubredditRankedMetric subscriberRank = subredditRankingService.getRankedMetric(subreddit.getName(), Metric.SUBSCRIBER, 1);
        subredditInfo.setSubscribers(subscriberRank.getNumber());
        subredditInfo.setSubscriberRank(subscriberRank.getRank());

        SubredditRankedMetric commentsRank = subredditRankingService.getRankedMetric(subreddit.getName(), Metric.COMMENT, 30);
        subredditInfo.setCommentsPerDay(commentsRank.getNumber());
        subredditInfo.setCommentsPerDayRank(commentsRank.getRank());

        SubredditRankedMetric postsRank = subredditRankingService.getRankedMetric(subreddit.getName(), Metric.POST, 30);
        subredditInfo.setPostsPerDay(postsRank.getNumber());
        subredditInfo.setPostsPerDayRank(postsRank.getRank());

        SubredditRankedMetric growthRank = subredditRankingService.getRankedMetric(subreddit.getName(), Metric.SUBSCRIBER_GROWTH, 1);
        subredditInfo.setGrowthToday(growthRank.getNumber());
        subredditInfo.setGrowthTodayRank(growthRank.getRank());

        subredditInfo.setGrowthWeek(subredditRankingService.getSubredditGrowth(subreddit.getName(), 7).getNumber());
        subredditInfo.setGrowthMonth(subredditRankingService.getSubredditGrowth(subreddit.getName(), 30).getNumber());

        subredditInfo.setSubredditMetricGrowth(
                subredditRankingService.getSubredditMetricGrowthOverTime(subreddit.getName()).stream()
                        .filter(subredditRanking ->
                                !DateUtil.dateToString(subredditRanking.getDate()).equals(DateUtil.daysAgo(0)))
                        .collect(Collectors.toList()));

        DefaultPaginator<Submission> paginator = redditClient.subreddit(subreddit.getName()).posts()
                .sorting(SubredditSort.HOT)
                .build();

        subredditInfo.setBestSubmissions(convertToSubredditPost(paginator.next()));
        return subredditInfo;
    }

    private List<SubredditPost> convertToSubredditPost(Listing<Submission> submissionListing) {
        return submissionListing.stream()
                .map(s -> new SubredditPost(s.getTitle(), s.getPermalink(), s.getScore()))
                .limit(5)
                .collect(Collectors.toList());
    }
}
