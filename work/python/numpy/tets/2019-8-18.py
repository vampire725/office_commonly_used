import re


def string_to_atoi(str):
    str = str.strip()
    if len(str) == 0:
        return 0
    index = 0
    if str[index] in ['-', '+']:
        index = 1
    if len(str[index:]) == 0 or not str[index].isdecimal():
        return 0
    while index < len(str):
        if not str[index].isdecimal():
            break
        index += 1
    if int(str[:index]) > 2 ** 31 - 1:
        return 2 ** 31 - 1
    if int(str[:index]) < -2 ** 31:
        return -2 ** 31
    return int(str[:index])


print(string_to_atoi("+111989767564564534"))


def string_to_atoi_other(s):
    print(int())
    print(int(*re.findall('^[\+\-]?\d+', s.lstrip())))
    return max(min(int(*re.findall('^[\+\-]?\d+', s.lstrip())), 2 ** 31 - 1), -2 ** 31)


# print(string_to_atoi_other('+-666f'))
# print(''.join(list('ksjdk')))


def string_to_atoi_2(s):
    s = s.lstrip()
    if len(s) == 0:
        return 0
    index = 0
    if s[index] in ['-', '+']:
        index = 1
    if len(s[index:]) == 0 or not s[index].isdecimal():
        return 0
    while index < len(s):
        if not s[index].isdecimal():
            break
        index += 1
    s = int(s[:index])
    if s < -2 ** 31:
        return -2 ** 31
    if s > 2 ** 31 - 1:
        return 2 ** 31 - 1
    return s


print(string_to_atoi_2('-98981234567890'))
