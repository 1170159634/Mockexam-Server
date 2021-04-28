#! /bin/sh
killall nginx
docker stop mockexam-server
systemctl stop jenkins
xxl_pid=`ps -ef|grep xxl-job-admin-2.0.2.jar  |grep -v grep|awk '{printf $2}'`  
kill -9 $xxl_pid

redis_pid=`ps -ef | grep "redis-server" | grep -v grep | awk '{print $2}'`
kill -9 $redis_pid


killall fdfs_trackerd
killall fdfs_storaged
