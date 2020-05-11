# drone_gitlab之stack搭建

- 前提要搭建gitlab获取下面参数需要的clientid和secret

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
      - MYSQL_DATABASE=drone_test
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
      - 8085:80
      - 9000:9000
    volumes:
      - /opt/drone_test/drone:/var/lib/drone/
      - /opt/drone_test/drone/data:/data
      - /var/run/docker.sock:/var/run/docker.sock
    depends_on:
      - "dt_mysql_server"
    environment:
      - DRONE_GIT_ALWAYS_AUTH=false
      - DRONE_GITLAB_SERVER=http://60.28.140.210:10163
      - DRONE_GITLAB_CLIENT_ID=1bac2232b5edf9913676cbb3e446a494c77bdad391ac228d9d942dbad8d97433
      - DRONE_GITLAB_CLIENT_SECRET=5355fdc2650cd89393688dd3eb3bc4fc9cefe964c914200c1b6c8912a964e79c
      - DRONE_AGENTS_ENABLED=true
      - DRONE_TLS_AUTOCERT=false
      - DRONE_LOGS_DEBUG=true
      - DRONE_SERVER_HOST=60.28.140.210:10165
      - DRONE_SERVER_PROTO=http
      - DRONE_DATABASE_DRIVER=mysql
      - DRONE_DATABASE_DATASOURCE=root:asd1234@tcp(dt_mysql_server:3306)/drone_test?parseTime=true
      - DRONE_RPC_SECRET=asd1234
      - DRONE_USER_CREATE=username:root,admin:true

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
```
