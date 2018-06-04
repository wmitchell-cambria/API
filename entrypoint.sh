#!/bin/bash


CWDS_OPTS=${CWDS_OPTS:-"-Xms128m -Xmx512m"}

if [ -x /paramfolder/parameters.sh ]; then
    source /paramfolder/parameters.sh
fi

java ${CWDS_OPTS} -jar api.jar server api.yml
