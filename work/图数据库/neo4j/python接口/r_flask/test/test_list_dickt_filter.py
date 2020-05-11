list_test = [{'name': 'pp'}, {'name': 'pp'}]
list_yy = []
# print([dict(t) for t in set([tuple(d.items()) for d in list_test])])
for d in list_test:
    print(d,d.items())
    print(tuple(d.items()))
    list_yy.append(tuple(d.items()))
    print(set(list_yy))
    for t in set(list_yy):
        print(dict(t))

