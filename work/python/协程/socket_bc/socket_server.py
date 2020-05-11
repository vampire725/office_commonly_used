import socket
from urllib.parse import urlparse


def get_url(url):
    url = urlparse(url)
    host = url.netloc
    path = url.path
    if path == "":
        path = "/"
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        client.connect((host, 80))
        client.send(("GET {} HTTP/1.1\r\nHost: {}\r\n\r\n").format(path,host).encode('utf8'))
    except BlockingIOError as e:
        pass
    data = b""
    d = client.recv(1024)
    while d:
        data += d
        d = client.recv(1024)
        print(data)
    data = data.decode('utf8')
    html_data = data.split('\r\n\r\n')[0]
    print(data)
    client.close()


if __name__ == '__main__':
    get_url("http://www.baidu.com")
