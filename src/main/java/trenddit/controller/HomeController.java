package trenddit.controller;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import trenddit.bean.Metric;
import trenddit.bean.SubredditMetric;
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
        model.addAttribute("firstActivity", subredditRankingService.getSubredditsActivityGrowth().stream()
                .limit(10).collect(Collectors.toList()));
        model.addAttribute("lastActivity",
                Lists.reverse(subredditRankingService.getSubredditsActivityGrowth()).stream()
                        .limit(15).map(s -> new SubredditMetric(s.getName(), -s.getNumber()))
                        .collect(Collectors.toList()));
        model.addAttribute("mostCommented", subredditRankingService.getMetricList(Metric.COMMENT, 1, 10, false));
        model.addAttribute("mostPosted", subredditRankingService.getMetricList(Metric.POST, 1, 10, false));
        model.addAttribute("growthToday", subredditRankingService.getMetricList(Metric.GROWTH, 1, 15, true));
        model.addAttribute("growthWeek", subredditRankingService.getMetricList(Metric.GROWTH, 7, 15, true));
        model.addAttribute("growthMonth", subredditRankingService.getMetricList(Metric.GROWTH, 30, 15, true));
        return "home";
    }
}
