# rook-ceph部署思路

## 部署rook组件（operator.yaml）

1. Rook Operator
   1. Rook与kubernetes交互的组件
   2. 整个rook集群只有一个

2. Rook Agent
   1. 与Rook Operator交互，执行命令
   2. 每个kubernetes的node上都会启动一个
   3. 不同的存储系统，启动的agent是不一样的

## ceph集群（cluster.yaml）

1. mgr
2. mon
3. osd

## ceph toolbox部署（toolbox.yaml）

默认启动的Ceph集群，是开启Ceph认证的，这样你登陆Ceph组件所在的Pod里，是没法去获取集群状态

要想获取集群状态，执行CLI命令，部署Ceph toolbox

1. rook-ceph-tools

## RDB服务

- 步骤：
  1. 创建rbd-provisioner pod （通过rook创建Ceph Cluster之后，rook自身提供了rbd-provisioner服务，所以我们不需要再部署其provisioner。）
  2. 创建rbd对应的storageclass（相当于创建pv）
  3. 创建pvc使用rbd对应的storageclass

## CephFS服务

1. 创建CephFS(filesystem.yaml)
2. 创建StorageClass(pv)
3. 创建pvc




CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1700.000 MHz
Total size of Disk   : 12744.0 GB (6784.2 GB Used)
Total amount of Mem  : 128375 MB (52317 MB Used)
Total amount of Swap : 95366 MB (4295 MB Used)
System uptime        : 139 days, 10 hour 27 min
Load average         : 5.27, 7.76, 7.09
OS                   : CentOS 7.6.1810
Arch                 : x86_64 (64 Bit)
Kernel               : 3.10.0-862.14.4.el7.x86_64

CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1700.000 MHz
Total size of Disk   : 5371.2 GB (3400.4 GB Used)
Total amount of Mem  : 515422 MB (90885 MB Used)
Total amount of Swap : 95366 MB (3818 MB Used)
System uptime        : 139 days, 10 hour 29 min
Load average         : 5.62, 6.92, 7.34
OS                   : CentOS 7.5.1804
Arch                 : x86_64 (64 Bit)
Kernel               : 3.10.0-862.14.4.el7.x86_64


CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1700.000 MHz
Total size of Disk   : 5371.2 GB (2998.4 GB Used)
Total amount of Mem  : 515422 MB (37559 MB Used)
Total amount of Swap : 95366 MB (4535 MB Used)
System uptime        : 139 days, 10 hour 30 min
Load average         : 1.74, 2.12, 2.26
OS                   : CentOS 7.6.1810
Arch                 : x86_64 (64 Bit)
Kernel               : 3.10.0-862.14.4.el7.x86_64


CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1696.040 MHz
Total size of Disk   : 2712.9 GB (926.2 GB Used)
Total amount of Mem  : 515607 MB (148865 MB Used)
Total amount of Swap : 102399 MB (145 MB Used)
System uptime        : 27 days, 5 hour 18 min
Load average         : 0.78, 0.83, 0.81
OS                   : CentOS 7.5.1804
Arch                 : x86_64 (64 Bit)
Kernel               : 5.2.2-1.el7.elrepo.x86_64


CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1699.896 MHz
Total size of Disk   : 429.4 GB (83.2 GB Used)
Total amount of Mem  : 63867 MB (17650 MB Used)
Total amount of Swap : 95366 MB (284 MB Used)
System uptime        : 31 days, 9 hour 29 min
Load average         : 9.99, 9.66, 9.47
OS                   : CentOS 7.6.1810
Arch                 : x86_64 (64 Bit)
Kernel               : 3.10.0-862.14.4.el7.x86_64




CPU model            : Intel(R) Xeon(R) CPU E5-2609 v4 @ 1.70GHz
Number of cores      : 16
CPU frequency        : 1700.000 MHz
Total size of Disk   : 429.4 GB (168.2 GB Used)
Total amount of Mem  : 63867 MB (11672 MB Used)
Total amount of Swap : 95366 MB (8777 MB Used)
System uptime        : 83 days, 7 hour 44 min
Load average         : 69.34, 67.64, 63.31
OS                   : CentOS 7.6.1810
Arch                 : x86_64 (64 Bit)
Kernel               : 3.10.0-862.14.4.el7.x86_64




