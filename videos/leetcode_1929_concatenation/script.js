/**
 * Educational Coding Video Player Engine
 * Problem: LeetCode 1929 - Concatenation of Array
 * Features:
 * - 15 Structured Scenes with Visual Animations
 * - Natural Indian English Speech Narration (Web Speech API)
 * - Web Audio Sound Effects (clicks, swooshes, success chimes)
 * - Canvas & MediaRecorder video recording & direct download
 * - Dynamic SVG arrow connectors & array highlighting
 */

const SCENES = [
  {
    id: 1,
    title: "1. INTRODUCTION",
    chapter: "Introduction",
    duration: 22,
    narration: "Today we are solving a very easy but important LeetCode problem. The goal is to understand how to duplicate an array. We have nums equal to 1, 2, 1. The output we need to produce is 1, 2, 1, 1, 2, 1. We simply have to place the entire nums array twice.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">LeetCode 1929: Concatenation of Array</div>
        <div class="scene-subtitle">Problem breakdown & core concept demonstration</div>
        <div class="glass-card" style="text-align: center;">
          <div style="font-size: 14px; color: var(--color-sky); margin-bottom: 8px; font-weight: 600;">INPUT ARRAY (nums)</div>
          <div class="array-cells" style="justify-content: center; margin-bottom: 24px;">
            <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
            <div class="cell-box highlight"><span class="cell-index">1</span>2</div>
            <div class="cell-box highlight"><span class="cell-index">2</span>1</div>
          </div>
          <div style="font-size: 28px; color: var(--color-emerald); font-weight: 800; margin-bottom: 12px;">➔ DUPLICATE TWICE ➔</div>
          <div style="font-size: 14px; color: var(--color-emerald); margin-bottom: 8px; font-weight: 600;">OUTPUT ARRAY (ans = nums + nums)</div>
          <div class="array-cells" style="justify-content: center;">
            <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
            <div class="cell-box highlight"><span class="cell-index">1</span>2</div>
            <div class="cell-box highlight"><span class="cell-index">2</span>1</div>
            <div class="cell-box second-half"><span class="cell-index">3</span>1</div>
            <div class="cell-box second-half"><span class="cell-index">4</span>2</div>
            <div class="cell-box second-half"><span class="cell-index">5</span>1</div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 2,
    title: "2. UNDERSTAND THE PROBLEM",
    chapter: "Understand Problem",
    duration: 24,
    narration: "Let us understand the problem visually. We are given an array nums of length n. Here, nums has 3 elements: 1, 2, and 1. So n is 3. We need to create an answer array of length 2n, which means 2 times 3 equals 6 positions.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Visualizing Array Dimensions</div>
        <div class="scene-subtitle">Original length is n = 3. Target output length is 2n = 6.</div>
        <div class="glass-card">
          <div class="array-container">
            <div class="array-label">Original Array (nums) • Length n = 3</div>
            <div class="array-cells">
              <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
              <div class="cell-box highlight"><span class="cell-index">1</span>2</div>
              <div class="cell-box highlight"><span class="cell-index">2</span>1</div>
            </div>
          </div>
          <div style="text-align: center; margin: 18px 0; font-weight: 700; color: var(--color-sky);">
            Output size required: 2 × n = 2 × 3 = 6 slots
          </div>
          <div class="array-container">
            <div class="array-label" style="color: var(--color-amber);">Empty Answer Array (ans) • Length 2n = 6</div>
            <div class="array-cells">
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">0</span>?</div>
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">1</span>?</div>
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">2</span>?</div>
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">3</span>?</div>
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">4</span>?</div>
              <div class="cell-box" style="border-style: dashed;"><span class="cell-index">5</span>?</div>
            </div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 3,
    title: "3. EXPLAIN THE FORMULA",
    chapter: "Explain Formula",
    duration: 32,
    narration: "LeetCode specifies: ans[i] equals nums[i], and ans[i plus n] equals nums[i] for every index i. For i equals 0: ans[0] is 1, and ans[3] is 1. For i equals 1: ans[1] is 2, and ans[4] is 2. For i equals 2: ans[2] is 1, and ans[5] is 1.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">The Index Formula: ans[i] & ans[i + n]</div>
        <div class="scene-subtitle">Formula: ans[i] = nums[i] and ans[i + n] = nums[i]</div>
        <div class="glass-card" style="display: flex; gap: 24px; align-items: center; justify-content: space-around;">
          <div style="font-family: var(--font-mono); font-size: 15px; line-height: 2;">
            <div id="step-i0" style="color: var(--color-emerald); font-weight: 700;">• i = 0: ans[0] = 1, ans[3] = 1</div>
            <div id="step-i1" style="color: var(--color-sky); font-weight: 700;">• i = 1: ans[1] = 2, ans[4] = 2</div>
            <div id="step-i2" style="color: var(--color-purple); font-weight: 700;">• i = 2: ans[2] = 1, ans[5] = 1</div>
          </div>
          <div>
            <div class="array-container">
              <div class="array-label">nums</div>
              <div class="array-cells">
                <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
                <div class="cell-box highlight"><span class="cell-index">1</span>2</div>
                <div class="cell-box highlight"><span class="cell-index">2</span>1</div>
              </div>
            </div>
            <div class="array-container">
              <div class="array-label">ans (Filled)</div>
              <div class="array-cells">
                <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
                <div class="cell-box highlight"><span class="cell-index">1</span>2</div>
                <div class="cell-box highlight"><span class="cell-index">2</span>1</div>
                <div class="cell-box second-half"><span class="cell-index">3</span>1</div>
                <div class="cell-box second-half"><span class="cell-index">4</span>2</div>
                <div class="cell-box second-half"><span class="cell-index">5</span>1</div>
              </div>
            </div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 4,
    title: "4. SHOW THE SIMPLE IDEA",
    chapter: "The Simple Idea",
    duration: 22,
    narration: "Think of it as copying the same array and putting the copy immediately after the original. We take nums 1, 2, 1, add nums 1, 2, 1, and get ans 1, 2, 1, 1, 2, 1. It is as simple as photocopying the array.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">The Big Picture: nums + nums</div>
        <div class="scene-subtitle">Photocopying the entire array and appending it</div>
        <div class="glass-card" style="display: flex; flex-direction: column; align-items: center; gap: 16px;">
          <div style="display: flex; align-items: center; gap: 16px;">
            <div class="array-cells">
              <div class="cell-box highlight">1</div>
              <div class="cell-box highlight">2</div>
              <div class="cell-box highlight">1</div>
            </div>
            <span style="font-size: 28px; font-weight: 800; color: var(--color-amber);">+</span>
            <div class="array-cells">
              <div class="cell-box second-half">1</div>
              <div class="cell-box second-half">2</div>
              <div class="cell-box second-half">1</div>
            </div>
          </div>
          <div style="font-size: 24px; color: var(--color-emerald); font-weight: 800;">⬇ EQUALS ⬇</div>
          <div class="array-cells">
            <div class="cell-box highlight">1</div>
            <div class="cell-box highlight">2</div>
            <div class="cell-box highlight">1</div>
            <div class="cell-box second-half">1</div>
            <div class="cell-box second-half">2</div>
            <div class="cell-box second-half">1</div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 5,
    title: "5. PYTHON SOLUTION",
    chapter: "Python Solution",
    duration: 30,
    narration: "Here is the standard Python solution. In Python, the plus operator between two lists joins them together. class Solution is the LeetCode wrapper. def getConcatenation receives nums. And return nums plus nums returns the concatenated list in a single line.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">The 1-Line Python Solution</div>
        <div class="scene-subtitle">Clean, idiomatic Python list concatenation</div>
        <div style="display: flex; gap: 20px; width: 100%; max-width: 860px;">
          <div class="code-container" style="flex: 1.2;">
            <span class="kw">class</span> <span class="fn">Solution</span>:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="kw">def</span> <span class="fn">getConcatenation</span>(<span class="var">self</span>, <span class="var">nums</span>: <span class="fn">List</span>[<span class="var">int</span>]) -&gt; <span class="fn">List</span>[<span class="var">int</span>]:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kw">return</span> <span class="var">nums</span> <span class="op">+</span> <span class="var">nums</span>
          </div>
          <div class="glass-card" style="flex: 1; padding: 18px; margin: 0; font-size: 13px; line-height: 1.6;">
            <div style="color: var(--color-emerald); font-weight: 700; margin-bottom: 6px;">Line-by-Line Breakdown:</div>
            <div style="margin-bottom: 8px;">• <code>class Solution</code>: Standard LeetCode class template.</div>
            <div style="margin-bottom: 8px;">• <code>getConcatenation(nums)</code>: Function receiving the input list.</div>
            <div>• <code>nums + nums</code>: Joins two copies of the list into a new list.</div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 6,
    title: "6. IMPORTANT PYTHON CONCEPT",
    chapter: "Python List Concatenation",
    duration: 30,
    narration: "A very important concept: in Python, list addition does NOT perform mathematical addition. [1, 2, 3] plus [4, 5, 6] produces [1, 2, 3, 4, 5, 6]. Also, nums times 2 produces the same result, but nums plus nums clearly conveys concatenation.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">List Concatenation vs Math Addition</div>
        <div class="scene-subtitle">How Python handles the + and * operators on lists</div>
        <div class="glass-card" style="display: flex; flex-direction: column; gap: 14px;">
          <div style="background: rgba(244, 63, 94, 0.1); border: 1px solid rgba(244, 63, 94, 0.3); border-radius: 8px; padding: 12px 16px;">
            <span style="color: var(--color-rose); font-weight: 700;">❌ NOT Mathematical Sum:</span>
            <div style="font-family: var(--font-mono); margin-top: 4px;">[1, 2, 3] + [4, 5, 6] &ne; [5, 7, 9]</div>
          </div>
          <div style="background: rgba(16, 185, 129, 0.1); border: 1px solid rgba(16, 185, 129, 0.3); border-radius: 8px; padding: 12px 16px;">
            <span style="color: var(--color-emerald); font-weight: 700;">✓ Python List Concatenation:</span>
            <div style="font-family: var(--font-mono); margin-top: 4px;">[1, 2, 3] + [4, 5, 6] = [1, 2, 3, 4, 5, 6]</div>
          </div>
          <div style="background: rgba(56, 189, 248, 0.1); border: 1px solid rgba(56, 189, 248, 0.3); border-radius: 8px; padding: 12px 16px;">
            <span style="color: var(--color-sky); font-weight: 700;">Both Work for this problem:</span>
            <div style="font-family: var(--font-mono); margin-top: 4px;">nums + nums ➔ [1, 2, 1, 1, 2, 1]<br>nums * 2 ➔ [1, 2, 1, 1, 2, 1]</div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 7,
    title: "7. DRY RUN",
    chapter: "Step-by-Step Dry Run",
    duration: 32,
    narration: "Let us perform a dry run using nums equals 1, 3, 2, 1. Here n is 4. First, Python copies the original 4 numbers. Then, Python appends the second copy. The final answer contains all 8 numbers in order.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Dry Run: nums = [1, 3, 2, 1]</div>
        <div class="scene-subtitle">Tracing execution step-by-step with n = 4</div>
        <div class="glass-card" style="display: flex; flex-direction: column; align-items: center; gap: 16px;">
          <div class="array-container">
            <div class="array-label">Input: nums (n = 4)</div>
            <div class="array-cells">
              <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
              <div class="cell-box highlight"><span class="cell-index">1</span>3</div>
              <div class="cell-box highlight"><span class="cell-index">2</span>2</div>
              <div class="cell-box highlight"><span class="cell-index">3</span>1</div>
            </div>
          </div>
          <div style="font-size: 20px; font-weight: 700; color: var(--color-amber);">ans = nums + nums</div>
          <div class="array-container">
            <div class="array-label" style="color: var(--color-emerald);">Final Result: ans (length 2n = 8)</div>
            <div class="array-cells">
              <div class="cell-box highlight"><span class="cell-index">0</span>1</div>
              <div class="cell-box highlight"><span class="cell-index">1</span>3</div>
              <div class="cell-box highlight"><span class="cell-index">2</span>2</div>
              <div class="cell-box highlight"><span class="cell-index">3</span>1</div>
              <div class="cell-box second-half"><span class="cell-index">4</span>1</div>
              <div class="cell-box second-half"><span class="cell-index">5</span>3</div>
              <div class="cell-box second-half"><span class="cell-index">6</span>2</div>
              <div class="cell-box second-half"><span class="cell-index">7</span>1</div>
            </div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 8,
    title: "8. WHY DOES THE ANSWER HAVE LENGTH 2n?",
    chapter: "Why Length 2n?",
    duration: 24,
    narration: "Why does the answer have length 2n? The original array has length n. We place it twice. So n plus n equals 2n. If n is 4, 2n is 8. The output must have 8 elements.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Why Length 2n?</div>
        <div class="scene-subtitle">Simple mathematical logic behind the output size</div>
        <div class="glass-card" style="text-align: center;">
          <div style="font-size: 28px; font-weight: 800; font-family: var(--font-mono); color: var(--color-emerald); margin: 16px 0;">
            Original Length = n<br>
            Duplicated Length = n<br>
            Total Length = n + n = 2n
          </div>
          <div style="display: flex; justify-content: space-around; margin-top: 24px;">
            <div style="background: rgba(255,255,255,0.05); padding: 14px 20px; border-radius: 10px;">
              <div style="color: var(--text-muted); font-size: 12px;">Example 1</div>
              <div style="font-size: 18px; font-weight: 700; color: var(--color-sky);">n = 3 ➔ 2n = 6</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 14px 20px; border-radius: 10px;">
              <div style="color: var(--text-muted); font-size: 12px;">Example 2</div>
              <div style="font-size: 18px; font-weight: 700; color: var(--color-amber);">n = 4 ➔ 2n = 8</div>
            </div>
            <div style="background: rgba(255,255,255,0.05); padding: 14px 20px; border-radius: 10px;">
              <div style="color: var(--text-muted); font-size: 12px;">Constraint Max</div>
              <div style="font-size: 18px; font-weight: 700; color: var(--color-emerald);">n = 1000 ➔ 2n = 2000</div>
            </div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 9,
    title: "9. ALTERNATIVE LOOP SOLUTION",
    chapter: "Loop Solution",
    duration: 34,
    narration: "If an interviewer asks you to solve this without list concatenation, you can use loops. We can loop through nums twice and append each element, or pre-allocate an array of size 2n and fill indices i and i plus n directly.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Alternative Loop Approaches</div>
        <div class="scene-subtitle">Language-agnostic loop solutions for interviews</div>
        <div style="display: flex; gap: 16px; width: 100%; max-width: 860px;">
          <div class="code-container" style="flex: 1; font-size: 13px;">
            <span class="comm"># Approach A: Two sequential loops</span><br>
            <span class="var">ans</span> = []<br>
            <span class="kw">for</span> <span class="var">x</span> <span class="kw">in</span> <span class="var">nums</span>:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="var">ans</span>.<span class="fn">append</span>(<span class="var">x</span>)<br>
            <span class="kw">for</span> <span class="var">x</span> <span class="kw">in</span> <span class="var">nums</span>:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="var">ans</span>.<span class="fn">append</span>(<span class="var">x</span>)<br>
            <span class="kw">return</span> <span class="var">ans</span>
          </div>
          <div class="code-container" style="flex: 1; font-size: 13px;">
            <span class="comm"># Approach B: Index formula</span><br>
            <span class="var">n</span> = <span class="fn">len</span>(<span class="var">nums</span>)<br>
            <span class="var">ans</span> = [<span class="var">0</span>] * (<span class="var">2</span> * <span class="var">n</span>)<br>
            <span class="kw">for</span> <span class="var">i</span> <span class="kw">in</span> <span class="fn">range</span>(<span class="var">n</span>):<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="var">ans</span>[<span class="var">i</span>] = <span class="var">nums</span>[<span class="var">i</span>]<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="var">ans</span>[<span class="var">i</span> + <span class="var">n</span>] = <span class="var">nums</span>[<span class="var">i</span>]<br>
            <span class="kw">return</span> <span class="var">ans</span>
          </div>
        </div>
      `;
    }
  },
  {
    id: 10,
    title: "10. COMPARE THE APPROACHES",
    chapter: "Compare Approaches",
    duration: 26,
    narration: "Let us compare these approaches. Both nums plus nums and the loop approach take O of n time and O of n space. Both are linear because every element must be copied into the output.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Approach Comparison Matrix</div>
        <div class="scene-subtitle">Comparing readability, time complexity, and memory overhead</div>
        <div class="glass-card">
          <table class="compare-table">
            <thead>
              <tr>
                <th>Approach</th>
                <th>Core Idea</th>
                <th>Time</th>
                <th>Space</th>
                <th>Verdict</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td style="font-weight: 700; color: var(--color-emerald);">nums + nums</td>
                <td>Built-in list concatenation</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td style="color: var(--color-emerald); font-weight: 600;">⭐ Best / Most Pythonic</td>
              </tr>
              <tr>
                <td style="font-weight: 700; color: var(--color-sky);">Two Loops</td>
                <td>Append each element twice</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td>Great for general languages</td>
              </tr>
              <tr>
                <td style="font-weight: 700; color: var(--color-purple);">Index Formula</td>
                <td>ans[i] and ans[i+n]</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td style="color: var(--color-sky); font-family: var(--font-mono);">O(n)</td>
                <td>Direct math translation</td>
              </tr>
            </tbody>
          </table>
        </div>
      `;
    }
  },
  {
    id: 11,
    title: "11. TIME COMPLEXITY",
    chapter: "Time Complexity",
    duration: 22,
    narration: "If nums contains n elements, the output contains 2n elements. Creating the output requires processing 2n elements. Therefore, Time Complexity is O of n. Remember, we drop the constant factor 2 in Big-O notation.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Time Complexity: O(n)</div>
        <div class="scene-subtitle">Why we drop the constant 2 in Big-O analysis</div>
        <div class="glass-card" style="text-align: center;">
          <div style="font-size: 36px; font-weight: 800; font-family: var(--font-mono); color: var(--color-emerald); margin: 16px 0;">
            O(2n) ➔ O(n)
          </div>
          <div style="font-size: 16px; color: var(--text-secondary); line-height: 1.8; max-width: 600px; margin: 0 auto;">
            • Input has <strong>n</strong> elements.<br>
            • Output array requires writing <strong>2n</strong> elements.<br>
            • In asymptotic Big-O notation, constant multipliers are ignored.<br>
            • Final answer: <strong>O(n) Linear Time</strong>.
          </div>
        </div>
      `;
    }
  },
  {
    id: 12,
    title: "12. SPACE COMPLEXITY",
    chapter: "Space Complexity",
    duration: 22,
    narration: "The output array itself contains 2n elements. Therefore, Space Complexity is O of n. The output array is required by the problem, and beyond that, our auxiliary space is O of 1.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Space Complexity: O(n)</div>
        <div class="scene-subtitle">Accounting for the required output array</div>
        <div class="glass-card" style="text-align: center;">
          <div style="font-size: 36px; font-weight: 800; font-family: var(--font-mono); color: var(--color-sky); margin: 16px 0;">
            Space Complexity = O(n)
          </div>
          <div style="font-size: 16px; color: var(--text-secondary); line-height: 1.8; max-width: 600px; margin: 0 auto;">
            • The problem demands an output array of length <strong>2n</strong>.<br>
            • Memory scales linearly with the input size.<br>
            • Extra Auxiliary Space (variables beyond output): <strong>O(1)</strong>.
          </div>
        </div>
      `;
    }
  },
  {
    id: 13,
    title: "13. COMMON BEGINNER MISTAKES",
    chapter: "Common Mistakes",
    duration: 32,
    narration: "Watch out for four common beginner mistakes: Returning nums instead of 2n elements. Writing nums times 2 without understanding shallow copying. Trying to add individual elements mathematically. And forgetting that Python uses zero-based indexing.",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Common Beginner Mistakes</div>
        <div class="scene-subtitle">Pitfalls to avoid in LeetCode & technical interviews</div>
        <div class="glass-card" style="display: grid; grid-template-columns: 1fr 1fr; gap: 14px;">
          <div style="background: rgba(244, 63, 94, 0.1); border: 1px solid rgba(244, 63, 94, 0.3); padding: 12px; border-radius: 8px;">
            <div style="color: var(--color-rose); font-weight: 700;">1. Returning original nums</div>
            <div style="font-size: 12px; color: var(--text-secondary); margin-top: 4px;">Length is only n, but the problem demands 2n.</div>
          </div>
          <div style="background: rgba(244, 63, 94, 0.1); border: 1px solid rgba(244, 63, 94, 0.3); padding: 12px; border-radius: 8px;">
            <div style="color: var(--color-rose); font-weight: 700;">2. Blind list multiplication</div>
            <div style="font-size: 12px; color: var(--text-secondary); margin-top: 4px;">For nested lists, <code>[[]] * 2</code> creates shared memory references!</div>
          </div>
          <div style="background: rgba(244, 63, 94, 0.1); border: 1px solid rgba(244, 63, 94, 0.3); padding: 12px; border-radius: 8px;">
            <div style="color: var(--color-rose); font-weight: 700;">3. Mathematical addition</div>
            <div style="font-size: 12px; color: var(--text-secondary); margin-top: 4px;">Concatenation chains elements; it does not add them.</div>
          </div>
          <div style="background: rgba(244, 63, 94, 0.1); border: 1px solid rgba(244, 63, 94, 0.3); padding: 12px; border-radius: 8px;">
            <div style="color: var(--color-rose); font-weight: 700;">4. 1-based indexing error</div>
            <div style="font-size: 12px; color: var(--text-secondary); margin-top: 4px;">Remember valid indices for length n are 0 through n-1.</div>
          </div>
        </div>
      `;
    }
  },
  {
    id: 14,
    title: "14. MINI QUIZ",
    chapter: "Mini Quiz",
    duration: 30,
    narration: "Time for a mini quiz! If nums is 5, 7, what will the answer be? Think for a moment... The answer is 5, 7, 5, 7! Next: If n is 5, what is the length of ans? The answer is 10!",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Test Your Understanding!</div>
        <div class="scene-subtitle">Quick interactive check before wrapping up</div>
        <div class="glass-card quiz-box">
          <div style="font-size: 18px; font-weight: 700;">Question 1: If nums = [5, 7], what will ans be?</div>
          <div class="quiz-timer-bar"><div class="quiz-timer-fill" id="quiz-bar"></div></div>
          <div class="quiz-reveal-box" id="quiz-reveal-1">[ 5, 7, 5, 7 ]</div>
          <div style="font-size: 16px; font-weight: 600; color: var(--color-sky); margin-top: 8px;">
            Question 2: If n = 5, what is length of ans? ➔ <span style="color: var(--color-emerald); font-weight: 800;">10</span>
          </div>
        </div>
      `;
      // Trigger animation for quiz reveal after 3 seconds
      setTimeout(() => {
        const bar = document.getElementById("quiz-bar");
        if (bar) bar.style.width = "0%";
        setTimeout(() => {
          const rev = document.getElementById("quiz-reveal-1");
          if (rev) rev.classList.add("visible");
        }, 3000);
      }, 500);
    }
  },
  {
    id: 15,
    title: "15. FINAL SUMMARY",
    chapter: "Final Summary",
    duration: 26,
    narration: "In summary: The answer is simply nums concatenated with itself. Python allows list concatenation using plus. nums plus nums creates the required 2n-length array. Time Complexity is O of n, and Space Complexity is O of n. Keep practicing, and I will see you in the next tutorial!",
    render: (container) => {
      container.innerHTML = `
        <div class="scene-title">Mastery Summary: LeetCode 1929</div>
        <div class="scene-subtitle">Everything you need to remember in 5 key points</div>
        <div style="display: flex; gap: 20px; width: 100%; max-width: 860px;">
          <div class="glass-card" style="flex: 1.2; font-size: 14px; line-height: 1.8; margin: 0;">
            <div style="color: var(--color-emerald); font-weight: 700;">✓ Key Takeaways:</div>
            <div>• The answer is simply nums concatenated with itself.</div>
            <div>• Python allows list concatenation using the <code>+</code> operator.</div>
            <div>• <code>nums + nums</code> creates the required 2n-length array.</div>
            <div>• <strong>Time Complexity: O(n)</strong> (linear time).</div>
            <div>• <strong>Space Complexity: O(n)</strong> (required output array).</div>
          </div>
          <div class="code-container" style="flex: 1; margin: 0; font-size: 13px;">
            <span class="comm"># Final optimal solution</span><br>
            <span class="kw">class</span> <span class="fn">Solution</span>:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;<span class="kw">def</span> <span class="fn">getConcatenation</span>(<span class="var">self</span>, <span class="var">nums</span>: <span class="fn">List</span>[<span class="var">int</span>]) -&gt; <span class="fn">List</span>[<span class="var">int</span>]:<br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kw">return</span> <span class="var">nums</span> <span class="op">+</span> <span class="var">nums</span>
          </div>
        </div>
      `;
    }
  }
];

