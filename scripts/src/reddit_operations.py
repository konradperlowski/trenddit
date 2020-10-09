import concurrent.futures

from config import *


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


def get_last_day_posts(subreddit):
    return reddit.subreddit(subreddit).top('day', limit=None)


def get_subreddits_rank_by_number_of_daily_posts_and_comments():
    final_list = list()
    with concurrent.futures.ThreadPoolExecutor() as executor:
        for result in executor.map(unpack_subreddit, get_subreddit_list()):
            final_list.append(result)
    return final_list


def unpack_subreddit(subreddit):
    post_list = list(get_last_day_posts(subreddit.display_name))
    return subreddit.display_name, len(post_list), sum([post.num_comments for post in post_list])
