FROM appium/appium:latest

COPY jiqiwangjundiceng.zip /home/
WORKDIR /home/node
RUN rpm -ivh xorg-x11-server-Xvfb-1.15.0-7.el7.x86_64.rpm
WORKDIR /home
RUN unzip -d jiqiwangjundiceng jiqiwangjundiceng.zip \
    && rm jiqiwangjundiceng.zip
WORKDIR /home/jiqiwangjundiceng/bin

EXPOSE 8010
EXPOSE 8011
EXPOSE 8012

CMD ["java", "-jar", "jqwj_server.jar"]
