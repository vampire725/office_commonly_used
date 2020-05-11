class NumArray(object):
    def __init__(self,nums):
        self.nums = []
        total = 0
        for n in nums:
            total += n
            self.nums.append(total)
    def sumrange(self,i,j):
        if i>0:
            return self.nums[j]-self.nums[i-1]
        else:
            return self.nums[j]
print(NumArray([1,2,3,4,]).sumrange(2,3))