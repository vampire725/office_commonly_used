# import logging
#
# from py2neo import Node, Relationship, Graph
# from setting import HOST, NEO_USER, NEO_PASS, LOG_FILENAME, LOG_FORMAT, DATE_FORMAT
#
# """
# example:
#
# item = {
#     "text": "巴格达市中心于23日遭火箭弹袭击",
#     "harm_type": "3_2_2_3",
#     "triple": [
#         {
#             "subject": {
#                 "label": ["ITEM", ],
#                 "attribute": {"word": "火箭弹",
#                               }
#             },
#             "predicate": {
#                 "label": "ITEM-LOC",
#                 "is_mutual": False,
#                 "attribute": {"word": "袭击",
#                               "time": "9012-04-31",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOC", ],
#                 "attribute": {"word": "巴格达市中心",
#                               }
#             }
#         }
#     ],
#     "entity": [
#         {
#             "label": ["LOC", ],
#             "attribute": {"word": "巴格达",
#                           }
#         }
#     ]
# }
# item = {
#     "text": "乌鲁木齐七五事件",
#     "harm_type": "3_2_2_3",
#     "triple": [
#         {
#             "subject": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "乌鲁木齐七五事件",
#                               }
#             },
#             "predicate": {
#                 "label": "摧毁",
#                 "is_mutual": False,
#                 "attribute": {"word": "摧毁",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "车辆",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "乌鲁木齐七五事件",
#                               }
#             },
#             "predicate": {
#                 "label": "摧毁",
#                 "is_mutual": False,
#                 "attribute": {"word": "摧毁",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "建筑物",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "乌鲁木齐七五事件",
#                               }
#             },
#             "predicate": {
#                 "label": "造成",
#                 "is_mutual": False,
#                 "attribute": {"word": "造成",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "死亡",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "乌鲁木齐七五事件",
#                               }
#             },
#             "predicate": {
#                 "label": "发生",
#                 "is_mutual": False,
#                 "attribute": {"word": "发生",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "乌鲁木齐市",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "乌鲁木齐市",
#                               }
#             },
#             "predicate": {
#                 "label": "属于",
#                 "is_mutual": False,
#                 "attribute": {"word": "属于",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "新疆维吾尔自治区",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "新疆维吾尔自治区",
#                               }
#             },
#             "predicate": {
#                 "label": "属于",
#                 "is_mutual": False,
#                 "attribute": {"word": "属于",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "中华人民共和国",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "中国政府",
#                               }
#             },
#             "predicate": {
#                 "label": "属于",
#                 "is_mutual": False,
#                 "attribute": {"sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "中华人民共和国",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "中国政府",
#                               }
#             },
#             "predicate": {
#                 "label": "出动",
#                 "is_mutual": False,
#                 "attribute": {"word": "出动",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["PERSON", ],
#                 "attribute": {"word": "武警",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["PERSON", ],
#                 "attribute": {"word": "武警",
#                               }
#             },
#             "predicate": {
#                 "label": "冲突",
#                 "is_mutual": False,
#                 "attribute": {"word": "冲突",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "暴乱分子",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "暴乱分子",
#                               }
#             },
#             "predicate": {
#                 "label": "参与",
#                 "is_mutual": False,
#                 "attribute": {"word": "参与",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["EVENT", ],
#                 "attribute": {"word": "乌鲁木齐七五事件",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "暴乱分子",
#                               }
#             },
#             "predicate": {
#                 "label": "袭击",
#                 "is_mutual": False,
#                 "attribute": {"word": "袭击",
#                               "time": "2009-7-5",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "汉族",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "暴乱分子",
#                               }
#             },
#             "predicate": {
#                 "label": "属于",
#                 "is_mutual": False,
#                 "attribute": {"word": "属于",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "维吾尔族",
#                               }
#             }
#         },
#         {
#             "subject": {
#                 "label": ["ORGANIZATION", ],
#                 "attribute": {"word": "维吾尔族",
#                               }
#             },
#             "predicate": {
#                 "label": "居住",
#                 "is_mutual": False,
#                 "attribute": {"word": "居住",
#                               "sentiment": "0",
#                               "tendency": "0",
#                               }
#             },
#             "object": {
#                 "label": ["LOCATION", ],
#                 "attribute": {"word": "乌鲁木齐市",
#                               }
#             }
#         }
#     ],
#     "entity": [
#         {
#             "label": ["LOC", ],
#             "attribute": {"word": "巴格达",
#                           }
#         }
#     ]
# }
# """
#
#
class GraphAO(object):

    def __init__(self):
        self.logger = logging.basicConfig(filename=LOG_FILENAME,
                                          level=logging.INFO,
                                          format=LOG_FORMAT,
                                          datefmt=DATE_FORMAT)

        self.graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)
        self.tx = self.graph.begin(autocommit=False)

    @staticmethod
    def create_node(nodes: list):
        return [Node(*node['label'], **node['attribute']) for node in nodes]

    @staticmethod
    def create_rel(relations: list):
        return [Relationship(rel['subject'], rel['type'], rel['object'], **rel['attribute']) for rel in relations]

    def match(self, node: dict):
        return self.graph.nodes.match_one(*node['label'], **node['attribute']) \
            if self.graph.nodes.exists(Node(*node['label'], **node['attribute'])) \
            else Node(*node['label'], **node['attribute'])

    def save(self, relation: list = None, node: list = None):
        relations = self.create_rel(relation) if relation else None
        nodes = self.create_node(node) if node else None

        for rel_ in relations:
            self.tx.merge(rel_)
        for node_ in nodes:
            self.tx.merge(node_)
        self.tx.commit()

    def safe_save(self, relation: list = None, node: list = None):
        """
        1. 创建一个已有实体集[{k:v}] k-> dict,v -> Node
        2. 遍历
        1. 从relation中取出所有的主/宾语dict->实体集
        2. 遍历实体集
        3. 新实体建立Node，已有实体pull下来
        """
        # 1. 从relation中取出所有的主/宾语
        #
        # node_rel = []
        # node_node = []

    # def save(self, item: dict):
    #     data_all = []
    #     if isinstance(item, dict) and item.get('entity'):
    #         # create entity
    #         for entity in item['entity']:
    #             entity = is_exists_node(Node(*entity['label'], **entity['attribute']), self.graph)
    #             if type(entity) == Node:
    #                 data_all.append(entity)
    #             else:
    #                 pass
    #
    #     # create relationship and entity
    #     for rel in item['relation']:
    #         node_list = [Node(*entity['label'], **entity['attribute']) for entity in rel['node']]
    #         len_node_list = len(node_list)
    #         for node_local in range(len_node_list):
    #             # Determine if a node exists
    #             node_list[node_local] = is_exists_node(node_list[node_local], graph)
    #             # If the type does not node,the node already exist
    #             if type(node_list[node_local]) != Node:
    #                 for i in node_list[node_local]:
    #                     node_list[node_local] = i
    #         data_all.append(
    #             Relationship(node_list[0],
    #                          rel['label'],
    #                          node_list[1],
    #                          **rel['attribute']))
    #
    #         # save for entity and relation
    #         for data in data_all:
    #             # graph.create(data)
    #             self.graph.merge(data)
