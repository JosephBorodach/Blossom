package algo.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/interval-list-intersections
 *
 * Example #1:
 *      Input: [[0,2], [5,10], [13,23], [24,25]],
 *             [[1,5], [8,12], [15,24], [25,26]]
 *      Output: [[1,2], [5,5], [8,10], [15,23], [24,24], [25,25]]
 *      Explanation:
 *          [1,2]: [1,5] & [0,2]
 *          [5,5]: [1,5] & [5,10]
 *
 * Approaches:
 * 1. Brute Force - Nest loops:
 *      for (each pair in one list):
 *          while (overlaps with pair in other list)
 *              Add overlap to return list
 * 2. Two Pointers
 *
 * @time: O(n)
 *      where n is the size of the two arrays combined
 * @space: O(m)
 *      where m is the number of intervals returned in the array,
 *      because needed to use a growing sized list before converting it to an pre-defined sized array
 */
public class IntervalIntersections {
    private int[][] A;
    private int[][] B;

    /**
     *
     * @param firstList
     * @param secondList
     * @return
     */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if (firstList == null || secondList == null) {
            throw new IllegalArgumentException("Input lists cannot be null");
        }
        A = firstList;
        B = secondList;
        return twoPointers();
    }

    /**
     *
     * @return
     */
    private int[][] twoPointers() {
        List<int[]> intersections = new ArrayList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1 < A.length && p2 < B.length) {
            // Use higher start point
            int lo = A[p1][0] >= B[p2][0] ? A[p1][0] : B[p2][0];

            // Use & remove lower end point
            int hi;
            if (A[p1][1] <= B[p2][1]) {
                hi = A[p1][1];
                p1++;
            } else {
                hi = B[p2][1];
                p2++;
            }
            if (lo <= hi) {
                intersections.add(new int[]{lo, hi});
            }
        }
        return intersections.toArray(new int[0][0]);
    }
}















