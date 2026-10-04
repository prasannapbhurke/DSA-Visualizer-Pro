# 📘 Problem 55 of 250 — LeetCode 205: Isomorphic Strings

> **Pattern:** Hash Map | **Roadmap Position:** #55/250

---

LeetCode 205 — Isomorphic Strings
Problem #55 of 250 | Difficulty: Easy | Pattern: HashMap — Bidirectional Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two strings s and t are isomorphic if characters can be MAPPED one-to-one.
Every occurrence of a char in s maps to the same char in t, and vice versa.

Example:
  s="egg", t="add": e→a, g→d → isomorphic
  s="foo", t="bar": f→b, o→a, o→r → NOT isomorphic (o maps to both a and r!)
  s="paper", t="title": p→t, a→i, p→t, e→l, r→e → isomorphic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Need two mappings:
  s_to_t: char in s → char in t
  t_to_s: char in t → char in s (to avoid two-to-one)

  s="foo", t="bar"

  i=0: f→b: s_to_t={f:b}, t_to_s={b:f}
  i=1: o→a: s_to_t={f:b,o:a}, t_to_s={b:f,a:o}
  i=2: o→r: s_to_t[o]=a ≠ r → NOT isomorphic!

  (o already mapped to a, but now needs to map to r)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of it as a cipher/code:
  Each letter in s has exactly one corresponding letter in t.
  No two different letters in s can map to the same letter in t.

You need to track BOTH directions:
  Forward: s_char → t_char (one s letter maps to one t letter)
  Backward: t_char → s_char (no two s letters map to the same t letter)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isIsomorphic(s, t):
    s_to_t = {}
    t_to_s = {}

    for cs, ct in zip(s, t):
        if cs in s_to_t:
            if s_to_t[cs] != ct:   # s's char already mapped to different t char
                return False
        elif ct in t_to_s:
            if t_to_s[ct] != cs:   # t's char already mapped from different s char
                return False
        else:
            s_to_t[cs] = ct
            t_to_s[ct] = cs

    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s="egg", t="add"

e,a: s_to_t={e:a}, t_to_s={a:e}
g,d: s_to_t={e:a,g:d}, t_to_s={a:e,d:g}
g,d: s_to_t[g]=d == d ✓, t_to_s[d]=g == g ✓

Return True ✓

s="foo", t="bar"
f,b: {f:b}, {b:f}
o,a: {f:b,o:a}, {b:f,a:o}
o,r: s_to_t[o]=a ≠ r → return False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Bidirectional mapping is crucial for isomorphism.
One-directional check misses cases like:
  s="ab", t="aa" → a→a, b→a (two s chars to one t char — NOT valid!)

The two-map approach checks both:
  Does this s char already have a different t mapping?
  Does this t char already have a different s mapping?

Same bidirectional approach applies to LC290 (Word Pattern).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s_to_t, t_to_s = {}, {}
for cs, ct in zip(s, t):
    if s_to_t.get(cs) not in (None, ct): return False
    if t_to_s.get(ct) not in (None, cs): return False
    s_to_t[cs] = ct; t_to_s[ct] = cs
return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass through both strings
Space: O(1) — at most 26 characters mapped

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. One-directional mapping only (misses many-to-one case)
2. Forgetting to check both directions
3. Confusing "already mapped" vs "not yet mapped" in condition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC290 — Word Pattern (same idea but with WORDS instead of characters)
s="dog cat cat dog" and pattern="abba"
Same bidirectional mapping, just at word level.

---

*Next up: Problem 56 of 250*
