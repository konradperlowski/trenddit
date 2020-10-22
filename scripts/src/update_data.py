from datetime import date
from time import time

from get_best_subreddits import update_best_subreddits
from subreddit_rankings import update_subscriber_ranking

start = time()

print('Updating best subreddits...')
update_best_subreddits(date.today())
print('Updating best subreddits complete')

print('Updating subscriber ranking...')
update_subscriber_ranking(date.today())
print('Updating subscriber ranking complete')

# execution time: 72.39123058319092
print(f'\nexecution time: {time() - start}')
