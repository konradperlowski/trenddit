package trenddit.repository;

import org.springframework.data.repository.CrudRepository;
import trenddit.bean.BestSubredditWeekly;

public interface BestSubredditWeeklyRepository extends CrudRepository<BestSubredditWeekly, Integer> {
}
