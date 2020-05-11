import re

from py2neo import Node, Relationship, Graph, NodeMatcher

graph = Graph("bolt://60.28.140.210:10585", username='neo4j', password='asd123456')

matcher = NodeMatcher(graph)

# nodes = matcher.match(*['ontology'])
# with open ('ontology_entity.txt','a')as f:
#     for i in nodes:
#         match_relation = graph.match(nodes=(i,), r_type='rel')
#         count = 0
#         for h in match_relation:
#             if h['relationship']=='include':
#                 count+=1
#                 print(h.start_node['name'],h.end_node['name'])
#                 f.write(str(h.start_node['name'])+" "+"include"+"   "+str(h.end_node['name'])+'\n')
#             else:
#                 pass
#         print(i['name'],count)
#         f.write(str(i['name'])+"    "+str(count)+'\n')
#     f.close()

a_list = []
rule_nodes = []
with open('rules.txt', 'r')as r:
    lines = r.readlines()
    for line in lines:
        node = re.findall('[（\[(](.*?)[)\]）]', line)
        print(node)
        for i in node:
            if i:
                if i != 'include':
                    a_list.append(i)
            else:
                pass
    r.close()
list_set = list(set(a_list))
for ru in list_set:
    nodes = matcher.match(*['ontology'],**{'name':f'{ru}'})
    for no in nodes:
        rule_nodes.append(no)
with open('rule_ontology_entity.txt','a')as h:
    for rule_node in rule_nodes:
        match_relation = graph.match(nodes=(rule_node,), r_type='rel')
        count = 0
        for m in match_relation:
            if m['relationship']=='include':
                count+=1
                print(m.start_node['name'],m.end_node['name'])
                h.write(str(m.start_node['name'])+" "+"include"+"   "+str(m.end_node['name'])+'\n')
            else:
                pass
        print(rule_node['name'],count)
        h.write(str(rule_node['name'])+"    "+str(count)+'\n')
    h.close()