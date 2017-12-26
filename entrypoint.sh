#!/bin/bash

CWDS_OPTS="-Xms128m -Xmx256m ${CWDS_OPTS}"

java ${CWDS_OPTS} -jar api.jar server api.yml
