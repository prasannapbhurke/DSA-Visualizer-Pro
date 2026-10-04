# 📘 Problem 48 of 250 — LeetCode 503: Next Greater Element II

> **Pattern:** Monotonic Stack | **Roadmap Position:** #48/250

---

LeetCode 503 — Next Greater Element II
Problem #48 of 250 | Difficulty: Medium | Pattern: Monotonic Stack — Circular Array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a CIRCULAR array, find next greater element for each position.
For circular: after the last element, wrap around to the first.

Example:
  nums = [1, 2, 1]
  For 1 (index 0): next greater going right = 2 → [2]
  For 2 (index 1): no greater in [1] going around → [-1]
  For 1 (index 2): wraps to index 0 → 2 is greater → [2]
  Result: [2, -1, 2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trick: Simulate circular by doubling the array!
Process indices 0 to 2n-1, using index % n for actual position.

  nums = [1, 2, 1] (n=3)

  Process indices 0,1,2,3,4,5:
  i=0: val=1, push 0
  i=1: val=2, 2>1 → ans[0]=2, pop. push 1
  i=2: val=1, push 2
  i=3: val=nums[0]=1, 1 < 2 → push 3%3=0... wait use % n
  Continue: only record in ans if i < n

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simulate the circular walk by going TWICE around.
Process indices 0 to 2n-1, but use i%n to get actual value.
Only update answer for i < n (first lap sets the answers).
Second lap just resolves remaining elements that haven't found next greater.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def nextGreaterElements(nums):
    n = len(nums)
    result = [-1] * n
    stack = []   # Stack of indices

    for i in range(2 * n):
        while stack and nums[stack[-1]] < nums[i % n]:
            idx = stack.pop()
            result[idx] = nums[i % n]
        if i < n:
            stack.append(i)  # Only push first lap indices

    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [1, 2, 1], n=3

result = [-1,-1,-1], stack=[]

i=0: val=1, push 0 → stack=[0]
i=1: val=2, 2>1 → result[0]=2, pop. Stack empty → push 1 → stack=[1]
i=2: val=1, 1<2 → push 2 → stack=[1,2]
i=3: val=nums[0]=1, 1<2 → don't push (i>=n)
i=4: val=nums[1]=2, 2==2 → no. stack==[1,2]
i=5: val=nums[2]=1, 1<2 → no. done

stack=[1,2] → result[1]=-1, result[2]=-1... wait
Actually i=4: nums[4%3]=nums[1]=2. 2>1(stack[-1]=2 means nums[2]=1) → result[2]=2

stack=[1]. i=5: nums[5%3]=nums[2]=1 < nums[1]=2 → no.

End: result=[-1,-1,-1]... let me recheck.
Actually stack stores indices. stack[-1]=2 means nums[2]=1.
i=4: nums[4%3]=nums[1]=2. 2>nums[stack[-1]]=nums[2]=1 → result[2]=2, pop. stack=[1]. 2>nums[1]=2? No.
i=5: nums[2]=1 < nums[1]=2 → no pop.
End: stack=[1] → result[1]=-1.

result = [2, -1, 2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The "double array" trick for circular arrays:
  Process 2n indices using i%n for value access.
  Only push to stack during the first n iterations (i < n).
  Second round resolves remaining lookups.

This same doubling trick applies to other circular array problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

n = len(nums)
result = [-1] * n
stack = []
for i in range(2*n):
    while stack and nums[stack[-1]] < nums[i%n]:
        result[stack.pop()] = nums[i%n]
    if i < n: stack.append(i)
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process 2n elements, each pushed/popped once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using i%n for circular access
2. Pushing for i >= n (only need to push in first pass)
3. Confusing when to record answers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you needed the "next smaller element" in a circular array?
Change the comparison: nums[stack[-1]] > nums[i%n]

---

*Next up: Problem 49 of 250*
