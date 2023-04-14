package algo.practice;

public class ShortestUnsortedContinuousSubarray {
    /**
     * @time: O(n), linear
     *      To find unsorted section, and min & max is n exactly.
     *      To expand outward will be at most n - 2
     * @space: O(1), constant
     */
    public int findUnsortedSubarray(int[] nums) {
        int len = nums.length;

        int left = 1;
        // Sorted from the left
        while (left < len && nums[left - 1] <= nums[left]) {
            left++;
        }

        // Already sorted
        if (left == len) {
            return 0;
        }

        int right = len - 1;
        // Sorted from the right
        while (right > 0 && nums[right] >= nums[right - 1]) {
            right--;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }

        while (expandLeft(left, min, nums) || expandRight(right, max, nums)) {
            while (expandLeft(left, min, nums)) {
                max = Math.max(max, nums[left - 1]);
                left--;
            }
            while (expandRight(right, max, nums)) {
                min = Math.min(min, nums[right + 1]);
                right++;
            }
        }
        return right - left + 1;
    }

    private boolean expandLeft(int left, int min, int[] nums) {
        return left > 0 && min < nums[left - 1];
    }

    private boolean expandRight(int right, int max, int[] nums) {
        return right < nums.length - 1 && max > nums[right + 1];
    }
}
