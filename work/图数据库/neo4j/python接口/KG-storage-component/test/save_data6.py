import logging

from py2neo import Node, Relationship, Graph, NodeMatcher

from lib.format_data import fmt
from setting import HOST, NEO_USER, NEO_PASS, LOG_FILENAME, LOG_FORMAT, DATE_FORMAT

# """
# example:

# example = {
#     "text": "巴格达市中心于23日遭火箭弹袭击",
#     "harm_type": "3_2_2_3",
#     "triple": [
#         {
#             "subject": {
#                 "label": ["ITEM"],
#                 "attribute": {"word": "火箭弹",
#                               }
#             },
#             "predicate": {
#                 "type": "袭击",
#                 "is_mutual": False,
#                 "attribute": {
#                     "time": "9012-04-31",
#                     "sentiment": "0",
#                     "tendency": "0",
#                 }
#             },
#             "object": {
#                 "label": ["LOC"],
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


example = {
    "text": "乌鲁木齐七五事件",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "label": ["EVENT"],
                "attribute": {"word": "乌鲁木齐七五事件",
                              "beaut": "pp"}
            },
            "predicate": {
                "type": "摧毁",
                "is_mutual": 0,
                "attribute": {"word": "摧毁",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["EVENT"],
                "attribute": {"word": "车辆"}
            }
        },
        {
            "subject": {
                "label": ["EVENT"],
                "attribute": {"word": "乌鲁木齐七五事件",
                              "sy": "suib"}
            },
            "predicate": {
                "type": "摧毁",
                "is_mutual": 0,
                "attribute": {"word": "摧毁",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["EVENT"],
                "attribute": {"word": "建筑物"}
            }
        },
        {
            "subject": {
                "label": ["EVENT"],
                "attribute": {"word": "乌鲁木齐七五事件"}
            },
            "predicate": {
                "type": "造成",
                "is_mutual": 0,
                "attribute": {"word": "造成",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["EVENT"],
                "attribute": {"word": "死亡"}
            }
        },
        {
            "subject": {
                "label": ["EVENT"],
                "attribute": {"word": "乌鲁木齐七五事件"}
            },
            "predicate": {
                "type": "发生",
                "is_mutual": 0,
                "attribute": {"word": "发生",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["LOCATION"],
                "attribute": {"word": "乌鲁木齐市"}
            }
        },
        {
            "subject": {
                "label": ["LOCATION"],
                "attribute": {"word": "乌鲁木齐市"}
            },
            "predicate": {
                "type": "属于",
                "is_mutual": 0,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["LOCATION"],
                "attribute": {"word": "新疆维吾尔自治区"}
            }
        },
        {
            "subject": {
                "label": ["LOCATION"],
                "attribute": {"word": "新疆维吾尔自治区"}
            },
            "predicate": {
                "type": "属于",
                "is_mutual": 0,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["LOCATION"],
                "attribute": {"word": "中华人民共和国"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "中国政府"}
            },
            "predicate": {
                "type": "属于",
                "is_mutual": 0,
                "attribute": {"sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["LOCATION"],
                "attribute": {"word": "中华人民共和国"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "中国政府"}
            },
            "predicate": {
                "type": "出动",
                "is_mutual": 0,
                "attribute": {"word": "出动",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["PERSON"],
                "attribute": {"word": "武警"}
            }
        },
        {
            "subject": {
                "label": ["PERSON"],
                "attribute": {"word": "武警"}
            },
            "predicate": {
                "type": "冲突",
                "is_mutual": 0,
                "attribute": {"word": "冲突",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "暴乱分子"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "暴乱分子"}
            },
            "predicate": {
                "type": "参与",
                "is_mutual": 0,
                "attribute": {"word": "参与",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["EVENT"],
                "attribute": {"word": "乌鲁木齐七五事件"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "暴乱分子"}
            },
            "predicate": {
                "type": "袭击",
                "is_mutual": 0,
                "attribute": {"word": "袭击",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "汉族"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "暴乱分子"}
            },
            "predicate": {
                "type": "属于",
                "is_mutual": 0,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "维吾尔族"}
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION"],
                "attribute": {"word": "维吾尔族"}
            },
            "predicate": {
                "type": "居住",
                "is_mutual": 0,
                "attribute": {"word": "居住",
                              "sentiment": "0",
                              "tendency": "0"}
            },
            "object": {
                "label": ["LOCATION"],
                "attribute": {"word": "乌鲁木齐市"}
            }
        }
    ],
    "entity": [
        {
            "label": ["LOC"],
            "attribute": {"word": "巴格达",
                          }
        }
    ],
}


class GraphAO(object):

    def __init__(self):
        self.logger = logging.basicConfig(filename=LOG_FILENAME,
                                          level=logging.INFO,
                                          format=LOG_FORMAT,
                                          datefmt=DATE_FORMAT)

        self.graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)
        self.matcher = NodeMatcher(self.graph)
        self.mtc = self.graph.nodes
        self.tx = self.graph.begin(autocommit=False)

    @staticmethod
    def create_node(*nodes: dict) -> list:
        """
        创建节点
        :param nodes: 节点信息
        :return: 返回subgraph
        """
        node_list = [Node(*node['label'], **node['attribute']) for node in nodes]
        return node_list

    def match_node(self, item: dict):
        """
        :param item: 输入节点信息，查找节点是否存在
        :return: 返回已存在节点或者None
        """
        node_attr = item['attribute']
        match_first = self.mtc.match(item['label'][0], word=node_attr['word']).first()
        if match_first:
            match_first.update(node_attr)
            self.graph.push(match_first)
            return match_first
        else:
            return None

    def merge_node(self, node: Node):
        """
        :param node: 输入node节点
        :return: 合并已存在节点
        """
        self.graph.merge(node, str(node.labels).split(':')[1],
                         'word')

    def create_rel(self, relations: list):
        """
        :param relations: 类型为列表
        :return: rdf 数据格式
        """
        rel_list = []
        for rel in relations:
            sub_node = self.match_node(rel['subject'])
            obj_node = self.match_node(rel['object'])
            if not sub_node:
                sub_node = self.create_node(rel['subject'])[0]
            if not obj_node:
                obj_node = self.create_node(rel['object'])[0]
            self.merge_node(sub_node)
            self.merge_node(obj_node)
            rel_list.append(Relationship(sub_node,
                                         rel['type'],
                                         obj_node,
                                         **rel['attribute'] if rel.get('attribute') else {}))
        return rel_list

    def save(self, relation: list = None, nodes: dict = None):
        """
        :param relation: Relationship化的关系
        :param nodes: Node化的节点
        :return: 创建关系和节点信息
        """
        relations = self.create_rel(relation) if relation else None
        nodes = self.create_node(nodes) if nodes else None
        if relations:
            for rel_ in relations:
                self.tx.push(rel_)
                self.tx.create(rel_)

        if nodes:
            for node_ in nodes:
                self.tx.create(node_)
        self.tx.commit()


if __name__ == '__main__':
    A = GraphAO()
    A.save(fmt(example))
