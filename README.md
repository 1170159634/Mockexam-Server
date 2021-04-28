## 驾校理论课模拟考试系统

## 工具

Git Npm Lombok

## CI/CD

**具体部署流程看/ServerDeploy/服务器部署流程.txt**

Jenkins + Docker 持续集成

![11](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%8715.png)

### 技术栈

1.后端：

​	权限控制：SpringSecurity + JWT

​	Ioc框架：SpringBoot 

​	持久层：MybatisPlus + Spring Data JPA

​	缓存：Redis

​	图片处理：FastDFS

​	定时任务：xxl-job

2.前端：

​	Vue、Element-UI

## 功能

系统五个功能模块为：试题管理模块、系统监控模块、模拟考试模块、系统管理模块、个人信息模块。

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%871.png)

## 登录

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%872.png)

###  **模拟考试模块**

**小车(c1,c2)、货车(b2)、客车(a1)**

**包含科目一 科目四试题**

1．顺序练习

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%879.png)

2．随机练习

3．专项练习：按照单选题和判断题划分。

4．模拟考试：随机100道题，计时45分钟。

 ![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%8710.png)

### **试题管理模块**

1．试题字典：按照指定条件搜索，添加、修改、删除试题（管理员权限）。

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%874.png)

添加/修改试题：

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%8712.png)

2．推荐试题：按照后台针对用户错题进行计算返回的一定数目的试题。

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%873.png)



![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%875.png)

3．我的错题：每次用户计算错题后进行记录。

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%876.png)

4．我的收藏：用户收藏的试题。

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%877.png)

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%878.png)

###  系统管理模块

1．编辑用户：查询，修改，添加，删除用户。

2．角色管理：查询，修改，添加，删除角色，并对角色进行菜单授权。

3．菜单管理：查询，修改，添加，删除菜单，并按照路由进行跳转。

###  系统监控模块

1．在线用户：管理员可查看当前在线用户，对可疑用户进行强退。

2．操作日志：记录用户，用户IP所作操作，请求耗时及操作时间。

3．异常日志：用户操作调用后台接口时出现内部错误会将虚拟栈中信息记录，并返回给前台。

4．服务监控：服务器CPU使用率，内存使用率，系统版本等信息。

### 个人信息模块

1．个人信息：修改个人资料，头像等信息。



### 定时任务模块

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%8713.png)

![1](https://raw.githubusercontent.com/1170159634/Mockexam-Server/master/images/%E5%9B%BE%E7%89%8714.png)

  (1)分析用户行为，按照错题类型及客观题类型后台计算出50道题作为“为您推荐模块”

（2)定时按照第三方接口获取每个题型100道题，存入库内

（3)批量处理第三方平台题库图片，存入FastDFS中

