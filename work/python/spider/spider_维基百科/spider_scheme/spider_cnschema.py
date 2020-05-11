# -*- coding: utf-8 -*-
import requests
import json
from array import array

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'
                  ' AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36'
}

url = 'http://cnschema.org/data2/classes.json'

respose = requests.get(url, headers=headers,verify=False)
length0 = len(respose.json()['children'])
for i in range(length0):
    name0 = respose.json()['children'][i]['name']
    nameZh0 = respose.json()['children'][i]['nameZh']
    try:
        h = respose.json()['children'][i]['children']
        length1 = len(h)
    except:
        continue
    list = []
    list2 = []
    try:
        for i in range(length1):
            name1 = h[i]['name']
            nameZh1 = h[i]['nameZh']
            dict = {name1: nameZh1}
            list.append(dict)
        for n in list:
            print(n)
    except:
        continue
    try:

        for i in range(length1):
            p = h[i]['children']
            length2 = len(p)
            print(length2)
            for j in range(length2):
                name2 = p[j]['name']
                nameZh2 = p[j]['nameZh']
                dict1 = {name2: nameZh2}
                list2.append(dict1)
        print(list2)

    except:
        continue
    print(name0,nameZh0)


