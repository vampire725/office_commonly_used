from confluent_kafka.cimpl import Consumer

from setting import *


def receive_from_kafka(kafka_ip, kafka_port, topic_x):
    c = Consumer({
        'auto.commit.interval.ms': 5000,
        'session.timeout.ms': 60000,
        'heartbeat.interval.ms': 3000,
        'batch.num.messages': 20000,
        'queue.buffering.max.messages': 300000,
        'bootstrap.servers': kafka_ip,
        'group.id': i_d,
        'default.topic.config': {
            'auto.offset.reset': 'earliest',
        }
    })
    pass
    # connect to kafka

    # recive message from topic:topic_x

    # process message

    # return processed msg

    return


def get_data_from_hbase(hbase_ip, hbase_port, table_name, rowkey):
    # connect

    # get by rowkey

    # process data

    return


def get_entity():
    pass


def get_relation():
    pass


def process_flow():
    result = {}
    for k in PROCESS_MIDDLEWARE.keys():
        result[k] = k()

    return result


def save_into_hbase():
    pass


def send_from_kafka():
    pass


if __name__ == '__main__':
    pass