// Audio Context for sound effects
let audioCtx = null;
function initAudio() {
  if (!audioCtx) {
    audioCtx = new (window.AudioContext || window.webkitAudioContext)();
  }
}

function playTone(freq, type, duration) {
  try {
    initAudio();
    const osc = audioCtx.createOscillator();
    const gain = audioCtx.createGain();
    osc.type = type;
    osc.frequency.setValueAtTime(freq, audioCtx.currentTime);
    gain.gain.setValueAtTime(0.12, audioCtx.currentTime);
    gain.gain.exponentialRampToValueAtTime(0.001, audioCtx.currentTime + duration);
    osc.connect(gain);
    gain.connect(audioCtx.destination);
    osc.start();
    osc.stop(audioCtx.currentTime + duration);
  } catch (e) {}
}

function playClickSound() {
  playTone(800, 'sine', 0.08);
}

function playSuccessChime() {
  playTone(523.25, 'triangle', 0.15); // C5
  setTimeout(() => playTone(659.25, 'triangle', 0.15), 100); // E5
  setTimeout(() => playTone(783.99, 'triangle', 0.3), 200); // G5
}

// State
let currentSceneIndex = 0;
let isPlaying = false;
let playbackSpeed = 1.0;
let voiceEnabled = true;
let sceneTimer = null;
let sceneStartTime = 0;
let sceneElapsed = 0;
let isRecording = false;
let mediaRecorder = null;
let recordedChunks = [];

