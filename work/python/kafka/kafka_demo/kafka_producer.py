import json
import os
import time
from confluent_kafka import Producer
from concurrent.futures import ProcessPoolExecutor
from setting import *


def pro_duc():
    c = Producer({
        'bootstrap.servers': KAFKA_IP,
        'batch.num.messages': 20000,
        'queue.buffering.max.messages': 1000000,
        'group.id': KAFKA_PRODUCE_GROUP,
        'queue.buffering.max.kbytes': 1048576,
        'default.topic.config': {
            'auto.offset.reset': 'smallest',
            # 'compression.codec': 'lz4',
        }
    })
    # print(c)
    count = 0
    for i in range(100):
        strr = str(i)
        data = {'infoTableName': 'gpp',
                'infoRowkey': 'row' + '{}'.format(strr),
                }
        message = json.dumps(data)
        c.produce(KAFKA_TABLE, message)
        count += 1

    c.flush()
    # test_msg = ('zzzzz ' * 20)[:100]
    # cycle_time = 10
    # count = 0
    # for i in range(cycle_time):
    #     count += 1
    #     # print(line_data.decode())
    #     c.produce(KAFKA_TABLE, test_msg)
    # c.flush()

    return os.getpid(), count, time.time()


# def process_thread():
#     with ProcessPoolExecutor(max_workers=3)as t_p:  # multi_processes
#         ob_js = []
#         start = time.time()
#         for i in range(3):
#             data = t_p.submit(pro_duc)
#             ob_js.append(data)
#     produce_process = []
#     for re in ob_js:
#         if re.done():
#             pid, co_unt, end_tm = re.result()
#             total_time = end_tm - start
#             produce_process.append(total_time)
#             print(total_time)
#             print(re)
#     return produce_process


if __name__ == '__main__':
    # process_thread()
    pro_duc()