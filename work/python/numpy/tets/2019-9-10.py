def findUnsortedSubarray(nums):
    if nums == sorted(nums):
        return 0
    while nums[-1] == max(nums):
        nums.pop()
    while nums[0] == min(nums):
        nums.pop(0)
    return len(nums)


print(findUnsortedSubarray([1,3,2,4,5]))
