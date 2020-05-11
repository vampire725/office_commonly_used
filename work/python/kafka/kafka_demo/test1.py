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
# verts = g.V().valueMap(True).toList()
# print(edge)
# for i in verts:
#     print(verts)
print(g.E().count().next())


# if __name__ == '__main__':
#     add_entity()



def defineGratefulDeadSchema(janusGraph) {
    m = janusGraph.openManagement()
    // vertex labels
    artist = m.makeVertexLabel("artist").make()
    song   = m.makeVertexLabel("song").make()
    // edge labels
    sungBy     = m.makeEdgeLabel("sungBy").make()
    writtenBy  = m.makeEdgeLabel("writtenBy").make()
    followedBy = m.makeEdgeLabel("followedBy").make()
    // vertex and edge properties
    blid         = m.makePropertyKey("bulkLoader.vertex.id").dataType(Long.class).make()
    name         = m.makePropertyKey("name").dataType(String.class).make()
    songType     = m.makePropertyKey("songType").dataType(String.class).make()
    performances = m.makePropertyKey("performances").dataType(Integer.class).make()
    weight       = m.makePropertyKey("weight").dataType(Integer.class).make()
    // global indices
    m.buildIndex("byBulkLoaderVertexId", Vertex.class).addKey(blid).buildCompositeIndex()
    m.buildIndex("artistsByName", Vertex.class).addKey(name).indexOnly(artist).buildCompositeIndex()
    m.buildIndex("songsByName", Vertex.class).addKey(name).indexOnly(song).buildCompositeIndex()
    // vertex centric indices
    m.buildEdgeIndex(followedBy, "followedByWeight", Direction.BOTH, Order.decr, weight)
    m.commit()
}

g.V().has('name',textContains('pluto')).as('id').project('Name','Label','Edge_count').by('name').by(__.label()).by(__.both().count()).order().by(select('Edge_count'),decr).select('id','Name','Label','Edge_count')
###
# where 查看label 为company 和representative得数据
###

# A=g.V().where(hasLabel('company').or_().hasLabel('representative')).valueMap(True).toList()
# print (A)

# g.V().has('chair_id', within('%s')).as_('A').V().has('repren_id', within('%s')).addE('法人').from_('A');
# from gremlin_python.process.traversal import T
# from gremlin_python.process.traversal import Order
# from gremlin_python.process.traversal import Cardinality
# from gremlin_python.process.traversal import Column
# from gremlin_python.process.traversal import Direction
# from gremlin_python.process.traversal import Operator
# from gremlin_python.process.traversal import P
# from gremlin_python.process.traversal import Pop
# from gremlin_python.process.traversal import Scope
# from gremlin_python.process.traversal import Barrier
# a=g.V().has("company_n",P.textContent("分公司")).valueMap()
# print (a)
####

####
##查看边界和个数
####
# g.E().group().by(label).by(count())











