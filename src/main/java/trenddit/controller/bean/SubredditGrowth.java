package trenddit.controller.bean;

import org.jetbrains.annotations.NotNull;

public class SubredditGrowth implements Comparable<SubredditGrowth> {
    private String name;
    private Integer growth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    @Override
    public int compareTo(@NotNull SubredditGrowth o) {
        return o.getGrowth().compareTo(getGrowth());
    }
}
