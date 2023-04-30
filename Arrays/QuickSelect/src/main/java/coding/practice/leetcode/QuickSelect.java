package coding.practice.leetcode;

public class QuickSelect {

    private int k;
    private int len;
    private int[] nums;

    public int solveIt(int[] nums, int k) {
        this.k = k;
        this.nums = nums;
        len = nums.length;

        return kthSmallest(0, len - 1);
    }

    // partition function similar to quick sort
    // Considers last element as pivot and adds
    // elements with less value to the left and
    // high value to the right and also changes
    // the pivot position to its respective position
    // in the final array.
    public int partition(int low, int high) {
        int pivot = nums[high], pivotloc = low;

        for (int i = low; i <= high; i++) {
            // inserting elements of less value
            // to the left of the pivot location
            if (nums[i] < pivot) {
                int temp = nums[i];
                nums[i] = nums[pivotloc];
                nums[pivotloc] = temp;
                pivotloc++;
            }
        }

        // swapping pivot to the final pivot location
        int temp = nums[high];
        nums[high] = nums[pivotloc];
        nums[pivotloc] = temp;

        return pivotloc;
    }

    // finds the kth position (of the sorted array)
    // in a given unsorted array i.e this function
    // can be used to find both kth largest and
    // kth smallest element in the array.
    // ASSUMPTION: all elements in arr[] are distinct
    public int kthSmallest(int low, int high) {
        // find the partition
        int partition = partition(low, high);

        // if partition value is equal to the kth position,
        // return value at k.
        if (partition == k - 1) {
            return nums[partition];
        }

        // if partition value is less than kth position,
        // search right side of the array.
        else if (partition < k - 1) {
            return kthSmallest(partition + 1, high);
        }

        // if partition value is more than kth position,
        // search left side of the array.
        else {
            return kthSmallest(low, partition - 1);
        }
    }
}