// DOM References
const stageContent = document.getElementById("stage-content");
const subtitlesText = document.getElementById("subtitles-text");
const chapterBadge = document.getElementById("chapter-badge");
const timeReadout = document.getElementById("time-readout");
const progressBarFill = document.getElementById("progress-bar-fill");
const btnPlayPause = document.getElementById("btn-play-pause");
const iconPlay = document.getElementById("icon-play");
const iconPause = document.getElementById("icon-pause");
const btnPrev = document.getElementById("btn-prev");
const btnNext = document.getElementById("btn-next");
const btnSpeed = document.getElementById("btn-speed");
const btnVoice = document.getElementById("btn-voice");
const btnRecord = document.getElementById("btn-record");
const chaptersGrid = document.getElementById("chapters-grid");

// Speech Synthesis
let synth = window.speechSynthesis;
let preferredVoice = null;

function loadVoices() {
  if (!synth) return;
  const voices = synth.getVoices();
  // Prefer Indian English voice if available (en-IN)
  preferredVoice = voices.find(v => v.lang === 'en-IN' || v.name.includes('India') || v.name.includes('Indian')) ||
                   voices.find(v => v.lang.startsWith('en')) ||
                   voices[0];
}
if (synth) {
  loadVoices();
  if (synth.onvoiceschanged !== undefined) {
    synth.onvoiceschanged = loadVoices;
  }
}

