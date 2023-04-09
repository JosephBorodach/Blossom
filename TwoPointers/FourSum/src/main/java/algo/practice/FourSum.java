package algo.practice;

import java.util.*;

/**
 * Instructions:
 *      Given an array nums of n ints, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 *          0 <= a, b, c, d < n
 *          a, b, c, and d are distinct.
 *          nums[a] + nums[b] + nums[c] + nums[d] == target
 *      You may return the answer in any order.
 *
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/4sum/
 *
 * Approaches
 * 1. Brute force:
 *      Create every combination of 4 #s
 *          Check if the given one == target
 *      time: O(n^4)
 * 2. Maps
 *
 */
public class FourSum {
    private int N;
    private int[] nums;
    private int target;
    private List<List<Integer>> subsets;

    /**
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        N = nums.length;
        this.nums = nums;
        this.target = target;

        subsets = new ArrayList<>();

        maps();

        return subsets;
    }

    /**
     * @time: O(2 * n^2) = O(n^2)
     * [Worst case, all values will be distinct]
     *      a. twoSums: Map all 2 sum combinations to the number of values that map to that sum
     *          O(n^2)
     *      b. compare 2sums
     *          O(n)
     * @space:
     */
    private void maps() {
        Map<Integer, Integer> counts = new HashMap<>();
        Map<Integer, Set<Integer>> twoSums = new HashMap<>();
        for (int i = 0; i < N; i++) {
            counts.put(nums[i], counts.getOrDefault(nums[i], 0) + 1);
            for (int j = i + 1; j < N; j++) {
                int sum = nums[i] + nums[j];
                Set<Integer> twoIndexes;
                if (twoSums.containsKey(sum)) {
                    twoIndexes = twoSums.get(sum);
                } else {
                    twoIndexes = new HashSet<>();
                    twoSums.put(sum, twoIndexes);
                }
                twoIndexes.add(nums[i] <= nums[j] ? nums[i] : nums[j]);
            }
        }

        Set<List<Integer>> sets = new HashSet<>();
        for (int sum : twoSums.keySet()) {
            int d = target - sum;
            try {
                Math.addExact(sum, d);
            } catch(ArithmeticException e) {
                continue;
            }
            if (!twoSums.containsKey(d)) {
                continue;
            }
            for (int v1 : twoSums.get(d)) {
                for (int v3 : twoSums.get(sum)) {
                    int v2 = d - v1;
                    int v4 = sum - v3;
                    if (isValid(v1, v2, v3, v4, counts)) {
                        List<Integer> sub = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));
                        Collections.sort(sub);
                        if (!sets.contains(sub)) {
                            sets.add(sub);
                            subsets.add(sub);
                        }
                    }
                }
            }
        }
    }

    private boolean isValid(int v1, int v2, int v3, int v4, Map<Integer, Integer> counts) {
        if (v1 != v2  && v1 != v3 && v1 != v4 && v2 != v3 && v2 != v4 && v3 != v4) {
            return true;
        }
        decrement(v1, counts);
        decrement(v2, counts);
        decrement(v3, counts);
        decrement(v4, counts);
        boolean valid = true;
        if (counts.get(v1) < 0) {
            valid = false;
        } else if (counts.get(v2) < 0) {
            valid = false;
        } else if (counts.get(v3) < 0) {
            valid = false;
        }
        increment(v1, counts);
        increment(v2, counts);
        increment(v3, counts);
        increment(v4, counts);
        return valid;
    }


    private void increment(int v, Map<Integer, Integer> map) {
        map.put(v, map.get(v) + 1);
    }

    private void decrement(int v, Map<Integer, Integer> map) {
        map.put(v, map.get(v) - 1);
    }
}
































