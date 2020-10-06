package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditWeekly;

public interface BestSubredditWeeklyRepository extends CrudRepository<BestSubredditWeekly, Integer> {
}
