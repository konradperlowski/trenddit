package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditMonthly;

public interface BestSubredditMonthlyRepository extends CrudRepository<BestSubredditMonthly, Integer> {
}
