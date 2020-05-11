#!/usr/bin/env python3
# coding:utf-8
from py2neo import Graph, Node, Relationship, NodeMatcher
from connector import neo4j_connector
import json
import csv
import re
import os, sys
from configparser import ConfigParser

# GV:
paths = {}
paths_rel = {}


# 获得路径下所有文件信息

def get_import_path():
    conf = ConfigParser()
    conf.read("../setup.conf")
    path = conf.get('universalKg', 'importpath')
    path = path
    return path


def find_path():
    pa = get_import_path()
    dirs = os.listdir(pa)
    for i in dirs:
        if len(i.split('.')) > 1:
            if i.split('.')[1] == 'csv':
                paths[i] = i.split('.')[0]


# 导入nodes csv
def putnodes_csv(path, label):
    neo_obj = neo4j_connector()
    graph = neo_obj.connect_graph()
    stri = label

    if not re.search('ontology', label) and not re.search('Rela', label):
        label = 'entity'

    # sta_use = 'LOAD CSV with headers FROM "file:/' + path + '" AS line create (:' + label + '{wiki_id:line.ori_id,name:line.name,descriptions:line.descriptions,aliases:line.aliases,name_zh:line.name_zh,descriptions_zh:line.descriptions_zh,aliases_zh:line.aliases_zh})'

    sta_use_uni = 'LOAD CSV with headers FROM "file:/' + path + '" AS line Merge (new{wiki_id:line.ori_id}) on create set new:' + label + ', new.wiki_id = line.ori_id, new.name=line.name, new.descriptions=line.descriptions, new.aliases=line.aliases, new.name_zh = line.name_zh, new.descriptions_zh=line.descriptions_zh, new.aliases_zh=line.aliases_zh'

    if re.search('Rela', label):
        paths_rel[path] = label
    else:
        print('Start Load csv:' + stri)
        neo_obj.exec_cypher(graph, sta_use_uni)


# 导入 rel csv
def putrel_csv(path, label):
    neo_obj = neo4j_connector()
    graph = neo_obj.connect_graph()

    print('Start Load csv:' + label)
    sta_rel = 'load csv with headers from "file:/' + path + '" as line match (from {wiki_id:line.from_id}) match (to {wiki_id:line.to_id}) create (from)-[r:rel{relationship:line.relationship}]->(to)'
    neo_obj.exec_cypher(graph, sta_rel)


# 主函数
if __name__ == '__main__':
    find_path()
    for i in paths.keys():
        putnodes_csv(i, paths[i])
    # paths_rel['addRela.csv'] = 'addRela'
    for i in paths_rel.keys():
        putrel_csv(i, paths_rel[i])
