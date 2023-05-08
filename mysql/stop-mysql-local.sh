#!/usr/bin/env bash
# shellcheck disable=SC2046
source $(dirname "$0")/env.sh

if docker ps -a | grep -q "${IMAGE_NAME}:${IMAGE_TAG} .* Up"; then
    docker rm -f ${CONTAINER_NAME} > /dev/null
    echo "${IMAGE_NAME}:${IMAGE_TAG} container is successfully removed"
    exit 1
fi

echo "${IMAGE_NAME}:${IMAGE_TAG} is not running"