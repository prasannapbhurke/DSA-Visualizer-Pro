# Educational Video Production Script: LeetCode 1929 - Concatenation of Array
**Target Audience**: Complete Beginner (Python & LeetCode)  
**Tone**: Calm, Encouraging, Clear Indian English Teaching Voice  
**Target Duration**: 6 to 8 minutes  
**Format**: High-Production Animated Coding Video with Synchronized Visuals, Audio & Code  

---

## Scene 1: Introduction (0:00 - 0:25)
- **Visual**:
  - Dark slate background (`#0b0f17`) with emerald `#10b981` LeetCode badge: **LeetCode 1929 • Easy**.
  - Animated card displaying input: `nums = [1, 2, 1]`.
  - Arrow glides downward revealing output: `ans = [1, 2, 1, 1, 2, 1]`.
- **Narration**:
  > *"Hello everyone! Welcome. Today we are solving a very easy but fundamental LeetCode problem called Concatenation of Array. Our goal is to understand how to duplicate an array in Python. Look at this example: we have nums equal to 1, 2, 1. The output we need to produce is 1, 2, 1, followed immediately by 1, 2, 1. We simply have to place the entire nums array twice. Let us understand this step by step."*

---

## Scene 2: Understand The Problem (0:25 - 0:55)
- **Visual**:
  - Original array box:
    ```
    Index:  0    1    2
    nums:  [1]  [2]  [1]
    ```
  - Empty answer array with 6 slots:
    ```
    Index:  0    1    2    3    4    5
    ans:   [?]  [?]  [?]  [?]  [?]  [?]
    ```
  - Glowing formula badge: `Length: n = 3  ➔  2n = 6`.
- **Narration**:
  > *"Let us break down what the question is asking. We are given an array called nums with length n. In our example, nums has three numbers: 1, 2, and 1. So here, n is 3.*  
  > *We need to build a new array called ans with length 2 times n. If n is 3, 2 times 3 is 6 positions! Right now, all six positions are waiting to be filled."*

---

## Scene 3: Explain The Formula (0:55 - 1:40)
- **Visual**:
  - Top card: `ans[i] = nums[i]` and `ans[i + n] = nums[i]` for each `0 <= i < n`.
  - Animated step for `i = 0`:
    - Highlight `nums[0]` (`1`).
    - Curved neon arrow to `ans[0]` (`1`).
    - Second curved neon arrow to `ans[0 + 3 = 3]` (`1`).
  - Animated step for `i = 1`:
    - Highlight `nums[1]` (`2`).
    - Neon arrow to `ans[1]` (`2`) and `ans[1 + 3 = 4]` (`2`).
  - Animated step for `i = 2`:
    - Highlight `nums[2]` (`1`).
    - Neon arrow to `ans[2]` (`1`) and `ans[2 + 3 = 5]` (`1`).
- **Narration**:
  > *"LeetCode gives us two mathematical rules: ans of i equals nums of i, and ans of i plus n equals nums of i.*  
  > *What does this mean? Let's check index by index.*  
  > *When i is 0: nums of 0 is 1. We copy 1 into ans of 0, and into ans of 0 plus 3, which is index 3.*  
  > *When i is 1: nums of 1 is 2. We copy 2 into ans of 1, and into ans of 1 plus 3, which is index 4.*  
  > *When i is 2: nums of 2 is 1. We copy 1 into ans of 2, and into ans of 2 plus 3, which is index 5.*  
  > *See how clean that is? Every element is placed into the first half, and simultaneously into the second half!"*

---

## Scene 4: Show The Simple Idea (1:40 - 2:10)
- **Visual**:
  - Two colorful blocks merging:
    ```
       [ 1 , 2 , 1 ]   (nums)
    +  [ 1 , 2 , 1 ]   (nums copy)
    ==================
       [ 1 , 2 , 1 , 1 , 2 , 1 ]  (ans)
    ```
  - Subtitle banner: *"Think of it as copying the same array and putting the copy immediately after the original."*
- **Narration**:
  > *"Now step back and look at the big picture. You do not even need complex index math to see what is happening.*  
  > *Take nums: 1, 2, 1. Add another copy of nums right after it: 1, 2, 1.*  
  > *Together, they form 1, 2, 1, 1, 2, 1.*  
  > *Think of it simply as photocopying the original list and attaching the photocopy right at the end!"*

---

## Scene 5: Python Solution (2:10 - 2:55)
- **Visual**:
  - Split screen: Monokai-style code editor on the left, interactive evaluation card on the right.
  - Code:
    ```python
    class Solution:
        def getConcatenation(self, nums: List[int]) -> List[int]:
            return nums + nums
    ```
  - Line highlights with explanation callouts.
