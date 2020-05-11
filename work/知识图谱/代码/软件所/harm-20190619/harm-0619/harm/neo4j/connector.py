# 连接neo4j

from py2neo import Graph,Node,Relationship, NodeMatcher
from configparser import ConfigParser

conf = ConfigParser()
conf.read("../setup.conf")
section = conf.sections()
host = conf.get('graph_db', 'host')
port = conf.get('graph_db', 'port')
user = conf.get('graph_db', 'user')
passwd = conf.get('graph_db', 'passwd')

ip = 'http://' + host + ':' + port

class neo4j_connector():
    """docstring for neo4j_connector"""
    def __init__(self, url = ip, usr = user, pwr = passwd):
        self.__url = url
        self.__usr = usr
        self.__pwr = pwr

    # 连接neo4j数据库，返回当前数据正在运行的图
    def connect_graph(self):
        graph = Graph(
            self.__url, 
            username=self.__usr, 
            password=self.__pwr
        )
        return graph

    # 在图中创建一个节点
    def create_node(self, graph, *label, **properties):
        node = Node(*label, **properties)
        graph.create(node)
        return node
    
    # 修改节点属性
    # def update_node(self, graph, node, **data):
    #     node.update(data)

    # 为两个节点创建关系
    def create_relation(self, graph, node1, r, node2):
        re = Relationship(node1, r, node2)
        graph.create(re)
        return re

    # 按标签和属性查询节点
    def match_lp(self, graph, *label, **properties):
        matcher = NodeMatcher(graph)
        res = matcher.match(*label, **properties).limit(25)
        return res

    # 执行Cypher语句
    def exec_cypher(self, graph, statement):
        res = graph.run(statement)
        return res

    # 返回节点的id
    def get_nodeid(self, node):
        return node.identity

    # 删除所有节点和关系
    def deal_all(self, graph):
        graph.delete_all()

def test():
    obj = neo4j_connector()
    graph = obj.connect_graph()

    stm = 'MATCH data = (na:Person)-[re:testRela]->(nb:Person) return na'
    t = obj.exec_cypher(graph, stm)
    r = t[0]['na']
    print(obj.get_nodeid(r))

    # res = obj.match_lp(graph, name='t1')
    # print(list(res))

    # t1 = obj.create_node(graph, "Person", name = "t1")
    # t2 = obj.create_node(graph, "Person", name = "t2")

    # r12 = obj.create_relation(graph, t1, 'testRela', t2)
    
    # obj.deal_all(graph)

    # matcher = NodeMatcher(graph)
    # res = matcher.match(label="Person",name="test_node_1").first()
    # print(res)

if __name__ == '__main__':
    test()