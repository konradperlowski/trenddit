package trenddit.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import trenddit.controller.bean.SubredditGrowth;
import trenddit.dao.SubredditRankingRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {

    private final SubredditRankingRepository subredditRankingRepository;

    public TestController(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
    }

    @RequestMapping(value = "/growth/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SubredditGrowth> growthDaily(@PathVariable Integer days) {

        List<SubredditGrowth> subredditGrowthList = new ArrayList<>();

        subredditRankingRepository.findDistinctName().forEach(subreddit -> {
            SubredditGrowth subredditGrowth = new SubredditGrowth();
            subredditGrowth.setName(subreddit);
            subredditGrowth.setGrowth(subredditRankingRepository.findSubredditGrowth(subreddit,
                    dateToString(getToday()), dateToString(daysAgo(days)), days));
            if (subredditGrowth.getGrowth() == null) subredditGrowth.setGrowth(0);
            subredditGrowthList.add(subredditGrowth);
        });

        return subredditGrowthList.stream().sorted().collect(Collectors.toList());
    }

    private Date getToday() {
        return daysAgo(0);
    }

    private Date daysAgo(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
