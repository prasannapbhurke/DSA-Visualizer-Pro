# 📘 Problem 4 of 250 — LeetCode 1431: Kids With the Greatest Number of Candies

> **Pattern:** Array | **Roadmap Position:** #4/250

---

LeetCode 1431 — Kids With the Greatest Number of Candies
Problem #4 of 250 | Difficulty: Easy | Pattern: Array — Max Comparison

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Each kid can have the greatest if their candies + extraCandies >= max(candies).
Precompute max_candy first. Then for each kid, check if kid+extra >= max_candy.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Max Comparison

1. Find max_candy = max(candies)
2. For each kid: result[i] = (candies[i] + extraCandies >= max_candy)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def kidsWithCandies(candies, extraCandies):
    max_candy = max(candies)
    return [c + extraCandies >= max_candy for c in candies]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

candies=[2,3,5,1,3], extraCandies=3
max_candy=5
2+3=5>=5 True, 3+3=6>=5 True, 5+3=8>=5 True, 1+3=4>=5 False, 3+3=6>=5 True
Result: [True,True,True,False,True] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Compare each element to a precomputed max. List comprehension with boolean expressions is concise Python.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #4 (Kids With the Greatest Number of Candies):
  Can you apply the Array — Max Comparison pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 5 of 250*
