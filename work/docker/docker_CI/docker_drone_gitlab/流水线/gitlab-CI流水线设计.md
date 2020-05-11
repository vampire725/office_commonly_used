# gitlab-CI 流水线设计

## 思路

1. 使用Docker来作为git runner的excutor(执行器)，这样在每个Job完成后都会清理build环境。
2. 每一个服务都作为一个项目，都有各自的git-runner-CI，而每一个项目都有master主分支和其他分支
    1、（或者不同功能的服务代码用不同分支来区分表示并且分别写CI job执行构建、测试、部署的工作，这个待定）
3. master主分支只接受合并请求不允许任何人push,控制发布正式环境。除自动触发外，也可以配置手动点击按钮部署
4. 将CI分成before_script、build、test、release、deploy五个阶段，其中release和deploy阶段可以设置为master触发，保证环境代码的准确性。

## 流程

在构建流程的时候怎么也想不明白，如何选择不同的服务器进行部署

- before_script

```yml
- docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY  # 首先登录私有仓库
```

- build

```yml
build:
  stage: build
  script:
    - docker build --pull -t $CONTAINER_TEST_IMAGE . # 创建测试镜像，$CONTAINER_TEST_IMAGE作为环境变量可以提前在项目的环境里设置。
    - docker push $CONTAINER_TEST_IMAGE # 上传测试镜像
```

- test

```yml
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
```

- release

```yml
release-image:
  stage: release  # 如果上面的测试镜像可用，则分支可以提交merge请求，告知master，master触发构建，发布镜像 
  script:
    - docker pull $CONTAINER_TEST_IMAGE  
    - docker tag $CONTAINER_TEST_IMAGE $CONTAINER_RELEASE_IMAGE
    - docker push $CONTAINER_RELEASE_IMAGE
  only:
    - master
```

- deploy

```yml
deploy:
  stage: deploy   # 最后部署，也是由master触发构建，
  script:
    - ./deploy.sh
  only:
    - master
```

## 问题

1. 想来想去还是有些不明白，我们已经有了完整的docker环境，还要docker in docker 。感觉有些多此一举，是不是用shell或者docker-machine、docker-ssh等更好，因为更方便连接别的服务器，可以直接部署在别的服务器上。

2. 问题1主要还是没弄明白docker in docker 是如何与别的服务器进行连接。

3. 镜像的更新发布问题，是我们每次run一个新的容器呢，还是为服务每次更新镜像。

4. 问题3有些提醒我，每次，创建完镜像以后，可以生成stack，写脚本如果有stack就更新服务镜像，如果没有，就生成。
