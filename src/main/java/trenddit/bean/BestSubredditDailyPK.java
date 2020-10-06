package trenddit.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BestSubredditDailyPK implements Serializable {
    private String name;
    private Date date;

    public BestSubredditDailyPK () {}

    public BestSubredditDailyPK(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestSubredditDailyPK that = (BestSubredditDailyPK) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
