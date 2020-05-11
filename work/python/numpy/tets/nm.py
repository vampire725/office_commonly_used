import codex as codex


def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr[len(arr) // 2]
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    return quick_sort(left) + middle + quick_sort(right)


# print(quick_sort([3, 6, 8, 10, 1, 2, 1]))

# s = 'hello'
# print(s.capitalize()) # 首字母大写
# print(s.upper()) # 全大写
# print(s.rjust(7)) # 七个字符靠右
# print(s.center(7)) # 七个字符中间
# print(s.replace('l', '(ell)')) # （ell）代替l
# print('  world '.strip()) # 去除空白)

# d = {'cat': 'cute', 'dog': 'furry'}  # Create a new dictionary with some data
# print(d['cat'])       # Get an entry from a dictionary; prints "cute"
# print('cat' in d)     # Check if a dictionary has a given key; prints "True"
# d['fish'] = 'wet'     # Set an entry in a dictionary
# print(d['fish'])      # Prints "wet"
# # print(d['monkey'])  # KeyError: 'monkey' not a key of d
# print(d.get('monkey', 'N/A'))  # Get an element with a default; prints "N/A"
# print(d.get('fish', 'N/A'))    # Get an element with a default; prints "wet"
# del d['fish']         # Remove an element from a dictionary
# print(d.get('fish', 'N/A'))

# nums = [0, 1, 2, 3, 4]
# even_num_to_square = {x: x ** 2 for x in nums if x % 2 == 0}
# print(even_num_to_square)



# from math import sqrt
# nums = {int(sqrt(x)) for x in range(30)}
# print(nums)

# d = {(x, x + 1): x for x in range(10)}  # Create a dictionary with tuple keys
# t = (5, 6)        # Create a tuple
# print(type(t))    # Prints "<class 'tuple'>"
# print(d[t])       # Prints "5"
# print(d[(1, 2)])

import numpy as np

# a = np.array([1,2,3])
# print(type(a))
# print(a.shape)
#
# a = np.zeros((2,2))
# print(a)
#
# b = np.ones((1,2))
# print(b)
#
# c = np.full((2,2),7)
# print(c)
#
# d = np.eye(3)
# print(d)



# import re
# mystr1="<div><title>hel<title>lowo</title>rld</title></div>"
# list_tes = ['div','title']
# for i in list_tes:
#     res1=re.findall(f"<{i}+?>([\s\S]*)<.*/{i}+?>.*?",mystr1)  #前后标签必须一样才能匹配
#     print(res1)
#
# html='<a href="//www.jb51.net">脚本之家</a>,Python学习！'
# dr = re.compile(r'<[^>]+>',re.S)
# dd = dr.sub('',html)
# print(dd)







# a = np.array([[1,2,3,4],[5,6,7,8],[9,10,11,12]])
# b = a[:2,1:3] # :2代表第0行和第1行，1：3代表第一列和第二列
# print(b)
# print(a[0,1]) # 2
# b[0,0] = 77
# print(a[0,1]) # 改变b以后，a的数据也会随之被改变


# a = np.array([[1,2,3,4],[5,6,7,8],[9,10,11,12]])
# row_r1 = a[1,:]
# row_r2 = a[1:2,:]
# print(row_r1,row_r1.shape)
# print(row_r2,row_r2.shape)

# a = np.array([[1,2],[3,4],[5,6]])
# print(a[[0,1,2],[0,1,0]])
#
# print(np.array([a[0,0],a[1,1],a[2,0]]))
# print(a[[0,0],[1,1]])
# print(np.array([a[0,1],a[0,1]]))


# a = np.array([[1,2,3],[4,5,6],[7,8,9],[10,11,12]])
# print(a,a.shape)
# b = np.array([0,2,0,1])
# print(a[np.arange(4),b])
# print(a[[0,1,2,3],[0,2,0,1]])
#
# a[np.arange(4),b]+=10
#
# print(a)


# a = np.array([[1,2],[3,4],[5,6]])
# bool_idx = (a > 2)
# print(bool_idx)
# print(a[bool_idx]) #[3,4,5,6]

# x = np.array([1,2])
# print(x.dtype)
#
# x = np.array([1.0,2.0])
# print(x.dtype)
#
# x = np.array([1,2],dtype=np.int64)
# print(x.dtype)

# x = np.array([[1,2],[3,4]],dtype=np.float64)
# y = np.array([[5,6],[7,8]],dtype=np.float64)


# print(np.subtract(x,y))
# print(x*y)
# print(np.multiply(x,y))
#
# print(np.sqrt(x))


# text = "aoeu tB'.kE08fId76Np7gGaoeK thA.k9K8f S765T7gbUoeuDth'Ek90Nf dT65pKgbaIeu Sh'.K908F d7U5p7Nb"
# text1="abcde fghijk lmnop q rst uvwx yz "
# text2 = "oel crh\r.uqb 3p4y2\n3u0j98 gb5ky9dy7k865 ej54 \t.qecmh87  pf86k5pjsoel crhd.uqb 3poy2\n3u0jg8 gb5ky  y7k865dpj" \
#         "54 \t.n[cmh87 axf86k5p ]oel cr,\r.uqb 3s4y2\n3u0n98 gb5ko9 y7k86i pj54 \ttq[cmh87a xf86k5lj]oel cuh\r.uqb tp4y2" \
#         "\n3uaj98 gb5ry9 y7k8g5 pj54 n.q[cmh8o  xf86kcpj]"
# hidden_message = text2[32::8][::-1]
# print(hidden_message)

# x = np.array([[1,2],[3,4]])
# y = np.array([[5,6],[7,8]])
#
# v = np.array([9,10])
# w = np.array([11,12])
#
# print(v.dot(w))
# print(np.dot(v,w))
# print(x.dot(y))
# print(np.dot(x,y))

# x = np.array([[1,2],[3,4]])
# print(np.sum(x))
# print(np.sum(x,axis=0)) # 计算每列的总和
# print(np.sum(x,axis=1)) # 计算每行的总和

# 转置矩阵
# x = np.array([[1,2],[3,4]])
# print(x)
# print(x.T)
#
# v= np.array([1,2,3])
# print(v)
# print(v.T)

# 广播

# x = np.array([[1,2,3],[4,5,6],[7,8,9],[10,11,12]])
# v = np.array([1,0,1])
# y = np.empty_like(x) # 创建一个跟x形状相同的矩阵
#
# for i in range(4):
#     y[i,:]=x[i,:]+v
#
# print(y)


# x = np.array([[1,2,3],[4,5,6],[7,8,9],[10,11,12]])
# v = np.array([1,0,1])
# vv = np.tile(v,(4,1))
# print(vv)
#
# y = x+vv
# print(y)

# class Solution:
#     def reverse(x: int) -> int:
#         s = str(abs(x))
#         reversed = int(s[::-1])
#         if reversed > 2147483647:
#             return 0
#         return reversed if x>0 else (reversed * -1)
# # print(Solution.reverse(990))
#
# list=['float','flower','floor']
#
# str_min= min(list)
# str_max = max(list)
# print(str_min,str_max)


# def longestCommonPrefix(strs):
#     if not strs: return ""
#     str_min = min(strs)
#     str_max = max(strs)
#     for index, i in enumerate(str_min):
#         if i != str_max[index]:
#             return str_min[:index]
#     return str_min
#
#
# print(longestCommonPrefix([']','as','ad']))


list_02 = [1,1,2]
list_02.pop(1)
# for i in list_02:
#     print(i.__index__())
# print(list(set(list_02)))
def num_pop(nums):
    if len(nums) <= 1:
        return nums
    for i in nums:
        for j in nums:
            if i == j and i.__index__() != j.__index__():
                nums.pop(i)
    return nums
# print(num_pop(list_02))



            

