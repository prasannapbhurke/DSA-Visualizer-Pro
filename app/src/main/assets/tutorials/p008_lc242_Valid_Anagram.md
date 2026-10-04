# 📘 Problem 8 of 250 — LeetCode 242: Valid Anagram

> **Pattern:** Hash Map | **Roadmap Position:** #8/250

---

LeetCode 242 — Valid Anagram
Problem #8 of 250 | Difficulty: Easy | Pattern: Frequency Count — HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two strings s and t are anagrams if t is a REARRANGEMENT of s.
They must have the same characters with the same frequencies.

Example:
  s = "anagram", t = "nagaram" → true
  (same letters: a×3, n×1, g×1, r×1, m×1)

Example:
  s = "rat", t = "car" → false
  (s has 't', t has 'c' — different characters)

Return true if t is an anagram of s, false otherwise.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Ask: "Do they have the exact same character frequencies?"

  s = "anagram"       t = "nagaram"
  Count s: {a:3, n:1, g:1, r:1, m:1}
  Count t: {n:1, a:3, g:1, r:1, m:1}
  Equal? YES → true

  s = "rat"           t = "car"
  Count s: {r:1, a:1, t:1}
  Count t: {c:1, a:1, r:1}
  Equal? NO (t has 't', c has 'c') → false

Trick: Use ONE counter — increment for s, decrement for t.
If all counters return to 0 → anagram!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine two puzzles using the same pieces:
  Can you rearrange one puzzle's pieces to make the other?
  If yes → anagram.

To check programmatically:
  Count each letter in s
  Subtract each letter count for t
  If everything cancels to 0 → perfect rearrangement

Quick check: len(s) != len(t) → immediately false!
(Different lengths can't be anagrams)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Step 1 — Early exit:
  if len(s) != len(t):
      return False

Step 2 — Count frequencies:
  count = {}
  for c in s:
      count[c] = count.get(c, 0) + 1  # +1 for each char in s
  for c in t:
      count[c] = count.get(c, 0) - 1  # -1 for each char in t

Step 3 — Check all zeros:
  return all(v == 0 for v in count.values())

Or shortcut: if count goes negative, return False early:
  for c in t:
      count[c] = count.get(c, 0) - 1
      if count[c] < 0:   # t has more of this char than s
          return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "rat", t = "car"

len(3) == len(3) → continue

Count s: r→{r:1}, a→{r:1,a:1}, t→{r:1,a:1,t:1}

Process t:
  c: count.get('c',0)-1 = -1 → return False ✓

Answer: False (correct — 'rat' and 'car' are NOT anagrams)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Frequency counting is fundamental in string problems.

The one-map trick:
  +1 for each character in s
  -1 for each character in t
  If all values = 0 → perfect balance → anagram

Alternatively: sorted(s) == sorted(t)
  But this is O(n log n) vs O(n) for the counter approach.

Whenever you see:
  - "Is X a rearrangement of Y?"
  - "Do they have the same characters?"
→ Frequency counter / HashMap pattern

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

if len(s) != len(t): return False

count = {}
for c in s: count[c] = count.get(c, 0) + 1
for c in t: count[c] = count.get(c, 0) - 1

return all(v == 0 for v in count.values())

# Or use Counter:
from collections import Counter
return Counter(s) == Counter(t)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — two passes of length n
Space: O(1) — at most 26 letters in counter (fixed alphabet)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not checking length early (saves time)
2. Forgetting to check negative values (t has char not in s)
3. Using sorted() — works but O(n log n)
4. Case sensitivity — "Anagram" vs "anagram" are different!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC49 — Group Anagrams (group all anagram strings from a list)
LC438 — Find All Anagrams in a String (sliding window + anagram check)

---

*Next up: Problem 9 of 250*
