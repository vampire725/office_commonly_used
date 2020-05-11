from py2neo import Relationship, Node, Graph, NodeMatcher

from setting import HOST, NEO_USER, NEO_PASS

graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)
matcher = NodeMatcher(graph)
relations = [{'subject': {'label': ['ITEM', 'sy'], 'attribute': {'word': '火箭弹', }},
              'type': 'ITEM-LOC',
              'object': {'label': ['LOC', ], 'attribute': {'word': '巴格达市中心'}},
              'attribute': {'word': '袭击', 'time': '9012-04-31', 'sentiment': '0', 'tendency': '0', }
              },
             {'subject': {'label': ['ITEM', ], 'attribute': {'word': '火箭弹', 'name': 'beng', 'qisil': 'uuu'}},
              'type': 'come_from',
              'object': {'label': ['TEXT', ], 'attribute': {'word': '巴格达市中心于23日遭火箭弹袭击', 'harm_type': '3_2_2_3', }}},
             {'subject': {'label': ['LOC', ], 'attribute': {'word': '巴格达市中心', }},
              'type': 'come_from',
              'object': {'label': ['TEXT', ], 'attribute': {'word': '巴格达市中心于23日遭火箭弹袭击', 'harm_type': '3_2_2_3', }}},
             {'subject': {'label': ['LOC', ], 'attribute': {'word': '巴格达', }}, 'type': 'come_from',
              'object': {'label': ['TEXT', ], 'attribute': {'word': '巴格达市中心于23日遭火箭弹袭击', 'harm_type': '3_2_2_3', }}}]


def create_node(node):
    return Node(*node['label'], **node['attribute'])


result_list = [Relationship(create_node(rel['subject']),
                            rel['type'],
                            create_node(rel['object']),
                            **rel['attribute'] if rel.get('attribute') else {})
               for rel in relations]


def create_node(node: dict):
    return Node(*node['label'], **node['attribute'])


a_list = []
for rel in relations:
    graph.merge(create_node(rel['subject']), rel['subject']['label'][0], 'word')
    graph.merge(create_node(rel['object']), rel['object']['label'][0], 'word')
    star_node = matcher.match(rel['subject']['label'][0], **rel['subject']['attribute']).first()
    end_node = matcher.match(rel['object']['label'][0], **rel['object']['attribute']).first()
    a_list.append(Relationship(star_node, rel['type'], end_node, **rel['attribute'] if rel.get('attribute') else {}))
for haha in a_list:
    graph.create(haha)
# for rel in result_list:
#     start_node_label = str(rel.start_node.labels).split(":")[1]
#     end_node_label = str(rel.end_node.labels).split(":")[1]
#     dic_start_node = {}
#     for k, v in rel.start_node.items():
#         dic_start_node[k] = v
#     print(dic_start_node)
#     dic_end_node = {}
#     for k, v in rel.end_node.items():
#         dic_end_node[k] = v
#     node_star = graph.merge(rel.start_node, start_node_label, 'word')
#     graph.merge(rel.end_node, end_node_label, 'word')
#     star_node = matcher.match(start_node_label, **dic_start_node).first()
#     end_node = matcher.match(end_node_label, **dic_end_node).first()
#     # star_node.update(dic_start_node)
#     # end_node.update(dic_end_node)
#     rel = Relationship(star_node, 'hehe',end_node)
#     # print(rel.start_node.items())
#     #     # print(graph.exists(rel.start_node))
#     #     # print(hehe, rel.start_node['word'])
#     #     nodes.append(rel.start_node)
#     #     nodes.append(rel.end_node)
#     # graph.push(rel)
#     graph.create(rel)
#
# print(nodes)
