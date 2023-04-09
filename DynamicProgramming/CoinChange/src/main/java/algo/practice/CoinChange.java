package algo.practice;

import java.util.*;

/**
 * Instructions:
 *      You are given:
 *          1. int array coins representing coins of different denominations
 *          2. an int amount representing a total amount of money
 *      Return the number of combinations that make up that amount.
 *          If that amount of money cannot be made up by any combination of the coins, return 0.
 *          You may assume that you have an infinite number of each kind of coin.
 *          The answer is guaranteed to fit into a signed 32-bit int.
 *
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/coin-change-ii/
 *
 * Example #1:
 *      Input: amount = 5, coins = [1,2,5]; Output: 4
 *      Explanation: there are four ways to make up the amount:
 *          {5}
 *          {2+2+1}
 *          {2+1+1+1}
 *          {1+1+1+1+1}
 *
 *
 * 0: {1, 2, 5} = 2 + 1 + 1
 * 1: {2:2, 3:2} = _ + 2 = 2 = {1+1, 1+2}
 * 2: {3:3, 4:3} = _ + 1 = 1 = {1+1+1, 1+1+2}
 * 3: {4:, 5:4} = 1 + 1 = 2
 * 4: {5} = 1
 *
 * Approaches:
 * 1. Brute Force: Nested Loops - compute every combination
 * 2. DFS Dynamic Programming
 * 3.
 *
 * Notation:
 *      n = len of coins array
 *      p = possible combinations that sum to <= than amount
 */
public class CoinChange {

    private int N;
    private int amount;
    private int[] coins;

    /**
     *
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        if (coins == null) {
            throw new IllegalArgumentException("Coins cannot be null.");
        }
        N = coins.length;
        this.coins = coins;
        this.amount = amount;
        int i = 1;
        if (i == 0) {
            return dfs();
        }
        return dp();
    }

    /**
     *
     * @return
     * @time O(n * p)
     * @space O(n * p)
     */
    private int dp() {
        int e = amount + 1;
        int[] dp = new int[e];
        dp[0] = 1;
        for (int coin : coins) {
            for (int x = coin; x < e; ++x) {
                dp[x] += dp[x - coin];
            }
        }
        return dp[amount];
    }


    /**
     *
     * @return
     * @time O(n * p)
     * @space O(n * p)
     */
    private int dfs() {
        return dfs(0, 0, new HashMap<>());
    }

    /**
     *
     * @param i
     * @param sum
     * @param map
     * @return
     */
    private int dfs(int i, int sum, Map<Integer, Integer> map) {
        if (sum == amount) {
            return 1;
        }
        if (sum > amount || i >= N) {
            return 0;
        }
        int coord = getCoord(i, sum);
        if (map.containsKey(coord)) {
            return map.get(coord);
        }
        int v = dfs(i + 1, sum, map) + dfs(i, sum + coins[i], map);
        map.put(coord, v);
        return v;
    }

    /**
     * @param i
     * @param sum
     * @return
     */
    private int getCoord(int i, int sum) {
        return i * amount + sum;
    }
}














