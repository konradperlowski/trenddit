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

    private final SubredditRankingService subredditService;

    public HomeController(SubredditRankingService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("firstActivity", subredditService.getMetricList(Metric.ACTIVITY_GROWTH, 31, 10, true));
        model.addAttribute("lastActivity", Lists.reverse(
                subredditService.getMetricList(Metric.ACTIVITY_GROWTH, 31, 0, true)).stream()
                .limit(10).collect(Collectors.toList()));
        model.addAttribute("mostCommented", subredditService.getMetricList(Metric.COMMENT, 1, 10, false));
        model.addAttribute("mostPosted", subredditService.getMetricList(Metric.POST, 1, 10, false));
        model.addAttribute("growthToday", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 1, 15, true));
        model.addAttribute("growthWeek", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 7, 15, true));
        model.addAttribute("growthMonth", subredditService.getMetricList(Metric.SUBSCRIBER_GROWTH, 30, 15, true));
        return "home";
    }
}
