package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditYearly;

import java.util.List;

public interface BestSubredditYearlyRepository extends CrudRepository<BestSubredditYearly, String> {

    List<BestSubredditYearly> findByOrderByNumberDesc();
}
