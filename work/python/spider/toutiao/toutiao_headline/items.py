# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class TodayHeadlineItem(scrapy.Item):
    title = scrapy.Field()
    source = scrapy.Field()
    content = scrapy.Field()
    publishtime = scrapy.Field()
    origin = scrapy.Field()
    # editor = scrapy.Field()



