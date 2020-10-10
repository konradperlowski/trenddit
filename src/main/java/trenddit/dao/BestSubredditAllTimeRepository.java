package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditAllTime;

import java.util.List;

public interface BestSubredditAllTimeRepository extends CrudRepository<BestSubredditAllTime, String> {

    List<BestSubredditAllTime> findByOrderByNumberDesc();
}
