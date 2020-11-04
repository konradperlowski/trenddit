# Trenddit
Application monitoring reddit's trends

## Requirements
- java 8 or newer
- mysql 8
- python 3.8 or newer (only to update db)

### Database configuration
- create database `Trenddit`: `CREATE DATABASE Trenddit`
- create user `trenddit`: `CREATE USER trenddit@localhost IDENTIFIED BY 'password';`, `GRANT ALL PRIVILEGES ON Trenddit.* TO trenddit@localhost;`

### Database updates
In order to update data properly you need to install requirements `pip3 install -r scripts/requirements.txt`
