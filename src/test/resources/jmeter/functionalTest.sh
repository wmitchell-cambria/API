#!/bin/bash

JMETER_LOG=jmeter.jtl

function runTests(){
  validation
  ASSERTION_FILE=${LOG_DIR}/failed.log
  ERROR_FILE=${LOG_DIR}/errors.log

  printf "Attempting to delete ${ERROR_FILE}\n"
  rm ${ERROR_FILE}
  printf "Attempting to delete ${ASSERTION_FILE}\n"
  rm ${ASSERTION_FILE}

  while read file
  do
    if [[ ! $file =~ ^# ]];
    then
      printf "================ Running tests ${file}:====================== \n"
      executeTest
      reportErrors
    fi
  done < ${TEST_PATH}/functionalTests
}
function validation(){
  if [ -z $JMETER ]
  then
    printf "JMeter path is required. Please supply path as -j option\n"
    exit
  fi

  if [ -z $LOG_DIR ]
  then
    printf "The log directory path is required. Please supply path as -l option\n"
    exit
  fi

  if [ -z $PROPS ]
  then
    printf "The property file path is required. Please supply path as -p option\n"
    exit
  fi

  if [ -z $TEST_PATH ]
  then
    printf "The JMeter test file path is required. Please supply path as -t option\n"
    exit
  fi
}


function executeTest(){
  printf "${JMETER} --propfile ${PROPS} -n -t ${TEST_PATH}/${file} -j ${LOG_DIR}/${JMETER_LOG} -l ${REPORT_DIR}/${JMETER_LOG} -e -o ${REPORT_DIR} -Jlogs.assertion.file=${ASSERTION_FILE}\n"
  ${JMETER} --propfile ${PROPS} -n -t ${TEST_PATH}/${file} -j ${LOG_DIR}/${JMETER_LOG} -l ${REPORT_DIR}/${JMETER_LOG} -e -o ${REPORT_DIR} -Jlogs.assertion.file=${ASSERTION_FILE}

}

function reportErrors(){
  printf "running reports \n"
  if [ -s $ASSERTION_FILE ]
  then
    printf "******************************************************\n"
    printf "There is an error for ${file} \n"
    printf "******************************************************\n"
    createErrorFile
	   printf "${file} contains errors \n"
    echo "*******************************************************" >> ${ERROR_FILE}
    echo "    Error found in: ${file}" >> ${ERROR_FILE}
    echo "*******************************************************" >> ${ERROR_FILE}
    cat "${ASSERTION_FILE}" >> ${ERROR_FILE}
    rm ${ASSERTION_FILE}
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
