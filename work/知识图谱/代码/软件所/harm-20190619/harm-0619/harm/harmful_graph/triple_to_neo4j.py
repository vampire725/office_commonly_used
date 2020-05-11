#把有害三元组放到数据库中
from py2neo import Graph,NodeMatcher,Node,Relationship,NodeMatch,RelationshipMatcher
import sys
sys.path.append('..')
from neo4j.connector import neo4j_connector
from openpyxl import Workbook
from openpyxl import load_workbook
from configparser import ConfigParser

neo_obj = neo4j_connector()
graph = neo_obj.connect_graph()

def load_data(path):                              #加载数据
    workbook = load_workbook(path)
    #sheet = workbook.get_sheet_names('Sheet1')
    booksheet = workbook.get_sheet_by_name('Sheet1')
    rows = booksheet.rows
    columns = booksheet.columns

    text=list()
    label=list()
    triple=list()
    for row in rows:
        line = [col.value for col in row]
        text.append(line[0] )       #原文本
        label.append(line[1] )        #有害类别
        triple.append(line[2] )       #三元组
    return text,label,triple

def creat_node(graph,entity):
    print("creat")
    node_event = neo_obj.create_node(graph,'person',name=entity)
    graph.create(node_event)

def creat_text(graph,text,label):
    node_event = neo_obj.create_node(graph, 'text', text=text,label=label)
    graph.create(node_event)

def get_harmdata_path():
    conf = ConfigParser()
    conf.read("../setup.conf")
    path = conf.get('data', 'harmdata')
    path = '../' + path
    return path

def import_data(tex, lab, tr):                     #解析数据



    for i in range(1,len(tr)):
        try:
            print("i=",i)

            if tr[i]:
                tr[i].replace('（', '(')
                tr[i].replace('）', ')')
                tr[i].replace(',', '，')
                tr[i]="".join(tr[i].split())             #去掉字符串所有空格
                print(tr[i])
                a=tr[i].split('),(')

                label=lab[i]
                print(label)
                text=tex[i]


                p=0                 #rel
                q=0                  #鉴定text
                for r in range(len(a)):
                    print(len(a))

                    q = find_text(graph, text, q)
                    if q == 0:
                        creat_text(graph, str(text), label)  # 创建text实体

                    a[r] = "".join(a[r].split('('))
                    a[r] = "".join(a[r].split(')'))
                    triple_list=a[r].split('-')                    #分成三元组的形式
                    sub=triple_list[0]    #第一个实体
                    rel=triple_list[1]    #关系
                    obj=triple_list[2]    #第二个实体


                    p=find_triple(graph,sub,rel,obj,p)
                    if p==1:                   #有三元组
                        print("haha")

                    else:
                        matcher = NodeMatcher(graph)
                        m_name = matcher.match(name=sub).first()  # 通过名称来匹配sub
                        n_name = matcher.match(name=obj).first()  # 通过名称来匹配obj

                        match = NodeMatch(graph)
                        m = match.where("'" + sub + "' in _.别名").first()  # 通过别名来匹配sub
                        n = match.where("'" + obj + "' in _.别名").first()  # 通过别名来匹配obj


                        if n is None:
                            if n_name is None:
                                creat_node(graph, obj)
                                n= matcher.match(name=obj).first()  # 通过名称来匹配obj
                            else:
                                n=n_name

                        if m is None:
                            if m_name is None:
                                creat_node(graph, sub)
                                m = matcher.match(name=sub).first()  # 通过名称来匹配sub
                            else:
                                m=m_name




                        m_text = matcher.match(text=text).first()  # 通过名称来匹配text
                        #print(m_text)

                        create_relation(graph,m,rel,n)                   #建立三元组及关系
                        create_relation(graph,m,'comeFrom',m_text)
                        create_relation(graph,n,'comeFrom',m_text)
        except(IndexError,ValueError):
            print("一个标注错误")
            continue

def create_relation( graph, node1, r, node2):
    # 为两个节点创建关系
    re = Relationship(node1, r, node2)
    graph.create(re)
    return re

def find_text(graph,text,q):

    matcher = NodeMatcher(graph)
    m = matcher.match(text=text).first()  # 通过名称来匹配text
    if m is None :
        q=0
    else:
        q=1
    #print(q)
    return q




def find_triple(graph,sub,rel,obj,p):                              #查重(返回0则没有，需要建立新的)

    matcher = NodeMatcher(graph)
    m_name=matcher.match(name=sub).first()         #通过名称来匹配sub
    n_name = matcher.match(name=obj).first()            #通过名称来匹配obj

    match=NodeMatch(graph)
    m=match.where("'"+sub+"' in _.别名").first()           #通过别名来匹配sub
    n = match.where("'" + obj + "' in _.别名").first()     #通过别名来匹配obj

    rel_macher = RelationshipMatcher(graph)

    if n and n_name is None:
        p=0                          #无该三元组实体obj

    elif n is None:

        n=n_name
        #print("n_y")

    if m and m_name is None:
        p=0                          #无该三元组实体sub

    elif m is None:

        m=m_name
        #print("m_y")
    if m:

        relationship=rel_macher.match(nodes={m,n}).first()


        if rel in str(relationship):
            p=1

            return p
    return p



if __name__ == '__main__':
    tex, lab, tr = load_data(get_harmdata_path())
    import_data(tex, lab, tr)







