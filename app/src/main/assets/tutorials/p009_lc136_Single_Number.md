# 📘 Problem 9 of 250 — LeetCode 136: Single Number

> **Pattern:** Bit Manipulation | **Roadmap Position:** #9/250

---

LeetCode 136 — Single Number
Problem #9 of 250 | Difficulty: Easy | Pattern: Bit Manipulation — XOR

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Every number except one appears exactly twice. XOR of same numbers = 0. XOR with 0 gives the number.
So XOR all elements → pairs cancel to 0 → only the unique number remains!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Bit Manipulation — XOR

result = 0
For each num: result ^= num
At end, result = the single number.

Why? XOR is associative and commutative.
  a^a=0, a^0=a
  So all pairs cancel: (a^a)^(b^b)^...^x = 0^0^...^x = x

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def singleNumber(nums):
    result = 0
    for num in nums:
        result ^= num
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[4,1,2,1,2]
result=0
^4=4, ^1=5, ^2=7, ^1=6 (5 XOR 1=4? no: 5=101, 1=001 XOR=100=4 ✓), ^2=6 (4=100, 2=010 XOR=110=6)
Wait: 0^4=4, 4^1=5, 5^2=7, 7^1=6, 6^2=4 ✓ (4 is single number)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

XOR is the perfect tool when pairs must cancel. x^x=0, x^0=x. No extra space needed! O(1) space, O(n) time.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #9 (Single Number):
  Can you apply the Bit Manipulation — XOR pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 10 of 250*
