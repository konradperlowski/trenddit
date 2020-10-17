package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import trenddit.bean.SubredditInfo;
import trenddit.service.RedditOperations;

@Controller
@RequestMapping("/subreddit")
public class SubredditController {

    private final RedditOperations redditOperations;

    public SubredditController(RedditOperations redditOperations) {
        this.redditOperations = redditOperations;
    }

    @GetMapping(value = "/{subreddit}")
    public String subredditHome(@PathVariable String subreddit, Model model) {
        SubredditInfo subredditInfo = redditOperations.getSubredditInfo(subreddit);
        if (subredditInfo == null) return "subreddit/not-found";
        model.addAttribute("subreddit", subredditInfo);
        return "subreddit/home";
    }
}
