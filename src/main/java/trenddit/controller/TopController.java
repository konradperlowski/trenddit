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
        model.addAttribute("growthToday", subredditRankingService.getSubredditsGrowth(1, 9999, true));
        model.addAttribute("growthWeek", subredditRankingService.getSubredditsGrowth(7, 9999, true));
        model.addAttribute("growthMonth", subredditRankingService.getSubredditsGrowth(30, 9999, true));
        return "top/growing";
    }

    @GetMapping("/comments")
    public String topComments(Model model) {
        model.addAttribute("commentsToday", subredditRankingService.getAverageComments(1, true));
        model.addAttribute("commentsWeek", subredditRankingService.getAverageComments(7, true));
        model.addAttribute("commentsMonth", subredditRankingService.getAverageComments(30, true));
        return "top/comments";
    }

    @GetMapping("/posts")
    public String topPosts(Model model) {
        model.addAttribute("postsToday", subredditRankingService.getAveragePosted(1, true));
        model.addAttribute("postsWeek", subredditRankingService.getAveragePosted(7, true));
        model.addAttribute("postsMonth", subredditRankingService.getAveragePosted(30, true));
        return "top/posts";
    }

    @GetMapping("/subreddits")
    public String topSubreddits(Model model) {
        model.addAttribute("subscribersList", subredditRankingService.getSubscriberRanking(true));
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
