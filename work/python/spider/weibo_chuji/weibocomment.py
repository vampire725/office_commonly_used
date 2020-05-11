import urllib3
from .sq import pysql
from .weibouser import main
import re
import requests
from .time_set import *
from .get_cookie import *
import threading

urllib3.disable_warnings()

conctrol = 0


#
def scrawl_reviews(id):
    cookies1 = {
        'Cookie': get_ck()}
    headers1 = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) '
    }
    url1 = 'https://m.weibo.cn/api/comments/show?id={}&page=1'.format(id)
    response1 = requests.get(url=url1, cookies=cookies1, headers=headers1, verify=False)
    number1 = response1.json()['data']['total_number']  # 评论总条数
    page1 = number1 // 9
    result = []
    for j in range(1, 3):
        url2 = 'https://m.weibo.cn/api/comments/show?id={}&page={}'.format(id, j)
        response2 = requests.get(url=url2, headers=headers1, cookies=cookies1, verify=False)
        a = response2.json()['data']['data']

        for h in range(len(a)):

            id2 = a[h]['user']['id']
            screen_name2 = a[h]['user']['screen_name']
            text1 = a[h]['text']

            if a[h]['created_at'] is not None:
                created_at = a[h]['created_at']
            else:
                created_at = '无'
            resultcontent = {
                'id': id2,
                'screen_name': screen_name2,
                'text': text1,
                'created_at': created_at,

            }
            result.append(resultcontent)
    return result


def scrawl():
    cp = re.compile(r'(<.*>)')
    url = 'https://m.weibo.cn/api/container/getIndex'
    cookies = {
        'Cookie': 'SUHB=010G1MBOraJvor;SCF=AuXoFDE9Oi002VLzxNkmxhkAdCltbhcEccAJR2xEiatE2RGeiRGjjXibMpM9tzNEe6X6nwSzwrQ7qpN98UMlin0.; _T_WM=31e79b8d84829418937c47a2f986208c;SUB=_2A252Ma5pDeRhGeNN61oS8S3IyDyIHXVV3TIhrDV6PUJbkdAKLVP_kW1NSckhJXIMj51vad8fTQJDkv9M90MxriQB; H5_INDEX=3; H5_INDEX_TITLE=%E5%A4%A7%E8%84%B8%E7%8C%AB%E4%B8%8D%E7%88%B1%E5%90%83%E9%B1%BC%E7%88%B1%E5%90%83%E8%82%89-; MLOGIN=1; WEIBOCN_FROM=1110006030'}
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0',
    }
    params = {
        'containerid': '1076031289986124',
        'featurecode': '20000320',
        'lfid': '1005055308313430_-_FOLLOWERS',
        'luicode': '10000012',
        'type': 'uid',
        'uid': '1289986124',
        'value': '1289986124',
    }
    response = requests.get(url=url, params=params, cookies=cookies, headers=headers, verify=False)
    data = response.json()['data']
    number = data['cardlistInfo']['total']  # 微博总条数
    page = number // 13 + 1  # 页 总数
    for pageNo in range(1, page):
        params['page'] = pageNo
        response = requests.get(url=url, params=params, cookies=cookies, headers=headers, verify=False)
        result = []

        for i in range(len(response.json()['data']['cards'])):  # json()['data']['cards'] 当前页微博总条数

            global conctrol
            if conctrol == 1:
                conctrol = 0
                print('不爬了')
                return

            # try:
            id = response.json()['data']['cards'][i]['mblog']['id']
            text = response.json()['data']['cards'][i]['mblog']['text']
            comments_count = response.json()['data']['cards'][i]['mblog']['comments_count']
            resultcontent = {
                'id': id,
                'text': text,
                'comments_count': comments_count

            }
            result.append(resultcontent)
            for j in result:
                print(j)

            sql = 'insert into water_amvy_wb_weibo(id,text,comments_count) values(%s,%s,%s)'

            args = [j['id'], j['text'], j['comments_count']]

            pysql(sql, args)
            infos = scrawl_reviews(id)

            for info in infos:

                if re.sub(cp, '', info['text']) != None:

                    info['text'] = re.sub(cp, '', info['text'])
                else:
                    info['text'] = '无'

                print(info)
                sql = 'insert into water_amvy_comment(userid,username,txt,time) values(%s,%s,%s,%s)'
                args = [info['id'], info['screen_name'], info['text'], set_time(info['created_at'])]

                pysql(sql, args)  # 调用sq文件下pysql函数  存入数据到com
                main(info['id'])  # 调用weibouser文件下main函数ment表中

            result = []  # 重置列表
            # except:
            #     continue


dict = 'something awful'  # Bad Idea... pylint: disable=redefined-builtin


def pr():
    global conctrol
    conctrol = 1
