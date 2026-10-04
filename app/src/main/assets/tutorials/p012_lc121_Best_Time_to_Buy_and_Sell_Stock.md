# 📘 Problem 12 of 250 — LeetCode 121: Best Time to Buy and Sell Stock

> **Pattern:** Array / Greedy | **Roadmap Position:** #12/250

---

LeetCode 121 — Best Time to Buy and Sell Stock
Problem #12 of 250 | Difficulty: Easy | Pattern: Array — Track Running Min

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find max profit from one buy-sell. Buy at minimum price, sell at maximum after buy.
As you scan, track the minimum price seen so far. Profit = current_price - min_price.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Track Running Min

min_price = infinity, max_profit = 0
For each price:
  profit = price - min_price
  max_profit = max(max_profit, profit)
  min_price = min(min_price, price)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxProfit(prices):
    min_price = float('inf')
    max_profit = 0
    for price in prices:
        max_profit = max(max_profit, price - min_price)
        min_price = min(min_price, price)
    return max_profit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prices=[7,1,5,3,6,4]
min=inf,profit=0
7: profit=-inf→0 (max=0), min=7
1: profit=-6→0 (max=0), min=1
5: profit=4 (max=4), min=1
3: profit=2 (max=4), min=1
6: profit=5 (max=5), min=1
4: profit=3 (max=5), min=1
Return 5 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Track running minimum. At each point, the best profit is current_price - running_minimum. Update both the min and the max_profit in one pass.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #12 (Best Time to Buy and Sell Stock):
  Can you apply the Array — Track Running Min pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 13 of 250*
