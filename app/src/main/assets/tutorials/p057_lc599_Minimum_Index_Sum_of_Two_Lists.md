# 📘 Problem 57 of 250 — LeetCode 599: Minimum Index Sum of Two Lists

> **Pattern:** Hash Map | **Roadmap Position:** #57/250

---

LeetCode 599 — Minimum Index Sum of Two Lists
Problem #57 of 250 | Difficulty: Easy | Pattern: HashMap — Index Sum Minimization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find common strings with minimum sum of indices.
Store list1 with indices in HashMap. Scan list2, check for common items, track minimum index sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap — Index Sum Minimization

map1 = {word: index} for list1.
min_sum = infinity, result = []
For i, word in list2:
  If word in map1: idx_sum = map1[word]+i
    If idx_sum < min_sum: update result
    If idx_sum == min_sum: append to result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def findRestaurant(list1, list2):
    map1 = {word: i for i, word in enumerate(list1)}
    min_sum = float('inf')
    result = []
    for i, word in enumerate(list2):
        if word in map1:
            s = map1[word] + i
            if s < min_sum: min_sum = s; result = [word]
            elif s == min_sum: result.append(word)
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

list1=['Shogun','Tapioca','Burger']
list2=['Piatti','Shogun','Tapioca']
map1={Shogun:0, Tapioca:1, Burger:2}
i=1,Shogun: sum=0+1=1, min=1, result=[Shogun]
i=2,Tapioca: sum=1+2=3>1, skip
Return ['Shogun'] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

HashMap lookup converts O(n²) brute force to O(m+n). Pattern: index one list, scan the other while checking HashMap.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #57 (Minimum Index Sum of Two Lists):
  Can you apply the HashMap — Index Sum Minimization pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 58 of 250*
