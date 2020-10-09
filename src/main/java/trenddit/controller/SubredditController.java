package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subreddit")
public class SubredditController {

    @GetMapping(value = "/{subreddit}")
    public String getSubreddit(@PathVariable String subreddit, Model model) {
        model.addAttribute("subreddit", subreddit);
        return "subreddit/home";
    }
}
