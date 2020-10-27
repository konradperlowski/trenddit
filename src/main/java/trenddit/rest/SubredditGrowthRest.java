package trenddit.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import trenddit.bean.SubredditMetric;
import trenddit.entity.SubredditRanking;
import trenddit.service.SubredditRankingService;
import trenddit.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/growth")
public class SubredditGrowthRest {

    private final SubredditRankingService subredditRankingService;

    public SubredditGrowthRest(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }

    @RequestMapping(value = "/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubredditMetric> getSubredditsGrowth(@PathVariable Integer days,
                                                     @RequestParam(required = false) Integer limit) {
        return subredditRankingService.getSubredditsGrowth(days, limit);
    }

    @RequestMapping(value = "/metric/{subreddit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubredditRanking> getSubredditSubscribersOverMonth(@PathVariable String subreddit) {
        return subredditRankingService.getSubredditMetricGrowthOverTime(subreddit).stream()
                .filter(subredditRanking -> !DateUtil.dateToString(subredditRanking.getDate()).equals(DateUtil.daysAgo(0)))
                .collect(Collectors.toList());
    }
}
