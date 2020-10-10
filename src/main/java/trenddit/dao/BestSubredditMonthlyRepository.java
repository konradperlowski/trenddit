package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditMonthly;

import java.util.List;

public interface BestSubredditMonthlyRepository extends CrudRepository<BestSubredditMonthly, String> {

    List<BestSubredditMonthly> findByOrderByNumberDesc();
}