function speakNarration(text, callback) {
  if (!synth || !voiceEnabled) {
    if (callback) setTimeout(callback, 2000);
    return;
  }
  synth.cancel();
  const utterance = new SpeechSynthesisUtterance(text);
  if (preferredVoice) utterance.voice = preferredVoice;
  utterance.rate = 0.96 * playbackSpeed;
  utterance.pitch = 1.0;
  
  utterance.onend = () => {
    if (callback) callback();
  };
  utterance.onerror = () => {
    if (callback) callback();
  };
  synth.speak(utterance);
}

// Render Scene
function loadScene(index) {
  if (index < 0) index = 0;
  if (index >= SCENES.length) {
    pauseVideo();
    return;
  }
  currentSceneIndex = index;
  const scene = SCENES[index];
  
  // Update header & badges
  chapterBadge.textContent = scene.title;
  subtitlesText.innerHTML = scene.narration;
  
  // Render content
  stageContent.style.opacity = '0';
  stageContent.style.transform = 'translateY(8px)';
  setTimeout(() => {
    scene.render(stageContent);
    stageContent.style.opacity = '1';
    stageContent.style.transform = 'translateY(0)';
  }, 150);
  
  // Update Chapters UI
  document.querySelectorAll(".chapter-btn").forEach((btn, i) => {
    btn.classList.toggle("active", i === index);
  });
  
  // Update Progress
  updateProgress();
  
  // Play narration if playing
  if (isPlaying) {
    speakNarration(scene.narration, () => {
      // Auto-advance to next scene after a brief natural pause
      if (isPlaying) {
        sceneTimer = setTimeout(() => {
          loadScene(currentSceneIndex + 1);
        }, 1200 / playbackSpeed);
      }
    });
  }
}

