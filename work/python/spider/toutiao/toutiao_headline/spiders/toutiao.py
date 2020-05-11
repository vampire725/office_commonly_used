import re
import time

import redis
import scrapy
from scrapy import Selector, Request

from ..items import TodayHeadlineItem
from toutiao_headline.spiders.header_cookies import headers, cookies

conn = redis.StrictRedis(host='localhost', port="6379", db="0", decode_responses=True)


class TotiaoSpider(scrapy.Spider):
    name = 'toutiao'

    def start_requests(self):
        # title, content_url = obtain_urls()
        # for title, content_url in obtain_urls():
        #     print(title, content_url)
        sy = conn.smembers('links')
        for content_url in sy:
            print(content_url)
            item = TodayHeadlineItem(source=content_url)
            # if is_new(content_url):
            yield Request(url=content_url, headers=headers, cookies=cookies, meta={'item': item}, dont_filter=False,
                          callback=self.parse)
            # else:
            #     pass

    def parse(self, response):
        item = response.meta['item']
        all_contents = response.text
        article = Selector(text=all_contents)
        article_main = article.xpath('//div[@id="article"]')
        try:
            if article_main:
                t_title = article_main.xpath('h1[@class="a-title"]//text()').extract()
                t_publish_time = article_main.xpath('div[@class="a-info"]/span[@class="time"]/text()').extract_first()
                t_origin = article_main.xpath(
                    'div[@class="a-info"]/span[@class="original"]/span/text()').extract_first()
                t_content = article_main.xpath('div[@class="a-con"]/p//text()').extract()
                t_editor = article_main.xpath('div[@class="a-con"]/div[@class="a-edit"]/span/text()').extract_first()

            # elif article is None:

            else:
                article1 = re.findall('articleInfo: {([\s\S]*)},[\s\S]*commentInfo', all_contents)
                t_title = re.findall('title: \'([\s\S]*),\s+content', article1[0])
                t_content = re.findall('content: \'([\s\S]*)\',\s+groupId', article1[0])
                t_publish_time = re.findall('(\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2})', article1[0])
                t_origin = re.findall('source: \'([\s\S]*)\',\s+time', article1[0])
            item['content'] = t_content
            item['title'] = t_title
            item['publishtime'] = t_publish_time
            item['origin'] = t_origin
            # item['editor'] = t_editor
            print(item)
            yield item
            time.sleep(6)
            sy = conn.smembers('links')
            for content_url in sy:
                print(content_url)
                item = TodayHeadlineItem(source=content_url)
                yield Request(url=content_url, headers=headers, cookies=cookies, meta={'item': item}, dont_filter=False,
                              callback=self.parse)
        except Exception as e:
            print(e)
            print('与规则不符,抓取失败')
