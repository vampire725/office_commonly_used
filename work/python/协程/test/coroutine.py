import time


def task1():
    while True:
        yield "<甲>也累了，让<乙>工作一会儿"
        time.sleep(1)
        print("<甲工作了一段时间.......>")


def task2(t):
    next(t)
    while True:
        print('-------------------')
        print("<乙>工作了一段时间......")
        time.sleep(2)
        print("<乙>累了，让<甲>工作一会儿......")
        ret = t.send(None)
        print(ret)
    t.close()


if __name__ == '__main__':
    t = task1()
    task2(t)


# def func():
#     for i in range(10):
#         yield i
#
#
# print(list(func()))


# # 上面函数等价于
#
# def func():
#     yield from range(10)
#
# print(list(func()))


# import asyncio
# import datetime
#
#
# @asyncio.coroutine  # 声明一个协程
# def display_date(num, loop):
#     end_time = loop.time() + 10.0
#     while True:
#         print(f"loop:{num}Time:{datetime.datetime.now()}")
#         if (loop.time() + 1.0) >= end_time:
#             break
#         yield from asyncio.sleep(2)  # 阻塞直到协程sleep(2)返回结束
#
#
# loop = asyncio.get_event_loop()  # 获取一个event_loop
# tasks = [display_date(1, loop), display_date(2, loop)]
# loop.run_until_complete(asyncio.gather(*tasks))
# loop.close()

# 3.7以后简单的协程例子
# import asyncio
#
#
# async def main():
#     print('hello')
#     await asyncio.sleep(1)
#     print('word')
#
#
# asyncio.run(main())


# import asyncio
# import time
#
#
# async def say_after(delay, what):
#     await asyncio.sleep(delay)
#     print(what)
#
#
# async def main():
#     print(f"started at {time.strftime('%X')}")
#     await say_after(1, 'hello')
#     await say_after(2, 'world')
#     print(f"finished at {time.strftime('%X')}")
#
#
# asyncio.run(main())


# asyncio.create_task() 函数用来并发运行作为 asyncio 任务 的多个协程
# import asyncio
# import time
#
#
# async def say_after(delay, what):
#     await asyncio.sleep(delay)
#     print(what)
#
#
# async def main():
#     task1 = asyncio.create_task(say_after(1, 'hello'))
#     task2 = asyncio.create_task(say_after(2, 'world'))
#     print(f"started at {time.strftime('%X')}")
#
#     # 等两个任务都完成需要两秒，并发执行两个协程
#     await task1
#     await task2
#     print(f"finished at {time.strftime('%X')}")
#
#
# asyncio.run(main())


# python 协程属于可等待对象，因此可以在其他协程中被等待
# import asyncio
#
#
# async def nested():
#     return 42
#
#
# async def main():
#     # Nothing happens if we just call "nested()".
#     # A coroutine object is created but not awaited,
#     # so it *won't run at all*.
#     nested()
#     # Let's do it differently now and await it:
#     print(await nested())  # will print "42".
#
#
# asyncio.run(main())


# 任务
# 任务被用来设置日程以便并发执行协程
# 当一个协程通过asyncio.create_task()等函数被打包为一个任务，
# 该协程将自动排入日程准备立即运行

# import asyncio
#
#
# async def nested():
#     print('ss')
#     return 42
#
#
# async def main():
#     # Schedule nested() to run soon concurrently
#     # with "main()".
#     task = asyncio.create_task(nested())
#     # "task" can now be used to cancel "nested()", or
#     # can simply be awaited to wait until it is complete:
#     await task
#
#
# asyncio.run(main())

# Future 对象
#
# import asyncio
#
#
# async def func(future):
#     await asyncio.sleep(1)
#     future.set_result('Future is done!')
#
#
# if __name__ == '__main__':
#     loop = asyncio.get_event_loop()
#     future = asyncio.Future()
#     asyncio.ensure_future(func(future))
#     print(loop.is_running())  # 查看当前状态时循环是否已经启动
#     loop.run_until_complete(future)
#     print(future.result())
#     loop.close()


# 休眠
# coroutine asyncio.sleep(delay,result=None,*,loop=None)
# import asyncio
# import datetime
#
#
# async def display_date():
#     loop = asyncio.get_running_loop()
#     end_time = loop.time() + 5.0
#     while True:
#         print(datetime.datetime.now())
#
#         if (loop.time() + 1.0) >= end_time:
#             break
#         await asyncio.sleep(1)
#
#
# asyncio.run(display_date())

# 并发运行任务
# import asyncio
#
#
# async def factorial(name, number):
#     f = 1
#     for i in range(2, number + 1):
#         # print(f"Task {name}: Computer factorial({i}...")
#         await asyncio.sleep(1)
#         f *= i
#     # print(f"Task {name}:actorial({number}={f})")
#     return number
#
#
# async def main():
#     await asyncio.gather(
#         factorial("A", 2),
#         factorial("B", 3),
#         factorial("C", 4),
#     )
#
#
# asyncio.run(main())

# 屏蔽取消操作

# awaitable asyncio.shield(aw,*,loop=None)
# 保护一个可等待对象防止其被取消

# res = await shield(something)
# 相当于：
# res = await something()
# 更加保险的做法，不会被取消，不过不推荐
# try:
#     res = await shield(something())
# except CancelledError:
#     res = None
# import asyncio
#
#
# async def eternity():
#     # Sleep for one hour
#     await asyncio.sleep(3600)
#     print('yay!')
#
# async def main():
#     # Wait for at most 1 second
#     try:
#         await asyncio.wait_for(eternity(), timeout=4.0)
#     except asyncio.TimeoutError:
#         print('timeout!')
#
# asyncio.run(main())


# 与 wait_for() 不同，wait() 在超时发生时不会取消可等待对象。
# import asyncio


# 注解 wait() 会自动将协程作为任务加入日程，以后将以 (done, pending) 集合形式返回显式创建的任务对象。因此以下代码并不会有预期的行为:
# async def foo():
#     return 42


# core = foo()
# done, pending = await asyncio.wait({core})
# if core in done:
# # this branch will never be run!

# task = asyncio.create_task(foo())
# done,pending = await asyncio.wait({task})
# if task in done:
#     print('----')
