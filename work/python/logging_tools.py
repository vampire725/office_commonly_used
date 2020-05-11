# !/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time     : 2018/1/3 13:01
# @Author   : Sz
import logging
from logging import handlers

log_level = {'INFO': logging.INFO,
             'DEBUG': logging.DEBUG,
             'WARNING': logging.WARNING,
             'ERROR': logging.ERROR,
             'CRITICAL': logging.CRITICAL}


def base_logger(log_name: str, file_path: str, mode: str = 'INFO', cmd_output: bool = False) -> logging:
    """
    logger with default setting as I used mostly.
    Formatted.
    Auto partitioning by day. Eg: abc.log.2018-04-01

    Future:
        to be completed...

    :param log_name: Name of log as it means.
    :param file_path: Where you may put the log file. Notice that its a path, not file.
    :param mode: Which level of log you wanna see. Default info cause ... I like.
    :param cmd_output: Whether output the log into your screen. Log in debug level default output it.
    :return: the logger get ready.
    """
    file_path = file_path if file_path.endswith('/') else file_path + '/'
    file_name = log_name.split('_')[-1] if '_' in log_name else log_name  # 按组放到一起，其实也可以改成同时输出组和单独日志，没时间折腾

    logger = logging.getLogger(log_name)
    logger.setLevel(log_level[mode])

    formatter = logging.Formatter('%(asctime)s %(levelname)s: *%(name)s* %(message)s')

    # file log
    fh = handlers.TimedRotatingFileHandler(filename='{path}{name}.log'.format(path=file_path, name=file_name),
                                           when='MIDNIGHT')
    fh.setLevel(log_level[mode])
    fh.setFormatter(formatter)

    # output log
    if mode == 'DEBUG' or cmd_output:
        ch = logging.StreamHandler()
        ch.setLevel(log_level[mode])
        ch.setFormatter(formatter)

        logger.addHandler(ch)

    logger.addHandler(fh)

    return logger
