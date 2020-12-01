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

    private final SubredditRankingService subredditService;

    public TopController(SubredditRankingService subredditService) {
        this.subredditService = subredditService;
    }


    @GetMapping(value = "/growing")
    public String topGrowing(Model model) {
        model.addAttribute("growthToday", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 1, 0, true));
        model.addAttribute("growthWeek", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 7, 0, true));
        model.addAttribute("growthMonth", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 30, 0, true));
        return "top/growing";
    }

    @GetMapping("/comments")
    public String topComments(Model model) {
        model.addAttribute("commentsToday", subredditService.getMetricList(Metric.COMMENT, 1, 0, true));
        model.addAttribute("commentsWeek", subredditService.getMetricList(Metric.COMMENT, 7, 0, true));
        model.addAttribute("commentsMonth", subredditService.getMetricList(Metric.COMMENT, 30, 0, true));
        return "top/comments";
    }

    @GetMapping("/posts")
    public String topPosts(Model model) {
        model.addAttribute("postsToday", subredditService.getMetricList(Metric.POST, 1, 0, true));
        model.addAttribute("postsWeek", subredditService.getMetricList(Metric.POST, 7, 0, true));
        model.addAttribute("postsMonth", subredditService.getMetricList(Metric.POST, 30, 0, true));
        return "top/posts";
    }

    @GetMapping("/subreddits")
    public String topSubreddits(Model model) {
        model.addAttribute("subscribersList", subredditService.getMetricList(Metric.SUBSCRIBER, 0, 0, true));
        return "top/subreddits";
    }

    @GetMapping("/analysis")
    public String topAnalyze() {
        return "top/analysis";
    }

    @GetMapping("/activity")
    public String topActivity(Model model) {
        model.addAttribute("activityList", subredditService.getMetricList(Metric.ACTIVITY_GROWTH, 31, 0, true));
        return "top/activity";
    }
}
