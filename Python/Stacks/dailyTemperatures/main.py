# This is a sample Python script.

# Press ⌃R to execute it or replace it with your code.
# Press Double ⇧ to search everywhere for classes, files, tool windows, actions, and settings.
from typing import List

def dailyTemperatures(temps):
    hottest = 0
    n = len(temps)
    ans = [0] * n
    for today in range(n - 1, -1, -1):
        if (temps[today] >= hottest):
            hottest = temps[today]
            continue
        days = 1
        while (temps[today + days] <= temps[today]):
            days += ans[today + days]
        ans[today] = days
    return ans


def test1():
    input = [73,74,75,71,69,72,76,73]
    actual = dailyTemperatures(input)
    expected = [1, 1, 4, 2, 1, 1, 0, 0]
    assert actual == expected


if __name__ == '__main__':
    test1()