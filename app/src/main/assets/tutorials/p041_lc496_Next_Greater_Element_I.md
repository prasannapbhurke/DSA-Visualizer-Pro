# 📘 Problem 41 of 250 — LeetCode 496: Next Greater Element I

> **Pattern:** Monotonic Stack | **Roadmap Position:** #41/250

---

LeetCode 496 — Next Greater Element I
Problem #41 of 250 | Difficulty: Easy | Pattern: Monotonic Stack — Next Larger to Right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two arrays nums1 and nums2 (all distinct),
for each element in nums1, find the NEXT GREATER element in nums2.
"Next greater" = first element to the right that is larger.

Example:
  nums1 = [4,1,2], nums2 = [1,3,4,2]
  For 4: no greater to its right → -1
  For 1: next greater = 3
  For 2: no greater to its right → -1
  Result: [-1, 3, -1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process nums2 with a MONOTONIC STACK.
Stack maintains elements without a "next greater" yet.

  nums2 = [1, 3, 4, 2]

  num=1: stack=[1]
  num=3: 3>1 → 1's next greater = 3! map[1]=3, pop 1. stack=[3]
  num=4: 4>3 → 3's next greater = 4! map[3]=4, pop 3. stack=[4]
  num=2: 2<4 → push. stack=[4,2]
  End: remaining stack = [4,2] → next greater = -1

  map = {1:3, 3:4, 4:-1, 2:-1}
  Result for nums1=[4,1,2]: [-1, 3, -1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine each person is waiting to find someone taller to their right.
Walk right to left through nums2.
Stack holds people still "waiting" for someone taller.

When you see a new person:
  If they're taller than the top of the stack → top's wait is over! Pop and record.
  Repeat until stack is empty or top is taller.
  Push new person — they now wait for their next greater.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def nextGreaterElement(nums1, nums2):
    stack = []
    next_greater = {}   # maps num → its next greater element

    for num in nums2:
        # Pop all elements smaller than current num
        while stack and stack[-1] < num:
            next_greater[stack.pop()] = num
        stack.append(num)

    # Remaining in stack have no next greater
    while stack:
        next_greater[stack.pop()] = -1

    return [next_greater[n] for n in nums1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums2 = [1, 3, 4, 2]

num=1: stack empty, push 1 → stack=[1]
num=3: stack[-1]=1 < 3 → map[1]=3, pop. Stack empty → push 3 → stack=[3]
num=4: stack[-1]=3 < 4 → map[3]=4, pop. Stack empty → push 4 → stack=[4]
num=2: stack[-1]=4 > 2 → push 2 → stack=[4,2]

End: stack=[4,2] → map[2]=-1, map[4]=-1

next_greater = {1:3, 3:4, 2:-1, 4:-1}

nums1=[4,1,2] → [-1, 3, -1] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

MONOTONIC STACK: a stack that's always sorted (either increasing or decreasing).

When we push an element:
  Pop everything smaller (for "next greater") or larger (for "next smaller").
  The popped elements have found their answer!

This runs in O(n) total — each element is pushed and popped at most once.

Monotonic stacks appear in:
  LC739 — Daily Temperatures (same pattern!)
  LC84 — Largest Rectangle in Histogram
  LC42 — Trapping Rain Water

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
next_greater = {}
for num in nums2:
    while stack and stack[-1] < num:
        next_greater[stack.pop()] = num
    stack.append(num)
while stack: next_greater[stack.pop()] = -1
return [next_greater[n] for n in nums1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — process nums2 O(n), build result O(m)
Space: O(n) — stack + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force: O(m×n) — searching for next greater for each element
2. Forgetting to handle elements with no next greater (assign -1)
3. Wrong direction: need to process nums2 and find first GREATER to RIGHT

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC503 — Next Greater Element II (circular array!)
LC739 — Daily Temperatures (same idea: next warmer day)

---

*Next up: Problem 42 of 250*
