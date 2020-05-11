# Docker入门组件-资源利用层(基础)

- [资源利用层](#资源利用层)
  - [存储卷——volumes](#存储卷volumes)
    - [数据卷使用](#数据卷使用)
      - [创建数据卷](#创建数据卷)
      - [查看数据卷](#查看数据卷)
      - [查看数据卷信息](#查看数据卷信息)
      - [使用数据卷的容器](#使用数据卷的容器)
      - [删除数据卷](#删除数据卷)
  - [网络——network](#网络network)
    - [Docker-network使用](#Docker-network使用)
      - [新建网络](#新建网络)
      - [连接容器](#连接容器)

## 存储卷——volumes

数据卷是一个可供一个或多个容器使用的特殊目录，可以提供很多有用的特性：

- 数据卷可以在容器之间共享和重用

- 对数据卷的修改会立马生效

- 对数据卷的更新，不会影响镜像

- 卷会一直存在，直到没有容器使用

> **注意：数据卷 的使用，类似于 Linux 下对目录或文件进行mount，镜像中的被指定为挂载点的目录中的文件会隐藏掉，能显示看的是挂载的数据卷。**

### 数据卷使用

#### 创建数据卷

```bash
>
$ docker volume create my-vol
```

#### 查看数据卷

```bash
$ docker volume ls
my-vol
```

#### 查看数据卷信息

```bash
$ docker volume inspect my-vol
[
    {
        "Driver": "local",
        "Labels": {},
        "Mountpoint": "/var/lib/docker/volumes/my-vol/_data",
        "Name": "my-vol",
        "Options": {},
        "Scope": "local"
    }
]
```

#### 使用数据卷的容器

在用 docker run 的时候，增加 `--mount` 参数来使用数据卷,以启动redis为例，这里我们启动redis并且开启aof持久化：

```bash
docker run -d \
    --name redis \
    --mount source=my-vol,target=/data \
    # -v my-vol:/data \
    redis \
    redis-server --appendonly yes
```

在这里redis产生的数据（`/data`目录下）被挂载到数据卷`my-vol`中。

我们也可以使用`-v`或者`--volume`语法，但是[官方建议](https://docs.docker.com/storage/volumes/#choose-the--v-or---mount-flag)尽量使用`--mount`。

同样使用`inspect`语法，我们可以查看redis容器的信息：

```bash
docker inspect redis

# 会看到类似如下内容
"Mounts": [
    {
        "Type": "volume",
        "Name": "my-vol",
        "Source": "/var/lib/docker/volumes/my-vol/_data",
        "Destination": "/data",
        "Driver": "local",
        "Mode": "",
        "RW": true,
        "Propagation": ""
    }
],
```

#### 删除数据卷

数据卷是被设计用来持久化数据的，它的生命周期独立于容器，Docker不会在容器被删除后自动删除数据卷，并且也不存在垃圾回收这样的机制来处理没有任何容器引用的数据卷。如果需要在删除容器的同时移除数据卷。可以在删除容器的时候使用`docker rm -v`这个命令。

```bash
>
$ docker volume rm my-vol
```

## 网络——network

随着Docker网络的完善，为了方便容器间通讯，将容器加入自定义的Docker网络来连接多个容器。

容器中可以运行一些网络应用，要让外部也可以访问这些应用，可以通过 -P 或 -p 参数来指定端口映射。

> 当使用 -P 标记时，Docker 会随机映射一个 49000~49900 的端口到内部容器开放的网络端口。

### Docker-network使用

#### 新建网络

```bash
>
$ docker network create -d bridge my-net
```

#### 连接容器

```bash
# 运行一个容器并连接到新建的`my-net`网络
$ docker run -it --rm --name busybox1 --network my-net busybox sh

# 再运行一个容器并加入到my-net网络
$ docker run -it --rm --name busybox2 --network my-net busybox sh
```

下面通过`ping`来证明busybox1容器和busybox2容器建立了互联关系。

```bash
# 在busybox1容器输入以下命令
$ ping busybox2
PING busybox2 (172.19.0.3):	56	data bytes
64 bytes from 172.19.0.3: seq=0	ttl=64	time=0.072	ms 
64 bytes from 172.19.0.3: seq=1	ttl=64	time=0.118	ms
```

由此，busybox1容器和busybox2容器建立了互联关系。
