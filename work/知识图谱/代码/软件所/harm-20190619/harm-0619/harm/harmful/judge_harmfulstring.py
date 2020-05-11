# !/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import print_function, unicode_literals
import sys
import xlrd
import xlwt

sys.path.append("..")
import jieba
import os
import numpy as np
import pandas as pd
from openpyxl import Workbook
from openpyxl import load_workbook
import jieba.posseg as pseg
from py2neo import Graph, Node, Relationship, NodeMatcher
from excelReader.excelReader import excel_reader
from configparser import ConfigParser
import re

conf = ConfigParser()
conf.read("../setup.conf")

netmsg = '../' + conf.get('data', 'netmsg')
jieba.load_userdict(netmsg)
section = conf.sections()
host = conf.get('graph_db', 'host')
port = conf.get('graph_db', 'port')
user = conf.get('graph_db', 'user')
passwd = conf.get('graph_db', 'passwd')

ip = 'http://' + host + ':' + port

graph = Graph(ip, username=user, password=passwd)

# 把关键词添加到分词库中
# 把关键词相关的句子进行分词以后存入到对应行

leader = ["1_1_1", "1_1_2", "1_1_3", "1_1_4", "1_1_5",
          "1_2_1", "1_2_2", "1_2_3", "1_2_4", "1_2_5",
          "1_3_1", "1_3_2", "1_3_3"]
sbsk = ["3_1_1", "3_1_2", "3_1_3",
        "3_2_1", "3_2_2", "3_2_3", "3_2_4", "3_2_5", "3_2-6", "3_2_7", "3_2_8",
        "3_3_1", "3_3_2", "3_3_3",
        "3_4_1", "3_4_2"]
religion = ["4_1_1", "4_1_2", "4_1_3", "4_1_4", "4_1_5",
            "4_2_1", "4_2_2", "4_2_3", "4_2_4", "4_2_5", "4_2_6", "4_2_7", "4_2_8",
            "4_3_1", "4_3_2", "4_3_3", "4_3_4", "4_3_5", "4_3_6",
            "4_4_1", "4_4_2", "4_4_3", "4_4_4", "4_4_5",
            "4_5_1", "4_5_2", "4_5_3", "4_5_4", "4_5_5"]


def read_data():  # 读取所有的关键词
    data = xlrd.open_workbook(r'有害关键词组1557909097582.xlsx')
    table = data.sheets()[0]

    start = 2  # 开始的行
    end = 1033  # 结束的行

    rows = end - start
    i = 0
    list_values = []
    f = open(netmsg, 'w', encoding='utf-8')
    temp = ""
    for x in range(start, end):
        values = []
        row = table.row_values(x)
        for i in range(0, 1):
            values.append(row[1])
            temp = temp + '\n' + row[1]

    f.write(temp)

    datamatrix = np.array(list_values)

    f.close()


def add_word(word):  # 查重填词
    i = find_key_word(word)
    if i == 1:  # 有重复
        i = 1
    else:
        jieba.add_word(word)  # 添加


def find_key_word(word):  # 找分词库是否有重复的词
    i = 0
    f = open(netmsg, 'r', encoding='utf-8')
    a = f.readline()
    while a:
        if a == word:
            i = 1  # 词库中出现该词
            break
        else:
            i = 0  # 词库中无该词
        a = f.readline()

    f.close()
    return i


def jieba_cut(text):
    words = jieba.cut(text)
    t = '/'.join(words)
    return t


# 对分词结果进行切割
def str_to_list(String):
    List = String.split('/')
    return List


def search_entity(List_res, graph):
    exp_res = []
    for item in List_res:
        dict_item = dict()
        dict_item["keyword"] = item
        found_res = graph.run(
            'MATCH (a:ontology{})-[:rel{relationship:"include"}]->(:entity{name:"' + item + '"}) RETURN a').data()

        if found_res:
            dict_item["upper_ontology"] = list()

            for i in list(found_res):
                dict_item["upper_ontology"].append(dict(i['a']))

        else:
            dict_item["upper_ontology"] = 'noinfo'
        exp_res.append(dict_item)

    return exp_res


