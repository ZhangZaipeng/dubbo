#!/bin/bash

# 执行 当前执行的脚本文件的父目录
. `dirname $0`/functions.sh

## root启动判断
exit_root

##初始化
prepare_env

## 启动应用容器
if [ $# -eq 0 -o "$1" = "start" ];then
    start_tomcat
fi

