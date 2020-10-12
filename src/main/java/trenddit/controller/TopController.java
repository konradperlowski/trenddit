package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "top/growing";
    }

    @GetMapping("/comments")
    public String topComments(Model model) {
        return "top/comments";
    }

    @GetMapping("/posts")
    public String topPosts(Model model) {
        return "top/posts";
    }

    @GetMapping("/subreddits")
    public String topSubreddits(Model model) {
        model.addAttribute("subscribersList", subredditRankingService.getSubscriberRanking());
        return "top/subreddits";
    }
}
