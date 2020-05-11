import copy

"""
example:
item = {
    "text": "巴格达市中心于23日遭火箭弹袭击",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "label": ["ITEM", ],
                "attribute": {"word": "火箭弹",
                              }
            },
            "predicate": {
                "label": "ITEM-LOC",
                "is_mutual": False,
                "attribute": {"word": "袭击",
                              "time": "9012-04-31",
                              "sentiment": "0",
                              "tendency": "0",
                              }
            },
            "object": {
                "label": ["LOC", ],
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

"""


def fmt(item: dict) -> dict:
    # 在这里去掉了entity的列表，因为每一个entity都要与text节点创建关系
    result = {'entity': [],
              'relation': [],
              'text': {}}
    # 确定text节点内容
    result['text']['label'] = ['TEXT', ]
    result['text']['attribute'] = {'text': item['text'], 'harm_type': item['harm_type']}
    result['entity'].extend(item['entity'])
    # 字典中非三元组的实体与text节点创建关系
    for entity in item['entity']:
        text_rel = dict()
        text_rel['node'] = [entity, result['text']]
        text_rel['label'] = '属于'
        text_rel['attribute'] = {'type': 'text'}
        result['relation'].append(text_rel)
    for triad in item['triple']:
        rel = dict()
        text_rel_sub = dict()
        text_rel_ob = dict()
        # 创建三元组关系
        rel['node'] = [triad['subject'], triad['object']]
        rel['label'] = triad['predicate']['label']
        rel['attribute'] = triad['predicate']['attribute']
        # 创建三元组中subject与text节点的关系
        text_rel_sub['node'] = [triad['subject'], result['text']]
        text_rel_sub['label'] = '属于'
        text_rel_sub['attribute'] = {'type': 'text'}
        # 创建三元组中object与text节点的关系
        text_rel_ob['label'] = '属于'
        text_rel_ob['node'] = [triad['object'], result['text']]
        text_rel_ob['attribute'] = {'type': 'text'}
        result['relation'].append(rel)
        result['relation'].append(text_rel_sub)
        result['relation'].append(text_rel_ob)

        #
        if triad['predicate']['is_mutual'] is True:
            re_ttt = copy.deepcopy(rel)
            re_ttt['node'] = [triad['object'], triad['subject']]
            result['relation'].append(re_ttt)
    return result

# fmt(item)
