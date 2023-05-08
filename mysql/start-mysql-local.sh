#!/usr/bin/env bash
# shellcheck disable=SC2046
source $(dirname "$0")/env.sh

if docker ps -a | grep -q "${IMAGE_NAME}:${IMAGE_TAG} .* Up"; then
    echo "${IMAGE_NAME}:${IMAGE_TAG} is already running"
    exit 1
fi

docker run -dit --name ${CONTAINER_NAME} \
--user ${IMAGE_USER}:${IMAGE_GROUP} \
-e MYSQL_ROOT_PASSWORD=${DB_ROOT_PASSWORD} \
-e MYSQL_USER=${DB_USER} \
-e MYSQL_PASSWORD=${DB_PASSWORD} \
-e MYSQL_DATABASE=${DB_NAME} \
-p ${CONTAINER_OUTER_PORT}:${CONTAINER_INNER_PORT} \
-v ${DB_VOLUME_NAME}:${CONTAINER_INNER_DATA_PATH} \
${IMAGE_NAME}:${IMAGE_TAG}
