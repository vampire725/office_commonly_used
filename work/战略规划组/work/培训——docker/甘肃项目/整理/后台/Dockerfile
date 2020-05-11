FROM tomcat:alpine

COPY  JQWJ-8082.zip /usr/
WORKDIR /usr/
RUN unzip JQWJ-8082.zip \
    && rm JQWJ-8082.zip

WORKDIR /usr/JQWJ-8082/bin
RUN chmod -R 755 shutdown.sh startup.sh version.sh catalina.sh
EXPOSE 8082

cmd ["./startup.sh"]
