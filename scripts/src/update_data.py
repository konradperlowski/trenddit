from datetime import date
from time import time

from get_best_subreddits import update_best_subreddits
from subreddit_rankings import update_ranking

start = time()

print('Updating best subreddits...')
update_best_subreddits(date.today())
print('Update best subreddits complete')

print('Updating subreddit rankings...')
update_ranking(date.today())
print('Updating subreddit rankings complete')

print(f'\nexecution time: {time() - start}')
