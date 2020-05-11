# 利用Schema建立索引，并使用Hadoop-Gremlin导入json数据到JanusGraph

- [版本配置](#版本配置)
- [思路](#思路)
- [前期准备](#前期准备)
  - [Schema定义](#schema定义)
  - [目标图配置](#目标图配置)
  - [HadoopGraph图配置](#hadoopgraph图配置)
  - [满足Schema格式的json文件](#满足schema格式的json文件)
- [步骤](#步骤)
- [步骤](#步骤-1)

## 版本配置

|           环境           |                        内容                        |
| :----------------------: | :------------------------------------------------: |
|           版本           |              janusgraph-0.3.0-hadoop2              |
|         存储后端         |               hbase(192.168.129.10)                |
|         索引后端         |           elasticsearch(192.168.129.15)            |
|      内网地址/端口       |                192.168.129.13/8082                 |
|      外网地址/端口       |                60.28.140.210/10503                 |
|      Schema定义文件      |   [test-janusgraph-schema.groovy](###Schema定义)   |
|        目标图配置        |    [test-janusgraph1.properties](###目标图配置)    |
|    HadoopGraph图配置     | [hadoop-graphson.properties](###HadoopGraph图配置) |
| 满足Schema格式的json文件 |  [test-janusgraph.json](满足Schema格式的json文件)  |

## 思路

1. 创建Schema，在Schema文件里定义索引以及图的标签和属性
2. 打开目标主图，并加载Schema方法到图里
3. 打开HadoopGraph图，并输入json数据到图里
4. 使用IncrementBulkLoader方法批量导入，在这里，HadoopGraph图看作传输数据的中介

## 前期准备

### Schema定义

- test-janusgraph-schema.groovy

    ```groovy
def defineGratefulDeadSchema(janusGraph) {
    m = janusGraph.openManagement()
    person = m.makeVertexLabel("person").make()
    blid  = m.makePropertyKey("bulkLoader.vertex.id").dataType(Long.class).make()
    birth = m.makePropertyKey("birth").dataType(Date.class).make()
    age = m.makePropertyKey("age").dataType(Integer.class).make()
    name = m.makePropertyKey("name").dataType(String.class).make()
    index = m.buildIndex('nameMixed', Vertex.class).addKey(name, Mapping.TEXTSTRING.asParameter()).buildMixedIndex("search")
    bidIndex = m.buildIndex("byBulkLoaderVertexId", Vertex.class).addKey(blid).indexOnly(person).buildCompositeIndex()
    m.commit()
}
    ```

### 目标图配置

- test-janusgraph1.properties

    ```properties
    storage.backend=hbase
    storage.hostname=newnode10
    storage.hbase.table=janustest1
    cache.db-cache = true
    cache.db-cache-clean-wait = 20
    cache.db-cache-time = 180000
    cache.db-cache-size = 0.5
    index.search.backend=elasticsearch
    index.search.hostname=192.168.129.15
    gremlin.graph=org.janusgraph.core.JanusGraphFactory

    schema.default = none
    storage.batch-loading=true
    ```

### HadoopGraph图配置

- hadoop-graphson.properties

    ```properties
    gremlin.graph=org.apache.tinkerpop.gremlin.hadoop.structure.HadoopGraph
    gremlin.hadoop.graphReader=org.apache.tinkerpop.gremlin.hadoop.structure.io.graphson.GraphSONInputFormat
    gremlin.hadoop.graphWriter=org.apache.tinkerpop.gremlin.hadoop.structure.io.graphson.GraphSONOutputFormat
    gremlin.hadoop.inputLocation=data/test-janusgraph.json
    gremlin.hadoop.outputLocation=output
    gremlin.hadoop.jarsInDistributedCache=true
    gremlin.vertexProgram=org.apache.tinkerpop.gremlin.process.computer.ranking.pagerank.PageRankVertexProgram
    giraph.minWorkers=2
    giraph.maxWorkers=2
    spark.master=local[*]
    ```
    
### 满足Schema格式的json文件

- test-janusgraph.json

    ```json
    {"id":4136,"label":"person","properties":{"name":[{"id":"16t-36w-5j9","value":"lisi"}],"birth":[{"id":"1z9-36w-3yd","value":1509443638951}],"age":[{"id":"101-26w-5qt","value":4136}]}}
    {"id":4702,"label":"person","properties":{"name":[{"id":"171-38o-5j9","value":"fu1 "}],"birth":[{"id":"1zh-38o-3yd","value":1509043638952}],"age":[{"id":"1l9-38o-4qt","value":1}]}}
    {"id":4700,"label":"person","properties":{"name":[{"id":"171-38o-5j9","value":"fu2 "}],"birth":[{"id":"1zh-38o-3yd","value":1509043638976}],"age":[{"id":"1l9-38o-4qt","value":1}]}}
    {"id":4703,"label":"person","properties":{"name":[{"id":"171-38o-5j9","value":"fu lisi "}],"birth":[{"id":"1zh-38o-3yd","value":1509043638976}],"age":[{"id":"1l9-38o-4qt","value":1}]}}
    {"id":4705,"label":"person","properties":{"name":[{"id":"171-38o-5j9","value":"fu "}],"birth":[{"id":"1zh-38o-3yd","value":1509043638976}],"age":[{"id":"1l9-38o-4qt","value":1}]}}
    {"id":4706,"label":"person","properties":{"name":[{"id":"171-38o-5j9","value":"fu1 lisi "}],"birth":[{"id":"1zh-38o-3yd","value":1509043638976}],"age":[{"id":"1l9-38o-4qt","value":1}]}}
    ```

## 步骤

1. 创建Schema，并加载Schema方法到图里

    ```bash
    gremlin> :load data/test-janusgraph-schema.groovy
    ==>true
    gremlin> graph = JanusGraphFactory.open('data/test-janusgraph1.properties')
    ==>standardjanusgraph[hbase:[newnode10]]
    gremlin> defineGratefulDeadSchema(graph)
    ==>null
    ```

2. 打开HadoopGraph图，并输入json数据到图里

   ```bash
    gremlin> graph1 = GraphFactory.open('data/hadoop-graphson.properties')
    ==>hadoopgraph[graphsoninputformat->graphsonoutputformat]
    gremlin> h = graph1.traversal()
    ==>graphtraversalsource[hadoopgraph[graphsoninputformat->graphsonoutputformat], standard]
    gremlin> h.V()
    ==>v[4136]
    ==>v[4702]
    ==>v[4700]
    gremlin> h.V().count()
    ==>3
   ```

3. 使用IncrementBulkLoader方法批量导入数据，在这里，HadoopGraph图作为传输数据的中介
   
   ```bash
   gremlin> blvp = BulkLoaderVertexProgram.build().writeGraph('data/test-janusgraph1.properties').create(graph1)
    ==>BulkLoaderVertexProgram[bulkLoader=OneTimeBulkLoader, vertexIdProperty=null, userSuppliedIds=false, keepOriginalIds=false, batchSize=0]
   
    gremlin> graph1.compute(SparkGraphComputer).program(blvp).submit().get()
    ==>result[hadoopgraph[graphsoninputformat->graphsonoutputformat],memory[size:0]]
   ```

4. 查询数据是否导入

    ```bash
    gremlin> graph = JanusGraphFactory.open('data/test-janusgraph1.properties')
    ==>standardjanusgraph[hbase:[newnode10]]
    gremlin> g = graph.traversal()
    ==>graphtraversalsource[standardjanusgraph[hbase:[newnode10]], standard]

    gremlin> g.V()
    ==>v[4136]
    ==>v[4702]
    ==>v[4700]
    gremlin> g.V().count()
    ==>3
    ```

5. 使用索引模糊查询

    ```bash
    gremlin> g.V().has('name', textContains('fu1')).properties('name')
    ==>vp[name->fu1 ]
    ==>vp[name->fu1 ]
    ==>vp[name->fu1 lisi ]
    ```

6. 更新索引并提交

    ```bash
    gremlin> mgmt = graph.openManagement()
    ==>org.janusgraph.graphdb.database.management.ManagementSystem@474e34e4
    gremlin> mgmt.updateIndex(mgmt.getGraphIndex("nameMixed"),SchemaAction.REINDEX).get()
    ==>org.janusgraph.diskstorage.keycolumnvalue.scan.StandardScanMetrics@7ead1d80
    gremlin> mgmt.commit()
    ==>null
    gremlin> graph.tx().commit()
    ==>null
    ```

# 在gremlin客户端建立混合索引

## 步骤

1. 连接60.28.140.210/10503的gremlinserver
   
    ```bash
    gremlin> :remote connect tinkerpop.server conf/remote-local.yaml session
    ==>Configured 192.168.129.13/192.168.129.13:8182-[6a6c678d-70cb-40cb-a51d-a9ea519041fb]
    gremlin> :remote console
    ==>All scripts will now be sent to Gremlin Server - [192.168.129.13/192.168.129.13:8182]-[6a6c678d-70cb-40cb-a51d-a9ea519041fb] - type ':remote console' to return to loca
    l mode

    gremlin> g
    ==>graphtraversalsource[standardjanusgraph[hbase:[newnode10]], standard]
    gremlin> g.V().count()
    ==>89
    ```

2. 创建属性name的混合索引
   
    ```bash
    gremlin> graph.tx().rollback()
    ==>null
    gremlin> mgmt = graph.openManagement()
    ==>org.janusgraph.graphdb.database.management.ManagementSystem@646eb52e
    gremlin> name = mgmt.getPropertyKey('name')
    ==>name
    gremlin> mgmt.buildIndex('namemixedindex', Vertex.class).addKey(name, Mapping.TEXT.asParameter()).buildMixedIndex("search")
    ==>namemixedindex
    ```

3. 使用索引查询
   
   - **此时展示的是正则查询**

    ```bash
    gremlin> graph.traversal().V().has('name',textContainsRegex('.*wu.*'))
    ==>v[368640]
    ==>v[372736]
    ==>v[413712]
    ==>v[417808]
    ==>v[446608]
    ==>v[450704]
    ==>v[41423000]
    ==>v[471216]
    ==>v[475312]
    ==>v[479408]
    ==>v[442576]
    ==>v[446672]
    ==>v[377064]
    ==>v[364792]
    ==>v[368888]
    ==>v[372984]
    ```

4. 更新索引并提交

    ```bash
    mgmt.updateIndex(mgmt.getGraphIndex("namemixedindex"),SchemaAction.REINDEX)
    ==>null
    gremlin> mgmt.commit()
    ==>null
    gremlin> graph.tx().commit()
    ==>null
    ```
    