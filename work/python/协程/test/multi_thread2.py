import threading
import time


# def show(arg):
#     time.sleep(1)
#     print('thread' + str(arg) + "runnt.ing......")
#
#
# if __name__ == '__main__':
#     for i in range(10):
#         t = threading.Thread(target=show, args=(i,))
#         t.start()

# # 主线程没有等子线程，而是只顾自己跑
# def do_waiting():
#     print('start waiting', time.strftime('%H:%M:%S'))
#     time.sleep(3)
#     print('start waiting', time.strftime('%H:%M:%S'))
#
#
# t = threading.Thread(target=do_waiting)
# t.start()
# # 确保 线程t已经启动
# time.sleep(1)
# print('start job')
# print(threading.current_thread())
# print('end job')


# # 线程join方法
# def do_waiting():
#     print('start waiting:', time.strftime('%H:%M:%S'))
#     time.sleep(3)
#     print('stop waiting', time.strftime('%H:%M:%S'))
#
#
# t = threading.Thread(target=do_waiting)
# t.start()
# time.sleep(1)
# print('start join')
# t.join()
# print('end join')


# 使用setDeamon(True)设置主线程的守护线程，当主线程结束，守护进程也随之结束

# def run():
#     print(threading.current_thread().getName(), '开始工作')
#     time.sleep(3)
#     print('子进程工作完毕')
#
#
# for i in range(3):
#     t = threading.Thread(target=run)
#     # t.setDaemon(True)
#     t.start()
# time.sleep(1)
# print('主线程结束了！')
# print(threading.active_count())  # 输出活跃线程数


# 自定义线程类

class MyThreading(threading.Thread):
    def __init__(self, func, arg):
        super(MyThreading, self).__init__()
        self.func = func
        self.arg = arg

    def run(self):
        self.func(self.arg)


def my_func(args):
    """
    可以把任何你想让线程做的事都定义在这里
    :return:
    """
    print(args)
    pass


obj = MyThreading(my_func, 123)
obj.start()