- **Narration**:
  > *"In Python, this problem can be solved in literally one single line of code! Let us examine every line.*  
  > *Line 1: class Solution. This is just the standard wrapper class LeetCode uses for test execution.*  
  > *Line 2: def getConcatenation self comma nums. This is the function that receives our input list.*  
  > *Line 3: return nums plus nums. In Python, the plus operator between two lists joins them together.*  
  > *So nums plus nums takes [1, 2, 1] plus [1, 2, 1], producing [1, 2, 1, 1, 2, 1]. That is our entire solution!"*

---

## Scene 6: Important Python Concept (2:55 - 3:40)
- **Visual**:
  - Warning banner: **List Addition != Math Addition**.
  - Side-by-side comparison:
    - `[1, 2, 3] + [4, 5, 6]` ➔ `[1, 2, 3, 4, 5, 6]` (Correct concatenation)
    - Not `[1+4, 2+5, 3+6] = [5, 7, 9]` (Math vector addition)
  - Secondary comparison:
    - `nums + nums` ➔ `[1, 2, 1, 1, 2, 1]`
    - `nums * 2` ➔ `[1, 2, 1, 1, 2, 1]`
- **Narration**:
  > *"Here is an essential Python concept every beginner must understand.*  
  > *When you use the plus symbol between two Python lists, like [1, 2, 3] plus [4, 5, 6], Python does NOT perform mathematical addition on the numbers. It concatenates them into [1, 2, 3, 4, 5, 6].*  
  > *You might also notice that writing nums times 2 produces the exact same result in Python! Both give [1, 2, 1, 1, 2, 1].*  
  > *However, we recommend nums plus nums for your interviews, because it explicitly communicates: I am concatenating nums with nums."*

---

## Scene 7: Dry Run (3:40 - 4:25)
- **Visual**:
  - New test example: `nums = [1, 3, 2, 1]` (length 4).
  - Array card with indices `0, 1, 2, 3`.
  - Visual construction of `ans` (length 8):
    - First copy slides into indices 0, 1, 2, 3 in emerald green.
    - Second copy slides into indices 4, 5, 6, 7 in sky blue.
    - Result: `[1, 3, 2, 1, 1, 3, 2, 1]`.
- **Narration**:
  > *"Let us do a complete dry run with a fresh example to cement your confidence.*  
  > *Suppose nums is [1, 3, 2, 1]. Here, n is 4.*  
  > *When we execute ans equals nums plus nums:*  
  > *First, Python copies the original elements: 1, 3, 2, 1 into the first 4 slots.*  
  > *Then, Python appends the second copy: 1, 3, 2, 1 into slots 4, 5, 6, and 7.*  
  > *Our final array is [1, 3, 2, 1, 1, 3, 2, 1], with exactly 8 elements. Perfectly matching the requirement!"*

---

## Scene 8: Why Does The Answer Have Length 2n? (4:25 - 4:55)
- **Visual**:
  - Math derivation diagram:
    - `Original Length = n`
    - `Duplicate Length = n`
    - `Total Length = n + n = 2n`
  - Examples card:
    - `n = 3` ➔ `2n = 6`
    - `n = 4` ➔ `2n = 8`
    - `n = 1000` ➔ `2n = 2000`
- **Narration**:
  > *"Why does the problem statement insist that the answer has length 2n?*  
  > *Because the original array has length n. Since we place the entire array twice without skipping any elements, n plus n is equal to 2n!*  
  > *If n is 4, the output has 8 elements. If n was 1000, the output would have 2000 elements. Simple and logical!"*

---

## Scene 9: Alternative Loop Solution (4:55 - 5:45)
- **Visual**:
  - Code block 1 (Simple loop with `append`):
    ```python
    class Solution:
        def getConcatenation(self, nums: List[int]) -> List[int]:
            ans = []
            for x in nums:
                ans.append(x)
            for x in nums:
                ans.append(x)
            return ans
    ```
  - Code block 2 (Index formula matching problem statement):
    ```python
    n = len(nums)
    ans = [0] * (2 * n)
    for i in range(n):
        ans[i] = nums[i]
        ans[i + n] = nums[i]
    return ans
    ```
- **Narration**:
  > *"Now, what if an interviewer asks: Can you solve this without using the Python plus operator?*  
  > *No problem! You can use a loop.*  
  > *Here, we create an empty list ans. We loop through nums once and append each number. Then we loop through nums a second time and append each number again!*  
  > *Or even better, we can pre-allocate an array of size 2n, and use a single loop with our formula: ans of i equals nums of i, and ans of i plus n equals nums of i. This directly reflects the mathematical definition given in the problem."*

---

## Scene 10: Compare The Approaches (5:45 - 6:15)
- **Visual**:
  - Comparison matrix:
    | Approach | Implementation | Time | Space | Notes |
    | :--- | :--- | :--- | :--- | :--- |
    | **Concatenation** | `return nums + nums` | **O(n)** | **O(n)** | Clean, Pythonic, 1 line |
    | **Double Loop** | `for x in nums: ans.append(x)` | **O(n)** | **O(n)** | Language-agnostic |
    | **Index Formula** | `ans[i] = ans[i+n] = nums[i]` | **O(n)** | **O(n)** | Exactly matches formula |
