import json
import sys
sys.path.append('..')

from neo4j.connector import neo4j_connector

# 通过cypher create语句导入数据（可以实时导入），先创建实体并记录实体变量名（比如，(CREATE(n:民族{名称:'蒙古族'}))）,在根据变量名创建实体之间的关系

test = {
    "text": "巴格达市中心于23日遭火箭弹袭击",
    "harm_type": "3_2_2_3",
    "triple": [
        {
            "subject": {
                "word": "火箭弹",
                "label": "ITEM"
            },
            "predicate": {
                "word": "袭击",
                "label": "ITEM-LOC",
                "is_mutual": False,
                "sentiment": "0",
                "tendency": "0",
                "attribute": [
                    {
                        "time": "2019-09-23"
                    }
                ]
            },
            "object": {
                "word": "巴格达市中心",
                "label": "LOC"
            }
        }
    ],
    "entity": [
        {
            "word": "巴格达",
            "label": "LOC",
            "attribute": []
        }
    ]
}



# 读取标注平台传递的数据
def read_annotation(data):
    # 将原数据分三部分：1.原文本实体；2.三元组；3.非三元组实体

    # 原文本实体，所有实体都要与原文本实体存在comeFrom关系
    main_entity = dict()
    main_entity['label'] = 'text'
    main_entity['harm_type'] = data['harm_type']
    main_entity['text'] = data['text']

    triples = data['triple']

    entities = data['entity']

    return [main_entity, triples, entities]

# 对每一个三元组构建create语句
def create_triple(triple, index):

    create_subj = 'CREATE(subj' + str(index) + ':' + triple['subject']['label'].replace('-', '') + '{word:"' + triple['subject']['word'] + '"})'

    create_obj = 'CREATE(obj' + str(index) + ':' + triple['object']['label'].replace('-', '') + '{word:"' + triple['object']['word'] + '"' + '})'


    pred_attr = ''
    for i in triple['predicate']['attribute']:
        tmp = ''
        for j in i:
            tmp += j + ':"' + i[j] + '",'

        pred_attr += tmp

    pred_attr += 'word:"' + triple['predicate']['word'] + '",'
    pred_attr += 'is_mutual:"' + str(triple['predicate']['is_mutual']) + '",'
    pred_attr += 'sentiment:"' + triple['predicate']['sentiment'] + '",'
    pred_attr += 'tendency:"' + triple['predicate']['tendency'] + '"'

    create_rela = 'CREATE(subj' + str(index) + ')-[:' + triple['predicate']['label'].replace('-', '') + '{' + pred_attr + '}]->(obj' + str(index) + ')'

    return create_subj, create_obj, create_rela

# 标注平台数据入图数据库
def cypher_annotation(entity_list):

    main_entity, triples, entities = entity_list[0], entity_list[1], entity_list[2]

    # 创建原文本的语句
    create_main = 'CREATE(main:' + main_entity['label'] + '{text:"' + main_entity['text'] + '",harm_type:"' + main_entity['harm_type'] + '"})'
    create_triples = list()

    # 创建实体的语句
    create_nodes = create_main + '\n'

    # 创建关系的语句
    create_relas = ''

    for i, item in enumerate(triples):
        create_subj, create_obj, create_rela = create_triple(item, i)
        create_nodes += create_subj + '\n' + create_obj + '\n'

        create_relas += create_rela  + '\n'

        create_relas += 'CREATE(subj' + str(i) + ')-[:comeFrom]->(main)' + '\n'
        create_relas += 'CREATE(obj' + str(i) + ')-[:comeFrom]->(main)' + '\n'

    for e_ind,item in enumerate(entities):
        entity_attr = ''
        for i in item['attribute']:
            tmp = ''
            for j in i:
                tmp += j + ':"' + i[j] + '",'

            entity_attr += tmp[:-1]

        if entity_attr is not '':
            tmp = 'CREATE(entity' + str(e_ind) + ':' + item['label'] + '{word:"' + item['word'] + '",' + entity_attr + '})'
        else:
            tmp = 'CREATE(entity' + str(e_ind) + ':' + item['label'] + '{word:"' + item['word'] + '"})'

        create_nodes += tmp + '\n'
        create_relas += 'CREATE(entity' + str(e_ind) + ')-[:comeFrom]->(main)' + '\n'

        return create_nodes, create_relas

def exec_create(create_nodes, create_relas):
    obj = neo4j_connector()
    graph = obj.connect_graph()
    stm = create_nodes + create_relas
    t = obj.exec_cypher(graph, stm)

def store_annotation(entity_list):
    create_nodes, create_relas = cypher_annotation(entity_list)
    exec_create(create_nodes, create_relas)

def process_annotation(data):
    entity_list = read_annotation(data)
    store_annotation(entity_list)

if __name__ == '__main__':

    process_annotation(test)



