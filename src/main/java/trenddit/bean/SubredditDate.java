package trenddit.bean;

import java.util.Date;

public class SubredditDate {

    private Date date;
    private Integer number;

    public SubredditDate(Date date, Integer number) {
        this.date = date;
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
