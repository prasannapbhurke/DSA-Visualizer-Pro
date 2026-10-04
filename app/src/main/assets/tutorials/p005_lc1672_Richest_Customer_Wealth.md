# 📘 Problem 5 of 250 — LeetCode 1672: Richest Customer Wealth

> **Pattern:** Array | **Roadmap Position:** #5/250

---

LeetCode 1672 — Richest Customer Wealth
Problem #5 of 250 | Difficulty: Easy | Pattern: 2D Array — Row Sum Max

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

accounts[i][j] = money in bank j for customer i.
Compute sum of each row. Return the maximum row sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D Array — Row Sum Max

For each customer (row), compute their total wealth = sum(accounts[i]).
Track the maximum wealth seen.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maximumWealth(accounts):
    return max(sum(row) for row in accounts)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

accounts=[[1,2,3],[3,2,1]]
Customer 0: 1+2+3=6
Customer 1: 3+2+1=6
max=6 ✓

accounts=[[1,5],[7,3],[3,5]]
Sums: 6, 10, 8 → max=10 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

2D array traversal: iterate rows, sum each. Python's sum() and max() with generator make it one line. Build intuition for 2D array access: accounts[row][col].

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #5 (Richest Customer Wealth):
  Can you apply the 2D Array — Row Sum Max pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 6 of 250*
