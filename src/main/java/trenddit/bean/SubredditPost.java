package trenddit.bean;

public class SubredditPost {
    private String title;
    private String url;
    private Integer upVotes;

    public SubredditPost(String title, String url, Integer upVotes) {
        this.title = title.replaceAll("(?<=.{45})\\b.*", "...");
        this.url = "https://www.reddit.com" + url;
        this.upVotes = upVotes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(Integer upVotes) {
        this.upVotes = upVotes;
    }
}
