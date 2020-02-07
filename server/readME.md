#Running the Server
Run the following instructions when configuring the database:
```
CREATE USER 'athleticstorm'@'localhost' IDENTIFIED WITH mysql_native_password BY 'mysqlpazz';
CREATE DATABASE athleticstorm
GRANT ALL PRIVILEGES ON athleticstorm.* TO 'athleticstorm'@'localhost'
```