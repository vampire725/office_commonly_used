# HTTP socket TCP

# socket 相当于一个插电。用于连接TCP 和 HTTP

import socket
import threading

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(('0.0.0.0', 8000))
server.listen()


def handle_socket(sock):
    data = sock.recv(1024)
    print(data.decode("utf8"))
    re_data = input()
    sock.send(("hello,".encode() + re_data.encode()))


while True:
    sock_arg, add = server.accept()
    client_thread = threading.Thread(target=handle_socket, args=(sock_arg,))
    client_thread.start()
# server.close()
# sock.close()
