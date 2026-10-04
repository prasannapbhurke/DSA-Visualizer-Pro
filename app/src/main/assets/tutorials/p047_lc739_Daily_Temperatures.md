# 📘 Problem 47 of 250 — LeetCode 739: Daily Temperatures

> **Pattern:** Monotonic Stack | **Roadmap Position:** #47/250

---

LeetCode 739 — Daily Temperatures
Problem #47 of 250 | Difficulty: Medium | Pattern: Monotonic Stack — Next Greater Index

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given daily temperatures, for each day find how many days until
a warmer temperature.
If no future warmer day exists, the answer is 0.

Example:
  temps = [73,74,75,71,69,72,76,73]
  For day 0 (73°): warmer on day 1 (74°) → wait 1 day
  For day 1 (74°): warmer on day 2 (75°) → wait 1 day
  For day 2 (75°): warmer on day 6 (76°) → wait 4 days
  For day 3 (71°): warmer on day 5 (72°) → wait 2 days
  Answer: [1,1,4,2,1,1,0,0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Monotonic Stack: stores indices of temperatures waiting for a warmer day.
Stack is DECREASING (temperatures decrease from bottom to top).

  temps = [73, 74, 75, 71, 69, 72, 76, 73]

  i=0: push 0 → stack=[0] (73°)
  i=1: 74>73 → ans[0]=1-0=1, pop 0. Stack empty → push 1 → stack=[1]
  i=2: 75>74 → ans[1]=2-1=1, pop 1. Stack empty → push 2 → stack=[2]
  i=3: 71<75 → push 3 → stack=[2,3]
  i=4: 69<71 → push 4 → stack=[2,3,4]
  i=5: 72>69 → ans[4]=5-4=1, pop 4. 72>71 → ans[3]=5-3=2, pop 3. 72<75 → push 5 → stack=[2,5]
  i=6: 76>72 → ans[5]=6-5=1, pop 5. 76>75 → ans[2]=6-2=4, pop 2. Stack empty → push 6
  i=7: 73<76 → push 7 → stack=[6,7]
  End: stack=[6,7] → ans[6]=0, ans[7]=0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Each person in a line is waiting to see someone TALLER ahead.
As a new (taller) person arrives, everyone who was shorter and waiting
gets their answer: "I waited X positions!"

The stack holds people who are STILL WAITING.
When a new person is taller than the top of the stack:
  The top person's wait is over! pop and record wait time.
  Repeat for anyone else who is now shorter.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def dailyTemperatures(temperatures):
    n = len(temperatures)
    answer = [0] * n
    stack = []   # Stack of indices

    for i in range(n):
        while stack and temperatures[i] > temperatures[stack[-1]]:
            prev_idx = stack.pop()
            answer[prev_idx] = i - prev_idx   # Days waited
        stack.append(i)

    return answer   # Remaining in stack keep answer=0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

temps = [73,74,75,71,69,72,76,73]

i=0: stack=[], push 0 → [0]
i=1: 74>73 → ans[0]=1, pop. Push 1 → [1]
i=2: 75>74 → ans[1]=1, pop. Push 2 → [2]
i=3: 71<75 → push 3 → [2,3]
i=4: 69<71 → push 4 → [2,3,4]
i=5: 72>69 → ans[4]=1, pop. 72>71 → ans[3]=2, pop. Push 5 → [2,5]
i=6: 76>72 → ans[5]=1, pop. 76>75 → ans[2]=4, pop. Push 6 → [6]
i=7: 73<76 → push 7 → [6,7]

End: ans = [1,1,4,2,1,1,0,0] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Monotonic decreasing stack for "next greater element to the right."

The distance formula: i - prev_idx
  i = index of the warmer day (current)
  prev_idx = index of the cooler day (popped from stack)
  Difference = days waited

This single-pass O(n) solution beats nested loops O(n²).

Pattern trigger: "next X to the right" → monotonic stack!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

answer = [0] * n
stack = []  # indices, decreasing temperatures
for i in range(n):
    while stack and nums[i] > nums[stack[-1]]:
        idx = stack.pop()
        answer[idx] = i - idx
    stack.append(i)
return answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — each index pushed and popped at most once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force nested loops: O(n²) — too slow for large n
2. Storing values in stack instead of INDICES (need index for distance calc!)
3. Not initializing answer with zeros

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC496 — Next Greater Element I (same pattern, simpler version)
LC503 — Next Greater Element II (circular array version)

---

*Next up: Problem 48 of 250*
