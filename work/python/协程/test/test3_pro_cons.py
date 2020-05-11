# import asyncio
# import time
#
#
# async def produce(a, n):
#     for x in range(1, n + 1):
#         # produce an item
#         print(f'producing {x}/{n}')
#         # simulate i/o operation using sleep
#         await asyncio.sleep(0.1)
#         item = str(x)
#         # put the item in the queue
#         await a.put(item)
#
#
# async def consume(b):
#     time.sleep(1)
#     while True:
#         # wait for an item from the producer
#         item = await b.get()
#         if not item:
#             # the producer emits None to indicate that it is done
#             break
#
#         # process the item
#         print(f'consuming item {item}...')
#         # simulate i/o operation using sleep
#         await asyncio.sleep(0.2)
#
#         # queue.task_done()
#
#
# queue = asyncio.Queue()
#
#
# async def main():
#     t = asyncio.gather(
#         produce(queue, 10),
#         consume(queue),
#     )
#     task1 = asyncio.create_task(produce(queue,10))
#     task2 = asyncio.create_task(consume(queue))
#     tasks = [task1,task2]
#     for f in asyncio.as_completed(tasks):
#         await f


# loop = asyncio.get_event_loop()
# loop.run_until_complete(main())
# loop.close()

# asyncio.run(main())


# import asyncio
# import time
# from asyncore import loop
#
#
# async def eternity(delay):
#     await asyncio.sleep(delay)
#     print(f'delay for {delay} seconds')
#     return delay
#
#
# async def main():
#     print(f"start at:{time.strftime('%X')}")
#     tasks = [eternity(i) for i in range(10)]
#     for f in asyncio.as_completed(tasks):
#         res = await f
#     print(f"End at:{time.strftime('%X')}")
#
#
# asyncio.run(main())



