package trenddit.service;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Subreddit;
import org.springframework.stereotype.Component;
import trenddit.bean.SubredditInfo;

@Component
public class RedditOperations {

    private final RedditClient redditClient;

    public RedditOperations(RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    public SubredditInfo getSubredditInfo(String subredditName) {
        Subreddit subreddit = redditClient.subreddit(subredditName).about();
        SubredditInfo subredditInfo = new SubredditInfo();
        subredditInfo.setName(subredditName);
        subredditInfo.setUrl(subreddit.getUrl());
        subredditInfo.setDescription(subreddit.getPublicDescription());
        subredditInfo.setCreatedAt(subreddit.getCreated());
        return subredditInfo;
    }
}
