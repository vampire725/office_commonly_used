import logging

from py2neo import Node, Relationship, Graph
from lib.format_data import fmt
from lib.is_exists import is_exists_node
from setting import HOST, NEO_USER, NEO_PASS, LOG_FILENAME, LOG_FORMAT, DATE_FORMAT

# """example
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
example = {
    "text": "乌鲁木齐七五事件",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "label": ["EVENT", ],
                "attribute": {"word": "乌鲁木齐七五事件",
                              "ii": 'ttttt',
                              "uuuu": "ppp",
                              'rt': 'op',
                              }
            },
            "predicate": {
                "label": "摧毁",
                "is_mutual": False,
                "attribute": {"word": "摧毁",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["EVENT", ],
                "attribute": {"word": "车辆",
                              "fff": "llll",
                              }
            }
        },
        {
            "subject": {
                "label": ["EVENT", ],
                "attribute": {"word": "乌鲁木齐七五事件",
                              }
            },
            "predicate": {
                "label": "摧毁",
                "is_mutual": False,
                "attribute": {"word": "摧毁",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["EVENT", ],
                "attribute": {"word": "建筑物",
                              }
            }
        },
        {
            "subject": {
                "label": ["EVENT", ],
                "attribute": {"word": "乌鲁木齐七五事件",
                              }
            },
            "predicate": {
                "label": "造成",
                "is_mutual": False,
                "attribute": {"word": "造成",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["EVENT", ],
                "attribute": {"word": "死亡",
                              }
            }
        },
        {
            "subject": {
                "label": ["EVENT", ],
                "attribute": {"word": "乌鲁木齐七五事件",
                              }
            },
            "predicate": {
                "label": "发生",
                "is_mutual": False,
                "attribute": {"word": "发生",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOCATION", ],
                "attribute": {"word": "乌鲁木齐市",
                              }
            }
        },
        {
            "subject": {
                "label": ["LOCATION", ],
                "attribute": {"word": "乌鲁木齐市",
                              "呵呵": "可",
                              }
            },
            "predicate": {
                "label": "属于",
                "is_mutual": False,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOCATION", ],
                "attribute": {"word": "新疆维吾尔自治区",
                              }
            }
        },
        {
            "subject": {
                "label": ["LOCATION", ],
                "attribute": {"word": "新疆维吾尔自治区",
                              }
            },
            "predicate": {
                "label": "属于",
                "is_mutual": False,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOCATION", ],
                "attribute": {"word": "中华人民共和国",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "中国政府",
                              }
            },
            "predicate": {
                "label": "属于",
                "is_mutual": False,
                "attribute": {"sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOCATION", ],
                "attribute": {"word": "中华人民共和国",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "中国政府",
                              }
            },
            "predicate": {
                "label": "出动",
                "is_mutual": False,
                "attribute": {"word": "出动",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["PERSON", ],
                "attribute": {"word": "武警",
                              }
            }
        },
        {
            "subject": {
                "label": ["PERSON", ],
                "attribute": {"word": "武警",
                              }
            },
            "predicate": {
                "label": "冲突",
                "is_mutual": False,
                "attribute": {"word": "冲突",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "暴乱分子",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "暴乱分子",
                              }
            },
            "predicate": {
                "label": "参与",
                "is_mutual": False,
                "attribute": {"word": "参与",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["EVENT", ],
                "attribute": {"word": "乌鲁木齐七五事件",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "暴乱分子",
                              }
            },
            "predicate": {
                "label": "袭击",
                "is_mutual": False,
                "attribute": {"word": "袭击",
                              "time": "2009-7-5",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "汉族",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "暴乱分子",
                              }
            },
            "predicate": {
                "label": "属于",
                "is_mutual": False,
                "attribute": {"word": "属于",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "维吾尔族",
                              }
            }
        },
        {
            "subject": {
                "label": ["ORGANIZATION", ],
                "attribute": {"word": "维吾尔族",
                              }
            },
            "predicate": {
                "label": "居住",
                "is_mutual": False,
                "attribute": {"word": "居住",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOCATION", ],
                "attribute": {"word": "乌鲁木齐市",
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

logging.basicConfig(filename=LOG_FILENAME, level=logging.INFO, format=LOG_FORMAT, datefmt=DATE_FORMAT)
# connect neo4j
graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)


def save(item: dict):
    data_all = []
    # create relationship and entity
    for rel in item['relation']:
        node_list = [Node(*entity['label'], **entity['attribute']) for entity in rel['node']]
        relation = Relationship(is_exists_node(node_list[0], graph),
                                rel['label'],
                                is_exists_node(node_list[1], graph),
                                **rel['attribute'])
        # print(relation.start_node,relation.types(),relation.end_node)
        graph.match(relation.start_node, relation.types(), relation.end_node)
        data_all.append(relation)

    # save for entity and relation
    for data in list(set(data_all)):
        graph.create(data)
        graph.push(data)


save(fmt(example))
