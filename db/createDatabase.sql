CREATE DATABASE groshi_db;
CREATE USER 'groshi_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON groshi_db.* TO 'groshi_user'@'localhost';
FLUSH PRIVILEGES;

