import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số ISBN (10 chữ số): ");
        String check = scanner.nextLine();

        if (check.length() != 10) {
            System.out.println("Số ISBN phải có đúng 10 chữ số.");
            return;
        }
        Stack<Integer> digits = new Stack<>();

        for (int i = 0; i < check.length(); i++) {
            char digitChar = check.charAt(i);
            if (!Character.isDigit(digitChar)) {
                System.out.println("Số ISBN chỉ được chứa các chữ số.");
                return;
            }
            int digit = Character.getNumericValue(digitChar);
            digits.push(digit);
        }

        int sum = 0;
        int yasua = 1;
        while (!digits.isEmpty()) {
            int digit = digits.pop();
            sum += yasua * digit;
            yasua++;
        }

        if (sum % 11 == 0) {
            System.out.println("Số ISBN hợp lệ.");
        } else {
            System.out.println("Số ISBN không hợp lệ.");
        }
    }
}