# !/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time     : 2019/7/16 9:52
# @Author   : Sz
# @File     : answer.py
import csv

# cell 1
### BEGIN SOLUTION
from bin import my_lib

with open('Beerwings.csv', 'r', newline='') as file:
    csv_data = csv.reader(file)
    # 不确定要不要把表头读进去，需要带表头就注释掉下一行，后面需要自己处理一下
    header = next(csv_data)
    bar_data = [tuple(raw) for raw in csv_data]

# cell 2
beer_dict = dict()
### BEGIN SOLUTION
for raw in bar_data:
    # 统计各类啤酒消费盎司出现的次数，字典里没有的则初始化为1，有的则+1
    beer_dict[raw[2]] = 1 if raw[2] not in beer_dict.keys() else beer_dict[raw[2]] + 1
# 按照字典的value逆序取（啤酒盎司数：出现次数）
sorted_bear_list = [(k, v) for k, v in sorted(beer_dict.items(), reverse=True, key=lambda d: d[1])]

# cell 3



def bubble_sort(unordered_list):
    ### BEGIN SOLUTION
    # my_lib.bubble每次确定一个数的排位，循环调用直到所有数字排位确定
    sorted_list = unordered_list
    for i in range(len(unordered_list) - 1):
        sorted_list = my_lib.bubble(unordered_list)
    ### END SOLUTION
    return sorted_list


# assert bubble_sort([]) == []
# assert bubble_sort([1]) == [1]
# assert bubble_sort([1, 2, 3, 4]) == [1, 2, 3, 4]
assert bubble_sort([4, 3, 2, 1]) == [1, 2, 3, 4]

# cell 4


def insertion_sort(unsorted_list):
    ### BEGIN SOLUTION
    # 从未排序列表挨个取值，插入到新列表正确位置，直到未排序列表为空
    sorted_list = []
    for item in unsorted_list:
        sorted_list = my_lib.insert_item(item, sorted_list)
    ### END SOLUTION
    return sorted_list


assert insertion_sort([]) == []
assert insertion_sort([1]) == [1]
assert insertion_sort([1, 2, 3, 4]) == [1, 2, 3, 4]
assert insertion_sort([4, 3, 2, 1]) == [1, 2, 3, 4]

# cell 5
"""
The average time complexity of both algorithms is same(O(n*n) as avg/worst,O(n) as best).
But insertion_sort above is better than bubble_sort above cause it's code is more reasonable.

理论上讲，冒泡排序和插入排序的时间复杂度是一样的，但是上面的代码，冒泡排序每次都是遍历完整list，插入排序是拿一个插一个，
所以插入排序效率更高。

下面是证明，分别统计上面代码跑10000次排序的平均用时。在我电脑上插入排序平均快了0.025秒左右。

import timeit

test_list_1 = [1, 2, 3, 4, 5, 6, 7]
test_list_2 = [7, 6, 5, 4, 3, 2, 1]
test_list_3 = list(set(test_list_1))

avg_time = dict()
bubble_sort_import = "from __main__ import bubble_sort,test_list_1,test_list_2,test_list_3"
avg_time['bubble_sort'] = [
    timeit.Timer('bubble_sort(test_list_1)', bubble_sort_import).timeit(10000),
    timeit.Timer('bubble_sort(test_list_2)', bubble_sort_import).timeit(10000),
    timeit.Timer('bubble_sort(test_list_3)', bubble_sort_import).timeit(10000),
]
insertion_sort_import = "from __main__ import insertion_sort,test_list_1,test_list_2,test_list_3"
avg_time['insertion_sort'] = [
    timeit.Timer('insertion_sort(test_list_1)', insertion_sort_import).timeit(10000),
    timeit.Timer('insertion_sort(test_list_2)', insertion_sort_import).timeit(10000),
    timeit.Timer('insertion_sort(test_list_3)', insertion_sort_import).timeit(10000),
]
print(avg_time)
"""

# cell 6
### BEGIN SOLUTION
with open('dictionary.txt', 'rt') as file:
    word_list = [word.strip('\n') for word in file.readlines()]


# cell 7a
def equal_ends(word):
    ### BEGIN SOLUTION
    # 判断首尾字母是否相同，不区分大小写（也就是都转化成大写再比较）
    return True if word[0].upper() == word[-1].upper() else False


assert equal_ends("abba") == True
assert equal_ends("panda") == False
assert equal_ends("oboe") == False
assert equal_ends("Alaska") == True

# cell 7b
matching_ends = dict()

### BEGIN SOLUTION
for word in word_list:
    if equal_ends(word):
        matching_ends[word[0]] = 1 if word[0] not in matching_ends.keys() else matching_ends[word[0]] + 1


# cell 8

def are_anagrams(word1, word2):
    ### BEGIN SOLUTION
    return True if sorted(word1) == sorted(word2) else False


assert are_anagrams("dad", "add") == True
assert are_anagrams("yoyo", "yolo") == False
assert are_anagrams("tomriddle", "voldemort") == False
assert are_anagrams("tom.marvolo.riddle", "iam.lord.voldemort") == True

# cell 9
### BEGIN SOLUTION
from itertools import permutations

"""
!!!
警告，直接对完整word_list执行会很慢，有可能会卡死。
9个不重复的字符组成的字符串就有362880种排列，所以...就像题目说的"Choose your key Carefully."
!!!
"""
import random

# 从word_list随便取100个词，选出其中不超过5个字符的作为测试集
small_word_list = [word for word in random.sample(word_list, 100) if len(word) < 6]
anagrams_dict = {k: list(permutations(k)) for k in iter(small_word_list)}
print(anagrams_dict)
# cell 10
### BEGIN SOLUTION
# 新建一个{词：排列数}的字典，将排列数和字典值最大值相同的词放入letters
anagrams_num_dict = {k: len(v) for k, v in anagrams_dict.items()}
letters = [k for k in anagrams_num_dict.keys() if anagrams_num_dict[k] == max(anagrams_num_dict.values())]
