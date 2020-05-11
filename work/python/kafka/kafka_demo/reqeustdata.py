import json

import requests

# url = 'http://192.168.120.51:10120/sb/'
url = 'http://192.168.120.51:30120/sb'
# d = {
#     "text": "新浪科技讯 北京时间6月20日早间消息，谷歌近期发表的一篇学术论文或许为机器学习未来的发展制定了蓝图。",
#     "task": "sb",
#     "format": "json",
#     "lang": "zh-CN",
#     "key": "wangxinban"
# }
d = {
    "text": "'In the last company I worked for , we had a QQ group for those who play King of Glory, and colleagues sometimes played together in their spare time.The head of the tech department took the lead, ' said Liu. Playing a game together can be a quick way to break the ice and build up close relationships in the office.",
    "task": " sb ",
    "format": "json",
    "lang": "en-US",
    "key": "wangxinban"
}

s = json.dumps(d)
print(type(d))
r = requests.get(url, data=s)
t = r.json()
print(t)
# print(t)
