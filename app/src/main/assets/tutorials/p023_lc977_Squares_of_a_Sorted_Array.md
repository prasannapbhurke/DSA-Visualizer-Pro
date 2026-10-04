# 📘 Problem 23 of 250 — LeetCode 977: Squares of a Sorted Array

> **Pattern:** Two Pointers | **Roadmap Position:** #23/250

---

LeetCode 977 — Squares of a Sorted Array
Problem #23 of 250 | Difficulty: Easy | Pattern: Two Pointers — Fill from Ends

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Array is sorted (may have negatives). Square of largest absolute value could be at either end.
Two pointers from both ends, fill result from BACK (largest first).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Fill from Ends

left=0, right=n-1, pos=n-1
Compare |nums[left]| and |nums[right]|.
Whichever is larger, square it and put at result[pos], advance that pointer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortedSquares(nums):
    n = len(nums)
    result = [0] * n
    left, right = 0, n-1
    pos = n-1
    while left <= right:
        if abs(nums[left]) > abs(nums[right]):
            result[pos] = nums[left]**2; left += 1
        else:
            result[pos] = nums[right]**2; right -= 1
        pos -= 1
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[-4,-1,0,3,10]
left=0,right=4: |-4|=4, |10|=10: 10>4 → result[4]=100, right=3
left=0,right=3: 4 vs 3: 4>3 → result[3]=16, left=1
left=1,right=3: 1 vs 3: 3>1 → result[2]=9, right=2
left=1,right=2: 1 vs 0: 1>0 → result[1]=1, left=2
left=2,right=2: 0 → result[0]=0
Return [0,1,9,16,100] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill from back when you know the largest element but not the order of smaller ones. Two pointers from ends, place the winner at the back.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #23 (Squares of a Sorted Array):
  Can you apply the Two Pointers — Fill from Ends pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 24 of 250*
