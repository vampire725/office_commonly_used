from gremlin_python import statics
from gremlin_python.structure.graph import Graph
from gremlin_python.driver.driver_remote_connection import DriverRemoteConnection

statics.load_statics(globals())
graph = Graph()
g = graph.traversal().withRemote(DriverRemoteConnection('ws://127.0.0.1:8182/gremlin', 'g'))
# g = graph.traversal().withRemote(DriverRemoteConnection('ws://60.28.140.210:8182/gremlin', 'g'))


# def add_entity():
#     count = 0
#     while True:
#         count += 1
#         strrr = str(count)
#         edge = g.addV('person').property('name', 'jason' + '{}'.format(strrr)).addE('helps').to(
#             g.addV('person').property('name', 'victor' + '{}'.format(strrr))).next()
#         if count > 9999:
#             break



# g.V().drop()
# g.E().drop()
print(g.V().count().next())
verts = g.V().valueMap(True).toList()
# print(edge)
for i in verts:
    print(verts)
print(g.E().count().next())


# if __name__ == '__main__':
#     add_entity()