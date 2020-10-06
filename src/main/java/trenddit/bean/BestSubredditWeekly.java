package trenddit.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BestSubredditWeekly {
    @Id
    private String name;
    private Integer number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
