import logging

from py2neo import Node, Relationship, Graph, NodeMatcher

from lib.format_data import fmt
from setting import HOST, NEO_USER, NEO_PASS, LOG_FILENAME, LOG_FORMAT, DATE_FORMAT

# """
# example:

example = {
    "text": "巴格达市中心于23日遭火箭弹袭击",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "label": ["ITEM"],
                "attribute": {"word": "火箭弹",
                              }
            },
            "predicate": {
                "type": "袭击",
                "is_mutual": False,
                "attribute": {
                              "time": "9012-04-31",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOC"],
                "attribute": {"word": "巴格达市中心",
                              }
            }
        }
    ],
    "entity": [
        {
            "label": ["LOC", ],
            "attribute": {"word": "巴格达",
                          }
        }
    ]
}


# example = {
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
#                 "type": "摧毁",
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
#                 "type": "摧毁",
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
#                 "type": "造成",
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
#                 "type": "发生",
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
#                 "type": "属于",
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
#                 "type": "属于",
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
#                 "type": "属于",
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
#                 "type": "出动",
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
#                 "type": "冲突",
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
#                 "type": "参与",
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
#                 "type": "袭击",
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
#                 "type": "属于",
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
#                 "type": "居住",
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


class GraphAO(object):

    def __init__(self):
        self.logger = logging.basicConfig(filename=LOG_FILENAME,
                                          level=logging.INFO,
                                          format=LOG_FORMAT,
                                          datefmt=DATE_FORMAT)

        self.graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)
        self.matcher = NodeMatcher(self.graph)
        self.tx = self.graph.begin(autocommit=False)

    @staticmethod
    def create_node(nodes: dict or list):
        if type(nodes) == dict:
            return Node(*nodes['label'], **nodes['attribute'])
        if type(nodes) == list:
            return [Node(*node['label'], **node['attribute']) for node in nodes]

    def merge(self, item_inner: dict):
        self.graph.merge(self.create_node(item_inner),
                         item_inner['label'][0],
                         'word')

    def match(self, item: dict):
        return self.matcher.match(item['label'][0], **item['attribute']).first() \
            if self.matcher.match(item['label'][0], **item['attribute']).first() \
            else self.create_node(item)

    def create_rel(self, relations: list):
        rel_list = []
        for rel in relations:
            self.merge(rel['subject'])
            self.merge(rel['object'])
            rel_list.append(
                Relationship(self.match(rel['subject']),
                             rel['type'],
                             self.match(rel['object']),
                             **rel['attribute'] if rel.get('attribute') else {}))
        return rel_list

    def save(self, relation: list = None, node: list = None):
        relations = self.create_rel(relation) if relation else None
        nodes = self.create_node(node) if node else None
        if relations:
            for rel_ in relations:
                self.tx.create(rel_)
        if nodes:
            for node_ in nodes:
                self.tx.create(node_)
        self.tx.commit()


if __name__ == '__main__':
    A = GraphAO()
    A.save(relation=fmt(example))
