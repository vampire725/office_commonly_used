import redis
from pymongo import MongoClient

# client = MongoClient('localhost', 27017)
#
# insert_data = {'a': 124, 'b': '33333'}
#
# db = client.item
#
# insert_data_id = db.toutiao.insert_one(insert_data).inserted_id
# print(insert_data_id)
conn = redis.StrictRedis(host='localhost', port="6379", db="0", decode_responses=True)
# for i in range(10):
#     conn.sadd('suibian', '{}'.format(str(i)))


def put_data():
    data = conn.smembers('links')
    return data


for i in put_data():
    print(i)
