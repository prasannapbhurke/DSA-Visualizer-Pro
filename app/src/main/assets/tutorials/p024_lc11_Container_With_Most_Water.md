# 📘 Problem 24 of 250 — LeetCode 11: Container With Most Water

> **Pattern:** Two Pointers | **Roadmap Position:** #24/250

---

LeetCode 11 — Container With Most Water
Problem #24 of 250 | Difficulty: Medium | Pattern: Two Pointers — Greedy Convergence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find two lines that form the largest container with the x-axis.
Area = min(height[left], height[right]) * (right - left).
Two pointers from ends: move the shorter line inward (it can only get worse if we keep it).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Greedy Convergence

left=0, right=n-1, max_water=0
While left<right:
  water = min(height[left], height[right]) * (right-left)
  max_water = max(max_water, water)
  If height[left] < height[right]: left++ (move shorter line)
  Else: right--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxArea(height):
    left, right = 0, len(height)-1
    max_water = 0
    while left < right:
        max_water = max(max_water, min(height[left],height[right])*(right-left))
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1
    return max_water

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

height=[1,8,6,2,5,4,8,3,7]
left=0,right=8: min(1,7)*8=8, move left (1<7)
left=1,right=8: min(8,7)*7=49, move right (8>7)
left=1,right=7: min(8,3)*6=18, move right
...eventually max=49 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Greedy two-pointer: always move the shorter line. Why? Moving the taller line can only decrease or maintain the area (width decreases, height limited by the shorter). Moving the shorter MIGHT find a taller line → chance of improvement.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #24 (Container With Most Water):
  Can you apply the Two Pointers — Greedy Convergence pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 25 of 250*
