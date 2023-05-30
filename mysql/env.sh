#!/usr/bin/env bash
export IMAGE_NAME=mysql
export IMAGE_TAG=kreamish

export CONTAINER_NAME=mysql
export CONTAINER_OUTER_PORT=1521
export CONTAINER_INNER_PORT=3306
export CONTAINER_INNER_DATA_PATH=/var/lib/mysql

export IMAGE_USER=1000
export IMAGE_GROUP=1000

export DB_VOLUME_NAME=mysql-data
export DB_ROOT_PASSWORD=secret
export DB_USER=kreamishwas
export DB_PASSWORD=kreamish
export DB_NAME=kreamishdb
