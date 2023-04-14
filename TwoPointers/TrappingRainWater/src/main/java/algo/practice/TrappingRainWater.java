package algo.practice;

import java.util.Stack;

/**
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/trapping-rain-water
 *
 */
public class TrappingRainWater {
    public int trap(int[] height) {
        int i = 3;
        if (i == 0) {
            return bruteForce(height);
        }
        if (i == 1) {
            return dynamicProgramming(height);
        }
        if (i == 2) {
            return stack(height);
        }
        return twoPointers(height);
    }

    /**
     * @param height
     * @return
     *
     * @time: O(n^2)
     * @space: O(1), constant
     */
    private int bruteForce(int[] height) {
        int ans = 0;
        int len = height.length;
        for (int i = 1; i < len - 1; i++) {

            int left_max = 0;
            int right_max = 0;

            // Search the left part for max bar size
            for (int j = i; j >= 0; j--) {
                left_max = Math.max(left_max, height[j]);
            }

            // Search the right part for max bar size
            for (int j = i; j < len; j++) {
                right_max = Math.max(right_max, height[j]);
            }

            ans += Math.min(left_max, right_max) - height[i];
        }
        return ans;
    }

    /**
     * @param height
     * @return
     *
     * @time: O(3n) == O(n), linear.
     * @space: O(2n) = O(n), linear.
     */
    private int dynamicProgramming(int[] height) {
        int ans = 0;
        int len = height.length;
        int[] left_max = new int[len];
        int[] right_max = new int[len];
        // Get the max #s to the left
        for (int i = 1; i < len - 1; i++) {
            left_max[i] = Math.max(height[i-1], left_max[i-1]);
        }
        // Get the max #s to the right
        for (int i = len-2; i >= 0; i--) {
            right_max[i] = Math.max(height[i+1], right_max[i+1]);
        }
        for (int i = 1; i < len; i++) {
            int minEnd = Math.min(left_max[i], right_max[i]);
            if (height[i] < minEnd) {
                ans += minEnd - height[i];
            }
        }
        return ans;
    }

    /**
     *
     * @param height
     * @return
     */
    private int stack(int[] height) {
        int ans = 0;
        int current = 0;
        int len = height.length;

        Stack<Integer> stack = new Stack<>();
        while (current < len) {
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int distance = current - stack.peek() - 1;
                int bounded_height = Math.min(height[current], height[stack.peek()]) - height[top];
                ans += distance * bounded_height;
            }
            stack.push(current++);
        }
        return ans;
    }

    /**
     * @param height
     * @return
     *
     * @time: O(n), linear.
     * @space: O(1), constant.
     */
    private int twoPointers(int[] height) {
        int ans = 0;
        int len = height.length;

        int left_max = 0;
        int right_max = 0;

        int left = 0;
        int right = len - 1;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= left_max) {
                    left_max = height[left];
                } else {
                    ans += left_max - height[left];
                }
                left++;
            } else {
                if (height[right] >= right_max) {
                    right_max = height[right];
                } else {
                    ans += right_max - height[right];
                }
                right--;
            }
        }
        return ans;
    }
}