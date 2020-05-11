import json
from bson import *

# class A(object):
#     def m1(self, n):
#         print("self:", self)
#
#     @classmethod
#     def m2(cls, n):
#         print("cls:", cls)
#
#     @staticmethod
#     def m3(n):
#         print(n)
#
#
# a = A()
# a.m1(1)  # self: <__main__.A object at 0x000001E596E41A90>
# A.m2(1)  # cls: <class '__main__.A'>
# A.m3(1)
# for i in range(10000):
#     data = {'infoTableName ': 'gpp1',
#             'infoRowkey': 'row'+'{}'.format(i),
#             }
#     message = json.loads(data)


data = b'{"infoTableName ": "gpp", "infoRowkey": "row0"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row1"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row2"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row3"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row4"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row5"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row6"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row7"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row8"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row9"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row10"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row11"}'
# b'{"infoTableName ": "gpp", "infoRowkey": "row12"}'

date = json.loads(data)
print(date['infoTableName '])
d = {"infoTableName ": "gpp", "infoRowkey": "row"}
list = []
list1 = []
for i in range(10):
    d = {"infoTableName ": "gpp", "infoRowkey": "row"+'{}'.format(i)}
    list1.append(i)
    list.append(d)
    print(d)
for i,h in zip(range(19),range(10)):
    # print(i['infoTableName '])
    print(i,h)