package algo.practice;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/create-maximum-number
 *
 * Instructions:
 *      Given two non-negative integer arrays nums1 and nums2 of length m and n,
 *      return the maximum number of length k <= m + n that can be formed
 *      by picking elements from the 2 arrays.
 *      The overall time complexity should be O(k * (m+n)).
 *
 * Constraints:
 *      m == nums1.length
 *      n == nums2.length
 *      1 <= m, n <= 500
 *      0 <= nums1[i], nums2[i] <= 9
 *      1 <= k <= m + n
 *
 * Approaches
 * 1. Brute Force: Compute every combination - save the best
 * 2.
 *
 * Example 1: k = 5
 *            a = [3, 4, 6, 5]
 *            b = [9, 1, 2, 5, 8, 3]
 *            [9,  8,  6,  5,  3]
 * Example 2: k = 5
 *            a = [6, 7]
 *            b = [6, 0, 4]
 *            [6,  7,  6,  0,  4]
 * Example 3: k = 3
 *            a = [3, 9]
 *            b = [8, 9]
 *            [9,  8,  9]
 *
 * @time: O((m + n) ^ 3)
 * @space:
 */
public class CreateMaxNumber {
    /**
     * @param nums1 the first non-negative integer array
     * @param nums2 the second non-negative integer array
     * @param k the length of the maximum number to be formed
     * @return the maximum number of length k that can be formed by picking elements
     *         from nums1 and nums2
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        int totalLength = len1 + len2;

        // Edge cases
        // Not enough elements in both arrays to form k-length number
        if (totalLength < k) {
            return new int[k];
        }
        // All elements in both arrays used to form k-len #
        if (totalLength == k){
            return merge(nums1, nums2, k);
        }

        int ans[] = new int[k];
        for (int i = 0; i <= k; i++) {

            // Not enough elements in nums1 to form i-length subarray
            if (i > len1) {
                break;
            }
            // Not enough elements in nums2 to form (k-i)-length subarray
            if (k - i > len2) {
                continue;
            }

            // Find the maximum i-length subarray in nums1 and (k-i)-length subarray in nums2
            int[] maxSub1 = getMaxSub(nums1, i);
            int[] maxSub2 = getMaxSub(nums2, k - i);

            // Merge the two subarrays to form a k-length number
            int[] maxNums = merge(maxSub1, maxSub2, k);

            /*
            System.out.println("Max Sub #1, size: " + i);
            print(maxSub1, i);

            System.out.println("Max Sub #2, size: " + (k - i));
            print(maxSub2, k - i);

            System.out.println("Merged");
            print(maxNums, k);

            System.out.println();
            */

            // If the merged number is greater than the current answer, replace the answer
            if (compare(maxNums, 0, ans, 0)) {
                ans = maxNums;
            }
        }
        return ans;
    }

    /**
     * Find the maximum k-length subarray that can be formed from nums.
     *
     * @param nums the integer array
     * @param k the length of the subarray to be formed
     * @return the maximum k-length subarray that can be formed from nums
     */
    private int[] getMaxSub(int[] nums, int k) {
        if (k == 0) {
            return new int[k];
        }

        int len = nums.length;
        int [] ans = new int[k];

        int idx = 0;

        // Pop #s from the end of ans that are smaller than the current # in nums and won't fit in the remaining slot
        for (int i = 0; i < len; i++) {

            // Cannot move a number beyond the 0 index
            while (idx > 0) {

                // Are there enough numbers remaining to keep moving this number to the left?
                int nFilled = idx + 1;
                int nRemaining = len - i - 1;
                if (nFilled + nRemaining <= k) {
                    break;
                }

                // Is this number greater than the number currently stored at idx - 1 in the subarray?
                if (nums[i] <= ans[idx - 1]) {
                    break;
                }
                idx--;
            }
            // Don't store #s which aren't in the top k
            // Also, this would cause an out of bounds exception
            if (idx < k) {
                ans[idx++] = nums[i];
            }
        }
        return ans;
    }

    private int[] merge(int[] nums1, int[] nums2, int k) {
        int i1 = 0;
        int i2 = 0;

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            // check the 2 remain arrays to see which one is larger.
            if (compare(nums1, i1, nums2, i2)) {
                ans[i] = nums1[i1++];
            } else {
                ans[i] = nums2[i2++];
            }
        }
        return ans;
    }

    /**
     * Compare 2 arrays at the "start" index
     */
    private boolean compare(int[] nums1, int start1, int[] nums2, int start2) {
        int len1 = nums1.length;
        int len2 = nums2.length;

        int lenSub1 = len1 - start1;
        int lenSub2 = len2 - start2;

        // start1 >= len1
        if (lenSub1 <= 0) {
            return false;
        }

        // start2 >= len2
        if (lenSub2 <= 0) {
            return true;
        }

        // max # of elemnts to check
        int len = Math.max(lenSub1, lenSub2);

        for (int i = 0; i < len; i++) {
            int i1 = start1 + i;
            int num1 = i1 < len1 ? nums1[i1] : 0;

            int i2 = start2 + i;
            int num2 = i2 < len2 ? nums2[i2] : 0;

            if (num1 != num2) {
                return num1 > num2;
            }
        }
        // equal, choose either one is ok
        return true;
    }

    /**
     *
     * @param nums
     * @param n
     */
    private void print(int[] nums, int n) {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(nums[i]);
            if (i != n - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}













