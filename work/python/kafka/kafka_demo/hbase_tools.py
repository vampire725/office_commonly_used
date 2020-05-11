# !/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time     : 2018/12/26 22:27
# @Author   : gpp

from thrift.transport import TSocket
from thrift.protocol import TBinaryProtocol
from hbase import THBaseService
from hbase.ttypes import *

from setting import *
from kafka_consumer import multi_consume


class Hbase(object):
    def __init__(self, hbase_ip, hbase_port, table: str, row_key: str):
        transport = TSocket.TSocket(hbase_ip, hbase_port)
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        transport.open()
        client = THBaseService.Client(protocol)
        self.hbase_ip = hbase_ip
        self.hbase_port = hbase_port
        self.client = client
        self.transport = transport
        self.table = table.encode()
        # get = TGet()
        # get.row = row_key.encode()
        self.row_key = row_key.encode()

    def ping(self):
        return self.transport.isOpen()

    def put_data_to_hbase(self) -> None:
        # 2 process data
        # column_value1 = TColumnValue('cf'.encode(), 'title'.encode(), 'huang_jin_shi_dai'.encode())
        # column_value2 = TColumnValue('cf'.encode(), 'author'.encode(), 'wang_xiao_bo'.encode())
        # column_value3 = TColumnValue('cf'.encode(), 'content'.encode(), 'wen_hua_da_ge_ming'.encode())
        # column_values = [column_value1, column_value2, column_value3]
        #
        # # 3 do`
        # t_put = TPut(self.row_key, column_values)
        # result = self.client.put(self.table, t_put)
        for r, v in zip(range(100), range(100)):
            c = 'title' + '{}'.format(str(v))
            h = 'huang_jin_shi_dai' + '{}'.format(str(v))
            column_value = TColumnValue('cf'.encode(), c.encode(), h.encode())
            rowkey = 'row' + '{}'.format(str(r))
            column_values = [column_value]
            t_put = TPut(rowkey.encode(), column_values)
            self.client.put(self.table, t_put)
        # 4 return
        # return result

    def delete_by_hbase(self):
        # 2 process_data
        column_value1 = TColumnValue('cf'.encode(), 'author'.encode())
        column_value2 = TColumnValue('cf'.encode(), 'content'.encode())
        column_values = [column_value1, column_value2]
        t_delete = TDelete(self.row_key, column_values)

        # 3 do
        result = self.client.deleteSingle(self.table, t_delete)
        return result

    def get_data_by_hbase(self):
        data = multi_consume()
        for i in data:
            for j in range(len(i)):
                table = i[j]['infoTableName ']
                table = table if type(table) == bytes else table.encode()
                infoRowkey: str = i[j]["infoRowkey"]
                value = {}
                get = TGet()
                get.row = infoRowkey if type(infoRowkey) == bytes else infoRowkey.encode()
                if self.client.exists(table, get):
                    result = self.client.get(table, get)
                    # 遍历H_base单条数据
                    for column in result.columnValues:
                        value[column.qualifier.decode()] = column.value.decode(errors="ignore")
                    print(value)
                else:
                    continue
        # return value


if __name__ == '__main__':
    H = Hbase(HBASE_IP, HBASE_PORT, HBASE_TABLE_NAME, ROW_KEY)
    print(H.ping())
    # H.put_data_to_hbase()
    H.get_data_by_hbase()
    # H.delete_by_hbase()
    # print("连接H_base成功")
    # print("获取数据")
    # print(values1)
    # # contents = ColumnDescriptor(name='cf:', maxVersions=1)
    # # get_client().createTable('test', [contents])
    # c = get_client()
    # get = TGet()
    # # print(client.openScanner(H_BASE_TABLE_NAME,TScan))
