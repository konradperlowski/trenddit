package trenddit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SubredditRankingPK implements Serializable {
    protected String name;
    protected Date date;

    public SubredditRankingPK() {}

    public SubredditRankingPK(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubredditRankingPK that = (SubredditRankingPK) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
