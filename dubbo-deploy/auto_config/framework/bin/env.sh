#!/bin/bash

APP_PORT=${appserverPort}
APP_NAME=${appName}
MODULE_NAME=${moduleName}
PRODUCTION=${productionModel}

# deploy and output root path
DEPLOY_HOME_ROOT=${deployhomeroot}
OUT_PUT_ROOT=${outputroot}

# product properties file path
PROPERTIES_FILE="$DEPLOY_HOME_ROOT/yml/"${fileProName}

TOMCAT_SERVER_HOME="$DEPLOY_HOME_ROOT/$APP_NAME/web-deploy/tomcat_server"
OUTPUT_HOME="$OUT_PUT_ROOT/$APP_NAME"
DEPLOY_HOME="$DEPLOY_HOME_ROOT/$APP_NAME/web-deploy"

# propreties
# class path  --spring.profiles.active=prod
# localfile  --spring.config.location=

PRO_OPTS=" "
if [ "$PRODUCTION" = "product" ]; then
	# PRO_OPTS="-Dspring.config.location=$PROPERTIES_FILE"
	PRO_OPTS="--spring.config.location=file:$PROPERTIES_FILE"
elif [ "$PRODUCTION" = "test" ]; then
  # PRO_OPTS="-Dspring.config.location=classpath:/application-test.yml";
  # PRO_OPTS="--spring.config.location=file:/home/www/application-test.yml"
  PRO_OPTS="--spring.profiles.active=test"
else
  # PRO_OPTS="-Dspring.config.location=classpath:/application-dev.yml";
	PRO_OPTS="--spring.profiles.active=dev"
fi

export PRO_OPTS APP_PORT APP_NAME MODULE_NAME DEPLOY_HOME_ROOT OUT_PUT_ROOT PROPERTIES_FILE TOMCAT_SERVER_HOME OUTPUT_HOME DEPLOY_HOME


