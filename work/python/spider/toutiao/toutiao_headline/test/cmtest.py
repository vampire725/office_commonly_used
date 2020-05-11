import multiprocessing
import re
import threading

import redis
from selenium import webdriver
from bs4 import BeautifulSoup as BS
import time

from selenium.webdriver.chrome.options import Options

chrome_options = Options()
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')
chrome_options.add_argument("--disable-gpu")
chrome_options.add_argument('--headless')

browser = webdriver.Chrome(chrome_options=chrome_options)
conn = redis.StrictRedis(host='localhost', port="8085", db="0", decode_responses=True)

pipe = conn.pipeline()


def get_link(n, n1):
    """
    拿到每篇文章的链接
    :return:
    """
    url = 'https://www.toutiao.com/ch/news_hot/'
    browser.get(url)
    # 设置隐式等待，最多等待10s
    browser.implicitly_wait(5)
    # 模拟鼠标拖动
    for x in range(n, n1):
        js = "var q=document.documentElement.scrollTop=" + str(x * 700)
        browser.execute_script(js)
        time.sleep(2)
    time.sleep(4)
    # 链接数组
    links = []
    response = browser.page_source
    soup = BS(response, 'lxml')
    groups = soup.find_all(class_='link')
    for group in groups:
        # start_url = 'https://www.toutiao.com'
        # print(group)
        group_url = group.attrs['href']
        group_id = re.findall('\d+', group_url)
        group_str = group_id[0]

        links.append(group_str)
    print(len(links))
    return links


def input_redis(n, n1):
    count = 0
    for i in get_link(n, n1):
        count += 1
        conn.sadd('group_id', i)
    print(count)


def thread_main():
    thread_list = []
    for i in range(0, 4):
        t = threading.Thread(target=input_redis, args=(0, 40))
        thread_list.append(t)

    start = time.time()
    print(' [+] much thread start')
    for i in thread_list:
        i.start()
    for i in thread_list:
        i.join()
    print(' [-] much thread use ', time.time() - start, 's')


def process_main():
    p = multiprocessing.Pool(4)
    for i in range(0, 4):
        p.apply_async(func=input_redis, args=(0, 40))
    start = time.time()
    print(' [+] much process start')
    p.close()
    p.join()
    print(' [-] much process use ', time.time() - start, 's')


if __name__ == '__main__':
    count = 0
    while True:
        start_time = time.time()
        for i in get_link(0, 50):
            conn.sadd('group_id', i)
        count += 1
        pipe.execute()
        print(time.time()-start_time)
        if count > 10:
            break
    # print("[++]When")
    # thread_main()
    # process_main()
    pipe.execute()
    browser.quit()
    browser.close()
