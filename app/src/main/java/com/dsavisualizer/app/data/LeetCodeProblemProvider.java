package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.List;
import java.util.Map;

/**
 * Curated repository provider for top LeetCode interview questions,
 * featuring problem descriptions, company tags, animated inputs, and
 * multi-language code solutions (Java, C++, Python, JavaScript).
 */
public final class LeetCodeProblemProvider {

    private LeetCodeProblemProvider() {}

    public static void register(
        List<CategoryItem> categories,
        List<TechniqueItem> techniques,
        Map<String, TechniqueContent> contentMap
    ) {
        // 1. Add Category
        categories.add(0, new CategoryItem(
            "leetcode",
            "🔥 LeetCode Top Picks",
            "Top interview problems with animated solutions & code",
            "#F59E0B"
        ));

        // 2. Register Techniques
        techniques.add(0, new TechniqueItem(
            "leetcode_two_sum",
            "leetcode",
            "LeetCode #1: Two Sum",
            "Google, Amazon, Meta • Hash Map O(n)",
            "Easy"
        ));
        techniques.add(1, new TechniqueItem(
            "leetcode_valid_parentheses",
            "leetcode",
            "LeetCode #20: Valid Parentheses",
            "Amazon, Microsoft, Bloomberg • Stack Matching O(n)",
            "Easy"
        ));
        techniques.add(2, new TechniqueItem(
            "leetcode_reverse_linked_list",
            "leetcode",
            "LeetCode #206: Reverse Linked List",
            "Apple, Microsoft, Google • Pointer Reversal O(n)",
            "Easy"
        ));
        techniques.add(3, new TechniqueItem(
            "leetcode_binary_search",
            "leetcode",
            "LeetCode #704: Binary Search",
            "Google, Microsoft, Uber • Divide & Conquer O(log n)",
            "Easy"
        ));
        techniques.add(4, new TechniqueItem(
            "leetcode_best_time_stock",
            "leetcode",
            "LeetCode #121: Best Time to Buy & Sell Stock",
            "Amazon, TCS, Microsoft • One-Pass Window O(n)",
            "Easy"
        ));
        techniques.add(5, new TechniqueItem(
            "leetcode_merge_two_sorted_lists",
            "leetcode",
            "LeetCode #21: Merge Two Sorted Lists",
            "Apple, Amazon, Microsoft • Two Pointers O(n)",
            "Easy"
        ));
        techniques.add(6, new TechniqueItem(
            "leetcode_invert_binary_tree",
            "leetcode",
            "LeetCode #226: Invert Binary Tree",
            "Google, Meta, Amazon • Tree DFS O(n)",
            "Easy"
        ));
        techniques.add(7, new TechniqueItem(
            "leetcode_linked_list_cycle",
            "leetcode",
            "LeetCode #141: Linked List Cycle",
            "Amazon, Microsoft • Floyd's Tortoise & Hare O(n)",
            "Easy"
        ));

        // 3. Register TechniqueContent
        registerTwoSum(contentMap);
        registerValidParentheses(contentMap);
        registerReverseLinkedList(contentMap);
        registerBinarySearch(contentMap);
        registerBestTimeStock(contentMap);
        registerMergeTwoLists(contentMap);
        registerInvertBinaryTree(contentMap);
        registerLinkedListCycle(contentMap);
    }

