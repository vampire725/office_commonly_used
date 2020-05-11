from sanic import Sanic
from sanic.response import json

app = Sanic()


@app.route('/json')
def hello():
    pass

# @app.websocket('/feed')
# async def feed(request, ws):
#     while True:
#         data = 'hello'
#         print('sending' + data)
#         await ws.recv()
#         print('Receive:' + data)
