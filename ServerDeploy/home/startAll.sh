#! /bin/bash
cd /usr/local/nginx
touch nginx.pid
cd /usr/local/nginx/sbin
./nginx 

/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart

cd /usr/local/middleSoftWare/redis/redis-4.0.6/src
./redis-server ../redis.conf


systemctl start jenkins
systemctl start docker
docker start mockexam-server
cd /home/mockExam/xxl-job
nohup java -jar *.jar >/dev/null 2>&1 &
