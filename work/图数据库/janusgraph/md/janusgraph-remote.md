# 远程连接janusgraph并创建混合索引

## 环境
|        环境         |             内容              |
| :-----------------: | :---------------------------: |
|        版本         |   janusgraph-0.3.1-hadoop2    |
|    内网地址/端口    |      192.168.129.13/8082      |
|    外网地址/端口    |      60.28.140.210/10503      |
| gremlin连接配置文件 | [remote.yaml](###remote.yaml) |

### remote.yaml

```yaml
hosts: [60.28.140.210]
port: 10503
serializer: { className: org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0, config: { serializeResultToString: true }}
```

## 步骤
1. 进入本机Janusgraph中的gremlin客户端

    ```bash

    [root@xnode208 janusgraph-0.3.1-hadoop2] ./bin/gremlin.sh

            \,,,/
            (o o)
    -----oOOo-(3)-oOOo-----
    SLF4J: Class path contains multiple SLF4J bindings.
    SLF4J: Found binding in [jar:file:/opt/opt/janusgraph-0.3.1-hadoop2/lib/slf4j-log4j12-1.7.12.jar!/org/slf4j/impl/StaticLoggerBinder.class]
    SLF4J: Found binding in [jar:file:/opt/opt/janusgraph-0.3.1-hadoop2/lib/logback-classic-1.1.2.jar!/org/slf4j/impl/StaticLoggerBinder.class]
    SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
    SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
    plugin activated: janusgraph.imports
    plugin activated: tinkerpop.server
    plugin activated: tinkerpop.utilities
    14:15:03 WARN  org.apache.hadoop.util.NativeCodeLoader  - Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
    plugin activated: tinkerpop.hadoop
    plugin activated: tinkerpop.spark
    plugin activated: tinkerpop.tinkergraph
    gremlin>

    ```
    2. 在gremlin客户端通过remote.yaml远程连接janusgraph
    
    ```bash
    gremlin> :remote connect tinkerpop.server conf/remote.yaml session
    ==>Configured 60.28.140.210/60.28.140.210:10503-[664079b5-cffc-42ef-b80d-5cbf6977b3ff]
    gremlin> :remote console
    ==>All scripts will now be sent to Gremlin Server - [60.28.140.210/60.28.140.210:10503]-[664079b5-cffc-42ef-b80d-5cbf6977b3ff] - type ':remote console' to return to local
    mode

    # 查看是否连接成功
    gremlin> g
    ==>graphtraversalsource[standardjanusgraph[hbase:[newnode10]], standard]
    gremlin> :> graph
    ==>standardjanusgraph[hbase:[newnode10]]
    gremlin> graph
    ==>standardjanusgraph[hbase:[newnode10]]

    ```   

    3. 建立索引

    ```bash

    # 为属性name建立混合索引

    gremlin> mgmt = graph.openManagement()
    ==>org.janusgraph.graphdb.database.management.ManagementSystem@40170ee9
    gremlin> name = mgmt.getPropertyKey('name')
    ==>name
    gremlin> mgmt.buildIndex('nameMixed', Vertex.class).addKey(name, Mapping.STRING.asParameter()).buildMixedIndex("search")
    ==>nameMixed

    # 提交
    gremlin> mgmt.commit()
    gremlin> graph.tx().commit()


    ```



|                     Janus0.3  改进                     |                        Janus0.3  bug                         |
| :----------------------------------------------------: | :----------------------------------------------------------: |
|           添加了对kerberized solr实例的支持            | JanusGraph 0.3.0创建了不正确的ConfigurationManagementGraph索引 |
|      修复JanusGraphGremlinPlugin中的类和方法导入       |          动态遍历源未添加到JanusGraphManager映射中           |
|  动态创建新图时，在JanusGraphManager中更新遍历源地图   |         gremlin-python服务器配置抛出ScriptException          |
|       修复了ConfigurationManagementGraph中的错误       |       ManagementLogger尝试查找TinkerPop的GraphManager        |
|                创建一个HBase 2配置文件                 |                                                              |
|        添加使用JanusGraph构建的项目[skip   ci]         |                                                              |
|          在ElasticSearchIndex中使用自定义属性          |                                                              |
|                  合并分支0.2到主节点CTR                  |                                                              |
| 使用JanusGraphManagerUtility来避免NoClassDefFoundError |                                                              |
|            用下载替换Gitter和SO徽章[跳过ci]            |                                                              |
|                    合并为主文件修正                    |                                                              |


|                 janus0.2改进                 |                         Janus0.2bug                          |
| :------------------------------------------: | :----------------------------------------------------------: |
|     修复空DB中新创建的索引的REINDEX延迟      |          0.2.2 StorageBackend版本与0.2.1 bug不兼容           |
|     修复GremlinServer StackOverflowError     |          当索引限制为新标签 错误时，不需要重新索引           |
|           避免不必要的重新索引操作           | SchemaAction,在空DB中新创建的索引上的REINDEX会导致3分钟的延迟 |
|      Cassandra Privilege“在桌面上创建”       |               GremlinServer StackOverflowError               |
| Pull Request审查和批准政策文件的更新[ski ... | 当通过http api bug获取Vertex属性时，Gremlin-Server提供stackoverflow |
|   [skip ci]修正了以顶点为中心的doc中的错误   |          Travis没有捕获当前正在 测试的所有编译错误           |
|             添加版本的下拉列表：             |                                                              |
|           文档部署方案  [skip ci]            |                                                              |
|           更新OLAP文档[skip   ci]            |                                                              |
|   即使不进行覆盖扫描，也可以运行CI批量编译   |                                                              |
|              回到0.2.2-SNAPSHOT              |                                                              |
