package coding.practice.leetcode;

/**
 * Problem: https://leetcode.com/problems/remove-element/description/
 *
 * Simultaneous Iteration Two-Pointer
 *
 * Time: O(n), where n is the size of the input array.
 *      1) In the worst case, every element is equal to val, but the the array will still be traveresed only 1 time!
 *      2) There are numerous constant operations which are negligent to the overall runtime of the program, c.
 *      - Therefore, n + c = O(n)
 *
 * Space: O(1), this was one of the requirements.
 *
 * Therefore, is in place.
 *
 * ~10 min to write solution
 * ~10 min to write tests
 */
public class RemoveElement {
    private final int[] nums;
    private final int val;

    /**
     * @throws IllegalArgumentException if input is null
     * @param nums
     */
    RemoveElement(int[] nums, int val) {
        if (nums == null) {
            throw new IllegalArgumentException();
        }
        this.val = val;
        this.nums = nums;
    }

    /**
     * @return
     * @throws IllegalArgumentException if array is not sorted
     */
    public int solveIt() {
        return simultaneousIteration(nums, val);
    }

    private int simultaneousIteration(int[] nums, int val) {
        int len = nums.length;
        int L = 0;
        for (int R = 0; R < len; R++) {
            if (nums[R] != val) {
                nums[L++] = nums[R];
            }
        }
        return L;
    }
}