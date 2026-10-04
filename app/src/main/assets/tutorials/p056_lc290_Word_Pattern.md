# 📘 Problem 56 of 250 — LeetCode 290: Word Pattern

> **Pattern:** Hash Map | **Roadmap Position:** #56/250

---

LeetCode 290 — Word Pattern
Problem #56 of 250 | Difficulty: Easy | Pattern: HashMap — Bidirectional Word Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern 'aabb' and string 'dog dog cat cat'.
Check if pattern characters biject to words.
Needs bidirectional mapping: char→word AND word→char.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap — Bidirectional Word Mapping

Split string into words. Check len(pattern)==len(words).
Two maps: char_to_word and word_to_char.
For each (char, word): check consistency in both directions.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def wordPattern(pattern, s):
    words = s.split()
    if len(pattern) != len(words): return False
    c_to_w, w_to_c = {}, {}
    for c, w in zip(pattern, words):
        if c in c_to_w and c_to_w[c] != w: return False
        if w in w_to_c and w_to_c[w] != c: return False
        c_to_w[c] = w; w_to_c[w] = c
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pattern='abba', s='dog cat cat dog'
words=['dog','cat','cat','dog']
a,dog: c_to_w={a:dog}, w_to_c={dog:a}
b,cat: c_to_w={a:dog,b:cat}, w_to_c={dog:a,cat:b}
b,cat: b→cat ✓, cat→b ✓
a,dog: a→dog ✓, dog→a ✓
Return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Same as Isomorphic Strings but at word level. Bidirectional mapping catches: 'ab'/'aa' (a→a, b→a, but a→? maps to both a and b when reversed).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #56 (Word Pattern):
  Can you apply the HashMap — Bidirectional Word Mapping pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 57 of 250*
