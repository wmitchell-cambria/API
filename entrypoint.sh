#!/bin/bash

CWDS_OPTS=${CWDS_OPTS:-"-Xms128m -Xmx512m"}
echo  $CWDS_OPTS
java ${CWDS_OPTS} -jar api.jar server api.yml
