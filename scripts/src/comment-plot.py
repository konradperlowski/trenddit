from matplotlib import pyplot as plt
import datetime

from subreddit_rankings import get_comment_ranking

x = []
y = []
for row in get_comment_ranking(datetime.date.today() - datetime.timedelta(days=1)):
    x.append(row[0])
    y.append(row[2])

plt.subplot(211)
plt.title('Comment ranking linear')
plt.xlabel('Subreddit ranking')
plt.ylabel('Comment number')
plt.yscale('linear')
plt.plot(x, y)
plt.grid(True)

plt.subplot(212)
plt.title('Comment ranking log')
plt.xlabel('Subreddit ranking')
plt.ylabel('Comment number')
plt.yscale('log')
plt.plot(x, y)
plt.grid(True)
plt.show()
