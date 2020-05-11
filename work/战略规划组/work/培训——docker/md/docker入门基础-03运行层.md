# Docker入门组件-运行层(基础)

- [Docker入门组件-运行层(基础)](#Docker入门组件-运行层基础)
  - [版本-Image](#版本-Image)
    - [镜像加速器](#镜像加速器)
      - [配置加速站点](#配置加速站点)
      - [修改启动脚本配置加速](#修改启动脚本配置加速)
    - [Docker 镜像使用](#Docker-镜像使用)
      - [查找镜像](#查找镜像)
      - [获取镜像](#获取镜像)
        - [1. `docker pull`](#1-docker-pull)
        - [2. `Dockerfile`构建镜像](#2-Dockerfile构建镜像)
      - [查看镜像列表](#查看镜像列表)
      - [打包镜像](#打包镜像)
      - [删除本地镜像](#删除本地镜像)
      - [导出镜像](#导出镜像)
  - [运行-Container](#运行-Container)
    - [Docker容器使用](#Docker容器使用)
      - [启动容器](#启动容器)
      - [查看容器](#查看容器)
      - [容器日志查看](#容器日志查看)
      - [容器详细信息](#容器详细信息)
      - [开启容器](#开启容器)
      - [停止容器](#停止容器)
      - [删除容器](#删除容器)

## 版本-Image

我们都知道，操作系统分为内核和用户空间。对于 Linux 而言，内核启动后，会挂载 root 文件系统为其提供用户空间支持。而 Docker 镜像（Image），就相当于是一个 root 文件系统。

Docker 镜像是一个特殊的文件系统，除了提供容器运行时所需的程序、库、资源、配置等文件外，还包含了一些为运行时准备的一些配置参数（如匿名卷、环境变量、用户等）。 **镜像不包含任何动态数据，其内容在构建之后也不会被改变。**

严格来说，镜像并非是像一个 ISO 那样的打包文件，镜像只是一个虚拟的概念，其实际体现并非由一个文件组成，而是由一组文件系统组成，或者说，由多层文件系统联合组成。

**镜像构建时，会一层层构建，前一层是后一层的基础。每一层构建完就不会再发生改变，后一层上的任何改变只发生在自己这一层。** 比如，删除前一层文件的操作，实际不是真的删除前一层的文件，而是仅在当前层标记为该文件已删除。在最终容器运行的时候，虽然不会看到这个文件，但是实际上该文件会一直跟随镜像。因此，在构建镜像的时候，需要额外小心，每一层尽量只包含该层需要添加的东西，任何额外的东西应该在该层构建结束前清理掉。

分层存储的特征还使得镜像的复用、定制变的更为容易。甚至可以用之前构建好的镜像作为基础层，然后进一步添加新的层，以定制自己所需的内容，构建新的镜像。

### 镜像加速器

国内从Docker Hub 拉取镜像有时会遇到困难，此时可以配置镜像加速器。Docker	官方和国 内很多云服务商都提供了国内加速器服务，例如：

- <https://registry.docker-cn.com>
- <http://hub-mirror.c.163.com>
- <https://3laho3y3.mirror.aliyuncs.com>
- <http://f1361db2.m.daocloud.io>
- <https://mirror.ccs.tencentyun.com>

#### 配置加速站点

```bash
$ mkdir -p /etc/docker
$ sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["<your accelerate address>"]
}
```

#### 修改启动脚本配置加速

```bash
# 直接修改 /usr/lib/systemd/system/docker.service 启动脚本
$ vim /usr/lib/systemd/system/docker.service
# 在dockerd后面加参数
ExecStart=/usr/bin/dockerd --registry-mirror=<your accelerate address>
```

> **注：以上操作后重启一下 Docker**

```bash
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### Docker 镜像使用

Docker运行容器前需要本地存在对应的镜像，如果本地不存在该镜像，Docker会从镜像仓库下载该镜像。

#### 查找镜像

- 我们可以登录[Docker Hub](https://hub.docker.com/)网站来搜索镜像.

- 也可以使用 `docker search` 命令来搜索镜像,搜索之后，可以`pull`我们需要的镜像到本地。

    eg:

    ```bash
    $ docker search ubuntu
    NAME                                                   DESCRIPTION                                     STARS               OFFICIAL            AUTOMA
    TED
    ubuntu                                                 Ubuntu is a Debian-based Linux operating sys…   9447                [OK]
    dorowu/ubuntu-desktop-lxde-vnc                         Docker image to provide HTML5 VNC interface …   293                                     [OK]
    rastasheep/ubuntu-sshd                                 Dockerized SSH service, built on top of offi…   212                                     [OK]
    ...

    ```

#### 获取镜像

##### 1. `docker pull`

从Docker镜像仓库获取镜像的命令是docker pull。其命令格式为：

```bash
docker pull [选项][Docker Registry 地址[:端口号]/] 仓库名[:标签]
```

具体的选项可以通过docker pull --help命令看到，这里我们说一下镜像名称的格式。

- Docker镜像仓库地址：地址的格式一般是<域名/IP>[:端口号]，默认地址是Docker Hub。
- 仓库名：如之前所说，这里的仓库名是两段式名称，即<用户名>/<软件名>。对于Docker Hub，如果不给出用户名，则默认为library，也就是官方镜像。

eg:

```bash
# 不指定Docker仓库会从Docker Hubz直接获取最新镜像
$ docker pull ubuntu
Using default tag: latest
latest: Pulling from library/ubuntu
898c46f3b1a1: Pull complete
63366dfa0a50: Pull complete
041d4cd74a92: Pull complete
6e1bee0f8701: Pull complete
Digest: sha256:017eef0b616011647b269b5c65826e2e2ebddbe5d1f8c1e56b3599fb14fabec8
Status: Downloaded newer image for ubuntu:latest
```

##### 2. `Dockerfile`构建镜像

除了直接从docker hub上直接获取镜像，我们也可以使用`Dockerfile`自定义镜像

- 利用Dockerfile构建一个标准的centos环境

```dockerfile
FROM centos:7
ENV container docker
RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == \
systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;
COPY CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo
VOLUME [ "/sys/fs/cgroup" ]
CMD ["/usr/sbin/init"]
```

构建镜像

```bash
docker build -t --name centos:v1 .
```

#### 查看镜像列表

```bash
$ docker images
REPOSITORY              TAG                 IMAGE ID            CREATED             SIZE
centos                  v1                  8820a20c5037        36 minutes ago      202MB
tensorflow/tensorflow   latest-py3          e8128de79386        8 weeks ago         1.11GB
python                  latest              2cc378c061f7        6 months ago        923MB

```

更细节的显示可以使用`docker image ls --format "{{.ID}}: {{.Repository}}"`直接列出镜像ID和仓库名,或者使用`docker image ls --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}"` 以表格等距显示.

#### 打包镜像

我们可以把制作好的镜像打包

```bash
docker save centos:v1 > centos.tar
```

#### 删除本地镜像

如果要删除本地的镜像，可以使用`docker image rm`命令，不要过先确保没有容器在使用这个镜像,其格式为：

```bash
# 其中，<镜像>可以是镜像短ID、镜像长ID、镜像名或者镜像摘要。

$ docker image rm [选项] <镜像1> [<镜像2>...]
```

#### 导出镜像

把打包好的镜像解压到任何docker环境的服务器上，都可以使用

```bash
docker load < centos.tar
```

## 运行-Container

镜像（`Image`）和容器（`Container`）的关系，就像是面向对象程序设计中的 `类` 和 `实例` 一样，镜像是静态的定义，容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等。

**容器的实质是进程** ，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的 `命名空间`。因此容器可以拥有自己的 `root` 文件系统、自己的网络配置、自己的进程空间，甚至自己的用户 ID 空间。容器内的进程是运行在一个隔离的环境里，使用起来，就好像是在一个独立于宿主的系统下操作一样。这种特性使得容器封装的应用比直接在宿主运行更加安全。也因为这种隔离的特性，很多人初学 Docker 时常常会混淆容器和虚拟机。

前面讲过镜像使用的是分层存储，容器也是如此。 **每一个容器运行时，是以镜像为基础层，在其上创建一个当前容器的存储层，** 我们可以称这个为容器运行时读写而准备的存储层为 **容器存储层** 。

容器存储层的生存周期和容器一样，容器消亡时，容器存储层也随之消亡。因此， **任何保存于容器存储层的信息都会随容器删除而丢失** 。

按照 Docker 最佳实践的要求，容器不应该向其存储层内写入任何数据，容器存储层要保持无状态化。 **所有的文件写入操作，都应该使用 [数据卷（Volume）](####方式1：数据卷（推荐）)、或者[绑定宿主目录](####方式2：挂载主机目录)** ，在这些位置的读写会跳过容器存储层，直接对宿主（或网络存储）发生读写，其性能和稳定性更高。

数据卷的生存周期独立于容器，容器消亡，数据卷不会消亡。因此，使用数据卷后，容器删除或者重新运行之后，数据却不会丢失。

### Docker容器使用

#### 启动容器

以上面的ubuntu为例，如果我们打算启动里面的bash并且进行交互式操作的话，可以执行下面的命令。

```bash
$ docker run -it --rm -p 5000:5000 \
ubuntu:latest \
bash

root@c94c6b39d067:/# cat /etc/os-release
NAME="Ubuntu"
VERSION="18.04.2 LTS (Bionic Beaver)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 18.04.2 LTS"
VERSION_ID="18.04"
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
VERSION_CODENAME=bionic
UBUNTU_CODENAME=bionic
```

- -it：这是两个参数，一个是`-i`：交互式操作，一个是`t`终端。我们这里打算进入bash执行一些命令并查看返回结果，因此我们需要交互式终端。

- --rm：这个参数是说容器退出后随之将其删除。默认情况下，为了排障需求，退出的容 器并不会立即删除，除非手动docker rm。使用 --rm可以避免浪费空间。

- -p：通过 -p 参数来设置不一样的端口,上面我们设置容器端口5000映射到宿主机端口号5000

#### 查看容器

```bash
# docker ps查看正在运行的容器，docker ps -a 查看所有存在的容器包括停止的
$ docker ps
CONTAINER ID        IMAGE                        COMMAND                  CREATED             STATUS              PORTS               NAMES
7b715223170c        python                       "python3"                4 months ago        Up 4 months                             hardcore_agnesi
```

#### 容器日志查看

为了获取容器的日志信息，可以使用`docker logs`命令

```bash
docker logs [NAME]/[CONTAINER ID]
```

#### 容器详细信息

docker提供了一个`docker inspect`命令，该命令会返回容器的详细配置信息。包括：名称、命令、运行状态、网络配置等。

```bash
docker inspect [NAME]/[CONTAINER ID]
```

#### 开启容器

通过docker start来启动之前已经停止的容器

```bash
docker start [NAME]/[CONTAINER ID]
```

#### 停止容器

```bash
# 将容器退出
docker stop [NAME]/[CONTAINER ID]

# 强制停止一个容器
docker kill [NAME]/[CONTAINER ID]
```

#### 删除容器

容器终止后，在需要的时候可以重新启动，确定不需要了，可以进行删除操作。

```bash
# 不能删除一个正在运行的容器，会报错,需要先停止容器。
docker rm [NAME]/[CONTAINER ID]
```
