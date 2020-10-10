package trenddit.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import trenddit.entity.SubredditRanking;
import trenddit.entity.SubredditRankingPK;

import java.util.List;

public interface SubredditRankingRepository extends CrudRepository<SubredditRanking, SubredditRankingPK> {

    @Query(value = "SELECT ((SELECT subscribers FROM subreddit_ranking WHERE date = :today AND name = :name) - " +
            "(SELECT subscribers FROM subreddit_ranking WHERE date = :date_from AND name = :name)) / :number",
            nativeQuery = true)
    Integer findSubredditGrowth(
            @Param("name") String name,
            @Param("today") String today,
            @Param("date_from") String from,
            @Param("number") Integer numberOfDays);

    @Query("select distinct name from SubredditRanking")
    List<String> findDistinctName();
}
