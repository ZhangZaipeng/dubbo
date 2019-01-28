#!/bin/bash
################################################################
#@Authro ruigang
#@Date 2011-3-15
#@Description httpd/jetty/jboss functions
################################################################

if [ -f /etc/sysconfig/init ]; then
    . /etc/sysconfig/init
else
  SETCOLOR_SUCCESS=
  SETCOLOR_FAILURE=
  SETCOLOR_WARNING=
  SETCOLOR_NORMAL=
fi

success () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_SUCCESS
        if [ -z "$*" ]; then
            echo "ok"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "ok"
        else
            echo -e "$*"
        fi
    fi
    return
}

failed () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_FAILURE
        if [ -z "$*" ]; then
            echo "failed"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "failed"
        else
            echo -e "$*"
        fi
    fi
    return 1
}

warning () {
    if [ "$BOOTUP" = "color" ]; then
        $SETCOLOR_WARNING
        if [ -z "$*" ]; then
            echo "warning"
        else
            echo -e "$*"
        fi
        $SETCOLOR_NORMAL
    else
        if [ -z "$*" ]; then
            echo "warning"
        else
            echo -e "$*"
        fi
    fi
    return
}

exit_root () {
    if [ `id -u` = 0 ]; then
    	failed "ERROR! root (the superuser) can't run this script."
    	exit 1
    fi
}

remove_ipcs () {
    pid=$*
    who=`whoami`
    if [ -z $pid ]; then
        warning "Warning: Removed share memory and semaphore--gived pid is NULL"
        return
    fi
    
    shmids=`ipcs -mp |grep -P "\d+[ ]+$who[ ]+$pid" |awk '{print $1}'`
    for id in $shmids ; do
        ipcrm -m $id
    done

    shsemids=`ipcs -sp |grep -P "\d+[ ]+$who[ ]+$pid" |awk '{print $1}'`
    for id in $shsemids ; do
        ipcrm -s $id
    done
}

get_pid() {	
	STR=$1 ##指定查询字符串
    if $cygwin; then
        JAVA_CMD="$JAVA_HOME\bin\java"
        JAVA_CMD=`cygpath --path --unix $JAVA_CMD`
        JAVA_PID=`ps |grep $JAVA_CMD |awk '{print $1}'`
    else
        STR=`ps aux | grep java | grep "$STR"`
        if [ ! -z "$STR" ]; then
            JAVA_PID=`ps aux | grep java | grep "$STR" | grep -v grep | awk '{print $2}'`
        fi
    fi
    echo $JAVA_PID;
}

check_monitor_ok() {
    # CHECK_STARTUP_URL="http://127.0.0.1:${APP_PORT}/ok.html" module

    CHECK_STARTUP_URL="http://127.0.0.1:${APP_PORT}/${MODULE_NAME}/ok.htm"

    STARTUP_SUCCESS_MSG="ok"
	
    if  $production_mode ; then
		while [ true ]; do
	        COUNT=`curl -m 3 -s $CHECK_STARTUP_URL | grep -v "ok.html" | grep -ic "$STARTUP_SUCCESS_MSG"`
	        if [ $COUNT -ge 1 ]; then
	            break
	        fi
		    sleep 3
	    done
		success " [$PRODUCTION] [$MODULE_NAME] Oook!"
	else
	    LOOPS=0
	    while [ $LOOPS -lt 10 ]; do
            COUNT=`curl -m 3 -s $CHECK_STARTUP_URL | grep -v "ok.html" | grep -ic "$STARTUP_SUCCESS_MSG"`
            if [ $COUNT -ge 1 ]; then
                break
            fi
            let LOOPS=LOOPS+1
            sleep 3
	    done
	    COUNT=`curl -m 3 -s $CHECK_STARTUP_URL | grep -v "ok.html" | grep -ic "$STARTUP_SUCCESS_MSG"`
        if [ $COUNT -gt 0 ] ; then
            success "Oook!";
            warning "check [$PRODUCTION] [$MODULE_NAME] [$CHECK_STARTUP_URL] is ok";
        else
            failed "check [$PRODUCTION] [$MODULE_NAME] [$CHECK_STARTUP_URL] is failed";
        fi
    fi
}
# ==========================================================================
# 						start/stop  (tomcat)
# ==========================================================================

prepare_env() {
	cd `dirname $0`
	BASE_BIN_DIR=`pwd`
	export  LANG=en_US.UTF-8
	# public env
	. $BASE_BIN_DIR/base_env.sh
	
	# private env
	if [ -f $BASE_BIN_DIR/env.sh ]; then
		. $BASE_BIN_DIR/env.sh
	fi
	
	HOST_NAME=`hostname`
	LOG_DIR=$OUTPUT_HOME/logs
	##以前的日志保存
	LOGS_SAVED="$LOG_DIR/logs_saved"
	TIMESTAMP=`date +%Y_%m_%d_%H_%M`

	# backup logs
	if [ ! -d $LOGS_SAVED ]; then
	   mkdir -p $LOGS_SAVED
	fi

	export BASE_BIN_DIR HOST_NAME LOG_DIR LOGS_SAVED TIMESTAMP
}

start_tomcat() {
	APP_CHECK_LOG="$LOG_DIR"/"$MODULE_NAME"_stdout.log

	# tomcat server
	if [ -f $APP_CHECK_LOG ]; then
	    mv -f $APP_CHECK_LOG $LOGS_SAVED/${MODULE_NAME}_stdout.log.$TIMESTAMP
	fi
	
	## check if started before
	STR=''
	if $cygwin; then
	    JAVA_CMD="$JAVA_HOME\bin\java"
	    JAVA_CMD=`cygpath --path --unix $JAVA_CMD`
	    STR=`ps | grep "$JAVA_CMD"`		
	else
	    STR=`ps -C java -f --width 1000 | grep "$APP_NAME"`
	fi
	if [ ! -z "$STR" ]; then
	    echo "Tomcat server already started"
	    exit;
	fi
	
	## start tomcat
	echo -e "$HOST_NAME: starting tomcat ... check log: $APP_CHECK_LOG "
	echo "start tomcat, BASE_BIN_DIR --> $BASE_BIN_DIR"
	$BASE_BIN_DIR/tomcatctl.sh start 1>$APP_CHECK_LOG 2>$APP_CHECK_LOG &
	
	## check monitor
	check_monitor_ok
}

stop_tomcat() {
	TOMCAT_JAVA_PID=`get_pid "$APP_NAME"`
    if [ ! -z "$TOMCAT_JAVA_PID" ] ; then
	    echo -e "$HOST_NAME: stopping tomcat ... "
	    $BASE_BIN_DIR/tomcatctl.sh stop >> /dev/null 2>&1

	    ## check if stop failed ,do kill -9
	    TOMCAT_JAVA_PID=`get_pid "$APP_NAME"`
	    if [ ! -z "$TOMCAT_JAVA_PID" ] ; then
	        echo -e "kill java process $TOMCAT_JAVA_PID ..."
	        /bin/kill -9 $TOMCAT_JAVA_PID >> /dev/null 2>&1
	    fi
	    success "Oook!"
	else
	   warning "$HOST_NAME: tomcat not running, who care?"
	fi
}
