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
    name = 'weibo2'
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0',
               'Accept': 'application/json, text/plain, */*', }
    cookies = {
        'Cookie': 'SUHB=010G1MBOraJvor; SCF=AuXoFDE9Oi002VLzxNkmxhkAdCltbhcEccAJR2xEiatE2RGeiRGjjXibMpM9tzNEe6X6nwSzwrQ7qpN98UMlin0.; _T_WM=31e79b8d84829418937c47a2f986208c; SUB=_2A252Ma5pDeRhGeNN61oS8S3IyDyIHXVV3TIhrDV6PUJbkdAKLVP_kW1NSckhJXIMj51vad8fTQJDkv9M90MxriQB; M_WEIBOCN_PARAMS=luicode%3D10000012%26lfid%3D1005055308313430_-_FOLLOWERS%26featurecode%3D20000320%26fid%3D1005051802742227%26uicode%3D10000011; MLOGIN=1; H5_INDEX=3; H5_INDEX_TITLE=%E5%A4%A7%E8%84%B8%E7%8C%AB%E4%B8%8D%E7%88%B1%E5%90%83%E9%B1%BC%E7%88%B1%E5%90%83%E8%82%89-; WEIBOCN_FROM=1110006030'}
    params = {
        'containerid': '1076031802742227',
        'featurecode': '20000320',
        'lfid': '1005055308313430_-_FOLLOWERS',
        'luicode': '10000012',
        'type': 'uid',
        'uid': '1802742227',
        'value': '1802742227',
    }

    @property
    def start_requests(self):
        for pageNo in range(1,10):
            weiboSpider.params['page'] = pageNo
            url = 'https://m.weibo.cn/api/container/getIndex'
            yield Request(url=url, headers=weiboSpider.headers, cookies=weiboSpider.cookies,params=weiboSpider.params, callback=self.parse,dont_filter=False)
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




