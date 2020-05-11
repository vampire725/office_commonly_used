# 笔记

## 组件介绍

Etcd存储服务（可选），运行Api Server进程，Controller Manager服务进程及Scheduler服务进程，关联工作节点Node。Kubernetes API server提供HTTP Rest接口的关键服务进程，是Kubernetes里所有资源的增、删、改、查等操作的唯一入口。也是集群控制的入口进程；Kubernetes Controller Manager是Kubernetes所有资源对象的自动化控制中心；Kubernetes Schedule是负责资源调度（Pod调度）的进程

## k8s概念详解

### k8s 命名空间

如果你使用Rancher来管理Kubernetes集群，那么你就可以使用projects特性提供的扩展功能。Rancher里的Project是一个额外的组织层，用于将多个命名空间绑定在一起。

Rancher的project在命名空间上覆盖了一个控制结构，允许你将命名空间分组成逻辑单元并对其应用相应的策略。Project在大多数情况下反映了命名空间，但它是作为命名空间的容器而不是单独的工作负载资源。Rancher中的每个命名空间只存在于一个project中，命名空间继承应用于该项目的所有策略。

Rancher的project没有引入新的组织模型，而是简单地将相同的抽象应用到了命名空间上，而命名空间作用于工作负载对象。如果你喜欢使用命名空间，但是又需要额外的控制层的话，那么它们就能够填补这一使用上的空白。

### service

Service定义了Pod的逻辑集合和访问该集合的策略，是真实服务的抽象。Service提供了一个统一的服务访问入口以及服务代理和发现机制，关联多个相同Label的Pod，用户不需要了解后台Pod是如何运行。外部系统访问Service的问题

### label

Label是Replication Controller和Service运行的基础，二者通过Label来进行关联Node上运行的Pod。

Label是attach到Pod的一对键/值对，用来传递用户定义的属性。比如，你可能创建了一个"tier"和“app”标签，通过Label（tier=frontend, app=myapp）来标记前端Pod容器，使用Label（tier=backend, app=myapp）标记后台Pod。然后可以使用Selectors选择带有特定Label的Pod，让具体某一个Pod或者Deployment去使用某一个Service实现特定的网络配置.
