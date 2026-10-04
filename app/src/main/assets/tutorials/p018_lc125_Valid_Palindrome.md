# 📘 Problem 18 of 250 — LeetCode 125: Valid Palindrome

> **Pattern:** Two Pointers | **Roadmap Position:** #18/250

---

LeetCode 125 — Valid Palindrome
Problem #18 of 250 | Difficulty: Easy | Pattern: Two Pointers — Converging

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After cleaning (lowercase, alphanumeric only), check if string reads same forwards and backwards.
Two pointers from both ends: compare characters until pointers meet.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Converging

left=0, right=len(s)-1
Skip non-alphanumeric characters.
Compare s[left].lower() with s[right].lower().
If mismatch → not palindrome.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isPalindrome(s):
    left, right = 0, len(s)-1
    while left < right:
        while left < right and not s[left].isalnum(): left += 1
        while left < right and not s[right].isalnum(): right -= 1
        if s[left].lower() != s[right].lower(): return False
        left += 1; right -= 1
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s='A man, a plan, a canal: Panama'
After alnum filter: 'amanaplanacanalpanama'
left=A, right=a: a==a ✓
left=m, right=m: ✓
...all match → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two-pointer palindrome check: converge from both ends. The key: skip non-alphanumeric and compare case-insensitively.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #18 (Valid Palindrome):
  Can you apply the Two Pointers — Converging pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 19 of 250*
