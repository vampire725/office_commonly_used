import time

import aiohttp
import asyncio

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
example = [
    {
        "text": "乌鲁木齐七五事件1",
        "harm_type": "3_2_2_3",
        "triple": [
            {
                "subject": {
                    "label": ["EVENT"],
                    "attribute": {"word": "乌鲁木齐七五事件1"}
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
                    "attribute": {"word": "车辆1"}
                }
            },
            {
                "subject": {
                    "label": ["EVENT"],
                    "attribute": {"word": "乌鲁木齐七五事件1"}
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
                    "attribute": {"word": "乌鲁木齐七五事件1"}
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
                    "attribute": {"word": "乌鲁木齐七五事件1"}
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
                    "label": ["LOC"],
                    "attribute": {"word": "乌鲁木齐市"}
                }
            },
            {
                "subject": {
                    "label": ["LOC"],
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
                    "label": ["LOC"],
                    "attribute": {"word": "新疆维吾尔自治区",
                                  "suc": "io"}
                }
            },
            {
                "subject": {
                    "label": ["LOC"],
                    "attribute": {"word": "新疆维吾尔自治区",
                                  "att": "自治区",
                                  "direct": "西北"}
                },
                "predicate": {
                    "type": "属于",
                    "is_mutual": 0,
                    "attribute": {"word": "属于",
                                  "sentiment": "0",
                                  "tendency": "0"}
                },
                "object": {
                    "label": ["LOC"],
                    "attribute": {"word": "中华人民共和国"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
                    "attribute": {"word": "中国政府"}
                },
                "predicate": {
                    "type": "属于",
                    "is_mutual": 0,
                    "attribute": {"sentiment": "0",
                                  "tendency": "0"}
                },
                "object": {
                    "label": ["LOC"],
                    "attribute": {"word": "中华人民共和国"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
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
                    "label": ["PER"],
                    "attribute": {"word": "武警"}
                }
            },
            {
                "subject": {
                    "label": ["PER"],
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
                    "label": ["ORG"],
                    "attribute": {"word": "暴乱分子"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
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
                    "attribute": {"word": "乌鲁木齐七五事件1"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
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
                    "label": ["ORG"],
                    "attribute": {"word": "汉族"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
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
                    "label": ["ORG"],
                    "attribute": {"word": "维吾尔族"}
                }
            },
            {
                "subject": {
                    "label": ["ORG"],
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
                    "label": ["LOC"],
                    "attribute": {"word": "乌鲁木齐市"}
                }
            }
        ],
        "entity": [
            {
                "label": ["LOC"],
                "attribute": {"word": "巴格达",
                              }
            },
            {
                "label": ["LOC"],
                "attribute": {"word": "测试"
                              }
            }
        ],
    },
    {
        "text": "乌鲁木齐七五事件1",
        "harm_type": "3_2_2_3",
        "triple": [
            {
                "subject": {
                    "label": ["EVENT"],
                    "attribute": {"word": "泡泡事件"}
                },
                "predicate": {
                    "type": "迷惑",
                    "is_mutual": 0,
                    "attribute": {"time": "2019-6-18",
                                  "sentiment": "0",
                                  "tendency": "0"}
                },
                "object": {
                    "label": ["PER"],
                    "attribute": {"word": "pp"}
                }
            }
        ],
        "entity": [
            {
                "label": ["LOC"],
                "attribute": {"word": "十堰",
                              }
            }
        ],
    }]
start = time.time()


async def post(url, data):
    session = aiohttp.ClientSession()
    response = await session.post(url, json=data)
    session.close()
    return response


async def request(data):
    url = 'http://127.0.0.1:8000/data/'
    await post(url, data)


tasks = []
for data in example:
    tasks.append(asyncio.ensure_future(request(data)))
loop = asyncio.get_event_loop()
loop.run_until_complete(asyncio.wait(tasks))
end = time.time()
print('Cost time',end-start)