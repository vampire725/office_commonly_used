# stack之gitlab搭建

- **potainer不能使用，因为这个compose文件版本是3.6,现用potainer版本太低不支持**

```yml
version: "3.6"
services:
  gitlab:
    image: gitlab/gitlab-ce:latest
    ports:
      - "26:22"
      - "8083:10163"    # 这里注意了，要改端口的话，这里映射也得改，之前默认80，现在里边设定外网端口，所以映射也应该映射设定的外网端口
      - "443:443"
    volumes:
      - /opt/drone_test/gitlab/config:/etc/gitlab
      - /opt/drone_test/gitlab/logs:/var/log/gitlab
      - /opt/drone_test/gitlab/data:/var/opt/gitlab
    environment:
      GITLAB_OMNIBUS_CONFIG: "from_file('/omnibus_config.rb')"
    configs:
      - source: gitlab
        target: /omnibus_config.rb
    secrets:
      - gitlab_root_password
    networks:
      - default
    deploy:
      placement:
        constraints: [node.role == manager]
      mode: replicated
      replicas: 1
configs:
  gitlab:
    file: /opt/drone_test/gitlab/gitlab.rb
secrets:
  gitlab_root_password:
    file: /opt/drone_test/gitlab/root_password.txt
networks:
  default:
    external:
      name: drone_test_net
```

## 新型的docker-compose编写形式，docker stack 部署命令

`docker stack deploy --compose-file docker-compose.yml git`

## 相关文件

### /opt/drone_test/gitlab/root_password.txt

- 虽然设定了密码，但是登录时还会出现错误，不知道为什么，不过没关系，直接进容器改就可以了。

```txt
asd123456
```

如果密码不好用，进容器修改密码

```bash
gitlab-rails console production
user = User.where(id: 1).first
user.password = 'asd123456'
user.password_confirmation = 'asd123456'
user.save!
```

### /opt/drone_test/gitlab/gitlab.rb

```rb
external_url 'http://60.28.140.210:10163/'
gitlab_rails['initial_root_password'] = File.read('/run/secrets/gitlab_root_password')
```
