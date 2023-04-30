package coding.practice.leetcode;

public class Utils {
    /**
     * Creates a linked list from an array of strings.
     * @param arr the array of strings to create the linked list from.
     * @return the head of the linked list.
     */
    public static int[] createNumArray(String[] arr) {
        int len = arr.length;
        if (len == 0) {
            return null;
        }

        int[] nums = new int[len];

        for (int i = 0; i < len; i++) {

            nums[i] = Integer.parseInt(arr[i]);
        }
        return nums;
    }

}
