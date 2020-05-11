"""
最大叙述和
"""

def list_max_num(nums):
    sum = 0
    max_sub_sum =nums[0]
    for num in nums:
        sum += num
        if sum > max_sub_sum:
            max_sub_sum = sum
        if sum < 0:
            sum = 0
    return max_sub_sum

print(list_max_num([1,-1,2,8,0,9,-4]))
