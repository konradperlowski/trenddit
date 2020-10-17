package trenddit.bean;

public class SubredditRankedMetric extends SubredditMetric{

    private Integer rank;

    public SubredditRankedMetric(String name, Integer number, Integer rank) {
        super(name, number);
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
