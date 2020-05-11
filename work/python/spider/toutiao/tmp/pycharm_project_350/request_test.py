import requests
from scrapy import Selector

base_url = "https://www.toutiao.com/group/6651701386759111176/"

headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0', }
cookies = {'tt_webid': '6651751144152925700',
           '__tasessionId': 'pq7d34gu41548731513332',
           'csrftoken': 'a5d6e43c8ce0797f72ddbdec8f8d560f',
           'UM_distinctid': '16897977f32153-0c64ee4943f4eb8-4c312e7e-100200-16897977f3415',
           'CNZZDATA1259612802': '371781949-1548728897-%7C1548728897'}

response = requests.get(base_url, headers=headers, cookies=cookies)
article = Selector(text=response.text)
article_main = article.xpath('//div[@id="article"]')
article_main1 = article.xpath('//div[@id="article"]')
if article_main:
    t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
    t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
    t_origin = article_main.xpath('div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
    t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
    t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()

elif article_main is None:
    article_main = article.xpath('//div[@id="article"]')
    t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
    t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
    t_origin = article_main.xpath('div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
    t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
    t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()
else:
    article_main = article.xpath('//div[@id="article"]')
    t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
    t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
    t_origin = article_main.xpath('div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
    t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
    t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()


item = {'title': t_title[1],
        'publish_time': t_publish_time,
        'origin': t_origin,
        'content': t_content,
        'editor': t_editor.split('：')[-1]}
print(item)


# all_contents = response.text
# selector = Selector(text=all_contents)
# if selector:
#     contents = selector.xpath('//*[@id="article"]/div[2]/p//text()')
#     if contents:
#         print(contents.extract())
#     else:
#         contents = selector.xpath('/html/body/div/div[2]/div[2]/div[1]/div[2]/div/p//text()')
#         if contents:
#             print(contents.extract())
#         else:
#             contents = selector.xpath('/html/body/article/p//text()')
#             print(contents)
