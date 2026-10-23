import java.util.Scanner;
import java.util.Stack;

public class DelimiterCheckerTrace {

    // Turns the stack into text, from bottom to top
    static String showStack(Stack<Character> stack) {
        if (stack.isEmpty()) {
            return "(empty)";
        }
        String text = "";
        for (char c : stack) {
            text = text + c + " ";
        }
        return text.trim();
    }

    // Checks if an opening symbol and a closing symbol belong together
    static boolean isPair(char open, char close) {
        if (open == '(' && close == ')') return true;
        if (open == '[' && close == ']') return true;
        if (open == '{' && close == '}') return true;
        return false;
    }

    public static void main(String[] args) {
        // Step 1: Get the input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a sequence: ");
        String input = scanner.nextLine();
        scanner.close();

        System.out.println("Input: " + (input.isEmpty() ? "(empty)" : input));
        System.out.println();

        Stack<Character> stack = new Stack<>();
        String reason = "";   // stays empty if there is no error

        // Step 2: Go through the input one character at a time
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            boolean isOpening = (c == '(' || c == '[' || c == '{');
            boolean isClosing = (c == ')' || c == ']' || c == '}');

            // Ignore every other character
            if (!isOpening && !isClosing) {
                continue;
            }

            System.out.println("Read: " + c);

            if (isOpening) {
                // Opening symbol: push it onto the stack
                stack.push(c);
                System.out.println("Operation: PUSH " + c);
                System.out.println("Stack: " + showStack(stack));

            } else if (stack.isEmpty()) {
                // Closing symbol but nothing to match it with
                System.out.println("Operation: isEmpty -> true");
                reason = "closing delimiter '" + c + "' has no matching opening delimiter (stack is empty)";

            } else {
                // Closing symbol: compare it with the top of the stack
                char top = stack.peek();
                System.out.println("Operation: PEEK -> " + top);

                if (isPair(top, c)) {
                    stack.pop();
                    System.out.println("Operation: POP " + top);
                    System.out.println("Stack: " + showStack(stack));
                } else {
                    reason = "mismatched delimiters: '" + c + "' does not match top '" + top + "'";
                }
            }

            System.out.println();

            // Stop at the first error
            if (!reason.isEmpty()) {
                break;
            }
        }

        // Step 3: If no error yet, check for leftover opening symbols
        if (reason.isEmpty() && !stack.isEmpty()) {
            reason = "unmatched opening delimiter remains: " + showStack(stack);
        }

        // Step 4: Print the result
        System.out.println("Final stack: " + showStack(stack));
        if (reason.isEmpty()) {
            System.out.println("Result: Valid");
        } else {
            System.out.println("Result: Invalid");
            System.out.println("Reason: " + reason);
        }
    }
}