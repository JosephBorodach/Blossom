package algo.practice;

import java.util.*;

public class BasicCalculatorII {
    private StringBuilder sb;
    public int calculate(String s) {
        String[] strings = s.split("(?<=[-+*/=()])|(?=[-+*/=()])");
        for (String str : strings) {
            System.out.println(str);
        }
        sb = new StringBuilder(s.replaceAll(" ", ""));

        Stack<String> stack = new Stack<>();

        stack.push(getDigit());
        while (sb.length() != 0) {
            char symbol = sb.charAt(0);
            sb.deleteCharAt(0);

            String num2 = getDigit();
            if (symbol != '*' && symbol != '/') {
                stack.push(String.valueOf(symbol));
                stack.push(num2);
                continue;
            }
            // perform opperation now
            int n1 = Integer.parseInt(stack.pop());
            int n2 = Integer.parseInt(num2);
            stack.push(String.valueOf(symbol == '*' ? n1 * n2 : n1 / n2));
        }

        Stack<String> reversed = new Stack<>();
        while (!stack.isEmpty()) {
            reversed.push(stack.pop());
        }
        stack = reversed;

        int ans = Integer.parseInt(stack.pop());
        while (!stack.isEmpty()) {
            String symbol = stack.pop();
            int num2 = Integer.parseInt(stack.pop());
            ans = symbol.equals("+") ? ans + num2 : ans - num2;
        }
        return ans;
    }

    private String getDigit() {
        int len = sb.length();
        if (len == 1) {
            String digit = sb.substring(0, 1);
            sb.deleteCharAt(0);
            return digit;
        }
        StringBuilder digit = new StringBuilder();
        while (!sb.isEmpty() && Character.isDigit(sb.charAt(0))) {
            digit.append(sb.charAt(0));
            sb.deleteCharAt(0);
        }
        return digit.toString();
    }
}
