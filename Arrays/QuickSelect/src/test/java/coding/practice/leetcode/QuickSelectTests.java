package coding.practice.leetcode;

import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class QuickSelectTests {
    @ParameterizedTest
    @CsvSource({
            "10->4->5->8->6->11->26, 3, 6"
    })
    void tests(String inputList, int k, int expected) {
        final int[] nums = Utils.createNumArray(inputList.split("->"));

        final int actual = new QuickSelect().solveIt(nums, k);

        assertEquals(expected, actual);
    }
}
