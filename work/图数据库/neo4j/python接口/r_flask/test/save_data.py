import logging

from py2neo import Node, Relationship, Graph

from setting import HOST, NEO_USER, NEO_PASS, LOG_FILENAME, LOG_FORMAT, DATE_FORMAT


logging.basicConfig(filename=LOG_FILENAME, level=logging.INFO, format=LOG_FORMAT, datefmt=DATE_FORMAT)


def save(item: dict):
    # connect to neo4j
    graph = Graph(HOST, username=NEO_USER, password=NEO_PASS)

    # create node & relationship
    result_item = []
    if isinstance(item, dict) and item.get('entity'):
        # create single entity.
        for node in item['entity']:
            node['attribute']['word'] = node['word']
            result_item.append(Node(*node['label'], **node['attribute']))
    if isinstance(item, dict) and item.get('relation'):
        # c r
        for rel in item['relation']:
            rel['subject']['attribute']['word'] = rel['subject']['word']
            rel['object']['attribute']['word'] = rel['object']['word']
            sub = Node(*rel['subject']['label'], **rel['subject']['attribute'])
            ob = Node(*rel['object']['label'], **rel['object']['attribute'])
            rel_type = rel['predicate']['label']
            rel['predicate']['attribute']['word'] = rel['predicate']['word']
            if rel['predicate']['is_mutual'] == 0:
                result_item.append(Relationship(sub, '{}'.format(rel_type), ob, **rel['predicate']['attribute']))
            else:
                result_item.append(Relationship(sub, '{}'.format(rel_type), ob, **rel['predicate']['attribute']))
                result_item.append(Relationship(ob, '{}'.format(rel_type), sub, **rel['predicate']['attribute']))

    # save
    for r_item in result_item:
        try:
            graph.create(r_item)
            logging.info('Data stored {} successfully'.format(r_item))
        except Exception as e:
            logging.error('Because of {},Failed to save data{}'.format(e, r_item))
