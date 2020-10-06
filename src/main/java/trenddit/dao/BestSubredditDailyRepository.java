package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditDaily;

public interface BestSubredditDailyRepository extends CrudRepository<BestSubredditDaily, Integer> {
}
