package com.dsavisualizer.app.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository providing authentic LeetCode problem statements, examples,
 * constraints, and optimal complexity profiles for roadmap problems.
 */
public final class LeetCodeProblemDetailRepository {

    public static class ProblemDetail {
        public final int lcNumber;
        public final String title;
        public final String description;
        public final String timeComplexity;
        public final String spaceComplexity;
        public final String companies;
        public final String techniqueId;

        public ProblemDetail(
                int lcNumber,
                String title,
                String description,
                String timeComplexity,
                String spaceComplexity,
                String companies,
                String techniqueId) {
            this.lcNumber = lcNumber;
            this.title = title;
            this.description = description;
            this.timeComplexity = timeComplexity;
            this.spaceComplexity = spaceComplexity;
            this.companies = companies;
            this.techniqueId = techniqueId;
        }
    }

    private static final Map<Integer, ProblemDetail> DETAILS = new HashMap<>();

    static {
        // ==========================================
        // LeetCode #1929: Concatenation of Array
        // ==========================================
        DETAILS.put(1929, new ProblemDetail(
                1929,
                "Concatenation of Array",
                "Given an integer array nums of length n, you want to create an array ans of length 2n where ans[i] == nums[i] and ans[i + n] == nums[i] for 0 <= i < n (0-indexed).\n\n"
                        + "Specifically, ans is the concatenation of two nums arrays.\n\n"
                        + "Return the array ans.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [1, 2, 1]\n"
                        + "Output: [1, 2, 1, 1, 2, 1]\n"
                        + "Explanation: The array ans is formed as follows:\n"
                        + "- ans = [nums[0], nums[1], nums[2], nums[0], nums[1], nums[2]]\n"
                        + "- ans = [1, 2, 1, 1, 2, 1]\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [1, 3, 2, 1]\n"
                        + "Output: [1, 3, 2, 1, 1, 3, 2, 1]\n"
                        + "Explanation: The array ans is formed as follows:\n"
                        + "- ans = [nums[0], nums[1], nums[2], nums[3], nums[0], nums[1], nums[2], nums[3]]\n"
                        + "- ans = [1, 3, 2, 1, 1, 3, 2, 1]\n\n"
                        + "Constraints:\n"
                        + "• n == nums.length\n"
                        + "• 1 <= n <= 1000\n"
                        + "• 1 <= nums[i] <= 1000",
                "O(n)",
                "O(n)",
                "Google, Amazon, Microsoft",
                "leetcode_concatenation_array"
        ));

        // ==========================================
        // LeetCode #1480: Running Sum of 1d Array
        // ==========================================
        DETAILS.put(1480, new ProblemDetail(
                1480,
                "Running Sum of 1d Array",
                "Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]…nums[i]).\n\n"
                        + "Return the running sum of nums.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [1, 2, 3, 4]\n"
                        + "Output: [1, 3, 6, 10]\n"
                        + "Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [1, 1, 1, 1, 1]\n"
                        + "Output: [1, 2, 3, 4, 5]\n"
                        + "Explanation: Running sum is obtained as follows: [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1].\n\n"
                        + "Example 3:\n"
                        + "Input: nums = [3, 1, 2, 10, 1]\n"
                        + "Output: [3, 4, 6, 16, 17]\n\n"
                        + "Constraints:\n"
                        + "• 1 <= nums.length <= 1000\n"
                        + "• -10^6 <= nums[i] <= 10^6",
                "O(n)",
                "O(1)",
                "Microsoft, Apple, Amazon",
                "leetcode_running_sum"
        ));

        // ==========================================
        // LeetCode #1470: Shuffle the Array
        // ==========================================
        DETAILS.put(1470, new ProblemDetail(
                1470,
                "Shuffle the Array",
                "Given the array nums consisting of 2n elements in the form [x1, x2, ..., xn, y1, y2, ..., yn].\n\n"
                        + "Return the array in the form [x1, y1, x2, y2, ..., xn, yn].\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [2, 5, 1, 3, 4, 7], n = 3\n"
                        + "Output: [2, 3, 5, 4, 1, 7]\n"
                        + "Explanation: Since x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 then the answer is [2, 3, 5, 4, 1, 7].\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [1, 2, 3, 4, 4, 3, 2, 1], n = 4\n"
                        + "Output: [1, 4, 2, 3, 3, 2, 4, 1]\n\n"
                        + "Constraints:\n"
                        + "• 1 <= n <= 500\n"
                        + "• nums.length == 2n\n"
                        + "• 1 <= nums[i] <= 10^3",
                "O(n)",
                "O(n)",
                "Apple, Google, Amazon",
                "leetcode_shuffle_array"
        ));

        // ==========================================
        // LeetCode #1431: Kids With Greatest Candies
        // ==========================================
        DETAILS.put(1431, new ProblemDetail(
                1431,
                "Kids With the Greatest Number of Candies",
                "There are n kids with candies. You are given an integer array candies, where each candies[i] represents the number of candies the ith kid has, and an integer extraCandies, denoting the number of extra candies that you have.\n\n"
                        + "Return a boolean array result of length n, where result[i] is true if, after giving the ith kid all the extraCandies, they will have the greatest number of candies among all the kids, or false otherwise.\n\n"
                        + "Example 1:\n"
                        + "Input: candies = [2, 3, 5, 1, 3], extraCandies = 3\n"
                        + "Output: [true, true, true, false, true]\n\n"
                        + "Constraints:\n"
                        + "• n == candies.length\n"
                        + "• 2 <= n <= 100\n"
                        + "• 1 <= candies[i] <= 100\n"
                        + "• 1 <= extraCandies <= 50",
                "O(n)",
                "O(1)",
                "Amazon, Microsoft",
                "leetcode_kids_candies"
        ));

        // ==========================================
        // LeetCode #1672: Richest Customer Wealth
        // ==========================================
        DETAILS.put(1672, new ProblemDetail(
                1672,
                "Richest Customer Wealth",
                "You are given an m x n integer grid accounts where accounts[i][j] is the amount of money the ith customer has in the jth bank. Return the wealth that the richest customer has.\n\n"
                        + "A customer's wealth is the amount of money they have in all their bank accounts. The richest customer is the customer that has the maximum wealth.\n\n"
                        + "Example 1:\n"
                        + "Input: accounts = [[1, 2, 3], [3, 2, 1]]\n"
                        + "Output: 6\n"
                        + "Explanation:\n"
                        + "1st customer has wealth = 1 + 2 + 3 = 6\n"
                        + "2nd customer has wealth = 3 + 2 + 1 = 6\n"
                        + "Both customers are considered the richest with a wealth of 6 each, so return 6.\n\n"
                        + "Constraints:\n"
                        + "• m == accounts.length\n"
                        + "• n == accounts[i].length\n"
                        + "• 1 <= m, n <= 50\n"
                        + "• 1 <= accounts[i][j] <= 100",
                "O(m × n)",
                "O(1)",
                "Google, Microsoft, Amazon",
                "leetcode_richest_customer"
        ));

        // ==========================================
        // LeetCode #1: Two Sum
        // ==========================================
        DETAILS.put(1, new ProblemDetail(
                1,
                "Two Sum",
                "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.\n\n"
                        + "You may assume that each input would have exactly one solution, and you may not use the same element twice.\n\n"
                        + "You can return the answer in any order.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [2, 7, 11, 15], target = 9\n"
                        + "Output: [0, 1]\n"
                        + "Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [3, 2, 4], target = 6\n"
                        + "Output: [1, 2]\n\n"
                        + "Constraints:\n"
                        + "• 2 <= nums.length <= 10^4\n"
                        + "• -10^9 <= nums[i] <= 10^9\n"
                        + "• -10^9 <= target <= 10^9\n"
                        + "• Only one valid answer exists.",
                "O(n)",
                "O(n)",
                "Google, Meta, Amazon, Apple, Microsoft",
                "leetcode_two_sum"
        ));

        // ==========================================
        // LeetCode #217: Contains Duplicate
        // ==========================================
        DETAILS.put(217, new ProblemDetail(
                217,
                "Contains Duplicate",
                "Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [1, 2, 3, 1]\n"
                        + "Output: true\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [1, 2, 3, 4]\n"
                        + "Output: false\n\n"
                        + "Constraints:\n"
                        + "• 1 <= nums.length <= 10^5\n"
                        + "• -10^9 <= nums[i] <= 10^9",
                "O(n)",
                "O(n)",
                "Apple, Amazon, Bloomberg, Microsoft",
                "leetcode_contains_duplicate"
        ));

        // ==========================================
        // LeetCode #242: Valid Anagram
        // ==========================================
        DETAILS.put(242, new ProblemDetail(
                242,
                "Valid Anagram",
                "Given two strings s and t, return true if t is an anagram of s, and false otherwise.\n\n"
                        + "An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.\n\n"
                        + "Example 1:\n"
                        + "Input: s = \"anagram\", t = \"nagaram\"\n"
                        + "Output: true\n\n"
                        + "Example 2:\n"
                        + "Input: s = \"rat\", t = \"car\"\n"
                        + "Output: false\n\n"
                        + "Constraints:\n"
                        + "• 1 <= s.length, t.length <= 5 * 10^4\n"
                        + "• s and t consist of lowercase English letters.",
                "O(n)",
                "O(1)",
                "Uber, Google, Amazon, Bloomberg",
                "leetcode_valid_anagram"
        ));

        // ==========================================
        // LeetCode #136: Single Number
        // ==========================================
        DETAILS.put(136, new ProblemDetail(
                136,
                "Single Number",
                "Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.\n\n"
                        + "You must implement a solution with a linear runtime complexity and use only constant extra space.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [2, 2, 1]\n"
                        + "Output: 1\n\n"
                        + "Example 2:\n"
                        + "Input: nums = [4, 1, 2, 1, 2]\n"
                        + "Output: 4\n\n"
                        + "Constraints:\n"
                        + "• 1 <= nums.length <= 3 * 10^4\n"
                        + "• -3 * 10^4 <= nums[i] <= 3 * 10^4\n"
                        + "• Each element in the array appears twice except for one element which appears only once.",
                "O(n)",
                "O(1)",
                "Amazon, Microsoft, Google",
                "leetcode_single_number"
        ));

        // ==========================================
        // LeetCode #349: Intersection of Two Arrays
        // ==========================================
        DETAILS.put(349, new ProblemDetail(
                349,
                "Intersection of Two Arrays",
                "Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.\n\n"
                        + "Example 1:\n"
                        + "Input: nums1 = [1, 2, 2, 1], nums2 = [2, 2]\n"
                        + "Output: [2]\n\n"
                        + "Example 2:\n"
                        + "Input: nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]\n"
                        + "Output: [9, 4]\n"
                        + "Explanation: [4, 9] is also accepted.\n\n"
                        + "Constraints:\n"
                        + "• 1 <= nums1.length, nums2.length <= 1000\n"
                        + "• 0 <= nums1[i], nums2[i] <= 1000",
                "O(n + m)",
                "O(n)",
                "Meta, Google, Amazon",
                "leetcode_intersection_arrays"
        ));

        // ==========================================
        // LeetCode #121: Best Time to Buy and Sell Stock
        // ==========================================
        DETAILS.put(121, new ProblemDetail(
                121,
                "Best Time to Buy and Sell Stock",
                "You are given an array prices where prices[i] is the price of a given stock on the ith day.\n\n"
                        + "You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.\n\n"
                        + "Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.\n\n"
                        + "Example 1:\n"
                        + "Input: prices = [7, 1, 5, 3, 6, 4]\n"
                        + "Output: 5\n"
                        + "Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6 - 1 = 5.\n\n"
                        + "Constraints:\n"
                        + "• 1 <= prices.length <= 10^5\n"
                        + "• 0 <= prices[i] <= 10^4",
                "O(n)",
                "O(1)",
                "Amazon, TCS, Microsoft, Google",
                "leetcode_best_time_stock"
        ));

        // ==========================================
        // LeetCode #20: Valid Parentheses
        // ==========================================
        DETAILS.put(20, new ProblemDetail(
                20,
                "Valid Parentheses",
                "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.\n\n"
                        + "An input string is valid if:\n"
                        + "1. Open brackets must be closed by the same type of brackets.\n"
                        + "2. Open brackets must be closed in the correct order.\n"
                        + "3. Every close bracket has a corresponding open bracket of the same type.\n\n"
                        + "Example 1:\n"
                        + "Input: s = \"()\"\n"
                        + "Output: true\n\n"
                        + "Example 2:\n"
                        + "Input: s = \"()[]{}\"\n"
                        + "Output: true\n\n"
                        + "Example 3:\n"
                        + "Input: s = \"(]\"\n"
                        + "Output: false\n\n"
                        + "Constraints:\n"
                        + "• 1 <= s.length <= 10^4\n"
                        + "• s consists of parentheses only '()[]{}'.",
                "O(n)",
                "O(n)",
                "Amazon, Microsoft, Bloomberg, Google",
                "leetcode_valid_parentheses"
        ));

        // ==========================================
        // LeetCode #206: Reverse Linked List
        // ==========================================
        DETAILS.put(206, new ProblemDetail(
                206,
                "Reverse Linked List",
                "Given the head of a singly linked list, reverse the list, and return the reversed list.\n\n"
                        + "Example 1:\n"
                        + "Input: head = [1, 2, 3, 4, 5]\n"
                        + "Output: [5, 4, 3, 2, 1]\n\n"
                        + "Example 2:\n"
                        + "Input: head = [1, 2]\n"
                        + "Output: [2, 1]\n\n"
                        + "Constraints:\n"
                        + "• The number of nodes in the list is the range [0, 5000].\n"
                        + "• -5000 <= Node.val <= 5000",
                "O(n)",
                "O(1)",
                "Apple, Microsoft, Google, Amazon",
                "leetcode_reverse_linked_list"
        ));

        // ==========================================
        // LeetCode #704: Binary Search
        // ==========================================
        DETAILS.put(704, new ProblemDetail(
                704,
                "Binary Search",
                "Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.\n\n"
                        + "You must write an algorithm with O(log n) runtime complexity.\n\n"
                        + "Example 1:\n"
                        + "Input: nums = [-1, 0, 3, 5, 9, 12], target = 9\n"
                        + "Output: 4\n"
                        + "Explanation: 9 exists in nums and its index is 4.\n\n"
                        + "Constraints:\n"
                        + "• 1 <= nums.length <= 10^4\n"
                        + "• -10^4 < nums[i], target < 10^4\n"
                        + "• All the integers in nums are unique.\n"
                        + "• nums is sorted in ascending order.",
                "O(log n)",
                "O(1)",
                "Google, Microsoft, Uber, Apple",
                "leetcode_binary_search"
        ));

        // ==========================================
        // LeetCode #21: Merge Two Sorted Lists
        // ==========================================
        DETAILS.put(21, new ProblemDetail(
                21,
                "Merge Two Sorted Lists",
                "You are given the heads of two sorted linked lists list1 and list2.\n\n"
                        + "Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.\n\n"
                        + "Return the head of the merged linked list.\n\n"
                        + "Example 1:\n"
                        + "Input: list1 = [1, 2, 4], list2 = [1, 3, 4]\n"
                        + "Output: [1, 1, 2, 3, 4, 4]\n\n"
                        + "Constraints:\n"
                        + "• The number of nodes in both lists is in the range [0, 50].\n"
                        + "• -100 <= Node.val <= 100\n"
                        + "• Both list1 and list2 are sorted in non-decreasing order.",
                "O(n + m)",
                "O(1)",
                "Apple, Amazon, Microsoft",
                "leetcode_merge_two_sorted_lists"
        ));

        // ==========================================
        // LeetCode #226: Invert Binary Tree
        // ==========================================
        DETAILS.put(226, new ProblemDetail(
                226,
                "Invert Binary Tree",
                "Given the root of a binary tree, invert the tree, and return its root.\n\n"
                        + "Example 1:\n"
                        + "Input: root = [4, 2, 7, 1, 3, 6, 9]\n"
                        + "Output: [4, 7, 2, 9, 6, 3, 1]\n\n"
                        + "Constraints:\n"
                        + "• The number of nodes in the tree is in the range [0, 100].\n"
                        + "• -100 <= Node.val <= 100",
                "O(n)",
                "O(h)",
                "Google, Meta, Amazon",
                "leetcode_invert_binary_tree"
        ));

        // ==========================================
        // LeetCode #141: Linked List Cycle
        // ==========================================
        DETAILS.put(141, new ProblemDetail(
                141,
                "Linked List Cycle",
                "Given head, the head of a linked list, determine if the linked list has a cycle in it.\n\n"
                        + "There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.\n\n"
                        + "Return true if there is a cycle in the linked list. Otherwise, return false.\n\n"
                        + "Example 1:\n"
                        + "Input: head = [3, 2, 0, -4], pos = 1\n"
                        + "Output: true\n"
                        + "Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).\n\n"
                        + "Constraints:\n"
                        + "• The number of the nodes in the list is in the range [0, 10^4].\n"
                        + "• -10^5 <= Node.val <= 10^5",
                "O(n)",
                "O(1)",
                "Amazon, Microsoft, Spotify",
                "leetcode_linked_list_cycle"
        ));
    }

