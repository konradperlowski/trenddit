package trenddit.repository;

import org.springframework.data.repository.CrudRepository;
import trenddit.bean.BestSubredditAllTime;

public interface BestSubredditAllTimeRepository extends CrudRepository<BestSubredditAllTime, Integer> {
}
