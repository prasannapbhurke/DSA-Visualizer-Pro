# 📘 Problem 101 of 250 — LeetCode 1052: Grumpy Bookstore Owner

> **Pattern:** Sliding Window | **Roadmap Position:** #101/250

---

LeetCode 1052 — Grumpy Bookstore Owner
Problem #101 of 250 | Difficulty: Medium | Pattern: Fixed Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1052: Grumpy Bookstore Owner | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fixed Sliding Window

Core Insight:
The bookstore owner can suppress grumpiness for a window of 'minutes' consecutive minutes.
Baseline = satisfaction when owner is NOT grumpy.
Extra = maximum additional customers gained from the best 'minutes' window of grumpy minutes.
Answer = baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
1. Compute baseline: sum of customers[i] where grumpy[i]=0
2. Find window of size 'minutes' that captures the MOST customers where grumpy[i]=1
3. Add that maximum extra to the baseline

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

baseline = sum(c for c,g in zip(customers,grumpy) if g==0)
window = sum(c*g for c,g in zip(customers[:k], grumpy[:k]))
max_extra = window
for i in range(k, len(customers)):
    window += customers[i]*grumpy[i] - customers[i-k]*grumpy[i-k]
    max_extra = max(max_extra, window)
return baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

customers=[1,0,1,2,1,1,7,5], grumpy=[0,1,0,1,0,1,0,1], minutes=3

baseline = 1+1+1+7 = 10 (non-grumpy minutes: 0,2,4,6)

Sliding window of size 3 over grumpy minutes:
[0,1,2]: extra = 0 (customers[1]=0, grumpy[1]=1)
[1,2,3]: extra = 2 (customers[3]=2, grumpy[3]=1)
[2,3,4]: extra = 2
[3,4,5]: extra = 2+1=3
[4,5,6]: extra = 1
[5,6,7]: extra = 1+5=6 ← max extra!

Answer = 10 + 6 = 16

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fixed-size sliding window: add new, remove old, track max.
Separation of baseline + bonus makes the problem tractable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

baseline = sum(c for c,g in zip(customers,grumpy) if g==0)
window = sum(c*g for c,g in zip(customers[:k], grumpy[:k]))
max_extra = window
for i in range(k, len(customers)):
    window += customers[i]*grumpy[i] - customers[i-k]*grumpy[i-k]
    max_extra = max(max_extra, window)
return baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — think: "what have I seen that looks like this?"
2. Brute force when a smarter approach exists
3. Off-by-one errors in indices/boundaries
4. Not handling edge cases (empty, single, all same)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: Can you identify 2 other LeetCode problems with the same pattern?
Pattern mastery is built through RECOGNITION, not memorization.

---

*Next up: Problem 102 of 250*
