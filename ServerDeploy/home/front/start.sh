#进入到当前前台目录
#!/bin/sh
echo $PATH
echo '当前npm node 检测无误'
vue_url=/var/lib/jenkins/workspace/MockExam-Vue
cd $vue_url
rm -rf ./dist && rm -rf ./dist.*
echo '已删除之前构建'

#安装node_modules
cnpm i
#开始构建
npm run build:prod
echo '构建完成，准备放入到指定目录'

cd dist
tar -zcvf dist.tar.gz *
cp -r  $vue_url/dist/dist.tar.gz /home/mockExam/front/dist
echo'已把包发送到指定服务器目录'
cd /home/mockExam/front/dist  && tar -zxvf dist.tar.gz
echo '执行完成'