# 分词结果和规则进行对比
def compare(exp_res, rule_processed_keyword):
    harm_flag = False

    rule_index = 0

    text_keys = []  # 返回匹配到的文本下标

    for text_index, text_item in enumerate(exp_res):

        # 提取当前文本关键的keyword，上级本体的name等值，用于与规则判断
        tmp = []
        tmp.append(text_item['keyword'])
        if text_item['upper_ontology'] != 'noinfo':
            for i in text_item['upper_ontology']:
                tmp.append(i['name'])

        if rule_processed_keyword[rule_index] in tmp:
            rule_index += 1

            text_keys.append(text_index)
            if rule_index == len(rule_processed_keyword):
                harm_flag = True
                break
        else:
            rule_index = 0
            text_keys = []

    return harm_flag, text_keys


def judge_harmfultype_test(text, rule, harmful_type, exp_res):
    keyword = []
    rule = rule.strip()
    # 提取规则信息
    rela_flag = True  # True 代表关系栏不为空， False代表为空
    if '[]' in rule:
        rela_flag = False

    rule_processed_keyword = re.findall('[（\[(](.*?)[\]）)]', rule)

    while '' in rule_processed_keyword:
        rule_processed_keyword.remove('')

    while 'include' in rule_processed_keyword:
        rule_processed_keyword.remove('include')

    # 分词结果和规则信息对比，相同部分存入compare_res
    harm_flag, text_keys = compare(exp_res, rule_processed_keyword)

    if harm_flag:
        harmful_type_res = harmful_type
        for i in text_keys:
            keyword.append(exp_res[i]['keyword'])
    else:
        harmful_type_res = 'N'
        keyword = []
        rule = 'N'

    return text, harmful_type_res, keyword, rule


def judge_harmfultype(text, rule, harmful_type, exp_res):
    keyword = []
    rule = rule.strip()
    # 提取规则信息

    rule_processed_keyword = re.findall('[（\[(](.*?)[\]）)]', rule)

    while '' in rule_processed_keyword:
        rule_processed_keyword.remove('')

    while 'include' in rule_processed_keyword:
        rule_processed_keyword.remove('include')

    # 分词结果和规则信息对比，相同部分存入compare_res
    compare_res = []

    for ktem in rule_processed_keyword:
        for n, ntem in enumerate(exp_res):
            if ktem == ntem['keyword']:
                compare_res.append(ktem)
                keyword.append(ntem['keyword'])

            else:
                if ntem['upper_ontology'] != 'noinfo':
                    for upot in ntem['upper_ontology']:
                        if ktem == upot['name']:
                            compare_res.append(ktem)
                            keyword.append(ntem['keyword'])
                            break
                        else:
                            if isinstance(upot['aliases'], list):
                                for qtem in upot['aliases']:
                                    if ktem == qtem:
                                        compare_res.append(ktem)
                                        keyword.append(ntem['keyword'])
                                        break
                            else:
                                for qtem in [upot['aliases']]:
                                    if ktem == qtem:
                                        compare_res.append(ktem)
                                        keyword.append(ntem['keyword'])
                                        break

    flag = False

    if set(rule_processed_keyword) <= set(compare_res):
        flag = True

    if flag:
        harmful_type_res = harmful_type

    else:
        keyword = []
        harmful_type_res = 'N'

    return text, harmful_type_res, keyword, rule


def read_harmfultype_rules(rules, text):
    word_cut = jieba_cut(text)
    List_res = str_to_list(word_cut)
    exp_res = search_entity(List_res, graph)

    for i in rules:
        text, harmful_type_res, keyword, rule = judge_harmfultype(text, i[0], i[1], exp_res)
        if harmful_type_res != 'N':
            break

    return text, harmful_type_res, keyword, rule


# 一次性读取所有规则
def rule_model():
    rule_path = get_rules_path()
    rules = []
    with open(rule_path, 'r') as f:
        for line in f:
            line_split = line.split('\t')
            harmful_type = line_split[0]
            rule = line_split[1]
            rules.append((rule, harmful_type))
    return rules


def identify_harmful(str_input):
    rules = rule_model()

    text, harmful_type_res, keyword, rule = read_harmfultype_rules(rules, str_input)

    return text, harmful_type_res, keyword, rule


def get_rules_path():
    path = conf.get('rules', 'path')
    path = '../' + path
    return path


if __name__ == '__main__':
    text, harmful_type_res, keyword, rule = identify_harmful("人要脸树要皮，中国gcd最不要脸皮   37楼")
    print(text, harmful_type_res, keyword, rule)
