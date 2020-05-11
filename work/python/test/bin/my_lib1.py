# needed imports to make the last function work
import time
import os

def we_made_it_this_far():
    """This shows up in every programming course,
    but I held off as long as I could."""
    print("hello world!")

# We can save constants in our library
pi = 3.1415926535

def shouldIcallhome():
    """Yes, your parents miss you"""
    print("yes")

def is_over_9000(num):
    """This functions takes a number for an input and
    returns True if the number is over 9000"""

    return num > 9000


def should_I_go():
    """This functions tells you if you should go home
    and get ready for the weekend"""

    if time.time() > 1562612315:
        print("have a great afternoon!")
    else:
        print("Class still has a few minutes left")

def go_look(path):
    """This functions will change your directory,
    but also show you your complete path,
    and the contents of your new location"""

    os.chdir(path)
    print(os.getcwd())
    print(*os.listdir(),sep = "\n")

def bubble(unordered_list):
    """This function looks at all sequential pairs of
    elements in a list, and swaps them if the left is
    less than the right"""



    last_comparison = len(unordered_list) - 1

    for x in range(last_comparison,0,-1):
        for j in range(x):
            if unordered_list[j] > unordered_list[1+j]:
                unordered_list[j],unordered_list[j+1] = unordered_list[j+1],unordered_list[j]

    bubbled_list = unordered_list
    print(bubbled_list)
    return bubbled_list

def insert_item(item, sorted_list):
    """This function take an item and puts it in a sorted_list,
    taking advantage of the fact that the list is sorted."""

    if sorted_list == []:
        return [item]

    left = 0
    right = len(sorted_list)-1

    mid = (left + right) // 2
    diff = mid

    while diff > 0:

        value = sorted_list[mid]

        if value == item:
            sorted_list.insert(mid,item)
            break

        elif value < item:
            left = mid
            mid = (left + right)//2
            diff = mid - left

        else:
            right = mid
            mid = (left +right)//2
            diff = mid - left

    else:
        if item < sorted_list[mid]:
            sorted_list.insert(mid,item)
        elif item < sorted_list[right]:
            sorted_list.insert(right,item)
        else:
            sorted_list.insert(right + 1,item)
    print(sorted_list)
    return sorted_list
