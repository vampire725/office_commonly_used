import operator
from itertools import permutations, dropwhile

word_list = ['dad', 'yoyo', 'tomriddle', 'tom.marvolo.riddle']
anagrams_dict = {k: list(permutations(k)) for k in iter(word_list)}
print(anagrams_dict)

print('hello')

def accumulate(iterable,func=operator.add):
    it = iter(iterable)
    try:
        total = next(it)
    except StopAsyncIteration:
        return
    yield total
    for element in it:
        total = func(total,element)
        yield total
data = [3,4,6,2,1]
print(list(accumulate(data,operator.mul)))
print(list(accumulate(data,max)))

cashflows = [1000,-90,-90,-90]
print(list(accumulate(cashflows,lambda bal,pmt: bal*1.05 + pmt)))


logistic_map = lambda x,_:  r * x * (1-x)
r = 3.8
x0=0.4
inputs=repeat(x0,36)





dropwhile(lambda x: x<5, [1,4,6,4,1])


















