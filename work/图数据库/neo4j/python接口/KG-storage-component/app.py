import logging

from flask import Flask, request, jsonify

from lib.format_data import fmt
from lib.save_data import GraphAO
from setting import LOG_FORMAT, DATE_FORMAT, LOG_FILENAME

logging.basicConfig(filename=LOG_FILENAME,
                    level=logging.INFO,
                    format=LOG_FORMAT,
                    datefmt=DATE_FORMAT)
app = Flask(__name__)
app.config.from_object('setting')
A = GraphAO()


@app.route('/data/', methods=['POST', 'GET'])
def trans():
    # receive data
    if not request.json:
        logging.error("No json received.{}".format(request.data))
        return 'no json'

    # data format
    neo_data = fmt(request.json)

    # save
    try:
        A.save(neo_data)
        save_ok_json = {
            'code': 200,
            'message': 'ok',
            'data': 'rdf data',
        }
        logging.info(f"save ok.{save_ok_json}")
        return 'ok'
    except Exception as e:
        except_json = {
            'status': 'fail',
            'data': f'{request}',
            'reason': f'{e}'
        }
        logging.error(jsonify(except_json))

        return 'trans fail'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=app.default_config['DEBUG'])
