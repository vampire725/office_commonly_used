def nums_pop(nums):
    flag = 0
    for num in nums:
        if nums[flag] != num:
            flag += 1
            nums[flag] = num
    return nums
# print(nums_pop([1,1,1,2,3,3,4]))


def removeDuplicates(nums):
    if len(nums) <= 1:
        return nums
    for i in range(len(nums)):
        for j in range(len(nums)):
            try:
                if nums[i] == nums[j] and i != j:
                    nums.pop(nums[i])
            except:
                break
    return nums

# print(removeDuplicates([0,0,0,0,0]))

def only_one(nums: list):
    return 2*sum(set(nums))-sum(nums)

def isValid(s):
    s = s.replace('{}', '').replace('()', '').replace('[]', '')
    s = s.replace('{}', '').replace('()', '').replace('[]', '')
    s = s.replace('{}', '').replace('()', '').replace('[]', '')
    print(s)
    if s == '':
        return True
    sp = len(s) // 2
    kh = {'(': ')', '{': '}', '[': ']'}
    a_half = s[0:sp][::-1]
    another_half = s[sp::]
    if len(a_half)!=len(another_half):
        return False
    for x, y in zip(a_half, another_half):
        if x in kh.keys() and y == kh[x]:
            continue
        else:
            return False
    return True
a_str="([]{{}})[{}[[]]]"
print(isValid(a_str))



