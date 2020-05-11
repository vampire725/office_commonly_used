# Neo4j使用指南

## 环境

|    环境    |      本地地址/端口      |    映射地址/端口    |
| :--------: | :---------------------: | :-----------------: |
|   可视化   |   192.168.129.13:7674   | 60.28.140.210:10584 |
|   数据库   |   192.168.129.13:7687   | 60.28.140.210:10585 |
| docker容器 | 172.17.0.5/kind_wescoff |   192.168.129.13    |

## 启动

### 启动neo4j数据库

```bash
/$NEOHOME# ./bin/neo4j start
```

### 启动cypher-shell

```bash
/$NEOHOME# ./bin/cypher-shell
username: neo4j
password: *********
Connected to Neo4j 3.5.5 at bolt://localhost:7687 as user neo4j.
Type :help for a list of available commands or :exit to exit the shell.
Note that Cypher queries must end with a semicolon.
neo4j>

```

## Neo4j使用(可视化和Cyper-shell一样的用法)

1. 基本的增删改查
   - 插入节点

    ```bash
      CREATE (n:Person {name : 'Andres'});
      # n:Person表示为n这个节点创建标签 {}里是n节点的属性
    ```

    - 插入边

    ```bash
     MATCH (a:Person),(b:Person)
     WHERE a.name = 'Node A' AND b.name = 'Node B'
    CREATE (a)-[r:Follow]->(b);
    # 插入一条a到b的有向边，并且边的类型也就是我们常说的边的标签为Follow; 其中使用MATCH和WHERE查询和筛选指定的节点
    ```

    - 更新节点
  
    ```bash
    MATCH (n:Person { name: 'Andres' })
    SET n.name = 'Taylor';
    # 更新一个Person类别的节点，设置新的name。
    ```

    - 删除节点

    ```bash
    MATCH (n:Person { name:'Taylor' })
    DETACH DELETE n;
    # 删除符合标签为person，名字为Taylor的所有节点
    ```

    - 删除边

    ```bash
    MATCH (a:Person)-[r:Follow]->(b:Person)
    WHERE a.name = 'Node A' AND b.name = 'Node B'
    DELETE r;
    ```

    - MATCH 查询

    ```bash
    MATCH (tom {name: "Tom Hanks"}) RETURN tom
    # 使用MATCH查询，使用RETURN返回结果
    ```

    ```bash
    MATCH (people:Person) RETURN people.name LIMIT 10
    # 可以选择返回的内容并且限制返回的节点数量
    ```

    ```bash
    MATCH (nineties:Movie) 
    WHERE nineties.released >= 1990 AND nineties.released < 2000 
    RETURN nineties.title
    # 使用and做多条件限制
    ```
