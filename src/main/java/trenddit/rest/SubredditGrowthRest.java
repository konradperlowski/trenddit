package trenddit.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import trenddit.bean.SubredditGrowth;
import trenddit.service.SubredditRankingService;

import java.util.List;

@RestController
@RequestMapping("/api/growth")
public class SubredditGrowthRest {

    private final SubredditRankingService subredditRankingService;

    public SubredditGrowthRest(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }

    @RequestMapping(value = "/{subreddit}/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubredditGrowth getSubredditGrowth( @PathVariable String subreddit, @PathVariable Integer days) {
        return subredditRankingService.getSubredditGrowth(subreddit, days);
    }

    @RequestMapping(value = "/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubredditGrowth> getSubredditsGrowth(@PathVariable Integer days,
                                                     @RequestParam(required = false) Integer limit) {
        return subredditRankingService.getSubredditsGrowth(days, limit);
    }
}