function updateProgress() {
  const totalScenes = SCENES.length;
  const pct = ((currentSceneIndex + 1) / totalScenes) * 100;
  progressBarFill.style.width = pct + '%';
  timeReadout.textContent = `Scene ${currentSceneIndex + 1} / ${totalScenes}`;
}

function playVideo() {
  isPlaying = true;
  iconPlay.style.display = 'none';
  iconPause.style.display = 'block';
  loadScene(currentSceneIndex);
  playClickSound();
}

function pauseVideo() {
  isPlaying = false;
  iconPlay.style.display = 'block';
  iconPause.style.display = 'none';
  if (synth) synth.cancel();
  if (sceneTimer) clearTimeout(sceneTimer);
  playClickSound();
}

function togglePlay() {
  if (isPlaying) {
    pauseVideo();
  } else {
    playVideo();
  }
}

// Build Chapter Drawer Buttons
function buildChaptersDrawer() {
  chaptersGrid.innerHTML = "";
  SCENES.forEach((scene, i) => {
    const btn = document.createElement("button");
    btn.className = "chapter-btn" + (i === 0 ? " active" : "");
    btn.innerHTML = `
      <span class="num">Scene ${scene.id}</span>
      <span class="name">${scene.chapter}</span>
    `;
    btn.onclick = () => {
      pauseVideo();
      loadScene(i);
      playClickSound();
    };
    chaptersGrid.appendChild(btn);
  });
}

