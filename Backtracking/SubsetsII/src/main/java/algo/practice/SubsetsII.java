package algo.practice;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Instructions:
 *      Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
 *      The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 * @Author: Joseph Borodach
 * @Version 1
 * @https://leetcode.com/problems/subsets-ii/
 *
 * Approaches
 * 1. Brute force:
 *      Compute every combination
 *      Check if it is already in a set before adding it
 * 2. Backtrack
 * 3. Trie
 *
 */
public class SubsetsII {
    private int N;
    private int[] nums;
    private List<List<Integer>> subsets;

    /**
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        N = nums.length;
        this.nums = nums;
        subsets = new ArrayList<>();

        int i = 0;
        if (i == 0) {
            backtrack();
        }

        return subsets;
    }

    /**
     *
     * @time: O(n * 2^n)
     *      Does not generate any duplicate subsets.
     *      Thus, in the worst case (array consists of n distinct elements):
     *          Yotal # of recursive function calls will be O(2^n).
     *      Also, at each function call:
     *          A deep copy of the subset currentSubset generated so far is created & added to the subsets list.
     *          This will incur an additional O(n) time
     *              (as the maximum # of elements in the currentSubset will be n).
     *      So overall, the time complexity of this approach will be O(n⋅2^n).
     *
     * @space: O(n)
     *      - The space complexity of the sorting algorithm depends on the implementation of each programming language.
     *          In Java, Arrays.sort() for primitives is implemented as a variant of Quicksort:
     *              Whose space complexity is O(log n).
     *          In C++ sort() function provided by STL is a hybrid of Quick Sort, Heap Sort & Insertion Sort
     *              With the worst case space complexity of O(log n).
     *      - Thus, the use of inbuilt sort() function adds O(log n) to space complexity.
     *      - The recursion call stack occupies at most O(n) space.
     *      - The output list of subsets is not considered while analyzing space complexity.
     *      - So, the space complexity of this approach is O(n)
     */
    private void backtrack() {
        Arrays.sort(nums);
        backtrack(0, new ArrayList<>());
    }

    /**
     *
     * @param index
     * @param currentSubset
     */
    private void backtrack(int index, List<Integer> currentSubset) {
        subsets.add(new ArrayList<>(currentSubset));
        for (int i = index; i < N; i++) {
            if (i == index || nums[i] != nums[i - 1]) {
                currentSubset.add(nums[i]);
                backtrack(i + 1, currentSubset);
                currentSubset.remove(currentSubset.size() - 1);
            }
        }
    }
}
