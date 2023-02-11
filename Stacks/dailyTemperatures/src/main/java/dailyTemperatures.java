import java.util.*;

public class dailyTemperatures {
    public int[] dailyTemperatures(int[] temps) {
        int i = 0;
        if (i == 0) {
            return backwards(temps);
        }
        return stack(temps);
    }

    /**
     * 1) backwards
     * Time: O(n), where n is the size of temps.
     * - In the worst case,
     * - The reason the iterations in the while loop does not exceed N is because the "jumps" prevent an index from being visited twice.
     * Space: O(1)
     * - In the worst case,
     * In place
     *
     * Example:
     * Input:   [73,74,75,71,69,72,76,73]
     * Indexes: [0, 1, 2, 3, 4, 5, 6, 7]
     * Output:  [1, 1, 4, 2, 1, 1, 0, 0]
     * 73 = 0       if (temps[7] >= hottest): True
     * 76 = 0       if (temps[6] >= hottest): True
     * 72 = 1       while (temps[5 + 1] <= temps[5])            False, ans[5] = 1
     * 69 = 1       while (temps[4 + 1] <= temps[4])            False, ans[4] = 1
     * 71 = 2       while (temps[3 + 1]:69 <= temps[3]:71)      True, days = 2
     *              while (temps[3 + 2]:72 <= temps[3]:71)      False, ans[3] = 1
     * 75 = 4       while (temps[2 + 1]:71 <= temps[2]:75)      True, days = 3
     *              while (temps[2 + 3]:72 <= temps[2]:75)      True, days = 4
     *              while (temps[2 + 4]:76 <= temps[2]:75)      False, ans[2] = 4
     * 74 = 1       while (temps[1 + 1]:75 <= temps[1]:74)      False, ans[1] = 1
     * 73 = 1       while (temps[0 + 1]:74 <= temps[0]:73)      False, ans[0] = 1
     */
    private int[] backwards(int[] temps) {
        int hottest = 0;
        int n = temps.length;
        int ans[] = new int[n];
        for (int today = n - 1; today >= 0; today--) {
            if (temps[today] >= hottest) {
                hottest = temps[today];
                continue;
            }
            int days = 1;
            while (temps[today + days] <= temps[today]) {
                days += ans[today + days];
            }
            ans[today] = days;
        }
        return ans;
    }

    /**
     * 2) Stack:
     * The stack represents the days' index
     * Time: O(n), where n is the size of temps.
     * - In the worst case, every the ans for each index is at the end of the array
     * - So, the size of the array is traversed 2x.
     * - 2n == O(n)
     * Space: O(n), where n is the size of temps.
     * - In the worst case, every the ans for each index is at the end of the array
     * Not in place
     *
     * Example:
     * Input:   [73,74,75,71,69,72,76,73]
     * Indexes: [0, 1, 2, 3, 4, 5, 6, 7]
     * Output:  [1, 1, 4, 2, 1, 1, 0, 0]
     * while (74 > {73}):           73 = 1 = (1 - 0)
     * while (75 > {74}):           74 = 1 = (2 - 1)
     * while (71 > {75}):           None
     * while (69 > {71, 75}):       None
     * while (72 > {69, 71, 75}):   69 = 1 = (5 - 4); 71 = 2 = (5 - 3);
     * while (76 > {72, 75}):       72 = 1 = (6 - 5); 75 = 4 = (6 - 2);
     * while (73 > {76}):           None
     */
    private int[] stack(int[] temps) {
        int n = temps.length;
        int[] ans = new int[n];
        Stack<Integer> s = new Stack<>();
        for (int today = 0; today < n; today++) {
            while (!s.isEmpty() && temps[s.peek()] < temps[today]) {
                ans[s.peek()] = today - s.pop();
            }
            s.push(today);
        }
        return ans;
    }
}