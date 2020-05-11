# python3.7.3 之deadsnake搭建过程

## 添加deadsnake的源

sudo apt install python-software-properties
sudo apt update
sudo apt install software-properties-common
sudo apt update
sudo add-apt-repository ppa:deadsnakes/ppa
rm -rf /usr/bin/python3
ln -s python3.7 /usr/bin/python3
apt install python3-pip
apt install python3.7-dev

## yum安装

yum -y groupinstall "Development tools"
yum -y install zlib-devel openssl-devel

yum install libffi-devel -y

wget https://www.python.org/ftp/python/3.7.1/Python-3.7.1.tar.xz

tar -xvJf Python-3.7.1.tar.xz
cd Python-3.7.1
./configure --prefix=/usr/local/bin/python3
sudo make
sudo make install

sudo ln -s /usr/local/bin/python3/bin/python3 /usr/bin/python3
sudo ln -s /usr/local/bin/python3/bin/pip3 /usr/bin/pip3

## Ubuntu编译安装

wget https://www.python.org/ftp/python/3.8.0/Python-3.8.0a2.tgz
tar -xvzf Python-3.8.0a2.tgz
cd Python-3.8.0a2
sudo apt update
sudo apt upgrade
sudo apt dist-upgrade
sudo apt install libbz2-dev libncurses5-dev libgdbm-dev liblzma-dev libsqlite3-dev libssl-dev openssl tk-dev uuid-dev libreadline-dev libffi-dev
./configure --prefix=/usr/local/python3
make
sudo make install
sudo rm -rf /usr/bin/python3
sudo rm -rf /usr/bin/pip3
sudo ln -s /usr/local/python3/bin/python3.8 /usr/bin/python3
sudo ln -s /usr/local/python3/bin/pip3.8 /usr/bin/pip
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple  --upgrade pip