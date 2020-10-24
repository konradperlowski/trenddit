from datetime import date
from time import time

from subreddit_rankings import update_comments

start = time()

print('Updating subreddit comments...')
update_comments(date.today())
print('Updating subreddit comments complete')

# execution time: 4289.336724996567
print(f'\nexecution time: {time() - start}')
