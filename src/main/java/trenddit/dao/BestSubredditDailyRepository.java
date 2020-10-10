package trenddit.dao;

import org.springframework.data.repository.CrudRepository;
import trenddit.entity.BestSubredditDaily;
import trenddit.entity.BestSubredditDailyPK;

import java.util.Date;
import java.util.List;

public interface BestSubredditDailyRepository extends CrudRepository<BestSubredditDaily, BestSubredditDailyPK> {

    List<BestSubredditDaily> findByDateOrderByNumberDesc(Date date);
}