    private LeetCodeProblemDetailRepository() {}

    public static ProblemDetail getProblemDetail(int lcNumber, String title, String pattern, String difficulty, String companies) {
        if (DETAILS.containsKey(lcNumber)) {
            return DETAILS.get(lcNumber);
        }

        // Dynamically synthesize a high-quality problem description for any problem
        String timeComplexity = "O(n)";
        String spaceComplexity = "O(1)";
        String patLower = (pattern != null ? pattern : "").toLowerCase();

        if (patLower.contains("binary search")) {
            timeComplexity = "O(log n)";
            spaceComplexity = "O(1)";
        } else if (patLower.contains("tree") || patLower.contains("graph") || patLower.contains("dfs") || patLower.contains("bfs")) {
            timeComplexity = "O(V + E)";
            spaceComplexity = "O(V)";
        } else if (patLower.contains("dp") || patLower.contains("dynamic")) {
            timeComplexity = "O(n²)";
            spaceComplexity = "O(n)";
        } else if (patLower.contains("hash")) {
            timeComplexity = "O(n)";
            spaceComplexity = "O(n)";
        } else if (patLower.contains("sliding window") || patLower.contains("two pointer")) {
            timeComplexity = "O(n)";
            spaceComplexity = "O(1)";
        }

        String desc = "Problem Statement:\n"
                + "Solve the standard interview problem \"" + title + "\" using the " + pattern + " algorithmic paradigm.\n\n"
                + "Core Objectives:\n"
                + "• Analyze input constraints and handle empty or single-element corner cases.\n"
                + "• Design an optimal solution avoiding redundant calculations.\n"
                + "• Target Time Complexity: " + timeComplexity + "\n"
                + "• Target Space Complexity: " + spaceComplexity + "\n\n"
                + "Pattern: " + pattern + "\n"
                + "Difficulty: " + difficulty + "\n"
                + "Frequent Interviewers: " + (companies != null && !companies.isEmpty() ? companies : "Google, Amazon, Meta");

        String techId = resolveTechniqueIdForPattern(lcNumber, pattern);
        return new ProblemDetail(lcNumber, title, desc, timeComplexity, spaceComplexity, companies, techId);
    }

    public static String resolveTechniqueIdForPattern(int lcNumber, String pattern) {
        if (DETAILS.containsKey(lcNumber)) {
            return DETAILS.get(lcNumber).techniqueId;
        }
        return "leetcode_" + lcNumber;
    }
}
