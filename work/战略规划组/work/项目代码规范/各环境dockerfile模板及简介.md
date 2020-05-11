# 各环境dockerfile模板以及简介

## 开发环境Dockerfile

专门为开发代码打造的一个环境镜像，由此镜像生成容器开放ssh端口与本地编辑器进行远程连接。需要在开发前手动构建。

```dockerfile
# pyenv-code-image
FROM python:3.6-buster

# 配置Debian软件源为国内清华源
RUN echo >/etc/apt/sources.list "\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-updates main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-backports main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian-security buster/updates main contrib non-free\n\
" && \
# 安装sudo、vim、ssh等，配置ssh设置root用户密码等使其能够正常使用
apt update && apt install -y sudo vim openssh-server openssh-client &&\
mkdir -p /var/run/sshd &&\
echo 'root:asd123' | chpasswd &&\
sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config &&\
sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd &&\
echo "export VISIBLE=now" >> /etc/profile

# 设置pip源为国内清华源
RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

EXPOSE 22

# 启动镜像生成容器时执行的命令
CMD /usr/sbin/sshd && /bin/bash
```

## 项目测试镜像

项目测试镜像主要包括项目需要的整体环境（安装包等）以及单元测试和集成测试所需要的环境。需要在代码开发完成以后手动构建。

```dockerfile
# pyenv-test-[project]-image
FROM python:3.6-buster

# 设置pip源为国内清华源
RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

# cd到项目目录下，如果镜像内没有此目录会自动创建
WORKDIR /usr/src/app/neo4j-flask

# 设置pythonpath为项目根目录
ENV PYTHONPATH=/usr/src/app/neo4j-flask

# requirements.txt中包括项目所需的整体环境以及测试需要的环境
COPY requirements.txt .
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt
CMD [ "python", "src/app.py" ]
```

## 项目镜像

经过测试后，此dockerfile打造生产级别的项目镜像，用于在正式环境部署服务。在CI/CD环境里用此dockerfile实现自动构建镜像。

```dockerfile
# pyenv-[production]-image
FROM 192.168.129.111:9897/python:3.6-buster
RUN pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple
WORKDIR /usr/src/app/neo4j-flask
ENV PYTHONPATH=/usr/src/app/neo4j-flask
COPY . .
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt

CMD [ "python", "src/app.py" ]

```
