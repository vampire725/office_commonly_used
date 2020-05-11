import pymysql
import requests



def pysql(sql,args):

        conn = pymysql.Connection(host='localhost', port=3306, user='root', password='000725', database='test',charset='utf8mb4')
        cursor = conn.cursor()
        cursor.execute(query=sql, args=args)
        list = cursor.fetchone()
        conn.commit()
        cursor.close()
        conn.close()
        return list


def pysql_select(sql):

        conn = pymysql.Connection(host='localhost', port=3306, user='root', password='000725', database='water_amvy',charset='utf8mb4')
        cursor = conn.cursor()
        cursor.execute(query=sql)
        list = cursor.fetchall()
        conn.commit()
        cursor.close()
        conn.close()
        return list
