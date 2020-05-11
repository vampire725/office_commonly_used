import logging
import time

from flask import jsonify
from sanic import Sanic
from sanic.response import json, text

from lib.format_data import fmt
from lib.save_data import GraphAO

app = Sanic('2019-6-19sanic-neo')

app.config.from_pyfile('settings.py')
logging.basicConfig(filename=app.config.LOG_FILENAME,
                    level=logging.INFO,
                    format=app.config.LOG_FORMAT,
                    datefmt=app.config.DATE_FORMAT)


@app.route('/')
async def test(request):
    return json({'hello': 'world'})


@app.route('/data/', methods=['GET', 'POST'])
async def trans(request):
    start = time.time()
    if not request.json:
        logging.error(f"no json received {request}")
        return text("no json")
    # data format
    neo_data = fmt(request.json)

    # save
    try:
        A = GraphAO()
        A.save(neo_data)
        save_ok_json = {
            'code': 200,
            'message': 'ok',
            'data': 'rdf data',
        }
        logging.info(f"save ok.{save_ok_json},spend {start,time.time(),time.time()-start}")
        return text('ok')
    except Exception as e:
        except_json = {
            'status': 'fail',
            'data': f'{request}',
            'reason': f'{e}'
        }
        logging.error(jsonify(except_json), e)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000)
