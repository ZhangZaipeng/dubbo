#!/bin/bash

if [ ! -d $DEPLOY_HOME_ROOT/$appName ];then
	mkdir -p $DEPLOY_HOME_ROOT/$appName
fi

time=`date '+%Y-%m-%d-%H:%M'`

cp $DEPLOY_HOME_ROOT/$appName/web-deploy.zip $DEPLOY_HOME_ROOT/$appName/web-deploy$time.zip
$DEPLOY_HOME_ROOT/$appName/web-deploy/bin/killws.sh

rm -rf /home/www/htdocs/$appName/web-deploy

cd $DEPLOY_HOME_ROOT/$appName;unzip -d web-deploy web-deploy.zip
$DEPLOY_HOME_ROOT/$appName/web-deploy/bin/startws.sh