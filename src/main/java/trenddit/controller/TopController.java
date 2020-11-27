package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import trenddit.bean.Metric;
import trenddit.service.SubredditRankingService;

@Controller
@RequestMapping("/top")
public class TopController {

    private final SubredditRankingService subredditRankingService;

    public TopController(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }


    @GetMapping(value = "/growing")
    public String topGrowing(Model model) {
        model.addAttribute("growthToday", subredditRankingService.getMetricList(Metric.GROWTH, 1, 0, true));
        model.addAttribute("growthWeek", subredditRankingService.getMetricList(Metric.GROWTH, 7, 0, true));
        model.addAttribute("growthMonth", subredditRankingService.getMetricList(Metric.GROWTH, 30, 0, true));
        return "top/growing";
    }

    @GetMapping("/comments")
    public String topComments(Model model) {
        model.addAttribute("commentsToday", subredditRankingService.getMetricList(Metric.COMMENT, 1, 0, true));
        model.addAttribute("commentsWeek", subredditRankingService.getMetricList(Metric.COMMENT, 7, 0, true));
        model.addAttribute("commentsMonth", subredditRankingService.getMetricList(Metric.COMMENT, 30, 0, true));
        return "top/comments";
    }

    @GetMapping("/posts")
    public String topPosts(Model model) {
        model.addAttribute("postsToday", subredditRankingService.getMetricList(Metric.POST, 1, 0, true));
        model.addAttribute("postsWeek", subredditRankingService.getMetricList(Metric.POST, 7, 0, true));
        model.addAttribute("postsMonth", subredditRankingService.getMetricList(Metric.POST, 30, 0, true));
        return "top/posts";
    }

    @GetMapping("/subreddits")
    public String topSubreddits(Model model) {
        model.addAttribute("subscribersList", subredditRankingService.getMetricList(Metric.SUBSCRIBER, 0, 0, true));
        return "top/subreddits";
    }

    @GetMapping("/analysis")
    public String topAnalyze() {
        return "top/analysis";
    }

    @GetMapping("/activity")
    public String topActivity(Model model) {
        model.addAttribute("activityList", subredditRankingService.getSubredditsActivityGrowth());
        return "top/activity";
    }
}
