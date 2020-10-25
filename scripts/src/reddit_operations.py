import concurrent.futures
import time
from json import JSONDecodeError

import requests

from config import *

comment_url = 'https://api.pushshift.io/reddit/comment/search/?size=1&after=24h&metadata=true&subreddit='
error_list = list()


def get_best_subreddit(period_of_time='all', limit=100):
    best_subreddit = {}
    for post in reddit.subreddit("all").top(time_filter=period_of_time, limit=limit):
        subreddit = post.subreddit.display_name
        best_subreddit[subreddit] = best_subreddit.get(subreddit, 0) + 1
    return sorted(best_subreddit.items(), key=lambda x: x[1], reverse=True)


def get_subreddits_rank_by_number_of_subscribers():
    return sorted(get_subreddit_list(), key=lambda x: x.subscribers, reverse=True)


def get_subreddit_list():
    return [subreddit for subreddit in reddit.subreddits.popular(limit=None)]


def get_subreddits_rank_by_number_of_last_hour_posts():
    final_list = list()
    with concurrent.futures.ThreadPoolExecutor() as executor:
        for result in executor.map(get_number_of_posts, get_subreddit_list()):
            final_list.append(result)
    return final_list


def get_subreddits_rank_by_number_of_last_day_comments():
    subreddit_list = [subreddit.display_name for subreddit in get_subreddit_list()]
    return list(filter(None.__ne__, get_subreddits_ranking_by_function(get_number_of_comments, subreddit_list)))


def get_subreddits_ranking_by_function(function, data):
    final_list = list()
    with concurrent.futures.ThreadPoolExecutor(max_workers=3) as executor:
        for result in executor.map(function, data):
            final_list.append(result)
    if len(error_list) != 0:
        return final_list + get_subreddits_ranking_by_function(function, error_list)
    return final_list


def get_number_of_posts(subreddit):
    post_list = list(reddit.subreddit(subreddit.display_name).top('hour', limit=None))
    post_list = [post for post in post_list if post.created_utc > time.time() - 3600]
    return subreddit.display_name, len(post_list)


def get_number_of_comments(subreddit):
    if subreddit in error_list:
        error_list.remove(subreddit)
    try:
        url = comment_url + subreddit
        data = requests.get(url=url).json()
    except JSONDecodeError:
        error_list.append(subreddit)
        return None
    return subreddit, data['metadata']['total_results']
