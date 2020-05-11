# -*- coding: utf-8 -*-

import re
import scrapy
import requests

from scrapy.http import *

from pachong.items import PachongItem
import json
import demjson
from scrapy.selector import Selector



class weiboSpider(scrapy.Spider):
    name = 'weibo'
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0',
               'Referer': ' http://travel.qunar.com/p-cs299914-beijing-meishi?',
               'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8', }
    cookies = {
        'Cookie': 'viewdist=299914-4; uld=1-299914-4-1530430459;QN1=ezu0qFs4gaWfNyuJq8iWAg==;QN205=organic;QN277=organic;csrfToken=GD5xEl3IsxtskkoWAMGSHQFFWWdwVlGz;_i=VInJOyRd8bfimXdqZPp4-HElOZpq;_vi=nQdY6-Hyfu3TQ1GAu7CwUsIOjgH5_ZDxKgRsol3BfQGyupmZ9zY55M4DSPiJTDiBad3A-B-sj63rcYOZeYG9nRsfoYzrVXAsBgtf7ers2nAp7xlgytGd6ZK94czDvvlsi0u8_PO2E2fYF_o-nxhJpvo5_03P6d76fpmnHPBzwq2H;QN269=00FBBCD27D0011E8A32BFA163E642F8B;Hm_lvt_c56a2b5278263aa647778d304009eafc=1530429961;Hm_lpvt_c56a2b5278263aa647778d304009eafc=1530430082'}

    def start_requests(self):
        for i in range(1,10):
            url = 'http://travel.qunar.com/p-cs299914-beijing-meishi?page={}'.format(i)

            yield Request(url=url, headers=weiboSpider.headers, cookies=weiboSpider.cookies, callback=self.parse,dont_filter=False)
            #callback，回调函数，不管下面的函数名字怎么变，只要这里有callback，就可以回调



    def parse(self, response):  #默认会使用parse方法，如果要自定义parse方法的名称，必须在request中指定callback参数
        two_urls = response.xpath('//ul[@class="list_item clrfix"]/li/a/@href').extract()
        # hrefs = two_urls.xpath('a/@href').extract_first()
        for two_url in two_urls:
            item = PachongItem()
            item['two_url']=two_url
            # yield item
            yield Request(url=item['two_url'],headers=weiboSpider.headers,cookies=weiboSpider.cookies,callback=self.parse1,meta={'item':item},dont_filter=False)
    def parse1(self,response):
        item = response.meta['item']
        item['pingjia'] = response.xpath('//h1[@class="tit"]/text()').extract()
        print(item)
        yield item




