#!/bin/bash

. `dirname $0`/functions.sh

## 初始化
prepare_env

## 关闭 tomcat
if [ $# -eq 0 -o "$1" = "stop" ];then
    stop_tomcat
fi
