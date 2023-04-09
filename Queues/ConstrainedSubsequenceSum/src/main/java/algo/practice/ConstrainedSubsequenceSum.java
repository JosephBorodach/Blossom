package algo.practice;

import java.util.*;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/constrained-subsequence-sum
 *
 * Instructions:
 *      Return max sum of a subsequence
 *          For every 2 consecutive ints,
 *          nums[i] and nums[j],
 *          where i < j,
 *          j - i <= k
 *      Subsequence is obtained by
 *          Deleting some # of elements (can be 0),
 *          leaving remaining elements in their original order
 *
 * Example:
 *      Input: nums = [10,2,-10,5,20], k = 2
 *      Output: 37
 *      Explanation: The subsequence is [10, 2, 5, 20].
 *
 *      Input: nums = [10, -2, -3, -2, 20], k = 2
 *
 * Approaches:
 * 1. Brute Force:
 *      Try every combination, removing all values < 0
 *      O(n^2), quadratic
 * 2. Use Dynamic Programming
 * 3. Min Queue
 */
public class ConstrainedSubsequenceSum {

    /**
     * @param nums
     * @param k
     * @return
     */
    public int constrainedSubsetSum(int[] nums, int k) {
        int i = 0;
        if (i == 0) {
            return basicDP(nums, k);
        }
        if (i == 1) {
            return decreasingMonotonicQueue(nums, k);
        }
        return priorityQueue(nums, k);
    }

    /**
     * Queue the actual values in the k size window
     *
     * @time: O(N)
     * @space: O(k)
     */
    private int priorityQueue(int[] dp, int k) {
        int ans = dp[0];
        Deque<Integer> Q = new LinkedList<>();
        for (int i = 0; i < dp.length; i++) {
            // Add max from window of size k to this sum
            if (!Q.isEmpty()) {
                dp[i] += Q.peek();
            }
            // Greedily, remove all entries which are less than the current one
            while (!Q.isEmpty() && dp[i] > Q.peekLast()) {
                Q.pollLast();
            }
            // Remove last element of range k
            if (!Q.isEmpty() && i >= k && Q.peek() == dp[i - k]) {
                Q.poll();
            }
            // Add this element to Q
            if (dp[i] > 0) {
                Q.offer(dp[i]);
            }
            // Update answer
            if (dp[i] > ans) {
                ans = dp[i];
            }
        }
        return ans;
    }

    /**
     * Queue the indexes in the k sized window
     *
     * @time: O(N)
     * @space: O(k)
     */
    private int decreasingMonotonicQueue(int[] dp, int k) {
        int ans = Integer.MIN_VALUE;
        Deque<Integer> Q = new LinkedList<>();
        for (int i = 0; i < dp.length; i++) {
            // Add max from window of size k to this sum
            if (!Q.isEmpty() && dp[Q.peekFirst()] > 0) {
                dp[i] += dp[Q.peekFirst()];
            }
            // Greedily, remove all entries which are less than the current one
            while (!Q.isEmpty() && dp[i] >= dp[Q.peekLast()]) {
                Q.pollLast();
            }
            Q.addLast(i);
            // Remove last element of range k
            if (i - Q.peek() + 1 > k) {
                Q.peek();
            }
            if (dp[i] > ans) {
                ans = dp[i];
            }
        }
        return ans;
    }

    /**
     * Basic Idea: For each index, for the max sum from the previous k indexes.
     * TLE
     * @time: O(N * k)
     * @space: O(N)
     */
    private int basicDP(int[] nums, int k) {
        int N = nums.length;
        int[] dp = new int[N];
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int max = 0;
            int start = i - k > 0 ? i - k : 0;
            for (int j = start; j < i; j++) {
                if (dp[j] > max) {
                    max = dp[j];
                }
            }
            dp[i] = max + nums[i];
            if (dp[i] > ans) {
                ans = dp[i];
            }
        }
        return ans;
    }
}

















