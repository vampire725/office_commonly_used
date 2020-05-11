# python3.7.3 之deadsnake搭建过程

## 添加deadsnake的源

`sudo add-apt-repository ppa:deadsnakes/ppa`

## 更新apt

`sudo apt update`

## 删除之前与3.6建立的软连接

`rm -rf /usr/bin/python3`

## 建立python3.7与python3之间的软连接

`ln -s python3.7 /usr/bin/python3`

## 安装pip3

`apt install python3-pip`

## 安装python3.7-dev

`apt install python3.7-dev`
