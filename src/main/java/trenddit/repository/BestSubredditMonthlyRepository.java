package trenddit.repository;

import org.springframework.data.repository.CrudRepository;
import trenddit.bean.BestSubredditMonthly;

public interface BestSubredditMonthlyRepository extends CrudRepository<BestSubredditMonthly, Integer> {
}
