# def delete_sort(a_list):
#     flag = 0
#     for a in a_list:
#         if a_list[flag] != a:
#             flag += 1
#             a_list[flag] = a
#     print(a_list)
#     return flag + 1
#
#
# a = [1, 3, 3, 5, 5, '1', 'a', 3, 'i', 'a']
#
# print(delete_sort([1,2,5,5,37]))
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None
def merge_two_list(l1,l2):
    res = ListNode(None)
    node = res
    while l1 and l2:
        if l1.val < l2.val:
            node.next, l1 = l1, l1.next
        else:
            node.next, l2 = l2, l2.next
        node = node.next
    if l1:
        node.next = l1
    else:
        node.next = l2
    return res.next
#
# print(merge_two_list([1,1,2,3,4],[0,1,1,2,2,3,3,3,4]))


def climbStairs(n: int):
    i = 1    # 爬到1台阶仅有1种方法
    j = 2    # 爬到2台阶有2种方法
    for _ in range(3, n):         # 自底向上递推 F(n)=F(n-1)+F(n-2)
        i, j = j, i + j           # 每次仅保留前两个值，依次往后推算
    return i + j if n > 2 else n

print(climbStairs(23))


def clim_stairs(n):
    if n==1:
        return 1
    if n==2:
        return 2
    else:
        return clim_stairs(n-1)+clim_stairs(n-2)

print(clim_stairs(4))