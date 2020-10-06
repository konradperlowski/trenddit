package trenddit.repository;

import org.springframework.data.repository.CrudRepository;
import trenddit.bean.BestSubredditDaily;

public interface BestSubredditDailyRepository extends CrudRepository<BestSubredditDaily, Integer> {
}
