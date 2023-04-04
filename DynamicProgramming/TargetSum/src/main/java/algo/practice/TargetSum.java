package algo.practice;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/target-sum/
 *
 * A solution to the "Target Sum" problem on LeetCode: https://leetcode.com/problems/target-sum/
 *
 * Given an array of integers and a target sum, return the number of different expressions that can be built by adding or
 * subtracting the integers in the array such that the expression evaluates to the target sum.
 */
public class TargetSum {
    private int N;
    private int total;
    private int target;
    private int[] nums;

    /**
     * Finds the number of different expressions that can be built by adding or subtracting the integers in the input
     * array such that the expression evaluates to the target sum.
     *
     * @param nums   the input array of integers
     * @param target the target sum
     * @return the number of different expressions that evaluate to the target sum
     * @throws IllegalArgumentException if nums is null
     */
    public int findTargetSumWays(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        N = nums.length;
        this.nums = nums;
        this.target = target;

        total = getTotal();
        if (total < Math.abs(target)) {
            return 0;
        }

        int i = 0;
        if (i == 0) {
            return bfs();
        }
        if (i == 1) {
            return new TopDown().recursive();
        }
        if (i == 2) {
            return new TopDown().iterative();
        }
        return new TopDown().optimized();
    }

    /**
     * This class represents the top-down approach with memoization for solving the problem.
     * It has three methods, each using a different type of data structure to store memoization values:
     * - `iterative()`: Uses a 2D array
     * - `arrays()`: Uses a 2D array of integers to store memoization values
     */
    private class TopDown {

        /**
         * Time complexity: O(n * p)
         * Space complexity: O(p)
         *      2 dp arrays of size 2 * total + 1 are used, therefore the space usage is O(p)
         *
         * @return The number of ways to reach the target value using addition or subtraction of the values in the array
         */
        private int optimized() {
            int[] dp = new int[2 * total + 1];
            dp[nums[0] + total] = 1;
            dp[-nums[0] + total] += 1;

            for (int i = 1; i < N; i++) {
                int[] next = new int[2 * total + 1];
                for (int sum = -total; sum <= total; sum++) {
                    int index = sum + total;
                    if (dp[index] > 0) {
                        next[index + nums[i]] += dp[index];
                        next[index - nums[i]] += dp[index];
                    }
                }
                dp = next;
            }

            return dp[target + total];
        }


        /**
         * This method uses the top-down approach with memoization and an iterative solution to solve the problem.
         * It uses a 2D array to store memoization values and returns the result.
         *
         * @return The number of ways to reach the target value using addition or subtraction of the values in the array
         */
        private int iterative() {
            int[][] dp = new int[N][2 * total + 1];
            dp[0][nums[0] + total] = 1;
            dp[0][-nums[0] + total] += 1;

            for (int i = 1; i < N; i++) {
                for (int sum = -total; sum <= total; sum++) {
                    int index = sum + total;
                    if (dp[i - 1][index] > 0) {
                        dp[i][index + nums[i]] += dp[i - 1][index];
                        dp[i][index - nums[i]] += dp[i - 1][index];
                    }
                }
            }

            return dp[N - 1][target + total];
        }

        /**
         * A bottom-up dynamic programming approach to solving the problem
         * Time complexity: O(n * p)
         * Space complexity: O(n * p)
         *      The depth of the recursion tree can go up to n.
         *      The array contains n * p elements
         *
         * The brute force solution would be the same code without saving the results.
         *
         * @return the number of different expressions that evaluate to the target sum
         */
        private int recursive() {
            int[][] memo = new int[N][2 * total + 1];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= total; j++) {
                    memo[i][j] = Integer.MAX_VALUE;
                }
            }
            return recurse(0, 0, memo, total);
        }

        /**
         * @param i
         * @param sum
         * @return
         */
        public int recurse(int i, int sum, int[][] memo, int total) {
            if (i == N) {
                return sum == target ? 1 : 0;
            }
            int index = sum + total;
            if (memo[i][index] != Integer.MAX_VALUE) {
                return memo[i][index];
            }
            return memo[i][index] = recurse(i + 1, sum + nums[i], memo, total) +
                                    recurse(i + 1, sum - nums[i], memo, total);
        }
    }

    /**
     * Uses a breadth-first search algorithm to find the number of different expressions that evaluate to the target sum.
     *
     * The idea is:
     *      - Keep track of all possible sums that can be obtained by adding or subtracting the #s in the input array,
     *      - Then to iterate through the array and compute all possible sums that can be obtained by adding or subtracting each new number to the existing sums.
     *      - At each iteration, the counts of all the new sums are stored in a map, which is used to update the previous map of sums.
     *      - The final count of the target sum is returned.
     *
     * @return the number of different expressions that evaluate to the target sum
     */
    private int bfs() {
        Map<Integer, Integer> sums = new HashMap<>();
        sums.put(0, 1);
        for (int i = 0; i < N; i++) {
            Map<Integer, Integer> newSums = new HashMap<>();
            for (Map.Entry<Integer, Integer> e : sums.entrySet()) {
                int s1 = e.getKey() + nums[i];
                int s2 = e.getKey() - nums[i];
                int count = e.getValue();
                newSums.put(s1, newSums.getOrDefault(s1, 0) + count);
                newSums.put(s2, newSums.getOrDefault(s2, 0) + count);
            }
            sums = newSums;
        }
        return sums.getOrDefault(target, 0);
    }

    private int getTotal() {
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        return total;
    }
}
