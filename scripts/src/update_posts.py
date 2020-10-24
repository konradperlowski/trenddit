from datetime import date
from time import time

from subreddit_rankings import update_posts

start = time()

print('Updating subreddit posts...')
update_posts(date.today())
print('Updating subreddit posts complete')

# execution time: 133.76444005966187
print(f'\nexecution time: {time() - start}')
