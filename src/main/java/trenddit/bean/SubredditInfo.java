package trenddit.bean;

import trenddit.entity.SubredditRanking;

import java.util.Date;
import java.util.List;

public class SubredditInfo {

    private String name;
    private String url;
    private String description;
    private Date createdAt;
    private SubredditRankedMetric subscribers;
    private SubredditRankedMetric comments;
    private SubredditRankedMetric posts;
    private SubredditRankedMetric growth;
    private Integer growthWeek;
    private Integer growthMonth;
    private Integer averageCommentsToPosts;
    private List<SubredditPost> bestSubmissions;
    private List<SubredditRanking> subredditMetricGrowth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = "https://www.reddit.com" + url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public SubredditRankedMetric getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(SubredditRankedMetric subscribers) {
        this.subscribers = subscribers;
    }

    public SubredditRankedMetric getComments() {
        return comments;
    }

    public void setComments(SubredditRankedMetric comments) {
        this.comments = comments;
    }

    public SubredditRankedMetric getPosts() {
        return posts;
    }

    public void setPosts(SubredditRankedMetric posts) {
        this.posts = posts;
    }

    public SubredditRankedMetric getGrowth() {
        return growth;
    }

    public void setGrowth(SubredditRankedMetric growth) {
        this.growth = growth;
    }

    public Integer getGrowthWeek() {
        return growthWeek;
    }

    public void setGrowthWeek(Integer growthWeek) {
        this.growthWeek = growthWeek;
    }

    public Integer getGrowthMonth() {
        return growthMonth;
    }

    public void setGrowthMonth(Integer growthMonth) {
        this.growthMonth = growthMonth;
    }

    public List<SubredditPost> getBestSubmissions() {
        return bestSubmissions;
    }

    public void setBestSubmissions(List<SubredditPost> bestSubmissions) {
        this.bestSubmissions = bestSubmissions;
    }

    public Integer getAverageCommentsToPosts() {
        return averageCommentsToPosts;
    }

    public void setAverageCommentsToPosts(Integer averageCommentsToPosts) {
        this.averageCommentsToPosts = averageCommentsToPosts;
    }

    public List<SubredditRanking> getSubredditMetricGrowth() {
        return subredditMetricGrowth;
    }

    public void setSubredditMetricGrowth(List<SubredditRanking> subredditMetricGrowth) {
        this.subredditMetricGrowth = subredditMetricGrowth;
    }
}
