package trenddit.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(SubredditRankingPK.class)
public class SubredditRanking {

    @Id
    private String name;
    private Integer subscribers;
    private Integer posts;
    private Integer comments;
    @Id
    @Temporal(TemporalType.DATE)
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
