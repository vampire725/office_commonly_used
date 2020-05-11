from gremlin_python import statics
from gremlin_python.process.graph_traversal import hasLabel, label, count
from gremlin_python.process.traversal import P
from gremlin_python.structure.graph import Graph
from gremlin_python.driver.driver_remote_connection import DriverRemoteConnection

statics.load_statics(globals())
graph = Graph()
g = graph.traversal().withRemote(DriverRemoteConnection('ws://127.0.0.1:8182/gremlin', 'g'))

from gremlin_python.driver import client
# client = client.Client('ws://127.0.0.1:8182/gremlin','g')  # 另一种连接方式，希望通过这种方式输入sql语句直接进行增删改查，但是还没有实现
# esql="g.V().has('name','CUMBERLAND BLUES').as('m').V().has('name','MIND LEFT BODY JAM').as('l').addE('followedBy').from('m')"
# client.submit(esql)


# print(g.V().count().next()) # 输出顶点个数
# print(g.V().hasLabel('song').toList()) # 输出标签为song的所有顶点id
# print(g.V().hasLabel('song').count().next()) # 输出顶点标签为'song'的个数
# print(g.V().label().toSet()) # 输出所有顶点标签(不重复)
# print(g.V().where(hasLabel('artist').or_().hasLabel('song')).valueMap(True).toList()) # 输出顶点标签为artist或song的顶点信息列表
# print(g.V().has('name','MIND LEFT BODY JAM').next()) # 查看name为''的顶点id

# print(g.V().valueMap().toList()) # 查看顶点所有属性
# print(g.V().properties().toList()) # 查看属性及属性值
# print(g.V().hasLabel('song').values().toList()) # 查看标签为song的所有顶点的属性及属性值
# print(g.V().hasLabel('song').properties().toList()) # 查看标签为song的所有顶点的属性以及属性值
print(g.V().values('name').toList())
print(g.V().values('name').toList())
# print(g.E().count().next()) # 输出关系个数
# print(g.E().label().toSet()) # 输出所有边标签(不重复)



# bs=g.V().toList()
# for b in bs:   # 额。。麻烦，不想说了
#     # print(g.V(b).valueMap().toList())
#     q=g.V(b).properties('songType').toList()
#     if q:
#         print (q)
#     else:
#         q = g.V(b).properties('name').toList()
#         print(q)




# c=g.V().has('name','yuxj9').valueMap().toList()
# print(c)
# d=g.V().hasLabel("1").count().toList()
# print (d)
