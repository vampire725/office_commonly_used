# drone_gogs_stack搭建

- 简单介绍一下这个yml,里面MySQL是必须创建的，因为gogs和drone需要在MySQL里存放数据，可以换做其他数据库。

```yml
version: '3'
services:
  mysql_server:
    image: mysql
    volumes:
      - /opt/drone_test/mysql/mysqlVolume:/var/lib/mysql
      - /opt/drone_test/mysql/logs:/logs
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=asd1234
      - MYSQL_DATABASE=drone_test,gogs,drone
    networks:
      - drone_test_net
    deploy:
      placement:
        constraints: [node.role == manager]
      mode: replicated
      replicas: 1

  gogs:
    image: gogs/gogs
    depends_on:
      - "dt_mysql_server"
    volumes:
      - /opt/drone_test/gogs:/data
    ports:
      - "8083:22"
      - "8085:3000"
    networks:
      - drone_test_net
    deploy:
      placement:
        constraints: [node.role == manager]
      mode: replicated
      replicas: 1

  drone_server:
    image: drone/drone:1.1.0
    ports:
      - 8089:80
      - 9000:9000
    volumes:
      - /opt/drone_test/drone:/var/lib/drone/
    depends_on:
      - "dt_mysql_server"
    environment:
      - DRONE_LOGS_DEBUG=true
      - DRONE_SERVER_HOST=dt_drone_server
      - DRONE_SERVER_PROTO=http
      - DRONE_GOGS=true
      - DRONE_GOGS_SERVER=http://60.28.140.210:10165
      - DRONE_DATABASE_DRIVER=mysql
      - DRONE_DATABASE_DATASOURCE=root:asd1234@tcp(dt_mysql_server:3306)/drone_test?parseTime=true
      - DRONE_RPC_SECRET=asd1234
      - DRONE_USER_CREATE=username:gog_user,admin:true

    networks:
      - drone_test_net
    deploy:
      placement:
        constraints: [node.role == manager]
      mode: replicated
      replicas: 1


  drone_agent:
    image: drone/agent
    depends_on:
      - "dt_drone_server"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      - DRONE_LOGS_DEBUG=true
      - DRONE_RPC_SERVER=http://dt_drone_server
      - DRONE_RPC_SECRET=asd1234
    networks:
      - drone_test_net
    deploy:
      placement:
        constraints: [node.role == manager]
      mode: replicated
      replicas: 1
networks:
  drone_test_net:
    external:
      name: drone_test_net
