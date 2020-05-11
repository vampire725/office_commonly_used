FROM appium/appium:latest

COPY jiqiwangjundiceng.zip /home/
WORKDIR /home
RUN unzip -d jiqiwangjundiceng jiqiwangjundiceng.zip \
    && rm jiqiwangjundiceng.zip
WORKDIR /home/jiqiwangjundiceng/bin

EXPOSE 8010
EXPOSE 8011
EXPOSE 8012

CMD ["java", "-jar", "jqwj_server.jar"]
