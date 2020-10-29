package trenddit.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import trenddit.entity.*;
import trenddit.service.BestSubredditService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/best")
public class BestSubredditRest {

    private final BestSubredditService bestSubredditService;

    public BestSubredditRest(BestSubredditService bestSubredditService) {
        this.bestSubredditService = bestSubredditService;
    }

    @RequestMapping(value = "/all-time", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditAllTime> getBestAllTime() {
        return bestSubredditService.getBestAllTime();
    }

    @RequestMapping(value = "/year", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditYearly> getBestYear() {
        return bestSubredditService.getBestYearly();
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditMonthly> getBestMonth() {
        return bestSubredditService.getBestMonthly();
    }

    @RequestMapping(value = "/week", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditWeekly> getBestWeek() {
        return bestSubredditService.getBestWeekly();
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditDaily> getBestDay() {
        return bestSubredditService.getBestDaily(new Date());
    }

    @GetMapping(value = "/analysis", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BestSubredditDaily> getAnalysis() {
        return bestSubredditService.getForAnalysis();
    }
}
