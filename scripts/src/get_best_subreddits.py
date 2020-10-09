from reddit_operations import get_best_subreddit
from sql_connector import *


def update_best(table, period_of_time, date):
    if period_of_time == 'day':
        trenddit_cursor.executemany(f'INSERT INTO {table} VALUES (%s, %s, %s)',
                                    [(date, best_subreddit[0], best_subreddit[1])
                                     for best_subreddit in get_best_subreddit(period_of_time)])
        trenddit_db.commit()
    else:
        trenddit_cursor.execute(f'DELETE FROM {table}')
        trenddit_cursor.executemany(f'INSERT INTO {table} VALUES (%s, %s)', get_best_subreddit(period_of_time))
        trenddit_db.commit()


def update_best_subreddits(date):
    update_best('best_subreddit_daily', 'day', date)
    update_best('best_subreddit_weekly', 'week', date)
    update_best('best_subreddit_monthly', 'month', date)
    update_best('best_subreddit_yearly', 'year', date)
    update_best('best_subreddit_all_time', 'all', date)
