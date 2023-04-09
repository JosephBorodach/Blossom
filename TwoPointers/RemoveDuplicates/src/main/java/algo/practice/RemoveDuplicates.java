package algo.practice;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/remove-duplicates-from-sorted-array
 *
 * @time: O(n), where n is the size of the array
 * @space: O(1)
 */
public class RemoveDuplicates {
    /**
     * Remove duplicates in place
     * @param nums - sorted int array
     * @return the new length of the array with the duplicate numbers removed
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Integer array cannot be null");
        }
        int N = nums.length;
        int newLength = N;

        int lo = 0;
        int hi = 1;
        while (hi < N) {
            if (nums[lo] == nums[hi]) {
                newLength -= 1;
            } else {
                lo += 1;
                nums[lo] = nums[hi];
            }
            if (hi != lo) {
                nums[hi] = Integer.MAX_VALUE;
            }
            hi += 1;
        }
        return newLength;
    }
}
