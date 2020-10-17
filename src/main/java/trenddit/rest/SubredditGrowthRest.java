package trenddit.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import trenddit.bean.SubredditDate;
import trenddit.bean.SubredditMetric;
import trenddit.service.SubredditRankingService;

import java.util.List;

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

    @RequestMapping(value = "/subscribers/{subreddit}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubredditDate> getSubredditSubscribersOverMonth(@PathVariable String subreddit) {
        return subredditRankingService.getSubredditGrowthOverTime(subreddit);
    }
}