- **Narration**:
  > *"Let us compare these approaches.*  
  > *Both the list concatenation and the loop approach take O(n) time and O(n) space.*  
  > *Why are they both linear time? Because no matter what, every single one of the 2n elements must be copied into memory. The Python plus operator is implemented in C under the hood, making it slightly faster and much cleaner to write."*

---

## Scene 11: Time Complexity (6:15 - 6:40)
- **Visual**:
  - Animated complexity card:
    - Input size = `n`
    - Total elements written = `2n`
    - `Operations = 2 * n`
    - Constant factor dropped: `O(2n) ➔ O(n)`
  - Badge: **Time Complexity: O(n)**
- **Narration**:
  > *"Let us talk about Time Complexity.*  
  > *If nums has n elements, our output has 2n elements. Creating the output requires processing and copying all 2n items.*  
  > *In Big-O notation, we always drop constant multipliers. So 2n becomes O(n).*  
  > *Our Time Complexity is strictly O(n), linear time."*

---

## Scene 12: Space Complexity (6:40 - 7:05)
- **Visual**:
  - Space complexity card:
    - New list created in memory: `ans` with length `2n`.
    - Memory used = `2n` integers.
    - `O(2n) ➔ O(n)`
  - Badge: **Space Complexity: O(n)**
- **Narration**:
  > *"Now for Space Complexity.*  
  > *The problem explicitly requires us to return a new array of size 2n. Therefore, the memory allocated for the answer is proportional to n, which is O(n).*  
  > *Beyond the required output array, we do not allocate any extra data structures. So extra auxiliary space is O(1)."*

---

## Scene 13: Common Beginner Mistakes (7:05 - 7:45)
- **Visual**:
  - 4 Warning Alert Cards with ❌ / ✓:
    1. ❌ Returning `nums` ➔ *Wrong: only length n, needs 2n.*
    2. ❌ Writing `nums * 2` without understanding reference copies in multidimensional lists.
    3. ❌ Mathematical element addition `[1+1, 2+2, 1+1]` ➔ *Wrong: problem is concatenation, not arithmetic sum.*
    4. ❌ Off-by-one errors like trying to access `nums[n]` ➔ *Remember Python uses 0-based indexing (0 to n-1).*
- **Narration**:
  > *"Let us review four common beginner mistakes so you can avoid them in tests.*  
  > *Mistake 1: Returning nums directly. That only has n elements, but the problem demands 2n.*  
  > *Mistake 2: Writing nums times 2 blindly without understanding that for nested lists, multiplication creates shallow references.*  
  > *Mistake 3: Trying to do element-wise addition, like 1 plus 1, 2 plus 2. Remember, concatenation means chaining elements together, not adding them mathematically.*  
  > *Mistake 4: Forgetting that Python is 0-indexed. An array of length n has indices from 0 up to n minus 1."*

---

## Scene 14: Mini Quiz (7:45 - 8:20)
- **Visual**:
  - Quiz Card 1:
    - *"If nums = [5, 7], what will the output be?"*
    - Glowing 5-second countdown timer bar: `5... 4... 3... 2... 1...`
    - Pop-in Answer: `[5, 7, 5, 7]` with green confetti checkmark!
  - Quiz Card 2:
    - *"If n = 5, what is the length of ans?"*
    - Countdown timer: `3... 2... 1...`
    - Pop-in Answer: `10` (`2 * 5 = 10`)!
- **Narration**:
  > *"Time for a quick mini-quiz to test yourself!*  
  > *Question 1: If nums is [5, 7], what will the answer be? Take a few seconds...*  
  > *(Pause for countdown)*  
  > *Yes! The answer is [5, 7, 5, 7]!*  
  > *Question 2: If the input array has length n equals 5, what is the length of ans?*  
  > *(Pause for countdown)*  
  > *Exactly 10! Because 2 times 5 is 10. Great job!"*

---

## Scene 15: Final Summary (8:20 - 8:50)
- **Visual**:
  - Master summary card with 5 checkmarks:
    1. ✓ Answer is `nums` concatenated with itself.
    2. ✓ In Python, list concatenation is done with `+`.
    3. ✓ `nums + nums` creates the required `2n`-length array.
    4. ✓ Time Complexity = **O(n)**.
    5. ✓ Space Complexity = **O(n)**.
  - Clean Final Code Card:
    ```python
    class Solution:
        def getConcatenation(self, nums: List[int]) -> List[int]:
            return nums + nums
    ```
- **Narration**:
  > *"To summarize our lesson today:*  
  > *First: the problem simply asks us to concatenate nums with itself.*  
  > *Second: Python gives us list concatenation out-of-the-box using the plus operator.*  
  > *Third: return nums plus nums solves the problem in one elegant line.*  
  > *Fourth: both Time and Space Complexity are optimal at O(n).*  
  > *You have mastered LeetCode 1929! Practice writing this solution on LeetCode, and I will see you in the next tutorial. Happy coding!"*
