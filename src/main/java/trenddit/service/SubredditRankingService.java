package trenddit.service;

import org.springframework.stereotype.Service;
import trenddit.bean.SubredditGrowth;
import trenddit.dao.SubredditRankingRepository;
import trenddit.entity.SubredditRanking;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditRankingService {

    private final SubredditRankingRepository subredditRankingRepository;

    public SubredditRankingService(SubredditRankingRepository subredditRankingRepository) {
        this.subredditRankingRepository = subredditRankingRepository;
    }

    public List<SubredditRanking> getSubscriberRanking() {
        return subredditRankingRepository.getByDateOrderBySubscribersDesc(new Date());
    }

    public List<SubredditRanking> getTodayMostCommented() {
        return subredditRankingRepository.findTop10ByDateOrderByCommentsDesc(new Date());
    }

    public List<SubredditRanking> getTodayMostPosted() {
        return subredditRankingRepository.findTop10ByDateOrderByPostsDesc(new Date());
    }

    public List<SubredditGrowth> getSubredditsGrowth(Integer days, Integer limit) {
        return mapToSubredditGrowth(subredditRankingRepository.findSubredditsGrowth(
                getToday(), daysAgo(days), days, limit == null ? 9999 : limit));
    }

    private List<SubredditGrowth> mapToSubredditGrowth(List<Tuple> tupleList) {
        return tupleList.stream().map(
                tuple -> new SubredditGrowth(
                        (String) tuple.get("name"),
                        tuple.get("growth") == null ? 0 : ((BigDecimal) tuple.get("growth")).intValue())
        ).collect(Collectors.toList());
    }

    private String getToday() {
        return dateToString(ago(0));
    }

    private String daysAgo(Integer days) {
        return dateToString(ago(days));
    }

    private Date ago(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    private String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
