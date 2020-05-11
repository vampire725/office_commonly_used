import requests
from scrapy import Selector

url = 'https://www.toutiao.com/api/pc/feed/'
start_url = 'https://www.toutiao.com'
data = {'category': 'news_hot',
        'utm_source': 'toutiao',
        'widen': 1,
        'max_behot_time': 0,
        'max_behot_time_tmp': '0',
        'tadrequire': 'true',
        'as': 'A135BC744F1C647',
        'cp': '5C4FAC36D4F7DE1',
        '_signature': 'c3AyKAAAL0L6kQuyuyhpXHNwMj'}
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0',
}
cookies = {'tt_webid': '6651751144152925700',
           '__tasessionId': 'pq7d34gu41548731513332',
           'csrftoken': 'a5d6e43c8ce0797f72ddbdec8f8d560f',
           'UM_distinctid': '16897977f32153-0c64ee4943f4eb8-4c312e7e-100200-16897977f3415',
           'CNZZDATA1259612802': '371781949-1548728897-%7C1548728897'}


def get_info(info_dict):
    title = info_dict['title']
    source_url = info_dict['source_url']
    content_url = start_url + '{}'.format(source_url)
    return title, content_url


def get_content(content_url):
    response = requests.get(url=content_url, headers=headers, cookies=cookies)
    all_contents = response.text
    article = Selector(text=all_contents)
    article_main = article.xpath('//div[@id="article"]')

    if article_main:
        t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
        t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
        t_origin = article_main.xpath('div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
        t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
        t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()

    elif article_main is None:
        article_main = article.xpath('//div[@class="article-box"]')
        t_title = article_main.xpath('h1[@class="article-title"]//text()').extract()
        t_publish_time = article_main.xpath('div[@class="article-sub"]/span[1]/text()').extract_first()
        t_origin = article_main.xpath('div[@class="article-sub"]/span[0]/text()').extract_first()
        t_content = article_main.xpath('div[@class="article-content"]/div/p/p//text()').extract()
        t_editor = article_main.xpath('div[@class="article-content"]/div/p/text()').extract_first()
    else:
        article_main = article.xpath('//div[@id="article"]')
        t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
        t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
        t_origin = article_main.xpath('div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
        t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
        t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()

    item = {'title': t_title,
            'publish_time': t_publish_time,
            'origin': t_origin,
            'content': t_content,
            'editor': t_editor}
    return item

def obtain_urls():
    wbdata = requests.get(url, headers=headers, verify=False, params=data, cookies=cookies).json()
    length = len(wbdata['data'])
    print(length)
    # info_all = []
    for i in range(length):
        yield get_info(wbdata['data'][i])

# contents = selector.xpath('//*[@id="article"]/div[2]/p//text()')
# if contents:
#     pass
# else:
#     contents = selector.xpath('/html/body/div/div[2]/div[2]/div[1]/div[2]/div/p//text()')
#     if contents:
#         pass
#     else:
#         contents = selector.xpath('//p//text()')
