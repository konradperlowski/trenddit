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
            "ON a.name = b.name  WHERE a.date = :today ORDER  BY growth DESC",
            nativeQuery = true)
    List<Tuple> findSubredditsGrowth(
            @Param("today") String today,
            @Param("date_from") String from,
            @Param("number") Integer numberOfDays);

    @Query(value = "SELECT a.name, (a.subscribers - b.subscribers) / :number AS growth " +
            "FROM subreddit_ranking a LEFT JOIN " +
            "(SELECT name, subscribers FROM subreddit_ranking WHERE date = :date_from AND name = :subreddit) b " +
            "ON a.name = b.name  WHERE a.date = :today AND a.name = :subreddit",
            nativeQuery = true)
    Tuple findSubredditGrowth(
            @Param("subreddit") String subreddit,
            @Param("today") String today,
            @Param("date_from") String from,
            @Param("number") Integer numberOfDays);

    List<SubredditRanking> getByDateOrderBySubscribersDesc(Date date);

    @Query(value = "SELECT name, AVG(comments) AS comments FROM subreddit_ranking " +
            "WHERE date BETWEEN :date_from AND :today GROUP BY name ORDER BY comments DESC",
            nativeQuery = true)
    List<Tuple> findAverageComments(
            @Param("today") String today,
            @Param("date_from") String from);

    @Query(value = "SELECT name, AVG(posts) AS posts FROM subreddit_ranking " +
            "WHERE date BETWEEN :date_from AND :today GROUP BY name ORDER BY posts DESC",
            nativeQuery = true)
    List<Tuple> findAveragePosts(
            @Param("today") String today,
            @Param("date_from") String from);

    List<SubredditRanking> findAllByNameOrderByDate(String name);

    @Query(value = "SELECT a.name, (AVG(IFNULL(comments / posts, comments)) / b.activity * 100) - 100 AS growth " +
            "FROM subreddit_ranking a LEFT JOIN " +
            "(SELECT name, AVG(IFNULL(comments / posts, comments)) AS activity " +
            "FROM subreddit_ranking WHERE date BETWEEN :date_from AND :today GROUP BY name) b " +
            "ON a.name = b.name WHERE date BETWEEN :today AND :today GROUP BY a.name ORDER BY growth DESC",
            nativeQuery = true)
    List<Tuple> findSubredditsActivityGrowth(
            @Param("today") String today,
            @Param("date_from") String from
    );

    List<SubredditRanking> findTop1000ByDateOrderByCommentsDesc(Date date);
}
