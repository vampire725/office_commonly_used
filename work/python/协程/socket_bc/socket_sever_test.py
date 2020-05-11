# 客户端请求网站-非阻塞实现
import socket
import time

# 访问网站
ACCESS_URL = 'www.baidu.com'
# 端口
ACCESS_PORT = 80


# socket非阻塞请求网站（时间消耗在不断的while循环中，和阻塞的时间差不多）
def blocking(pn):
    sock = socket.socket()
    sock.setblocking(False)  # 设置为非阻塞
    try:
        # connect连接需要一定时间，非阻塞发送完请求，立即返回，如果没有数据会报异常
        sock.connect((ACCESS_URL, ACCESS_PORT))  # 连接网站 ，发出一个HTTP请求
    except BlockingIOError:  # 非阻塞套接字捕获异常
        pass
    request_url = 'GET {} HTTP/1.0\r\nHost: www.baidu.com\r\n\r\n'.format('/s?wd={}'.format(pn))
    while True:  # 不断发送http请求
        try:
            sock.send(request_url.encode())
            break
        except OSError:
            pass
    response = b''
    while True:  # 不断地接收数据
        try:
            chunk = sock.recv(1024)  # 没有数据返回时会收到异常
            while chunk:  # 循环接收数据，因为一次接收不完整
                response += chunk
                chunk = sock.recv(1024)
            break
        except BlockingIOError:  # 处理非阻塞异常
            pass
    print(response.decode())
    return response


def block_way():
    for i in range(5):
        blocking(i)


if __name__ == '__main__':
    start = time.time()
    block_way()
    print('请求5次页面耗时{}'.format(time.time() - start))
    """
    请求5次页面耗时2.681565046310425
    时间消耗在不断的while循环中，和阻塞的时间差不多
    """
