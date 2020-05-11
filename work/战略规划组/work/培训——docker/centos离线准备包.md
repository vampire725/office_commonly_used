# 安装过程

```bash
# run命令
[root@xnode208 3] docker run -d -it --rm --name centos01 -v /sys/fs/cgroup:/sys/fs/cgroup:ro --privileged centos7:v1 /usr/sbin/init
7ea888357df915e95a54c79e59f60ed66841265e67552ad985bc9073a60da1f2

更新源

# 下载依赖到yum包里
 yum install --downloadonly --downloaddir=/yum-rpm/ -y yum-utils \
                         device-mapper-persistent-data \
                         lvm2
# 本地安装依赖
yum localinstall -y yum-rpm/*.rpm

# 添加docker源
yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

# 下载docker的rpm包
yum install --downloadonly --downloaddir=/docker-rpm/ -y docker-ce docker-ce-cli containerd.io

# 本地安装docker
yum localinstall --skip-broken -y docker-rpm/*.rpm
yum localinstall yum-rpm/*.rpm
```

rpm -evh --nodeps yum-utils  device-mapper-persistent-data lvm2


错误：软件包：7:lvm2-2.02.180-10.el7_6.8.x86_64 (/lvm2-2.02.180-10.el7_6.8.x86_64)
          需要：libdevmapper.so.1.02(DM_1_02_138)(64bit)
错误：软件包：7:lvm2-2.02.180-10.el7_6.8.x86_64 (/lvm2-2.02.180-10.el7_6.8.x86_64)
          需要：libdevmapper.so.1.02(DM_1_02_141)(64bit)
错误：软件包：7:lvm2-libs-2.02.180-10.el7_6.8.x86_64 (/lvm2-libs-2.02.180-10.el7_6.8.x86_64)
          需要：libdevmapper.so.1.02(DM_1_02_138)(64bit)
错误：软件包：7:device-mapper-event-1.02.149-10.el7_6.8.x86_64 (/device-mapper-event-1.02.149-10.el7_6.8.x86_64)
          需要：device-mapper = 7:1.02.149-10.el7_6.8
          已安装: 7:device-mapper-1.02.135-1.el7.isoft.5.x86_64 (@updates)
              device-mapper = 7:1.02.135-1.el7.isoft.5
          可用: 7:device-mapper-1.02.107-5.el7.isoft.x86_64 (base)
              device-mapper = 7:1.02.107-5.el7.isoft
          可用: 7:device-mapper-1.02.107-5.el7.isoft.5.x86_64 (updates)
              device-mapper = 7:1.02.107-5.el7.isoft.5
          可用: 7:device-mapper-1.02.135-1.el7.isoft.1.x86_64 (updates)
              device-mapper = 7:1.02.135-1.el7.isoft.1
          可用: 7:device-mapper-1.02.135-1.el7.isoft.2.x86_64 (updates)
              device-mapper = 7:1.02.135-1.el7.isoft.2
          可用: 7:device-mapper-1.02.135-1.el7.isoft.3.x86_64 (updates)
              device-mapper = 7:1.02.135-1.el7.isoft.3
          可用: 7:device-mapper-1.02.135-1.el7.isoft.4.x86_64 (updates)
              device-mapper = 7:1.02.135-1.el7.isoft.4
错误：软件包：7:lvm2-libs-2.02.180-10.el7_6.8.x86_64 (/lvm2-libs-2.02.180-10.el7_6.8.x86_64)
          需要：libdevmapper.so.1.02(DM_1_02_141)(64bit)
 您可以尝试添加 --skip-broken 选项来解决该问题
** 发现 8 个已存在的 RPM 数据库问题， 'yum check' 输出如下：
anaconda-core-21.48.22.56-5.el7.isoft.3.x86_64 有缺少的需求 yum-utils >= ('0', '1.1.11', '3')
ipa-client-4.5.0-22.el7.isoft.x86_64 有已安装冲突 freeipa-client: ipa-client-4.5.0-22.el7.isoft.x86_64
ipa-client-4.5.0-22.el7.isoft.x86_64 有已安装冲突 freeipa-admintools: ipa-client-4.5.0-22.el7.isoft.x86_64
ipa-client-common-4.5.0-22.el7.isoft.noarch 有已安装冲突 freeipa-client-common: ipa-client-common-4.5.0-22.el7.isoft.noarch
ipa-common-4.5.0-22.el7.isoft.noarch 有已安装冲突 freeipa-common: ipa-common-4.5.0-22.el7.isoft.noarch
ipa-python-compat-4.5.0-22.el7.isoft.noarch 有已安装冲突 freeipa-python-compat: ipa-python-compat-4.5.0-22.el7.isoft.noarch
libvirt-daemon-driver-storage-logical-3.2.0-14.el7.isoft.9.x86_64 有缺少的需求 lvm2
1:python-blivet-0.61.15.37-1.el7.isoft.1.noarch 有缺少的需求 lvm2 >= ('0', '2.02.99', None)
