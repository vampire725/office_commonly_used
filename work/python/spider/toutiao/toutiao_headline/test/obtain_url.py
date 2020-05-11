import random
import re
import time
from concurrent.futures import ProcessPoolExecutor
from multiprocessing import Process

import requests

import redis

data = {'category': 'news_hot',
        'utm_source': 'toutiao',
        'widen': 1,
        'max_behot_time': 0,
        'max_behot_time_tmp': '0',
        'tadrequire': 'true',
        'as': 'A135BC744F1C647',
        'cp': '5C4FAC36D4F7DE1',
        '_signature': 'c3AyKAAAL0L6kQuyuyhpXHNwMj'}

headers = [
    {
        'User-Agent': 'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3'},
    {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24'}
]
cookies = {'tt_webid': '6651751144152925700',
           '__tasessionId': 'pq7d34gu41548731513332',
           'csrftoken': 'a5d6e43c8ce0797f72ddbdec8f8d560f',
           'UM_distinctid': '16897977f32153-0c64ee4943f4eb8-4c312e7e-100200-16897977f3415',
           'CNZZDATA1259612802': '371781949-1548728897-%7C1548728897'}
conn = redis.StrictRedis(host='localhost', port="8087", db="0", decode_responses=True)
url = 'https://www.toutiao.com/api/pc/feed/'


def get_info(info_dict):
    try:
        title = info_dict['title']
        group_id = info_dict['group_id']
        print(group_id)
        return title, group_id
    except Exception as e:
        print(e)
        print('________')


def obtain_urls():
    web_data = requests.get(url, headers=random.choice(headers), verify=False, params=data).json()
    try:
        length = len(web_data['data'])
        print(length)
        for i in range(length):
            yield get_info(web_data['data'][i])
    except Exception as e:
        print(e)
        print('________')


def save_href():
    for title, group_id in obtain_urls():
        conn.sadd('group_id', group_id)


def xh():
    while True:
        save_href()
        time.sleep(3)



xh()