    // ==========================================
    // 1. LeetCode #1: Two Sum
    // ==========================================
    private static void registerTwoSum(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_two_sum",
            "LeetCode #1: Two Sum",
            "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.\n\n"
                + "You may assume that each input would have exactly one solution, and you may not use the same element twice.\n\n"
                + "Example 1:\n"
                + "Input: nums = [2, 7, 11, 15], target = 9\n"
                + "Output: [0, 1]\n"
                + "Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].\n\n"
                + "Constraints:\n"
                + "• 2 <= nums.length <= 10^4\n"
                + "• -10^9 <= nums[i] <= 10^9\n"
                + "• Only one valid answer exists.",
            "🏢 Asked in: Google, Amazon, Meta, Apple, Microsoft, Bloomberg\n⭐ Status: #1 Most Asked Technical Interview Problem",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {2, 7, 11, 15},
            9
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #1";
        tc.sourceReference = "https://leetcode.com/problems/two-sum/";

        tc.algorithmByStyle.put("Iterative",
            "💡 Optimal One-Pass Hash Table Approach:\n\n"
                + "1. Brute Force O(n²): Check every possible pair (i, j). This results in TLE (Time Limit Exceeded) for large arrays.\n\n"
                + "2. Hash Map Trick O(n):\n"
                + "   • As we iterate through nums with index i, calculate complement = target - nums[i].\n"
                + "   • If complement already exists in the map, return [map.get(complement), i].\n"
                + "   • Otherwise, store nums[i] -> i in the hash map.\n\n"
                + "3. Time Complexity: O(n) — only one traversal of the array.\n"
                + "4. Space Complexity: O(n) — to store elements in the hash map."
        );
        tc.algorithmByStyle.put("Functions",
            "⚠️ Interview Pitfalls & Edge Cases:\n\n"
                + "• Negative Numbers: Works seamlessly because (target - nums[i]) correctly handles negative values.\n"
                + "• Duplicates: e.g. nums = [3, 3], target = 6. Checking complement before inserting the second 3 avoids overwriting.\n"
                + "• Same Element Reuse: You cannot use nums[0] twice; our algorithm guarantees distinct indices."
        );

        String javaCode = "class Solution {\n"
            + "    public int[] twoSum(int[] nums, int target) {\n"
            + "        // Map to store value -> index\n"
            + "        Map<Integer, Integer> map = new HashMap<>();\n"
            + "        for (int i = 0; i < nums.length; i++) {\n"
            + "            int complement = target - nums[i];\n"
            + "            // Check if complement was already seen\n"
            + "            if (map.containsKey(complement)) {\n"
            + "                return new int[] { map.get(complement), i };\n"
            + "            }\n"
            + "            map.put(nums[i], i);\n"
            + "        }\n"
            + "        return new int[0];\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    vector<int> twoSum(vector<int>& nums, int target) {\n"
            + "        unordered_map<int, int> map;\n"
            + "        for (int i = 0; i < nums.size(); i++) {\n"
            + "            int complement = target - nums[i];\n"
            + "            if (map.find(complement) != map.end()) {\n"
            + "                return {map[complement], i};\n"
            + "            }\n"
            + "            map[nums[i]] = i;\n"
            + "        }\n"
            + "        return {};\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def twoSum(self, nums: list[int], target: int) -> list[int]:\n"
            + "        seen = {}\n"
            + "        for i, num in enumerate(nums):\n"
            + "            complement = target - num\n"
            + "            if complement in seen:\n"
            + "                return [seen[complement], i]\n"
            + "            seen[num] = i\n"
            + "        return []";
        String jsCode = "function twoSum(nums, target) {\n"
            + "    const map = new Map();\n"
            + "    for (let i = 0; i < nums.length; i++) {\n"
            + "        const complement = target - nums[i];\n"
            + "        if (map.has(complement)) {\n"
            + "            return [map.get(complement), i];\n"
            + "        }\n"
            + "        map.set(nums[i], i);\n"
            + "    }\n"
            + "    return [];\n"
            + "}";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("Java", "Iterative", javaCode, false);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.putCode("JavaScript", "Iterative", jsCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 2. LeetCode #20: Valid Parentheses
    // ==========================================
    private static void registerValidParentheses(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_valid_parentheses",
            "LeetCode #20: Valid Parentheses",
            "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.\n\n"
                + "An input string is valid if:\n"
                + "1. Open brackets must be closed by the same type of brackets.\n"
                + "2. Open brackets must be closed in the correct order.\n"
                + "3. Every close bracket has a corresponding open bracket of the same type.\n\n"
                + "Example 1:\n"
                + "Input: s = \"()[]{}\" -> Output: true\n\n"
                + "Example 2:\n"
                + "Input: s = \"(]\" -> Output: false",
            "🏢 Asked in: Amazon, Microsoft, Bloomberg, Meta, Google, LinkedIn",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {1, 2, 2, 1},
            1
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #20";

        tc.algorithmByStyle.put("Iterative",
            "💡 Stack Matching Approach:\n\n"
                + "1. As we iterate through each character in string s:\n"
                + "   • If it is an opening bracket '(', '{', or '[', push it onto the stack.\n"
                + "   • If it is a closing bracket, check the top of the stack:\n"
                + "       - If stack is empty -> invalid (closing bracket without opener).\n"
                + "       - If top matches the corresponding opener, pop it.\n"
                + "       - Else -> invalid (mismatched bracket type).\n"
                + "2. At the end, return true if stack is empty, false otherwise.\n\n"
                + "Time: O(n) | Space: O(n)"
        );

        String javaCode = "class Solution {\n"
            + "    public boolean isValid(String s) {\n"
            + "        Stack<Character> stack = new Stack<>();\n"
            + "        for (char c : s.toCharArray()) {\n"
            + "            if (c == '(') stack.push(')');\n"
            + "            else if (c == '{') stack.push('}');\n"
            + "            else if (c == '[') stack.push(']');\n"
            + "            else if (stack.isEmpty() || stack.pop() != c) return false;\n"
            + "        }\n"
            + "        return stack.isEmpty();\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    bool isValid(string s) {\n"
            + "        stack<char> st;\n"
            + "        for (char c : s) {\n"
            + "            if (c == '(') st.push(')');\n"
            + "            else if (c == '{') st.push('}');\n"
            + "            else if (c == '[') st.push(']');\n"
            + "            else if (st.empty() || st.top() != c) return false;\n"
            + "            else st.pop();\n"
            + "        }\n"
            + "        return st.empty();\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def isValid(self, s: str) -> bool:\n"
            + "        stack = []\n"
            + "        bracket_map = {')': '(', '}': '{', ']': '['}\n"
            + "        for char in s:\n"
            + "            if char in bracket_map:\n"
            + "                top = stack.pop() if stack else '#'\n"
            + "                if bracket_map[char] != top:\n"
            + "                    return False\n"
            + "            else:\n"
            + "                stack.append(char)\n"
            + "        return not stack";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 3. LeetCode #206: Reverse Linked List
    // ==========================================
    private static void registerReverseLinkedList(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_reverse_linked_list",
            "LeetCode #206: Reverse Linked List",
            "Given the head of a singly linked list, reverse the list, and return the reversed list's head.\n\n"
                + "Example:\n"
                + "Input: head = [1, 2, 3, 4, 5]\n"
                + "Output: [5, 4, 3, 2, 1]\n\n"
                + "Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?",
            "🏢 Asked in: Apple, Microsoft, Google, Amazon, Adobe, Bloomberg",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {1, 2, 3, 4, 5},
            5
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #206";

        tc.algorithmByStyle.put("Iterative",
            "💡 Three-Pointer In-Place Reversal:\n\n"
                + "Maintain three pointers: prev (starts null), curr (starts head), and next.\n"
                + "1. Save the next node: next = curr.next.\n"
                + "2. Reverse the current node's pointer: curr.next = prev.\n"
                + "3. Advance prev: prev = curr.\n"
                + "4. Advance curr: curr = next.\n"
                + "5. Repeat until curr is null. Return prev as the new head!\n\n"
                + "Time: O(n) | Space: O(1) in-place"
        );

        String javaCode = "class Solution {\n"
            + "    public ListNode reverseList(ListNode head) {\n"
            + "        ListNode prev = null;\n"
            + "        ListNode curr = head;\n"
            + "        while (curr != null) {\n"
            + "            ListNode nextNode = curr.next;\n"
            + "            curr.next = prev;\n"
            + "            prev = curr;\n"
            + "            curr = nextNode;\n"
            + "        }\n"
            + "        return prev;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    ListNode* reverseList(ListNode* head) {\n"
            + "        ListNode* prev = nullptr;\n"
            + "        ListNode* curr = head;\n"
            + "        while (curr != nullptr) {\n"
            + "            ListNode* nextNode = curr->next;\n"
            + "            curr->next = prev;\n"
            + "            prev = curr;\n"
            + "            curr = nextNode;\n"
            + "        }\n"
            + "        return prev;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:\n"
            + "        prev = None\n"
            + "        curr = head\n"
            + "        while curr:\n"
            + "            nxt = curr.next\n"
            + "            curr.next = prev\n"
            + "            prev = curr\n"
            + "            curr = nxt\n"
            + "        return prev";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 4. LeetCode #704: Binary Search
    // ==========================================
    private static void registerBinarySearch(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_binary_search",
            "LeetCode #704: Binary Search",
            "Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.\n\n"
                + "You must write an algorithm with O(log n) runtime complexity.\n\n"
                + "Example 1:\n"
                + "Input: nums = [-1, 0, 3, 5, 9, 12], target = 9\n"
                + "Output: 4\n"
                + "Explanation: 9 exists in nums and its index is 4.",
            "🏢 Asked in: Google, Microsoft, Uber, Apple, Amazon",
            "O(1)",
            "O(log n)",
            "O(log n)",
            "O(1)",
            new int[] {-1, 0, 3, 5, 9, 12},
            9
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #704";

        tc.algorithmByStyle.put("Iterative",
            "💡 Divide and Conquer Binary Halving:\n\n"
                + "1. Set low = 0 and high = nums.length - 1.\n"
                + "2. While low <= high:\n"
                + "   • mid = low + (high - low) / 2 (avoids integer overflow!)\n"
                + "   • If nums[mid] == target, return mid.\n"
                + "   • If nums[mid] < target, eliminate the left half: low = mid + 1.\n"
                + "   • If nums[mid] > target, eliminate the right half: high = mid - 1.\n"
                + "3. If loop ends without finding target, return -1.\n\n"
                + "Time: O(log n) | Space: O(1)"
        );

        String javaCode = "class Solution {\n"
            + "    public int search(int[] nums, int target) {\n"
            + "        int low = 0, high = nums.length - 1;\n"
            + "        while (low <= high) {\n"
            + "            int mid = low + (high - low) / 2; // Prevents overflow\n"
            + "            if (nums[mid] == target) return mid;\n"
            + "            else if (nums[mid] < target) low = mid + 1;\n"
            + "            else high = mid - 1;\n"
            + "        }\n"
            + "        return -1;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    int search(vector<int>& nums, int target) {\n"
            + "        int low = 0, high = nums.size() - 1;\n"
            + "        while (low <= high) {\n"
            + "            int mid = low + (high - low) / 2;\n"
            + "            if (nums[mid] == target) return mid;\n"
            + "            else if (nums[mid] < target) low = mid + 1;\n"
            + "            else high = mid - 1;\n"
            + "        }\n"
            + "        return -1;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def search(self, nums: list[int], target: int) -> int:\n"
            + "        low, high = 0, len(nums) - 1\n"
            + "        while low <= high:\n"
            + "            mid = (low + high) // 2\n"
            + "            if nums[mid] == target:\n"
            + "                return mid\n"
            + "            elif nums[mid] < target:\n"
            + "                low = mid + 1\n"
            + "            else:\n"
            + "                high = mid - 1\n"
            + "        return -1";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 5. LeetCode #121: Best Time to Buy & Sell Stock
    // ==========================================
    private static void registerBestTimeStock(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_best_time_stock",
            "LeetCode #121: Best Time to Buy & Sell Stock",
            "You are given an array prices where prices[i] is the price of a given stock on the ith day.\n\n"
                + "You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.\n\n"
                + "Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.\n\n"
                + "Example 1:\n"
                + "Input: prices = [7, 1, 5, 3, 6, 4]\n"
                + "Output: 5\n"
                + "Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6 - 1 = 5.",
            "🏢 Asked in: Amazon, Microsoft, TCS, Goldman Sachs, Google",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {7, 1, 5, 3, 6, 4},
            5
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #121";

        tc.algorithmByStyle.put("Iterative",
            "💡 One-Pass Min-Price Tracking:\n\n"
                + "1. Maintain minPrice initialized to Integer.MAX_VALUE and maxProfit initialized to 0.\n"
                + "2. For each day's price in prices:\n"
                + "   • If price < minPrice: update minPrice = price.\n"
                + "   • Else if price - minPrice > maxProfit: update maxProfit = price - minPrice.\n"
                + "3. Return maxProfit.\n\n"
                + "Time: O(n) | Space: O(1)"
        );

        String javaCode = "class Solution {\n"
            + "    public int maxProfit(int[] prices) {\n"
            + "        int minPrice = Integer.MAX_VALUE;\n"
            + "        int maxProfit = 0;\n"
            + "        for (int price : prices) {\n"
            + "            if (price < minPrice) {\n"
            + "                minPrice = price;\n"
            + "            } else if (price - minPrice > maxProfit) {\n"
            + "                maxProfit = price - minPrice;\n"
            + "            }\n"
            + "        }\n"
            + "        return maxProfit;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    int maxProfit(vector<int>& prices) {\n"
            + "        int minPrice = INT_MAX, maxProfit = 0;\n"
            + "        for (int price : prices) {\n"
            + "            minPrice = min(minPrice, price);\n"
            + "            maxProfit = max(maxProfit, price - minPrice);\n"
            + "        }\n"
            + "        return maxProfit;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def maxProfit(self, prices: list[int]) -> int:\n"
            + "        min_price = float('inf')\n"
            + "        max_profit = 0\n"
            + "        for price in prices:\n"
            + "            if price < min_price:\n"
            + "                min_price = price\n"
            + "            elif price - min_price > max_profit:\n"
            + "                max_profit = price - min_price\n"
            + "        return max_profit";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 6. LeetCode #21: Merge Two Sorted Lists
    // ==========================================
    private static void registerMergeTwoLists(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_merge_two_sorted_lists",
            "LeetCode #21: Merge Two Sorted Lists",
            "You are given the heads of two sorted linked lists list1 and list2.\n\n"
                + "Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.\n\n"
                + "Return the head of the merged linked list.\n\n"
                + "Example 1:\n"
                + "Input: list1 = [1, 2, 4], list2 = [1, 3, 4]\n"
                + "Output: [1, 1, 2, 3, 4, 4]",
            "🏢 Asked in: Apple, Amazon, Microsoft, Google, Meta",
            "O(n + m)",
            "O(n + m)",
            "O(n + m)",
            "O(1)",
            new int[] {1, 2, 4, 1, 3, 4},
            4
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #21";

        tc.algorithmByStyle.put("Iterative",
            "💡 Dummy Prehead Pointer Merge:\n\n"
                + "1. Create a dummy head node and a current pointer pointing to it.\n"
                + "2. While both list1 and list2 are not null:\n"
                + "   • If list1.val <= list2.val, attach list1: current.next = list1; list1 = list1.next.\n"
                + "   • Else attach list2: current.next = list2; list2 = list2.next.\n"
                + "   • Advance current = current.next.\n"
                + "3. Attach the remaining non-null list: current.next = (list1 != null) ? list1 : list2.\n"
                + "4. Return dummy.next as the new merged head.\n\n"
                + "Time: O(n + m) | Space: O(1) in-place splicing"
        );

        String javaCode = "class Solution {\n"
            + "    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {\n"
            + "        ListNode dummy = new ListNode(-1);\n"
            + "        ListNode curr = dummy;\n"
            + "        while (list1 != null && list2 != null) {\n"
            + "            if (list1.val <= list2.val) {\n"
            + "                curr.next = list1;\n"
            + "                list1 = list1.next;\n"
            + "            } else {\n"
            + "                curr.next = list2;\n"
            + "                list2 = list2.next;\n"
            + "            }\n"
            + "            curr = curr.next;\n"
            + "        }\n"
            + "        curr.next = list1 != null ? list1 : list2;\n"
            + "        return dummy.next;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    ListNode* mergeTwoLists(ListNode* list1, ListNode* list2) {\n"
            + "        ListNode dummy(-1);\n"
            + "        ListNode* curr = &dummy;\n"
            + "        while (list1 && list2) {\n"
            + "            if (list1->val <= list2->val) {\n"
            + "                curr->next = list1;\n"
            + "                list1 = list1->next;\n"
            + "            } else {\n"
            + "                curr->next = list2;\n"
            + "                list2 = list2->next;\n"
            + "            }\n"
            + "            curr = curr->next;\n"
            + "        }\n"
            + "        curr->next = list1 ? list1 : list2;\n"
            + "        return dummy.next;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:\n"
            + "        dummy = ListNode(-1)\n"
            + "        curr = dummy\n"
            + "        while list1 and list2:\n"
            + "            if list1.val <= list2.val:\n"
            + "                curr.next = list1\n"
            + "                list1 = list1.next\n"
            + "            else:\n"
            + "                curr.next = list2\n"
            + "                list2 = list2.next\n"
            + "            curr = curr.next\n"
            + "        curr.next = list1 if list1 else list2\n"
            + "        return dummy.next";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 7. LeetCode #226: Invert Binary Tree
    // ==========================================
    private static void registerInvertBinaryTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_invert_binary_tree",
            "LeetCode #226: Invert Binary Tree",
            "Given the root of a binary tree, invert the tree, and return its root.\n\n"
                + "Famous Tweet by Max Howell:\n"
                + "\"Google: 90% of our engineers use the software you wrote (Homebrew), but you can't invert a binary tree on a whiteboard so f*** off.\"\n\n"
                + "Example 1:\n"
                + "Input: root = [4, 2, 7, 1, 3, 6, 9]\n"
                + "Output: [4, 7, 2, 9, 6, 3, 1]",
            "🏢 Asked in: Google, Meta, Amazon, Microsoft, Twitter",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(h)",
            new int[] {4, 2, 7, 1, 3, 6, 9},
            7
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #226";

        tc.algorithmByStyle.put("Iterative",
            "💡 Recursive Subtree Swap (DFS):\n\n"
                + "1. Base Case: If root == null, return null.\n"
                + "2. Recursively invert left child: invertTree(root.left).\n"
                + "3. Recursively invert right child: invertTree(root.right).\n"
                + "4. Swap left and right child pointers:\n"
                + "   TreeNode temp = root.left;\n"
                + "   root.left = root.right;\n"
                + "   root.right = temp;\n"
                + "5. Return root.\n\n"
                + "Time: O(n) visiting all nodes | Space: O(h) recursion stack height"
        );

        String javaCode = "class Solution {\n"
            + "    public TreeNode invertTree(TreeNode root) {\n"
            + "        if (root == null) return null;\n"
            + "        TreeNode temp = root.left;\n"
            + "        root.left = invertTree(root.right);\n"
            + "        root.right = invertTree(temp);\n"
            + "        return root;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    TreeNode* invertTree(TreeNode* root) {\n"
            + "        if (!root) return nullptr;\n"
            + "        TreeNode* temp = root->left;\n"
            + "        root->left = invertTree(root->right);\n"
            + "        root->right = invertTree(temp);\n"
            + "        return root;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:\n"
            + "        if not root:\n"
            + "            return None\n"
            + "        root.left, root.right = self.invertTree(root.right), self.invertTree(root.left)\n"
            + "        return root";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }

    // ==========================================
    // 8. LeetCode #141: Linked List Cycle
    // ==========================================
    private static void registerLinkedListCycle(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "leetcode_linked_list_cycle",
            "LeetCode #141: Linked List Cycle",
            "Given head, the head of a linked list, determine if the linked list has a cycle in it.\n\n"
                + "There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.\n\n"
                + "Return true if there is a cycle in the linked list. Otherwise, return false.\n\n"
                + "Example 1:\n"
                + "Input: head = [3, 2, 0, -4], pos = 1 (cycle connects back to node index 1)\n"
                + "Output: true",
            "🏢 Asked in: Microsoft, Amazon, Cisco, Spotify, Oracle",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {3, 2, 0, -4},
            2
        );
        tc.sourceType = "LeetCode";
        tc.sourceTitle = "LeetCode #141";

        tc.algorithmByStyle.put("Iterative",
            "💡 Floyd's Tortoise and Hare (Fast & Slow Pointers):\n\n"
                + "1. Initialize two pointers at head: slow = head, fast = head.\n"
                + "2. Advance slow by 1 step (slow = slow.next) and fast by 2 steps (fast = fast.next.next).\n"
                + "3. If there is no cycle, fast will reach null -> return false.\n"
                + "4. If there is a cycle, fast will eventually lap slow and they will collide (slow == fast) -> return true!\n\n"
                + "Time: O(n) | Space: O(1) optimal without hash set"
        );

        String javaCode = "public class Solution {\n"
            + "    public boolean hasCycle(ListNode head) {\n"
            + "        if (head == null) return false;\n"
            + "        ListNode slow = head;\n"
            + "        ListNode fast = head;\n"
            + "        while (fast != null && fast.next != null) {\n"
            + "            slow = slow.next;\n"
            + "            fast = fast.next.next;\n"
            + "            if (slow == fast) {\n"
            + "                return true; // Cycle detected!\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}";
        String cppCode = "class Solution {\n"
            + "public:\n"
            + "    bool hasCycle(ListNode *head) {\n"
            + "        ListNode *slow = head, *fast = head;\n"
            + "        while (fast && fast->next) {\n"
            + "            slow = slow->next;\n"
            + "            fast = fast->next->next;\n"
            + "            if (slow == fast) return true;\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "};";
        String pyCode = "class Solution:\n"
            + "    def hasCycle(self, head: Optional[ListNode]) -> bool:\n"
            + "        slow = fast = head\n"
            + "        while fast and fast.next:\n"
            + "            slow = slow.next\n"
            + "            fast = fast.next.next\n"
            + "            if slow == fast:\n"
            + "                return True\n"
            + "        return False";

        tc.putCode("Java", "Iterative", javaCode, true);
        tc.putCode("C++", "Iterative", cppCode, true);
        tc.putCode("Python", "Iterative", pyCode, true);
        tc.codeByStyle.put("Iterative", javaCode);

        contentMap.put(tc.techniqueId, tc);
    }
}