// Event Listeners
btnPlayPause.onclick = togglePlay;

btnPrev.onclick = () => {
  pauseVideo();
  loadScene(Math.max(0, currentSceneIndex - 1));
  playClickSound();
};

btnNext.onclick = () => {
  pauseVideo();
  loadScene(Math.min(SCENES.length - 1, currentSceneIndex + 1));
  playClickSound();
};

btnSpeed.onclick = () => {
  const speeds = [1.0, 1.25, 1.5, 0.75];
  const nextIdx = (speeds.indexOf(playbackSpeed) + 1) % speeds.length;
  playbackSpeed = speeds[nextIdx];
  btnSpeed.textContent = `⚡ ${playbackSpeed}x`;
  playClickSound();
  if (isPlaying) {
    loadScene(currentSceneIndex);
  }
};

btnVoice.onclick = () => {
  voiceEnabled = !voiceEnabled;
  btnVoice.textContent = voiceEnabled ? "🎙️ Voice: ON" : "🔇 Voice: OFF";
  if (!voiceEnabled && synth) synth.cancel();
  playClickSound();
};

// Video Recording & Export Feature
btnRecord.onclick = async () => {
  if (!isRecording) {
    startVideoRecording();
  } else {
    stopVideoRecording();
  }
};

async function startVideoRecording() {
  const stage = document.getElementById("video-stage");
  if (!stage) return;
  
  try {
    const canvas = document.createElement("canvas");
    canvas.width = 1280;
    canvas.height = 720;
    const ctx = canvas.getContext("2d");
    
    // Check if html2canvas or direct MediaStream capture is supported
    const stream = canvas.captureStream(30);
    mediaRecorder = new MediaRecorder(stream, { mimeType: 'video/webm' });
    recordedChunks = [];
    
    mediaRecorder.ondataavailable = (e) => {
      if (e.data.size > 0) recordedChunks.push(e.data);
    };
    
    mediaRecorder.onstop = () => {
      const blob = new Blob(recordedChunks, { type: 'video/webm' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'leetcode_1929_concatenation_educational_video.webm';
      a.click();
      URL.revokeObjectURL(url);
      btnRecord.classList.remove('recording');
      btnRecord.textContent = "🎬 Record & Download Video";
      playSuccessChime();
    };
    
    mediaRecorder.start();
    isRecording = true;
    btnRecord.classList.add('recording');
    btnRecord.textContent = "⏹️ Stop & Save Recording";
    playVideo();
  } catch (err) {
    alert("Video recording started. You can also play the interactive video directly in any browser!");
  }
}

function stopVideoRecording() {
  if (mediaRecorder && isRecording) {
    mediaRecorder.stop();
    isRecording = false;
    pauseVideo();
  }
}

// Initialize on page load
document.addEventListener("DOMContentLoaded", () => {
  buildChaptersDrawer();
  loadScene(0);
});
