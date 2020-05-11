"""
a simple example
"""
# import os
# import multiprocessing
#
#
# def foo(i):
#     # 同样的参数传递方法
#     print("这里是", multiprocessing.current_process().name)
#     print("模块名称:", __name__)
#     print("父进程id:", os.getppid())  # 获取父进程id
#     print("当前子进程 id:", os.getpid())  # 获取自己的进程id
#     print('-----------------')
#
#
# if __name__ == '__main__':
#     for i in range(5):
#         p = multiprocessing.Process(target=foo, args=(i,))
#         p.start()


# from multiprocessing import Process
#
# lis = []
#
# # 此例全局列表lis没有起到任何作用，在主进程好的子进程中，lis指向内存中不同的列表
# def foo(i):
#     lis.append(i)
#     print(f"this is Process {i},and lis is {lis},and lis.address is {id(lis)}")
#
#
# if __name__ == '__main__':
#     for i in range(5):
#         p = Process(target=foo, args=(i,))
#         p.start()
#     print(f"the enf of list_1 {lis}")
# from multiprocessing import Array, Process
#
#
# def func(i, temp):
#     temp[1] += 100
#     print(f"进程{i}修改数组第二个元素----------{temp[1]}")
#
#
# if __name__ == '__main__':
#     temp = Array('i', [1, 2, 3, 4])
#     for i in range(10):
#         p = Process(target=func, args=(i, temp))
#         p.start()


# # 使用Manager共享数据
# from multiprocessing import Process
# from multiprocessing import Manager
#
#
# def func(i, dic):
#     dic['name'+f'{i}'] = 100 + i
#     print(dic.items())
#
#
# if __name__ == '__main__':
#     dic = Manager().dict()
#     for i in range(10):
#         p = Process(target=func, args=(i, dic))
#         p.start()
#         p.join()


# 使用queues的Queue类共享数据

# import multiprocessing
# from multiprocessing import Process, queues
#
#
# def func(i, q):
#     ret = q.get()
#     print(f"进程{i}从队列里获取了一个{ret},然后又向队列里放入了一个{i}")
#     q.put(i)
#
#
# if __name__ == '__main__':
#     lis = queues.Queue(20, ctx=multiprocessing)
#     lis.put(0)
#     for i in range(10):
#         p = Process(target=func, args=(i, lis,))
#         p.start()

# 进程锁
# from multiprocessing import Process
# from multiprocessing import Array, RLock, Lock, Event, Condition, Semaphore
# import time
#
#
# def func(i, lis, lc):
#     lc.acquire()
#     lis[1] = lis[1] - 1
#     time.sleep(1)
#     print(f'say hi {lis[1]}')
#     lc.release()
#
#
# if __name__ == '__main__':
#     array = Array('i', [1,8])
#     # array[1] = 10
#     lock = RLock()
#     for i in range(10):
#         p = Process(target=func, args=(i, array, lock))
#         p.start()

# 进程池Pool类
import multiprocessing
from multiprocessing import Pool
import time


def func(args):
    time.sleep(1)
    print(f'正在执行进程{args}')


if __name__ == '__main__':
    p = Pool(5)
    for i in range(30):
        p.apply_async(func=func, args=(i,))
    p.close()
    time.sleep(2)
    print(multiprocessing.current_process().name)
    print('==========')
    # p.terminate() # 等子进程执行完毕后关闭进程池
    p.join()

