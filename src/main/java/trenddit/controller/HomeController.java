package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import trenddit.service.SubredditRankingService;

@Controller
public class HomeController {

    private final SubredditRankingService subredditRankingService;

    public HomeController(SubredditRankingService subredditRankingService) {
        this.subredditRankingService = subredditRankingService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("firstActivity", subredditRankingService.getFirst15SubredditsActivityGrowth());
        model.addAttribute("lastActivity", subredditRankingService.getLast15SubredditsActivityGrowth());
        model.addAttribute("mostCommented", subredditRankingService.getTodayMostCommented());
        model.addAttribute("mostPosted", subredditRankingService.getTodayMostPosted());
        model.addAttribute("growthToday", subredditRankingService.getSubredditsGrowth(1, 15));
        model.addAttribute("growthWeek", subredditRankingService.getSubredditsGrowth(7, 15));
        model.addAttribute("growthMonth", subredditRankingService.getSubredditsGrowth(30, 15));
        return "home";
    }
}
