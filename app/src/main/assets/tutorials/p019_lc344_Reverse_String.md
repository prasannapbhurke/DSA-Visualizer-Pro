# 📘 Problem 19 of 250 — LeetCode 344: Reverse String

> **Pattern:** Two Pointers | **Roadmap Position:** #19/250

---

LeetCode 344 — Reverse String
Problem #19 of 250 | Difficulty: Easy | Pattern: Two Pointers — Swap Ends

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Reverse in-place: swap character at index i with character at index n-1-i.
Two pointers from ends converging to middle.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Swap Ends

left=0, right=len(s)-1
While left<right: swap s[left] and s[right], left++, right--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def reverseString(s):
    left, right = 0, len(s)-1
    while left < right:
        s[left], s[right] = s[right], s[left]
        left += 1
        right -= 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s=['h','e','l','l','o']
left=0,right=4: swap h,o → ['o','e','l','l','h']
left=1,right=3: swap e,l → ['o','l','l','e','h']
left=2,right=2: left>=right, stop
Result: ['o','l','l','e','h'] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two-pointer swapping from ends. This is the building block for array reversal, palindrome checking, and many other problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #19 (Reverse String):
  Can you apply the Two Pointers — Swap Ends pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 20 of 250*
