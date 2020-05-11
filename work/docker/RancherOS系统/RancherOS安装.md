# RancherOS安装使用

- [RancherOS安装使用](#RancherOS安装使用)
  - [环境](#环境)
  - [安装流程](#安装流程)
  - [附加](#附加)
    - [1. 配置网卡](#1-配置网卡)
    - [2. cloud-config](#2-cloud-config)

RancherOS是Rancher推出的一个轻量级的Linux内核操作系统，专为容器环境而设计。

## 环境

|     IP/配置      |                                                                           描述                                                                           |
| :--------------: | :------------------------------------------------------------------------------------------------------------------------------------------------------: |
|  rancheros.iso   | RancherOS镜像，根据实际使用参考[RancherOS的github下载中心](https://github.com/rancher/os/releases/)和[官网](https://rancher.com/docs/os)选择对应版本类型 |
|  192.168.2.120   |                                                     rancherOS远程管理主机（可以是Ubuntu/centos/..）                                                      |
|  192.168.2.100   |                                                                      RancherOS主机                                                                       |
| cloud-config.yml |                              文件为yml格式，eg:`cloud-config.yml`，内容包括远程主机ssh公钥和IP地址信息，放在RancherOS主机上                              |

## 安装流程

1. 在RancherOS Gitlab页面下载对应版本镜像。
2. 新建虚拟机，加载ISO镜像，默认会以rancher用户身份进入一个运行在内存之上的临时RancherOS，所以建虚拟机的时候内存可以适当大一点，比如2G
3. 获取远程管理主机的ssh公钥信息
4. 新建/上传一个`cloud-config.yml`文件到RancherOS服务器上，主要内容就是写入远程管理主机的ssh公钥，因为RancherOS安装之后就只能通过ssh+公钥的方式登陆(是的，虚拟机控制台都进不去)，一个最简单的示例如下：

    ```yml
    # cloud-config
    ssh_authorized_keys:
    - ssh-rsa AAA...
    ```

5. 校验`cloud-config.yml`格式

    ```bash
    sudo ros config validate -i cloud-config.yml
    ```

6. 将RancherOS安装到硬盘。

    ```bash
    sudo ros install -c cloud-config.yml -d /dev/sda --append rancher.password=1qaz2WSX3edc4RFV
    ```

    **注意**：安装过程中只有2个选项，是否要安装？Y，是否要重启？N （因为重启比卸载光驱还快，如果选择重启就直接又进入一个新的临时RancherOS了…）

7. 手动`sudo poweroff`，卸载光驱，重启，看到熟悉的牛头LOGO，恭喜完成~
8. 在远程管理主机登录Rancher，进行相关使用操作。

## 附加

### 1. 配置网卡

在Vmware上安装时，如果临时RancherOS没有网， 需要手动配置网卡：

- 修改/etc/network/interfaces文件，在末尾添加：

    ```txt
    auto eth0
    iface eth0 inet static
    # IP
    address             xxx.xxx.xxx.xxx
    # 子网掩码
    netmask             xxx.xxx.xxx.xxx
    # 广播地址(可选)
    broadcast           xxx.xxx.xxx.xxx
    # 所在网段(可选)
    network             xxx.xxx.xxx.xxx
    # 网关
    gateway             xxx.xxx.xxx.xxx
    # dns服务器
    dns-nameservers     xxx.xxx.xxx.xxx
    ```

- 配置完成以后，执行`sudo ifup eth0`即可连上网络。

### 2. cloud-config

现有的公有云/虚拟化厂商大多支持cloud-init工具进行系统配置初始化(某种意义上的事实标准)。cloud-config就是为cloud-init服务的。RancherOS在system-docker中运行了一个cloud-init容器，它会在启动时查找可能位置上的cloud-config文件并依此配置系统配置项。

- cloud-config的语法格式就是标准的YAML语法，一个比较完整的cloud-config的示例如下(可直接用)：

```yaml
# 主机名
hostname: ros-test

# 系统配置
rancher:
  # 使用alpine控制台，也可以换成ubuntu/centos/debian
  console: alpine

  # 初始Docker源
  bootstrap_docker:
    registry_mirror: "https://docker.mirrors.ustc.edu.cn/"
  # 系统Docker源
  system_docker:
    registry_mirror: "https://docker.mirrors.ustc.edu.cn/"
  # 用户Docker源
  docker:
    registry_mirror: "https://docker.mirrors.ustc.edu.cn/"
    insecure-registries:
    - 10.20.10.100:8888
    insecure_registry:
    - 10.20.10.100:8888


  # 网络
  network:
    interfaces:
      eth0:
        address: 192.168.0.1/24
        broadcast: 192.168.0.255
        gateway: 192.168.0.254
        mtu: 1500
        dhcp: false
    dns:
      nameservers:
        - 114.114.114.114
        - 8.8.8.8

  # # 扩容现有磁盘不要用fdisk，除非你想把系统格式化了，用这个就能调整磁盘大小
  # resize_device: /dev/sda

# 可登录的机器公钥
ssh_authorized_keys:
  - ssh-rsa ...
  - ssh-rsa ...

# 挂载新磁盘
# mounts:
#   - ["/dev/vdb", "/mnt/s", "ext4", ""]

# 写文件
write_files:
  # 修改apk使用国内镜像
  - path: /etc/apk/repositories
    permissions: "0755"
    owner: root
    content: |
      https://mirrors.ustc.edu.cn/alpine/latest-stable/main
      https://mirrors.ustc.edu.cn/alpine/latest-stable/community

  # 配置bash
  - path: /home/rancher/.bashrc
    permissions: "0755"
    owner: rancher
    content: |
      # .bashrc

      # Source global definitions
      if [ -f /etc/bashrc ]; then
              . /etc/bashrc
      fi

      # Uncomment the following line if you don't like systemctl's auto-paging feature:
      # export SYSTEMD_PAGER=

      # User specific aliases and functions
      PS1="\[\e[37m\][\[\e[32m\]\u\[\e[37m\]@\h \[\e[36m\]\w\[\e[0m\]]\\$ "
      alias  d="docker "
      alias di="docker image"
      alias dc="docker container"
      alias dv="docker volumn"
      alias dn="docker netwrok"
      alias c='clear'
  - path: /home/rancher/.bash_profile
    permissions: "0755"
    owner: rancher
    content: |
      # If the shell is interactive and .bashrc exists, get the aliases and functions
      if [[ $- == *i* && -f ~/.bashrc ]]; then
          . ~/.bashrc
      fi

runcmd:
  - apk update
  - crond

# 启动时执行命令
# runcmd:
#   # 两种写法
#   - [touch, /home/rancher/test1]
#   - echo "test" > /home/rancher/test2
    - source .bashrc
```
