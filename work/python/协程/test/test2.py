import asyncio
#
#
# async def division(divisor, dividend):
#     if divisor == 0:
#         raise ZeroDivisionError
#     else:
#         await asyncio.sleep(divisor)
#         print(f"{dividend}/{divisor}={dividend/divisor}")
#         return dividend / divisor
#
#
# async def main():
# t = asyncio.gather(
#     division(0, 2),
#     division(1, 2),
#     division(2, 2),
#     division(1, 5),
#     division(2, 6),
#     return_exceptions=True,
# )
# await asyncio.sleep(2)
# t.cancel()
# await t

# task1 = asyncio.create_task(division(0, 2))
# task2 = asyncio.create_task(division(1, 5))
# task3 = asyncio.create_task(division(3, 6))
# t = asyncio.gather(
#     task1,
#     task2,
#     task3,
#     return_exceptions=True
# )
# task1.cancel()
# print(await t) # 结果，仅task1被取消，其他任务不受影响
# # 5 / 1 = 5.0
# # 6 / 3 = 2.0
# # [CancelledError(), 5.0, 2.0]

#     task1 = asyncio.shield(division(1, 2))
#     task2 = asyncio.create_task(division(1, 5))
#     task3 = asyncio.create_task(division(3, 6))
#     res = asyncio.gather(task1, task2, task3, return_exceptions=True)
#     task1.cancel()
#     task2.cancel()
#     print(await res)
#
#
# asyncio.run(main())


# import asyncio
#
#
# async def foo():
#     print('---------')
#     return 42
#
#
# async def main():
#     task = asyncio.create_task(foo())
#     done, pending = await asyncio.wait({task})
#     print(pending)
#     if task in done:
#         # await task
#         print(task)
#
# asyncio.run(main())


import asyncio
import datetime


def display_date(end_time, loop):
    print(datetime.datetime.now())
    if (loop.time() + 1.0) < end_time:
        loop.call_later(1, display_date, end_time, loop)
    else:
        loop.stop()


loop = asyncio.get_event_loop()

# Schedule the first call to display_date()
end_time = loop.time() + 5.0
loop.call_soon(display_date, end_time, loop)

# Blocking call interrupted by loop.stop()
try:
    loop.run_forever()
finally:
    loop.close()
