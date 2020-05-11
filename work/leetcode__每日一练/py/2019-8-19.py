
# IP地址无效化
def daiti(str):
    return str.replace('.','[.]')
# print(daiti('0.0.0.0'))


def numpy_d(list):
    for i in range(list.count(0)):
        list.remove(0)
        list.append(0)
    return list

# print(numpy_d([9,0,8,0]))






def shuangzhizhen(list):
    flag=0
    for i in range(len(list)):
        if nums[i]!=0:
            nums[flag] = nums[i]
            flag+=1
    for i in range(flag,len(nums)):
        nums[i] = 0

def find_xiaoshi(nums):
    for num in nums:
        nums[abs(num) - 1] = -abs(nums[abs(num) - 1])
    return [i + 1 for i, num in enumerate(nums) if num > 0]

print(find_xiaoshi([4,7,4,3,2,3,1]))
















