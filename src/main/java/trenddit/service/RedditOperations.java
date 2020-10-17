package trenddit.service;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Subreddit;
import org.springframework.stereotype.Component;
import trenddit.bean.SubredditInfo;
import trenddit.bean.SubredditRankedMetric;

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
        } catch (Exception e) {
            return null;
        }
        SubredditInfo subredditInfo = new SubredditInfo();
        subredditInfo.setName(subredditName);
        subredditInfo.setUrl(subreddit.getUrl());
        subredditInfo.setDescription(subreddit.getPublicDescription());
        subredditInfo.setCreatedAt(subreddit.getCreated());

        SubredditRankedMetric subscriberRank = subredditRankingService.getSubredditRankedList(subredditName, "subscribers");
        subredditInfo.setSubscribers(subscriberRank.getNumber());
        subredditInfo.setSubscriberRank(subscriberRank.getRank());

        SubredditRankedMetric commentsRank = subredditRankingService.getSubredditRankedList(subredditName, "comments");
        subredditInfo.setCommentsPerDay(commentsRank.getNumber());
        subredditInfo.setCommentsPerDayRank(commentsRank.getRank());

        SubredditRankedMetric postsRank = subredditRankingService.getSubredditRankedList(subredditName, "posts");
        subredditInfo.setPostsPerDay(postsRank.getNumber());
        subredditInfo.setPostsPerDayRank(postsRank.getRank());

        SubredditRankedMetric growthRank = subredditRankingService.getSubredditRankedList(subredditName, "growth");
        subredditInfo.setGrowthToday(growthRank.getNumber());
        subredditInfo.setGrowthTodayRank(growthRank.getRank());
        return subredditInfo;
    }
}
