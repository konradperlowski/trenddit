package trenddit.bean;

public class SubredditPost {
    private String title;
    private String url;
    private String upVotes;

    public SubredditPost(String title, String url, Integer upVotes) {
        this.title = title.replaceAll("(?<=.{44})\\b.*", "...");
        this.url = "https://www.reddit.com" + url;
        this.upVotes = withSuffix(upVotes);
    }

    public static String withSuffix(int count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp - 1));
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

    public String getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(String upVotes) {
        this.upVotes = upVotes;
    }
}
