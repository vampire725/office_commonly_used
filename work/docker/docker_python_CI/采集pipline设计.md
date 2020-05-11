# 采集持续集成pipeline设计

- [采集持续集成pipeline设计](#采集持续集成pipeline设计)
  - [初始镜像准备模板（dockerfile）](#初始镜像准备模板dockerfile)
  - [二级镜像模板](#二级镜像模板)
  - [python项目pipeline模板](#python项目pipeline模板)

1. 每次都下载项目需要的包很不现实，所以要做两个Dockerfile
   1. 一个是初始镜像，项目需要打造的环境等(包括下载的常用的包等等)
   2. 一个是后面项目修改，需要的Dockerfile
      1. 修改代码重新上传的代码等等
2. 设计镜像生成流水线pipeline

## 初始镜像准备模板（dockerfile）

- dockerfile

```dockerfile
# 基础镜像为python
FROM python:3

# 切换到项目工作目录下
WORKDIR /usr/src/app

# 打包需要下载的包并进行pip安装
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

# 如果使用其他途径引进的包，可以使用类似下面的命令进行添加
# COPY packages /usr/local/lib/python3.7/dist-packages/

COPY . .

# 运行启动项目的文件
CMD [ "python", "./your-daemon-or-script.py" ]
```

- 生成镜像

```bash
docker build -t ZKR_IMAGE_NAME:1 .
```

## 二级镜像模板

- 二级镜像是基于一级基础镜像生成的

```dockerfile

FROM ZKR_IMAGE_NAME:1

# 切换到项目工作目录下
WORKDIR /usr/src/app

# 跟上面内容类似，不过需要下载的包应该没有一级镜像需要下载的那么多
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

# 如果使用其他途径引进的包，可以使用类似下面的命令进行添加
# COPY PACKAGES /usr/local/lib/python3.7/dist-packages/

COPY . .

# 运行启动项目的文件
CMD [ "python", "./your-daemon-or-script.py" ]
```

- 生成镜像

```bash
docker build -t ZKR_IMAGE_NAME:latest .
```

## python项目pipeline模板

```yaml
image: docker:stable
services:
  - docker:dind
variables:
  DOCKER_HOST: tcp://192.168.129.13:2376
  DOCKER_DRIVER: overlay2
  CI_REGISTRY_USER: admin
  CI_REGISTRY_PASSWORD: asd1234
  CI_REGISTRY: 60.28.140.210:10167
stages:
  - build
  - test
  - release
build:
  stage: build
  script:
    - cp domain.crt /etc/ssl/certs/ca-certificates.crt
    - docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY  # 登录私有仓库
    - docker build --pull -t $CONTAINER_TEST_IMAGE . # 创建测试镜像，$CONTAINER_TEST_IMAGE作为环境变量可以提前在项目的环境里设置。
    - docker push $CONTAINER_TEST_IMAGE # 上传测试镜像

test1:
  stage: test
  script:
    - docker pull $CONTAINER_TEST_IMAGE
    - docker run $CONTAINER_TEST_IMAGE /script/to/run/tests # 任务可以并行执行，这里的test可以测试两个例子，测试我们新建立的镜像是否可用

test2:
  stage: test
  script:
    - docker pull $CONTAINER_TEST_IMAGE
    - docker run $CONTAINER_TEST_IMAGE /script/to/run/another/test

release-image:
  stage: release  # 如果上面的测试镜像可用，则分支可以提交merge请求，告知master，master触发构建，发布镜像
  script:
    - docker pull $CONTAINER_TEST_IMAGE  
    - docker tag $CONTAINER_TEST_IMAGE $CONTAINER_RELEASE_IMAGE
    - docker push $CONTAINER_RELEASE_IMAGE
  only:
    - master

```
