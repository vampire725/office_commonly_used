# !/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time     : 2017/10/18 16:36
# @Author   : Sz
import random
import sys
from hbase import THBaseService
from hbase.ttypes import TColumnValue, TPut, TGet, TIOError, TScan
from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket, TTransport


class HBase(object):
    def __init__(self, hbase_address: str = None, hbase_port: int = None, hbase_server: list = None):
        if not hbase_address and not hbase_server:
            raise False
        # server端地址和端口
        transport = TSocket.TSocket(hbase_address, hbase_port)
        # 可以设置超时
        transport.setTimeout(5000)
        # 设置传输方式（TFramedTransport或TBufferedTransport）
        transport = TTransport.TBufferedTransport(transport)
        # 设置传输协议
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        # 确定客户端
        client = THBaseService.Client(protocol)
        # 打开连接
        transport.open()
        self.hbase_address = hbase_address
        self.hbase_port = hbase_port
        self.client = client
        self.transoprt = transport
        self.servers = hbase_server

    def ping(self):
        return self.transoprt.isOpen()

    def reconnect(self):
        if self.servers:
            h_a, h_p = random.choice(self.servers)
            self.__init__(h_a, h_p, self.servers)
        else:
            self.__init__(self.hbase_address, self.hbase_port)

    @staticmethod
    def pack_up(cf_name: str, c_value: dict):
        re_list = []
        for k, v in c_value.items():
            re_list.append([cf_name, k, v])
        return re_list

    @staticmethod
    def decode_result(hbase_value: dict):
        return {k.decode() if type(k) == bytes else k: v.decode() if type(v) == bytes else v
                for k, v in hbase_value.items()}

    def exists(self, rowkey: str, hbase_table: str):
        tget = TGet()
        tget.row = rowkey if type(rowkey) == bytes else rowkey.encode()
        table_name = hbase_table if type(hbase_table) == bytes else hbase_table.encode()
        result = self.client.exists(table_name, tget)
        return result

    def get_result(self, rowkey: str, hbase_table: str):
        values = {}
        tget = TGet()
        tget.row = rowkey if type(rowkey) == bytes else rowkey.encode()
        table_name = hbase_table if type(hbase_table) == bytes else hbase_table.encode()
        result = self.client.get(table_name, tget)
        for column in result.columnValues:
            values[column.qualifier] = column.value
        return values

    def put_result(self, hbase_row: str, hbase_value: list, hbase_table: str):
        if not hbase_row:
            return False
        coulumn_values = []
        rowkey = hbase_row if type(hbase_row) == bytes else hbase_row.encode()
        for data in hbase_value:
            data[2] = str(data[2]) if type(data[2]) == int else data[2]
            data[2] = '' if data[2] is None else data[2]
            row_value = TColumnValue(data[0] if type(data[0]) == bytes else data[0].encode(),
                                     data[1] if type(data[1]) == bytes else data[1].encode(),
                                     data[2] if type(data[2]) == bytes else data[2].encode())
            coulumn_values.append(row_value)
        tput = TPut(rowkey, coulumn_values)
        trash = 2
        while trash != 0:
            try:
                self.client.put(hbase_table.encode(), tput)
                return True
            except Exception as e:
                print('hbase exception: line:%s :%s' % (sys.exc_info()[2].tb_lineno, e) + self.hbase_address)
                trash -= 1
                self.reconnect()
        if trash == 0:
            print('fuck hbase down')
            # raise TIOError
            return False

    def scan_table(self, table_name: str, interval_count: int = 100, decode_responses: bool = True):
        if not table_name:
            return False
        elif type(table_name) == str:
            table_name = table_name.encode()
        elif type(table_name) != bytes:
            return False
        scan = TScan()
        scanner_id = self.client.openScanner(table_name, scan)
        row_list = self.client.getScannerRows(scanner_id, interval_count)
        while row_list:
            for row_info in row_list:
                row_key = row_info.row
                column_infos = row_info.columnValues
                if decode_responses:
                    column_value = {cf: {ci.qualifier.decode(): ci.value.decode()
                                         for ci in column_infos if ci.family.decode() == cf}
                                    for cf in set([cfi.family.decode() for cfi in column_infos])}
                    yield {row_key.decode(): column_value}
                else:
                    column_value = {cf: {ci.qualifier: ci.value
                                         for ci in column_infos if ci.family.decode() == cf}
                                    for cf in set([cfi.family for cfi in column_infos])}
                    yield {row_key: column_value}
            row_list = self.client.getScannerRows(scanner_id, interval_count)


if __name__ == '__main__':
    hb = HBase(hbase_address='192.168.129.11', hbase_port=9090)
    x = hb.ping()
    print(x)
    # x = hb.put_result('abcccc', [['cf', 'abc', 'cbba', ], ], 'gpp')
    y = hb.get_result('abcccc', 'gpp')
    print(y)
    # z = hb.decode_result(y)
    # print(z)
    #
    # y = hb.get_result('2133258491', 'WEIBO_USER_TABLE')
    # print(hb.decode_result(y))
    # z = hb.get_result('https://tva1.sinaimg.cn/crop.19.7.670.670.180/95e477abjw8es1kn7uk77j20jp11pjxd.jpg', 'BLOCK_TABLE')
    # print(hb.decode_result(z))
    # a = hb.get_result('2310244700-GEZY4hGzG', 'WEIBO_INFO_TABLE')
    # print(hb.decode_result(a))
    # b = hb.get_result('https://weibo.com/1948980611/CAkWHFCOe', 'WEIBO_ORG_TABLE')
    # print(hb.decode_result(b))
    # c = hb.get_result('http://www.yaxla.biz/m', 'BLOCK_TABLE')
    # d = hb.decode_result(c)
    # print(d)
