import threading
import time

import requests


class MyThread(threading.Thread):
    def __init__(self, thread_name):
        super(MyThread, self).__init__(name=thread_name)

    def run(self):
        list_port = ['10508', '10503', '10504', '10505', '10506', '10507', '10508',
                     '10520', '10521', '10522', '10523', '10524', '10525', '10526',
                     '10527', '10528', '10529', '10512', '10513', '10514', '10533',
                     '10534', ]
        erro_list = []
        for i in list_port:
            url = f'http://60.28.140.210:{i}'
            try:
                state = requests.get(url).status_code
                print(state)
            except Exception as  e:
                erro_list.append(e)
        print(erro_list)


if __name__ == '__main__':
    for i in range(22):
        MyThread("thread-" + str(i)).start()
