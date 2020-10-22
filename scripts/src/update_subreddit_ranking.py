from datetime import date
from time import time

from subreddit_rankings import update_rankings

start = time()

print('Updating subreddit rankings...')
update_rankings(date.today())
print('Updating subreddit rankings complete')

# execution time: 133.76444005966187
print(f'\nexecution time: {time() - start}')
