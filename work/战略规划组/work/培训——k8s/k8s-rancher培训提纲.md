# 培训提纲

- [培训提纲](#培训提纲)
  - [培训目的](#培训目的)
  - [服务地址](#服务地址)
  - [涉及知识点/工具](#涉及知识点工具)
  - [涉及](#涉及)

## 培训目的

介绍展现DevOps全流程

具体实现内容：

1. 规范开发环境
   1. 打造docker容器化开发环境
2. CI/CD使用
   1. rancher-pipeline

## 服务地址

| 服务                | 地址                            |
| :------------------ | :------------------------------ |
| 代码仓库——Gitlab    | <http://faii.com.cn:2525/>      |
| k8s管理——rancher    | <https://faii.com.cn:20001/>    |
| 私有仓库管理——Nexus | <http://faii.com.cn:10653/>     |
| 私有仓库——Docker    | <https://192.168.129.112:10001> |

## 涉及知识点/工具

1. Gitlab基本使用
2. docker
   1. Dockerfile
   2. 构建镜像并上传
3. kubernetes
   1. 基本概念
   2. 编写Deployment
4. rancher使用
5. CICD基本概念
6. rancher-pipeline
   1. 编写rancher-pipeline.yaml文件

## 具体步骤

1. gitlab工作流
2. 环境准备
   1. 远程开发准备
      1. 准备开发环境镜像
      2. 使用rancher在k8s集群中启动开发环境容器
      3. 远程连接容器
   2. CI/CD准备
      1. 连接代码仓库和k8s集群
      2. 配置CICD步骤
      3. 手动编辑配置文件
      4. 编写CI/CD脚本
         1. 测试阶段
         2. 构建阶段
         3. 部署阶段
