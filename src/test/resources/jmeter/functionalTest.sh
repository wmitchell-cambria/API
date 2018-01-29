#!/bin/bash

JMETER=/opt/jmeter/bin/jmeter
LOG_DIR=/Users/tparker/projects/API/logs
PROPS=/conf/user.properties
JMETER_LOG=jmeter.log

#TODO: Remove absolute paths.
TEST_PATH=/integrationTests/src/test/resources/jmeter

function runTests(){
    ASSERTION_FILE=${LOG_DIR}/failed.log
    ERROR_FILE=${LOG_DIR}/errors.log

    rm ${ERROR_FILE}
    rm ${ASSERTION_FILE}

    while read file
    do
      if [[ ! $file =~ ^# ]];
      then
        printf "Running tests ${file}: \n"
        executeTest
        reportErrors
      fi
    done < src/test/resources/jmeter/functionalTests
}

function executeTest(){
  printf "${JMETER} --propfile ${PROPS} -n -t ${TEST_PATH}/${file} -j ${LOG_DIR}/${JMETER_LOG} -l ${LOG_DIR}/${JMETER_LOG} -Jlogs.assertion.file=${ASSERTION_FILE} \n"
  ${JMETER} --propfile ${PROPS} -n -t ${TEST_PATH}/${file} -j ${LOG_DIR}/${JMETER_LOG} -l ${LOG_DIR}/${JMETER_LOG} -Jlogs.assertion.file=${ASSERTION_FILE}
}

function reportErrors(){
  printf "running reports \n"
  if [ -s $ASSERTION_FILE ]
  then
      printf "we have an error for ${file} \n"
      createErrorFile
	    printf "${file} contains errors \n"
      echo "****    Error found in: ${file}" >> ${ERROR_FILE}
      cat "${ASSERTION_FILE}" >> ${ERROR_FILE}
#      rm ${ASSERTION_FILE}
  fi
}

function createErrorFile () {
  if [ ! -e "${ERROR_FILE}" ]
  then
    printf "Creating ${ERROR_FILE} \n"
    touch ${ERROR_FILE}
  fi
}

function help_menu () {
cat << EOF
Usage: ${0} (-h | -j | -l | -r | -p | -t)

OPTIONS:
       -h|--help          Show this message
       -j|--jmeter        The path to Jmeter run command. Useful for running locally
       -l|--logs          The logs directory. Useful for running locally
       -r|--reports       The reports directory. Useful for running locally
       -p|--properties    The reports directory. Useful for running locally
       -t|--tests         The location of the tests in the docker container.
EOF
}

while [[ $# > 0 ]]
do
case "${1}" in
  -j|--jmeter)
  JMETER=${2}
  shift
  ;;
  -l|--logs)
  LOG_DIR=${2}
  shift
  ;;
  -r|--reports)
  REPORT_DIR=${2}
  shift
  ;;
  -p|--properties)
  PROPS=${2}
  shift
  ;;
  -t|--tests)
  TEST_PATH=${2}
  shift
  ;;
  -h|--help)
  help_menu
  shift
  exit 0
  ;;
  *)
  echo "${1} is not a valid flag, try running: ${0} --help"
  exit 128
  ;;
esac
shift
done

runTests
