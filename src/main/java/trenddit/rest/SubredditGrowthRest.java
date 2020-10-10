package trenddit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public List<SubredditGrowth> getSubredditsGrowth(@PathVariable Integer days) {
        return subredditRankingService.getSubredditsGrowth(days);
    }
}
