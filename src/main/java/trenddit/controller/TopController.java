package trenddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/top")
public class TopController {

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
        return "top/subreddits";
    }
}
