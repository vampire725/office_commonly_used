# 搭建kubernetes环境

- [搭建kubernetes环境](#搭建kubernetes环境)
  - [思路](#思路)
  - [利用rancher搭建kubernetes环境](#利用rancher搭建kubernetes环境)
    - [环境](#环境)
    - [启动rancher](#启动rancher)
    - [添加rancher用户](#添加rancher用户)
    - [添加集群](#添加集群)
    - [添加监控工具`Prometheus`](#添加监控工具prometheus)
    - [集群备份](#集群备份)

## 思路

<!-- 1. 利用kubernetes可视化管理应用rancher搭建k8集群
1. 在rancher中安装gitlab应用 -->

## 利用rancher搭建kubernetes环境

Rancher是一款非常简便易用的可视化容器管理应用，其中对Kubernetes的支持与维护非常用心，在生产环境中的基础设置里更易于搭建与使用。所以我们选择使用rancher搭建kubernetes集群并进行管理使用。

### 环境

- 版本说明

|      环境       |  版本   |         IP          |        外网IP        |
| :-------------: | :-----: | :-----------------: | :------------------: |
|     docker      | 18.09.0 | 192.168.129.15/2375 |          无          |
| rancher/rancher | 2.1.12  |  10.20.10.208/443   | 111.33.152.130/10161 |

- kubernetes角色

|          角色           |     主机IP     | 主机名称  |
| :---------------------: | :------------: | :-------: |
| etcd、control以及worker | 192.168.129.15 | newnode15 |

### 启动rancher

- 在`10.20.10.208`服务器上进行操作

```bash
# 分别映射容器内80和443端口到宿主机8082和8081
docker run -d --restart=unless-stopped -p 8082:80 -p 8081:443 rancher/rancher:latest
```

- 访问外网地址`https://111.33.152.130:10161/`进入rancher界面，设置admin密码

![rancher主页](https://i.loli.net/2019/08/03/qbzh37GFoZrOKA1.png)

### 添加rancher用户

![lALPDgQ9q4t8d1fNA8XNB4A_1920_965.png](https://i.loli.net/2019/08/03/rdLRjgSwq45GJhQ.png)

### 添加集群

- 添加集群01

![添加集群](https://i.loli.net/2019/08/03/zQDyR1Pt69qkeT2.png)

- 添加主机自建kubernetes集群

![添加集群02.png](https://i.loli.net/2019/08/03/X9qChArJ246diM7.png)

- 设置集群名称及kubernetes版本

![添加集群03.png](https://i.loli.net/2019/08/03/sPUqpL7gwfb4kAz.png)

- 自定义主机角色和主机名称，在这里我们设定`192.168.129.15`节点为etcd角色、control角色以及worker角色

![添加集群04.png](https://i.loli.net/2019/08/03/pw1kVuQG89xnfer.png)

- 复制产生的命令在`192.168.129.15`主机运行(可以在不同的主机上定义相同/不同的角色，一般来说k8s集群etcd角色个数为单数（防止选举冲突）)

```bash
[root@newnode15 ~] sudo docker run -d --privileged --restart=unless-stopped --net=host -v /etc/kubernetes:/etc/kubernetes -v /var/run:/var/run rancher/rancher-agent:v2.2.6 --server https://111.33.152.130:10165 --token p944r79v2h5hdb4sbxlvzgj7lk8j59ljh9hxcsjscjrj6f822f4lq5 --ca-checksum 380b6be0ebfe9d0ffd48af4edef2e032e8099b8e7e3328c25156b57d25ebc699 --node-name master --etcd --controlplane --worker
```

- 稍等片刻，可以看到已经创建完成的kubernetes集群
![访问集群.png](https://i.loli.net/2019/08/03/SkDRsLKzjbVMur9.png)

### 添加监控工具`Prometheus`

- 为了更好的监控查看k8s集群状态，我们可以添加监控工具Prometheus，点击集群名称进去，点击工具找到监控添加（需要确保Worker节点和Prometheus pod具有足够的资源）

![添加监控01.png](https://i.loli.net/2019/08/03/LDwye5aBfZ6Ajvh.png)

![添加监控02.png](https://i.loli.net/2019/08/03/PCUrdSNoiQuKbh2.png)

![添加监控03.png](https://i.loli.net/2019/08/03/PgvWwbCUMOxV6lX.png)

- 查看监控

![查看监控](https://github.com/gpp0725/pic/blob/master/%E6%9F%A5%E7%9C%8B%E7%9B%91%E6%8E%A7.png?raw=true)

### 集群备份

- 为了应对集群突发情况，我们可以在集群状态健康时进行备份

![备份](https://github.com/gpp0725/pic/blob/master/%E5%A4%87%E4%BB%BD.png?raw=true)

- 整体基本介绍（管理员和用户）
- 应用（容器）
- 存储
- 计算
- 网络
- 监控
