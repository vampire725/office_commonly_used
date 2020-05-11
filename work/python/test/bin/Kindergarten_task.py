import csv
import re
from itertools import permutations

from bin.my_lib1 import *

bar_data = []
with open('Beerwings.csv', 'r', newline='')as f:
    csv_data = csv.DictReader(f)


    # Problem1
    def add_each_column_to_list(reader):

        # 自动生成列表
        lists = [[] for _ in range(len(reader.fieldnames))]

        for row in reader:
            for i in lists:
                i.append(row[reader.fieldnames[lists.index(i)]])
        for h in lists:
            bar_data.append(tuple(h))


    # Problem2
    def sort_by_category(reader):
        beer_dict = dict()

        for row in reader:
            beer_dict[row['Beer']] = 1 if row['Beer'] not in beer_dict.keys() else beer_dict[row['Beer']] + 1
        sorted_bar_list = (sorted(beer_dict.items(), key=lambda d: d[1], reverse=True))
        print(sorted_bar_list)
    # sort_by_category(csv_data)

f.close()


# Problem3
def bubble_sort(unordered_list):
    for i in range(len(unordered_list) - 1):
        j = 0
        while j < len(unordered_list) - 1:
            a, b = unordered_list[j:j + 2]
            if a > b:
                unordered_list[j:j + 2] = b, a
            j += 1
    print(unordered_list)


list_test = [1, 8, 43, 0]
print(bubble_sort(list_test))


# Problem4
# 从未排序列表挨个取值，插入到新列表正确位置，直到未排序列表为空


# Problem5
# .....不想说话

# Problem6
def read_txt_to_list():
    with open('dictionary.txt', 'r') as file:
        word_list = [line.strip('\n') for line in file.readlines()]
    file.close()
    return word_list


# read_txt_to_list()

# 第7题a
def start_equal_end(word):
    split_word = list(word)
    return True if split_word[0].upper() == split_word[-1].upper() else False


# 第7题b
word_list = ['o_hello', 'd_add', 'pp', 'y_silly', 'o_hello', 'pp', 'pp']
word_dict = dict()
for word in word_list:
    if start_equal_end(word):
        word_dict[word] = 1 if word not in word_dict.keys() else word_dict[word] + 1
word_dict = sorted(word_dict.items(), key=lambda d: d[1], reverse=True)
print(word_dict)


# 第8题

def are_anagrams(word1, word2):
    return True if sorted(word1) == sorted(word2) else False


# are_anagrams('dad','add')

# 第9题

def dict_anagrams(word_list2):
    split_word_list = [sorted(word2) for word2 in word_list2]
    dict_word = {}
    for hh in split_word_list:
        value_list = [key for key, value in zip(word_list2, split_word_list) if hh == value]
        item = {"".join(hh): value_list}
        dict_word.update(item)
    return dict_word


dict_anagrams(['hello', 'llohe', 'word', 'wrod', 'add', 'lohel', 'dad'])


# 第九题可能会错意了，但是没事

def combination(random_word_list):
    a_dict = {key: list(permutations(key)) for key in random_word_list}
    return a_dict


# 第10题

def sorted_word_num():
    word_dict = dict_anagrams(['hello', 'llohe', 'word', 'wrod', 'add', 'lohel', 'dad'])
    sorted_the_most_word = dict(sorted(word_dict.items(), key=lambda d: len(d[1]), reverse=True))
    print(sorted_the_most_word)


sorted_word_num()
