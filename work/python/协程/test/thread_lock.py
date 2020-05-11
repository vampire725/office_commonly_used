import threading, time


# num = 0
#
#
# def plus():
#     global num
#     for i in range(1000000):
#         num += 1
#     print(f"子线程{threading.current_thread().getName()}结束后"
#           f"，num={num}")
#
#
# for i in range(2):
#     t = threading.Thread(target=plus)
#     t.start()
# time.sleep(2)
# print(f"主线程结束后，num = {num}")
# 结果
# 子线程Thread-1结束后，num=1374322
# 子线程Thread-2结束后，num=1586883
# 主线程结束后，num = 1586883


# 互斥锁Lock

# 互斥锁是一种独占锁，同一时刻只有一个线程可以访问共享的数据。使用很简单，初始化锁对象，然后将锁当做参数传递给任务函数，在任务中加锁，使用后释放锁。

# num = 0
# lock = threading.Lock()
#
#
# def plus(lk):
#     lk.acquire()
#     global num
#     for _ in range(1000000):
#         num += 1
#     print(f"子线程{threading.current_thread().getName()},num = {num}")
#     lk.release()
#
#
# if __name__ == '__main__':
#     for i in range(2):
#         t = threading.Thread(target=plus, args=(lock,))
#         t.start()
#     time.sleep(5)
#     print(f"主线程执行完毕后，num = {num}")
# 结果
# 子线程Thread - 1, num = 1000000
# 子线程Thread - 2, num = 2000000
# 主线程执行完毕后，num = 2000000


# 信号Semaphore

def run(n, se):
    se.acquire()
    print(f"run the thread {n}")
    time.sleep(1)
    se.release()



semaphore = threading.BoundedSemaphore(5)
for i in range(20):
    t = threading.Thread(target=run, args=(i, semaphore))
    t.start()

# 事件
#
# event = threading.Event()
#
#
# def lighter():
#     green_time = 5  # 绿灯时间
#     red_time = 5  # 红灯时间
#     event.set()  # 初始设为绿灯
#     while True:
#         print("\33[32;0m 绿灯亮...\033[0m")
#         time.sleep(green_time)
#         event.clear()
#         print("\33[31;0m 红灯亮...\033[0m")
#         time.sleep(red_time)
#         event.set()
#
#
# def run(name):
#     while True:
#         if event.is_set():
#             print(f"一辆车[{name}]呼啸而过....")
#             time.sleep(1)
#         else:
#             print(f"一辆车[{name}]开来，看到红灯，无奈的停下了...")
#             event.wait()
#             print(f"[{name}] 看到绿灯亮了，瞬间飞起.....")
#
#
# if __name__ == '__main__':
#     light = threading.Thread(target=lighter, )
#     light.start()
#     for name in ['奔驰', '宝马', '奥迪']:
#         car = threading.Thread(target=run, args=(name,))
#         car.start()


# Condition

# num = 0
# con = threading.Condition()
#
#
# class Foo(threading.Thread):
#     def __init__(self, name, action):
#         super(Foo, self).__init__()
#         self.name = name
#         self.action = action
#
#     def run(self):
#         global num
#         con.acquire()
#         print("%s开始执行..." % self.name)
#         while True:
#             if self.action == "add":
#                 num += 1
#             elif self.action == 'reduce':
#                 num -= 1
#             else:
#                 exit(1)
#             print("num当前为：", num)
#             time.sleep(1)
#             if num == 5 or num == 0:
#                 print("暂停执行%s！" % self.name)
#                 con.notify()
#                 con.wait()
#                 # print("%s开始执行..." % self.name)
#         con.release()
#
#
# if __name__ == '__main__':
#     a = Foo("线程A", 'add')
#     b = Foo("线程B", 'reduce')
#     a.start()
#     b.start()


# 定时器Timer
# from threading import Timer
#
#
# def hello():
#     print("hello,world")
#
#
# t = Timer(1, hello)
# t.start()


# 通过with语句使用线程锁
# some_lock = threading.Lock()
# with some_lock:
# # 执行任务.....
#
# # 等价于
#
# some_lock.acquire()
# try:
# # 执行任务....
# finally:
#     some_lock.release()
