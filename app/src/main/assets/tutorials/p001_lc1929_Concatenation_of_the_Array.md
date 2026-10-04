# 📘 Problem 1 of 250 — LeetCode 1929: Concatenation of the Array

> **Pattern:** Array | **Roadmap Position:** #1/250

---

LeetCode 1929 — Concatenation of the Array
Problem #1 of 250 | Difficulty: Easy | Pattern: Array — Index Doubling

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an integer array nums of length n, create and return an array ans of length 2n where ans[i]=nums[i] and ans[i+n]=nums[i] for all 0<=i<n. Simply double the array!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Index Doubling

Two passes or list concatenation. ans = nums + nums in Python is one line!
  For each i in 0..n-1: ans[i] = nums[i] and ans[i+n] = nums[i]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def getConcatenation(nums):
    return nums + nums
    # Or: n=len(nums); return [nums[i%n] for i in range(2*n)]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,2,1]
ans=[1,2,1,1,2,1]
i=0: ans[0]=1, ans[3]=1
i=1: ans[1]=2, ans[4]=2
i=2: ans[2]=1, ans[5]=1 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simplest array problem. Python list concatenation with + makes it a one-liner. Understand index arithmetic: ans[i+n] accesses the second half.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #1 (Concatenation of the Array):
  Can you apply the Array — Index Doubling pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 2 of 250*
