# present-constructor

## How to run
- init database
  - run `docker run --rm -e MYSQL_DATABASE=presents -e MYSQL_ROOT_PASSWORD=$ROOT_PASSWORD -e MYSQL_USER=$DB_USER -e MYSQL_PASSWORD=$DB_PASSWORD -v $PATH_TO_DB_DATA:/var/lib/mysql mysql:5.7.21`
  - when database initialised stop container
- run app
  - create `.env` file with configuration, e.g.
  ```
  DB_USER=test
  DB_PASS=test
  DB_DATA_PATH=~/mysql-data
  ```
  - run `docker-compose pull & docker-compose up`
- open start page on `localhost`