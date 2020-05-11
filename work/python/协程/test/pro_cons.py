import asyncio


async def produce(a, n):
    for x in range(1, n + 1):
        print(f'producing {x}/{n}')
        # simulate i/o operation using sleep
        await asyncio.sleep(0.1)
        item = str(x)
        # put the item in the queue
        await a.put(item)


async def consume(b):
    while True:
        # wait for an item from the producer
        item = await b.get()
        if not item:
            b.task_done()
            break
        print(f'consuming item {item}...')
        await asyncio.sleep(0.2)


queue = asyncio.Queue()


async def main():
    await asyncio.gather(
        produce(queue, 10),
        consume(queue),
    )


loop = asyncio.get_event_loop()
loop.run_until_complete(main())
loop.close()
# asyncio.run(main())
