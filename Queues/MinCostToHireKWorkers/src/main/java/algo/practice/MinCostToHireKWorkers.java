package algo.practice;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/minimum-cost-to-hire-k-workers
 *
 * This class solves the problem of hiring k workers with the minimum possible cost, given the following constraints:
 * - There are n workers.
 * - For each worker, we know their quality (Q) and their minimum wage expectation (W).
 * - We have to hire exactly k workers.
 * - Every worker must be paid whichever following wage is HIGHER:
 *      a) Ratio of their QUALITY compared to other workers
 *      b) Their min wage expectation
 * - We have to return the least amount needed to form a paid group.
 *
 * The problem can be reframed as follows: Which group can have the lowest individual max wage?
 *
 * Constraints:
 *      n == quality.len == wage.len
 *      1 <= k <= n <= 10^4
 *      1 <= quality[i], wage[i] <= 10^4
 *
 * The class uses two approaches to solve the problem:
 * 1. Brute Force: Compute every combination
 * 2. Sort by quality and move through using a sliding window
 */
public class MinCostToHireKWorkers {
    private int k;
    private int N;
    private int[] W;
    private int[] Q;

    /**
     * Solves the minimum cost to hire k workers problem.
     *
     * @param quality an array of n integers representing the quality of each worker.
     * @param wage an array of n integers representing the minimum wage expectation of each worker.
     * @param k an integer representing the number of workers to hire.
     * @return a double representing the minimum cost of hiring k workers.
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        W = wage;
        this.k = k;
        Q = quality;
        N = W.length;
        int i = 1;
        if (i == 0) {
            return new BruteForce().solveIt();
        }
        return sortedRatios();
    }

    /**
     * Solves the minimum cost to hire k workers problem using the sorted ratios approach.
     *
     * @return a double representing the minimum cost of hiring k workers.
     */
    private double sortedRatios() {
        double[][] workers = new double[N][2];
        for (int i = 0; i < N; i++) {
            workers[i] = new double[]{(double) W[i] / Q[i], Q[i]};
        }

        Arrays.sort(workers, Comparator.comparingDouble(a -> a[0]));

        double sumQ = 0.0;
        double ans = Double.MAX_VALUE;
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (double[] w : workers) {
            sumQ += w[1];
            pq.offer(-w[1]);
            if (pq.size() > k) {
                sumQ += pq.poll();
            }
            if (pq.size() == k) {
                ans = Math.min(ans, sumQ * w[0]);
            }
        }
        return ans;
    }

    /**
     * Brute Force: Compute every combination
     * TLE
     * time: O(n^k), since n choose k
     * space: O()
     */
    private class BruteForce {
        private double currentMax;

        /**
         * @return
         */
        private double solveIt() {
            currentMax = Integer.MAX_VALUE;
            recurse(0, k, new ArrayList<>());
            return currentMax;
        }

        private void recurse(int i, int rem, List<Integer> workers) {
            if (rem == 0) {
                computeTotal(workers);
                return;
            }
            if (rem > N - i) {
                return;
            }
            workers.add(i);
            recurse(i+1, rem-1, new ArrayList<>(workers));
            workers.remove(workers.size() - 1);

            recurse(i+1, rem, workers);
        }

        private void computeTotal(List<Integer> workers) {
            double totalQuality = 0.0;
            for (int i : workers) {
                totalQuality += Q[i];
            }
            double maxWage = 0.0;
            for (int i : workers) {
                double w = W[i] * (totalQuality / Q[i]);
                if (w > maxWage) {
                    if (w > currentMax) {
                        return;
                    }
                    maxWage = w;
                }
            }
            currentMax = maxWage;
        }
    }
}


















