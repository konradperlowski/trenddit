package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditWeekly;

import java.util.List;

public interface BestSubredditWeeklyRepository extends CrudRepository<BestSubredditWeekly, String> {

    List<BestSubredditWeekly> findByOrderByNumberDesc();
}
