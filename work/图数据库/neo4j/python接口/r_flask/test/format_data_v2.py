# import copy
#
#
# """
# example:
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
#
# """
#
#
# def fmt(item: dict) -> dict:
#     result = {'entity': [],
#               'relation': []}
#     result['entity'].extend(item['entity'])
#
#     for triad in item['triple']:
#         rel = dict()
#         rel['node'] = [triad['subject'], triad['object']]
#         rel['label'] = triad['predicate']['label']
#         rel['attribute'] = triad['predicate']['attribute']
#         result['relation'].append(rel)
#
#         #
#         if triad['predicate']['is_mutual'] is True:
#             re_ttt = copy.deepcopy(rel)
#             re_ttt['node'] = [triad['object'], triad['subject']]
#             result['relation'].append(re_ttt)
#     return result
#
# # fmt(item)
#
