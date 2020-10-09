import mysql.connector

trenddit_db = mysql.connector.connect(
    host='localhost',
    user='trenddit',
    password='password',
    database='Trenddit'
)

trenddit_cursor = trenddit_db.cursor()
