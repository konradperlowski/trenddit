package trenddit.repository;

import org.springframework.data.repository.CrudRepository;
import trenddit.bean.SubredditRanking;

public interface SubredditRankingRepository extends CrudRepository<SubredditRanking, Integer> {
}
