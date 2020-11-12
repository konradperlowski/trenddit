package trenddit.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trenddit.bean.SubredditMetric;
import trenddit.service.SubredditRankingService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubredditActivityRest {

    private final SubredditRankingService subredditRankingService;

    public SubredditActivityRest(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }

    @GetMapping(value = "/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubredditMetric> getActivity() {
        return subredditRankingService.getSubredditsActivity();
    }
}
