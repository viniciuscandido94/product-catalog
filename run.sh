#!/bin/bash
echo "building API image..."
mvn clean package
docker build -t compasso-rest-api .
echo "starting API, DataBase (Postgres) and PGAdmin..."
docker-compose up