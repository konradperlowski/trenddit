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
        model.addAttribute("growthToday", subredditRankingService.getSubredditsGrowth(1, 9999));
        model.addAttribute("growthWeek", subredditRankingService.getSubredditsGrowth(7, 9999));
        model.addAttribute("growthMonth", subredditRankingService.getSubredditsGrowth(30, 9999));
        return "top/growing";
    }

    @GetMapping("/comments")
    public String topComments(Model model) {
        model.addAttribute("commentsToday", subredditRankingService.getAverageComments(1));
        model.addAttribute("commentsWeek", subredditRankingService.getAverageComments(7));
        model.addAttribute("commentsMonth", subredditRankingService.getAverageComments(30));
        return "top/comments";
    }

    @GetMapping("/posts")
    public String topPosts(Model model) {
        model.addAttribute("postsToday", subredditRankingService.getAveragePosted(1));
        model.addAttribute("postsWeek", subredditRankingService.getAveragePosted(7));
        model.addAttribute("postsMonth", subredditRankingService.getAveragePosted(30));
        return "top/posts";
    }

    @GetMapping("/subreddits")
    public String topSubreddits(Model model) {
        model.addAttribute("subscribersList", subredditRankingService.getSubscriberRanking());
        return "top/subreddits";
    }
}
