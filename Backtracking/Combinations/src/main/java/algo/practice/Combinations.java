package algo.practice;

import java.util.*;

/**
 * Instructions:
 *      Given two integers n and k, return all possible combinations of k numbers chosen from the range [1, n].
 *      You may return the answer in any order.
 *
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/combinations/
 *
 * Approaches
 * 1. Brute force:
 *      DFS from every possible starting point with k remaining ints <= n.
 * 2. Backtracking
 *
 * @time: O(k * C(n,k))
 *      Where C(n,k) = # of combinations of n items taken k at a time
 *      This is because, for each combination, we iterate over the range of possible starting points,
 *      and then we perform k iterations for each starting point.
 *      The number of combinations is given by C(n,k) = n!/(k! * (n-k)!).
 *      Therefore, the time complexity of the algorithm is O(k * n!/((n-k)! * k!)).
 *
 * @space: O(k)
 *      In each recursive call, we add an element to the subset, and there can be at most k elements in the subset.
 *      Therefore, the space used by the recursion stack is also O(k).
 */
public class Combinations {
    private int n;
    private int k;
    private List<List<Integer>> subsets;

    /**
     * Returns all possible combinations of k numbers chosen from the range [1, n].
     * @param n - the upper limit of the range [1, n]
     * @param k - the number of elements to choose from the range
     * @return - a list of all possible combinations
     */
    public List<List<Integer>> combine(int n, int k) {
        this.n = n;
        this.k = k;
        subsets = new ArrayList<>();
        backtrack(1, new ArrayList<>(), n - k + 1);
        return subsets;
    }

    /**
     * Backtracking algorithm to find all possible combinations of k numbers chosen from the range [1, n].
     * @param s - the current starting point
     * @param subset - the current subset of numbers
     * @param N - the maximum possible number that can be added to the subset
     */
    private void backtrack(int s, List<Integer> subset, int N) {
        if (subset.size() == k) {
            subsets.add(new ArrayList<>(subset));
            return;
        }
        int next = N + 1;
        for (int i = s; i <= N; i++) {
            subset.add(i);
            backtrack(i + 1, subset, next);
            subset.remove(subset.size() - 1);
        }
    }
}
