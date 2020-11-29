package trenddit.controller;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import trenddit.bean.Metric;
import trenddit.service.SubredditRankingService;

import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final SubredditRankingService subredditRankingService;

    public HomeController(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("firstActivity", subredditRankingService.getMetricList(Metric.ACTIVITY_GROWTH, 31, 10, true));
        model.addAttribute("lastActivity", Lists.reverse(
                subredditRankingService.getMetricList(Metric.ACTIVITY_GROWTH, 31, 0, true)).stream()
                .limit(10).collect(Collectors.toList()));
        model.addAttribute("mostCommented", subredditRankingService.getMetricList(Metric.COMMENT, 1, 10, false));
        model.addAttribute("mostPosted", subredditRankingService.getMetricList(Metric.POST, 1, 10, false));
        model.addAttribute("growthToday", subredditRankingService.getMetricList(Metric.SUBSCRIBER_GROWTH, 1, 15, true));
        model.addAttribute("growthWeek", subredditRankingService.getMetricList(Metric.SUBSCRIBER_GROWTH, 7, 15, true));
        model.addAttribute("growthMonth", subredditRankingService.getMetricList(Metric.SUBSCRIBER_GROWTH, 30, 15, true));
        return "home";
    }
}
