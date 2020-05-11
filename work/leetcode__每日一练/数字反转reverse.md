# 题目reverse，对数字进行反转

- 描述

给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。

- 思路

1. 先求数字的绝对值
2. 利用切片倒置数字
3. 如果输入数字大于零则取倒置数字本身，反之乘以-1

- 代码

```python
class Solution:
    def reverse(self, x: int) -> int:
        s = str(abs(x))
        reversed = int(s[::-1])
        if reversed > 2147483647:
            return 0
        return reversed if x>0 else (reversed * -1)
```
