package trenddit.bean;

import net.dean.jraw.models.Submission;

import java.util.Date;
import java.util.List;

public class SubredditInfo {

    private String name;
    private String url;
    private String description;
    private Date createdAt;
    private Integer subscribers;
    private Integer subscriberRank;
    private Integer commentsPerDay;
    private Integer commentsPerDayRank;
    private Integer postsPerDay;
    private Integer postsPerDayRank;
    private Integer growthToday;
    private Integer growthTodayRank;
    private Integer growthWeek;
    private Integer growthMonth;
    private List<Submission> bestSubmissions;

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
        this.url = url;
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

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Integer getSubscriberRank() {
        return subscriberRank;
    }

    public void setSubscriberRank(Integer subscriberRank) {
        this.subscriberRank = subscriberRank;
    }

    public Integer getCommentsPerDay() {
        return commentsPerDay;
    }

    public void setCommentsPerDay(Integer commentsPerDay) {
        this.commentsPerDay = commentsPerDay;
    }

    public Integer getCommentsPerDayRank() {
        return commentsPerDayRank;
    }

    public void setCommentsPerDayRank(Integer commentsPerDayRank) {
        this.commentsPerDayRank = commentsPerDayRank;
    }

    public Integer getPostsPerDay() {
        return postsPerDay;
    }

    public void setPostsPerDay(Integer postsPerDay) {
        this.postsPerDay = postsPerDay;
    }

    public Integer getPostsPerDayRank() {
        return postsPerDayRank;
    }

    public void setPostsPerDayRank(Integer postsPerDayRank) {
        this.postsPerDayRank = postsPerDayRank;
    }

    public Integer getGrowthToday() {
        return growthToday;
    }

    public void setGrowthToday(Integer growthToday) {
        this.growthToday = growthToday;
    }

    public Integer getGrowthTodayRank() {
        return growthTodayRank;
    }

    public void setGrowthTodayRank(Integer growthTodayRank) {
        this.growthTodayRank = growthTodayRank;
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

    public List<Submission> getBestSubmissions() {
        return bestSubmissions;
    }

    public void setBestSubmissions(List<Submission> bestSubmissions) {
        this.bestSubmissions = bestSubmissions;
    }
}
