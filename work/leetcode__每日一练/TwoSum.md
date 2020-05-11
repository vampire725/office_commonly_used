# 练习

## 题目TwoSum

- 描述

1. 给定一个整数数组`nums`和一个目标值`target`，请你在该数组中找出和为目标值的那`两个`整数，并返回他们的`数组下标`。

2. 可以假设每种输入只会对应一个答案。不能重复利用这个数组中同样的元素。

eg:

    给定 nums = [2, 7, 11, 15], target = 9

    因为 nums[0] + nums[1] = 2 + 7 = 9
    
    所以返回 [0, 1]

- 思路

1. 利用enumerate遍历列表中，获取下标以及元素
2. 新建字典，key为遍历元素，value为下标值。
3. 每次遍历用`目标值`减去`遍历值`，得到的`结果`查询字典中是否存在，存在则返回`结果值`的下标和`遍历值`的下标，不存在则把`遍历值`以及`下标`存入字典中
4. 遍历结束以后，字典中没有符合条件的结果，则返回空列表

- 代码

```python
class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        index_dict={}
        for index,num in enumerate(nums):
            another_num=target-num
            if another_num in index_dict:
                return [index_dict[another_num],index]
            else:
                index_dict[num]=index
        return []
```
