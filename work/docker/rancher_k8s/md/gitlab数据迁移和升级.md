# gitlab数据迁移和升级

- [gitlab数据迁移和升级](#gitlab数据迁移和升级)
  - [思路](#思路)
  - [部署gitlab](#部署gitlab)
    - [创建项目](#创建项目)
    - [添加命名空间](#添加命名空间)
    - [创建PV持久存储卷](#创建pv持久存储卷)
    - [部署服务](#部署服务)
  - [gitalab数据迁移](#gitalab数据迁移)
  - [gitlab版本升级](#gitlab版本升级)

之前已经在rancher中搭建完成kubernetes环境，本片主要记录如何在rancher中安装gitlab，以及实现gitlab数据迁移和版本升级

## 思路

1. 部署gitlab服务
   1. 创建项目
   2. 添加命名空间
   3. 创建数据卷
   4. 部署服务
      1. 网络
      2. 环境变量
      3. 存储
2. gitlab数据迁移
   1. 在待迁移宿主机打包数据备份
   2. 加载数据
3. gitlab版本升级
   1. 跨版本升级
   2. 健康检查
   3. 回滚

## 部署gitlab

### 创建项目

创建项目，我们需要部署的服务都可以在这个项目里进行相关操作

- 进入在rancher中创建好的集群，点击`项目/命名空间`，开始添加项目
![添加项目1](https://github.com/gpp0725/pic/blob/master/%E6%B7%BB%E5%8A%A0%E9%A1%B9%E7%9B%AE1.png?raw=true)
![添加项目2](https://github.com/gpp0725/pic/blob/master/%E6%B7%BB%E5%8A%A0%E9%A1%B9%E7%9B%AE2.png?raw=true)

### 添加命名空间

自定义命名空间，命名空间存放的实体只在本命名空间域内有效，在此命名空间外部无效，这样便可解决了命名冲突问题。

![添加命名空间](https://github.com/gpp0725/pic/blob/master/%E6%B7%BB%E5%8A%A0%E5%91%BD%E5%90%8D%E7%A9%BA%E9%97%B4.png?raw=true)

### 创建PV持久存储卷

创建pv持久存储卷，以便于挂载数据卷使用

![创建持久卷1](https://github.com/gpp0725/pic/blob/master/%E5%88%9B%E5%BB%BA%E6%8C%81%E4%B9%85%E5%8D%B71.png?raw=true)
![创建持久卷2](https://github.com/gpp0725/pic/blob/master/%E5%88%9B%E5%BB%BA%E6%8C%81%E4%B9%85%E5%8D%B72.png?raw=true)

### 部署服务

1. 进入创建好的项目
![进入项目部署服务](https://github.com/gpp0725/pic/blob/master/%E8%BF%9B%E5%85%A5%E9%A1%B9%E7%9B%AE%E9%83%A8%E7%BD%B2%E6%9C%8D%E5%8A%A1.png?raw=true)

2. 填写服务名称，设置服务镜像及版本(如果未设置版本号，则默认为最新版本)，选择命名空间以及添加端口映射和环境变量
![部署服务1](https://github.com/gpp0725/pic/blob/master/%E9%83%A8%E7%BD%B2%E6%9C%8D%E5%8A%A11.png?raw=true)

3. 指定主机运行pods,添加数据卷，可以使用前面创建好的pv，可以挂载容器内多个路径到一个pvc下
![部署服务2](https://github.com/gpp0725/pic/blob/master/%E9%83%A8%E7%BD%B2%E6%9C%8D%E5%8A%A12%20(2).png?raw=true)

- 启动完成以后，访问gitlab服务，检查是否创建成功

![启动完成](https://github.com/gpp0725/pic/blob/master/%E5%90%AF%E5%8A%A8%E5%AE%8C%E6%88%90.png?raw=true)
![访问gitlab页面](https://github.com/gpp0725/pic/blob/master/%E8%AE%BF%E9%97%AEgitlab%E9%A1%B5%E9%9D%A2.png?raw=true)

## gitalab数据迁移

- 在需要数据迁移的服务器上执行命令打包gitlab数据

```bash
[root@node:~] gitlab-rake gitlab:backup:create

#使用以上命令会在/var/opt/gitlab/backups目录下创建一个名称类似为1564494627_2019_07_30_gitlab_backup.tar的压缩包, 这个压缩包就是Gitlab整个的完整部分, 其中开头的1564494627_2019_07_30是备份创建的日期

[root@node backups] pwd
/var/opt/gitlab/backups

[root@node backups] ls
1564494627_2019_07_30_gitlab_backup.tar

```

- 把打包好的数据，复制到我们刚刚部署gitlab的服务器里，并挂载到gitlab容器指定位置

```bash
[root@node backups] scp -P ./1564494627_2019_07_30_gitlab_backup.tar root@newnode15:/home/gitlab/data/backups/
```

```bash
[root@newnode15 backups] pwd
/home/gitlab/data/backups
[root@newnode15 backups] ls
1564494627_2019_07_30_gitlab_backup.tar
```

- 进入gitlab服务执行命令

![进入服务执行命令](https://github.com/gpp0725/pic/blob/master/%E8%BF%9B%E5%85%A5%E6%9C%8D%E5%8A%A1%E6%89%A7%E8%A1%8C%E5%91%BD%E4%BB%A4.png?raw=true)

```bash
# 停止相关数据连接服务
root@gitlab-ce-77f9c85988-9z2tn:/ gitlab-ctl stop unicorn
root@gitlab-ce-77f9c85988-9z2tn:/ gitlab-ctl stop sidekiq

# 从1481598919编号备份中恢复
root@gitlab-ce-77f9c85988-9z2tn:/ cd /var/opt/gitlab/backups

root@gitlab-ce-77f9c85988-9z2tn:/var/opt/gitlab/backups ls
1564494627_2019_07_30_gitlab_backup.tar

root@gitlab-ce-77f9c85988-9z2tn:/var/opt/gitlab/backups gitlab-rake gitlab:backup:restore BACKUP=1481598919

# 启动Gitlab
root@gitlab-ce-77f9c85988-9z2tn:/var/opt/gitlab/backups gitlab-cli start
```

## gitlab版本升级

- 跨版本升级
进入项目，点击升级修改镜像版本进行升级，GitLab社区版不支持直接跨版本升级，需一步步向上升级，若版本越旧，需要跨的版本就越多，我们需要从8.17.3-ce.0升级到最新版本。经过测试，gitlab从8.17.3升级到12.0.4需要跨过以下版本：
`8.17.3-ce.0-->9.2.8-ce.0-->10.2.7-ce.0-->11.3.5-ce.0-->12.0.4`

![服务升级](https://github.com/gpp0725/pic/blob/master/%E6%9C%8D%E5%8A%A1%E5%8D%87%E7%BA%A71.png?raw=true)

![修改镜像版本](https://github.com/gpp0725/pic/blob/master/%E4%BF%AE%E6%94%B9%E9%95%9C%E5%83%8F%E7%89%88%E6%9C%AC.png?raw=true)

- **每升级一个版本，都要对其进行健康检查，登录检测部署情况**

1. 若gitlab出现502页面，重新启动gitlab服务即可

- 如果升级以后服务不稳定，可选择版本进行回滚重新部署

![回滚](https://github.com/gpp0725/pic/blob/master/%E5%9B%9E%E6%BB%9A.png?raw=true)
![回滚2](https://github.com/gpp0725/pic/blob/master/%E5%9B%9E%E6%BB%9A2.png?raw=true)
