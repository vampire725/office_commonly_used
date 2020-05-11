from py2neo import Graph

from setting import HOST, NEO_USER, NEO_PASS

graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)


def is_exists_node(node, graph):
    a_dic = {}
    for i in node.keys():
        a_dic[i] = node[i]
    node_total = len(graph.nodes.match(*node.labels, **a_dic))
    print(node_total)
    if node_total >= 1:

        return graph.nodes.match(*node.labels, **a_dic)
    else:
        return node
