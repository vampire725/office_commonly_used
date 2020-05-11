import json
import os
import time
from concurrent.futures import ProcessPoolExecutor
from confluent_kafka import Consumer, KafkaError
from confluent_kafka.cimpl import KafkaException

from setting import *


# from ...benchmark.foo_save.save import svae_multi_data


def obtain_server():
    c = Consumer({
        'auto.commit.interval.ms': 3600,
        'session.timeout.ms': 60000,
        'heartbeat.interval.ms': 3000,
        'batch.num.messages': 20000,
        'queue.buffering.max.messages': 300000,
        'bootstrap.servers': KAFKA_IP,
        'group.id': KAFKA_CONSUMER_GROUP,
        'default.topic.config': {
            'auto.offset.reset': 'earliest',
        }
    })
    return c


def conf_csm():
    s = obtain_server()
    s.subscribe([KAFKA_TABLE])
    co_unt = 0
    # with open(path, 'a')as f_v:  # foo_save the consume data
    table_rowkey = []
    t = True
    f = False
    while t:
        msg = s.poll(timeout=1.0)
        if not msg:
            continue
        # co_unt += 1
        # if co_unt >= 33:
        #     break
        elif msg.error():
            if msg.error().code() == KafkaError._PARTITION_EOF:
                if f:
                    t = False
                    break
                time.sleep(1)
                continue
            raise KafkaException(msg.error())
        print(msg.offset, msg.value())
        data = msg.value()
        yield data
    #     data = json.loads(data)
    #     # print(data)
    #     table_rowkey.append(data)
    #
    # print(co_unt)
    # s.close()
    # return os.getpid(), co_unt, time.time(), table_rowkey


def multi_consume():
    data = []
    with ProcessPoolExecutor(max_workers=3)as t_p:
        start = time.time()
        data.append(t_p.submit(conf_csm))
        data.append(t_p.submit(conf_csm))
        data.append(t_p.submit(conf_csm))
    # consume_process = []
    for re in data:
        if re.done():
            pid, co_unt, end_tm, table_rowkey = re.result()
            total_time = end_tm - start
            # consume_process.append(table_rowkey)
            print(total_time)
            # print(table_rowkey)
            # print(re)
            print('main id is {}'.format(os.getpid()))
            yield table_rowkey
    # print(consume_process)
    # return consume_process


if __name__ == '__main__':
    g = conf_csm()
    # print(g)
    for i in g:
        print(type(i))
    # for i in g:
    #     for h in range(len(i)):
    #         print(i[h]['infoTableName '])
