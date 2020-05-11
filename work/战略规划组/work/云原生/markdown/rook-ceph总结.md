# rook-ceph整理

## 整体思路

1. 简介
2. 部署rook组件
3. 部署ceph集群
4. ceph toolbox部署
5. dashboard页面
6. RDB服务

## rook简介

- rook是什么

rook是基于`kubernetes`之上，提供一键部署存储系统的编排系统。

## rook组件

1. Rook Operator
   1. Rook与kubernetes交互的组件
   2. 整个rook集群只有一个

2. Rook Agent
   1. 与Rook Operator交互，执行命令
   2. 每个kubernetes的node上都会启动一个
   3. 不同的存储系统，启动的agent是不一样的
