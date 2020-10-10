package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;

import java.util.Date;
import java.util.List;

@Service
public class SubredditRankingService {

    private final SubredditRankingRepository subredditRankingRepository;

    public SubredditRankingService(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
    }

    public List<SubredditRanking> getTodayMostCommented() {
        return subredditRankingRepository.findTop10ByDateOrderByCommentsDesc(new Date());
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(new Date());
    }
}
