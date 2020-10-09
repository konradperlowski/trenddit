from reddit_operations import *
from sql_connector import *


def subscribers_ranking(date):
    trenddit_cursor.executemany(
        'INSERT INTO subreddit_ranking (name, subscribers, date) VALUES (%s, %s, %s)',
        [(subreddit.display_name, subreddit.subscribers, date) for subreddit in
         get_subreddits_rank_by_number_of_subscribers()])
    trenddit_db.commit()


def post_ranking(ranking_list, date):
    trenddit_cursor.executemany(
        'UPDATE subreddit_ranking SET posts=%s WHERE name=%s AND date=%s',
        [(subreddit[1], subreddit[0], date) for subreddit in ranking_list])
    trenddit_db.commit()


def comment_ranking(ranking_list, date):
    trenddit_cursor.executemany(
        'UPDATE subreddit_ranking SET comments=%s WHERE name=%s AND date=%s',
        [(subreddit[2], subreddit[0], date) for subreddit in ranking_list])
    trenddit_db.commit()


def update_ranking(date):
    ranking = get_subreddits_rank_by_number_of_daily_posts_and_comments()
    subscribers_ranking(date)
    post_ranking(ranking, date)
    comment_ranking(ranking, date)
