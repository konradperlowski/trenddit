package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditAllTime;

public interface BestSubredditAllTimeRepository extends CrudRepository<BestSubredditAllTime, Integer> {
}
