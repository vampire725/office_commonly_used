# # python3.3新加了yield from语法
# from itertools import chain
#
# my_list = [1, 2, 3]
# my_dict = {
#     "bobby1": "http://projectsedu.com",
#     "bobby2": "http://projectsedu.com",
# }
#
#
# def my_chain(*args, **kwargs):
#     for my_iterrable in args:
#         yield from my_iterrable
#         # for value in my_iterrable:
#         #     yield value
#
#
# for value in my_chain(my_list, my_dict, range(5, 10)):
#     print(value)
#
#
# # for value in chain(my_list, my_dict, range(5, 10)):
# #     print(value)
# def g1(gen):
#     yield from gen
#
#
# def main():
#     g = g1()
#     g.send(None)
# # main 调用方g1(委托生成器)gen子生成器
# # yield from 会在调用方与子生成器之间建立一个双向通道
import asyncio,aiohttp


async def get(url):
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as resp:
            print(url, resp.status)
            print(url, await resp.text())


loop = asyncio.get_event_loop()  # 得到一个事件循环模型
# tasks = [  # 初始化任务列表
#     get("http://shop.projectsedu.com/goods/2/"),
#     get("http://shop.projectsedu.com/goods/3/"),
#     get("http://shop.projectsedu.com/goods/5/")
# ]
tasks = []
for i in range(20):
    task = get(f"http://shop.projectsedu.com/goods/{i}/")
    tasks.append(task)
loop.run_until_complete(asyncio.wait(tasks))  # 执行任务
loop.close()  # 关闭事件循环列表
