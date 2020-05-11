import re

import requests
from scrapy import Selector

from toutiao_headline.spiders.header_cookies import headers, cookies

url = 'https://www.toutiao.com/group/6657483324509913603/'
response = requests.get(url=url, headers=headers, cookies=cookies)
all_contents = response.text
article = Selector(text=all_contents)
print(article)
article = re.findall('articleInfo: {([\s\S]*)},[\s\S]*commentInfo', all_contents)
title = re.findall('title: \'([\s\S]*),\s+content', article[0])
content = re.findall('content: \'([\s\S]*)\',\s+groupId', article[0])
publishtime = re.findall('(\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2})', article[0])
origin = re.findall('source: \'([\s\S]*)\',\s+time', article[0])
print(title)
print(content)
print(publishtime)
print(origin)

pass
