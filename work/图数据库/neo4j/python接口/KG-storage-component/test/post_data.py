import requests

"""example
example1 = {
    "text": "巴格达市中心于23日遭火箭弹袭击",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "word": "火箭弹",
                "label": ["ITEM", ],
                "attribute": {}
            },
            "predicate": {
                "word": "袭击",
                "label": "ITEM-LOC",
                "is_mutual": False,
                "attribute":
                    {
                        "time": "9012-04-31",
                        "sentiment": "0",
                        "tendency": "0",
                    }

            },
            "object": {
                "word": "巴格达市中心",
                "label": ["LOC", ],
                "attribute": {}
            }
        },
    ],
    "entity": [
        {
            "word": "巴格达",
            "label": ["LOC", ],
            "attribute": {}
        }
    ]
}
"""
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
                "label": ["ITEM"],
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
                "label": ["ITEM"],
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
                "attribute": {"word": "新疆维吾尔自治区",
                              "att": "自治区"}
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

# data = {'数据格式': 'json', '结构示例': example2}

r = requests.post("http://127.0.0.1:5000/data", json=example)
