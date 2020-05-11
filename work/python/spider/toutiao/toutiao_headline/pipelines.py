# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import redis
from pymongo import MongoClient

from toutiao_headline.items import TodayHeadlineItem
from toutiao_headline.settings import MONGO_URI, MONGO_DB


class TodayHeadlinePipeline(object):
    def __init__(self):
        self.mongo_uri = MONGO_URI
        self.mongo_db = MONGO_DB

    def open_spider(self, spider):
        self.client = MongoClient(self.mongo_uri, 27017)
        self.db = self.client[self.mongo_db]

    def process_item(self, item, spider):
        self.db.toutiao.insert_one(dict(item))
        return item

    def close_spider(self, spider):
        self.client.close()



