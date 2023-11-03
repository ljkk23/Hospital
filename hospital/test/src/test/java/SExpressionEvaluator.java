import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class SExpressionEvaluator {
    public static void main(String[] args) {
        String[] expressions = {

                "(-(*(+ 1 2)2)3 1)",
                "(+(*(- 300 (-90 12 3) 1 2 3) 12 2) 1)",
                "(- 890 18 23 (* 1 2 3 (+ 1288 32)))"
        };

        for (String expression : expressions) {
            int result = evaluateSExpression(expression);
            System.out.println(result);
        }
    }

    public static int evaluateSExpression(String expression) {
        Stack<Character> operators = new Stack<>();
        Deque<Integer> operands = new LinkedList<>();
        String test="asdf";

        int[] ints = evaluateSExpression2(0, expression);
        return ints[0];

    }

    private static int[] evaluateSExpression2(int start, String expression) {
        Stack<Character> operators = new Stack<>();
        Deque<Integer> operands = new LinkedList<>();
        operators.push('(');
        int[] res=new int[2];
        for (int i = start; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue;
            }

            if (c == '(') {
                operators.push(c);
                int[] tmp=evaluateSExpression2(i+1,expression);
                operands.offerLast(tmp[0]);
                i=tmp[1]-1;
                operators.pop();
            } else if (Character.isDigit(c)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                operands.offerLast(num);
                i--; // Decrement i to account for the extra increment in the loop
            } else if (c == '+' || c == '-' || c == '*') {
                operators.push(c);
            } else if (c == ')') {
                int result = 0;
                while (operators.peek() != '(' && operands.size()!=1) {
                    result=applyOperator(operators.peek(), operands.pollFirst(), operands.pollFirst());
                    operands.offerFirst(result);
                }
                //弹出运算符
                operators.pop();
                //弹出括号
                operators.pop(); // Discard the opening parenthesis
                res[0]=result;
                res[1]=i+1;
                return res;
            }
        }
        res[0]=operands.pop();
        return res;
    }

    private static int applyOperator(char operator, int a, int b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
        }
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }
}