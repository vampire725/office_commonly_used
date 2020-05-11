# !/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time     : 2019/6/11 11:02
# @Author   : Sz
# @File     : t_list_dict.py
a = {'x': 1, 'y': 'abc', 'z': 3333}
b = {'x': 1, 'y': 'abc', 'z': 3333}
c = {'x': 3, 'y': 'ddd', 'z': 3333}
d = {'f': 1, 'g': 'h', 'z': 3333}

l1 = [a, b, c, d]

l2 = []
for x in l1:
    if x not in l2:
        l2.append(x)

print(l2)
