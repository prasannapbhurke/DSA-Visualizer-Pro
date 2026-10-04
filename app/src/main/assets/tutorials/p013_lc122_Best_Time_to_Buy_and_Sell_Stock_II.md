# 📘 Problem 13 of 250 — LeetCode 122: Best Time to Buy and Sell Stock II

> **Pattern:** Greedy | **Roadmap Position:** #13/250

---

LeetCode 122 — Best Time to Buy and Sell Stock II
Problem #13 of 250 | Difficulty: Easy | Pattern: Greedy — Capture All Gains

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can buy and sell multiple times. Key insight: capture EVERY upward move!
If prices[i+1] > prices[i], add the difference (buy on day i, sell on day i+1).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Capture All Gains

Sum all positive differences between consecutive days.
profit += max(0, prices[i+1] - prices[i]) for each i.
This is equivalent to capturing every ascending sequence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxProfit(prices):
    profit = 0
    for i in range(1, len(prices)):
        if prices[i] > prices[i-1]:
            profit += prices[i] - prices[i-1]
    return profit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prices=[7,1,5,3,6,4]
7→1: negative, skip
1→5: +4, profit=4
5→3: negative, skip
3→6: +3, profit=7
6→4: negative, skip
Return 7 ✓ (buy at 1, sell at 5; buy at 3, sell at 6)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Greedy: capture every profitable day transition. No need to find best buy/sell windows — just sum all gains. Elegant and provably optimal.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #13 (Best Time to Buy and Sell Stock II):
  Can you apply the Greedy — Capture All Gains pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 14 of 250*
