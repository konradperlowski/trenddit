package trenddit.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import trenddit.entity.SubredditRanking;
import trenddit.entity.SubredditRankingPK;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

public interface SubredditRankingRepository extends CrudRepository<SubredditRanking, SubredditRankingPK> {

    @Query(value = "SELECT a.name, (a.subscribers - b.subscribers) / :number AS growth " +
            "FROM subreddit_ranking a LEFT JOIN " +
            "(SELECT name, subscribers FROM subreddit_ranking WHERE date = :date_from) b " +
            "ON a.name = b.name  WHERE a.date = :today ORDER  BY growth DESC LIMIT :limit",
            nativeQuery = true)
    List<Tuple> findSubredditsGrowth(
            @Param("today") String today,
            @Param("date_from") String from,
            @Param("number") Integer numberOfDays,
            @Param("limit") Integer limit);

    List<SubredditRanking> findTop10ByDateOrderByCommentsDesc(Date date);

    List<SubredditRanking> findTop10ByDateOrderByPostsDesc(Date date);

    List<SubredditRanking> getByDateOrderBySubscribersDesc(Date date);
}
