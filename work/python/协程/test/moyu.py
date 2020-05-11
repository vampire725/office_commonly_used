import asyncio

async def slow_operation(future):
    await asyncio.sleep(1)
    future.set_result('Future is done!')
loop = asyncio.get_event_loop()
future1 = asyncio.Future()