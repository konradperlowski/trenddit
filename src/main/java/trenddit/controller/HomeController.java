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
        model.addAttribute("mostCommented", subredditRankingService.getTodayMostCommented());
        model.addAttribute("mostPosted", subredditRankingService.getTodayMostPosted());
        return "home";
    }
}
