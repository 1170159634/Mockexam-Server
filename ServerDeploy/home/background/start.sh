#!/bin/sh
#先进行打包
cd /var/lib/jenkins/workspace/MockExam-Server
mvn clean package
cd /var/lib/jenkins/workspace/MockExam-Server/eladmin-system/target
IMAGE_NAME=mockexam-server
#进入到打包目录，开始Docker部署
docker    ps -a | grep $IMAGE_NAME &> /dev/null
#如果存在，关闭并删除该容器
echo $IMAGE_NAME" 容器正在运行。准备删除.."
docker   stop $IMAGE_NAME
echo $IMAGE_NAME" 容器已经停止。"
docker    rm $IMAGE_NAME
echo $IMAGE_NAME"删除容器"
docker   rmi $IMAGE_NAME
echo $IMAGE_NAME"删除镜像"
echo $IMAGE_NAME" 容器不存在。创建并启动容器。"
docker build -f /home/mockExam/background/Dockerfile -t $IMAGE_NAME  .  
echo $IMAGE_NAME" 容器创建成功。"
docker    run -d -p8000:8000  --name=$IMAGE_NAME $IMAGE_NAME --network=host
echo $IMAGE_NAME" 容器运行成功。"
