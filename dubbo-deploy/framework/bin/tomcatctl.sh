#!/bin/bash
set -e

BASE_BIN_DIR=`dirname $0`
# public env
. $BASE_BIN_DIR/base_env.sh

# private env
if [ -f $BASE_BIN_DIR/env.sh ]; then
	. $BASE_BIN_DIR/env.sh
fi

## tomcat 所需相关参数
TOMCAT_PID="$OUTPUT_HOME/logs/tomcat.pid"
JAVA="$JAVA_HOME/bin/java"
JAVA_OPTIONS="$JAVA_OPTS" ## jvm参数

export TOMCAT_PID TOMCAT_LOGS JAVA_OPTIONS JAVA

prepare() {
  # delete tomcat work home dir, then make the tomcat work
	if [ -d "$TOMCAT_SERVER_HOME" ]; then
   		rm -rf  "$TOMCAT_SERVER_HOME"
	fi

	if [ ! -d "$TOMCAT_SERVER_HOME" ]; then
    	mkdir -p "$TOMCAT_SERVER_HOME"
	fi
	
	# create dir
	mkdir -p "$OUTPUT_HOME/logs"
	if [ ! -f "$OUTPUT_HOME/logs/${MODULE_NAME}_stdout.log" ] ; then
  		touch "$OUTPUT_HOME/logs/${MODULE_NAME}_stdout.log"
  fi
  	  	
  rm -rf  "$TOMCAT_SERVER_HOME/*.jar"
  cp $DEPLOY_HOME/*.jar $TOMCAT_SERVER_HOME/$MODULE_NAME.jar
  echo -e "has copyed $TOMCAT_SERVER_HOME/$MODULE_NAME.jar"
}


start() {
	prepare
	## 启动tomcat
	# java [-options] -jar jarfile [args...]
	$JAVA $JAVA_OPTIONS -jar $TOMCAT_SERVER_HOME/$MODULE_NAME.jar $PRO_OPTS
}


stop() {
    BOOT_SHUTDOWN_URL="http://127.0.0.1:$APP_PORT/$MODULE_NAME/shutdown"
	curl -X POST $BOOT_SHUTDOWN_URL
}

if [ "$1" == "-v" ] || [ "$1" == "-h" ]; then
  echo "Useage: tomcatctl.sh run   ##前台启动"
  echo "        tomcatctl.sh start ##后台异步 tomcat --daemon"
  echo "        tomcatctl.sh stop  ##关闭"
elif [ "$1" == "start" ]; then
  start
elif [ "$1" == "stop" ]; then
  stop
else
  run
fi
