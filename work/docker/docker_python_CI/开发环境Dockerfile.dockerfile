FROM python:3.6-buster

RUN echo >/etc/apt/sources.list "\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-updates main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian/ buster-backports main contrib non-free\n\
deb https://mirrors.tuna.tsinghua.edu.cn/debian-security buster/updates main contrib non-free\n\
" && \
apt update && apt install -y sudo vim openssh-server openssh-client &&\
mkdir -p /var/run/sshd &&\
echo 'root:asd123' | chpasswd &&\
sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config &&\
sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd &&\
echo "export VISIBLE=now" >> /etc/profile

RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple pip -U &&\
pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple

EXPOSE 22

CMD /usr/sbin/sshd && /bin/bash
