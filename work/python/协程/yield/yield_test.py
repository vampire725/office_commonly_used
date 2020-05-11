def gend_func():
    yield "http://projectsedu.com"  # 我们希望返回的值可以传入别的函数进行操作
    yield 2
    yield 3
    return "bobby"


# throw,close

if __name__ == "__main__":
    gen = gend_func()
    # print(next(gen))  # next直接真的yield来用，生成器方式有两种，next(),send
    url = gen.send(None)
    html = 'bobby'
    print(gen.send(html))
    # 会抛出异常，第一次send的时候，不能send一个非none值，只能sendNone,
    # 在使用send发送none值之前，我们必须启动一次生成器，方式有两种，
    # 1、gen.send(None) 2、next（gen）
    print(url)

    # print(next(gen))
    # print(next(gen))
    # print(next(gen))
