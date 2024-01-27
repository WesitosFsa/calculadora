import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {

    private JTextField display;

    private double num1, num2, result;
    private String operator;

    public Calculadora() {

        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(4, 3));

        JPanel functionsPanel = new JPanel();
        functionsPanel.setLayout(new GridLayout(5, 3));

        String[] numberLabels = {
                "7", "8", "9",
                "4", "5", "6",
                "1", "2", "3",
                "0", ".", "="
        };

        for (String label : numberLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            numbersPanel.add(button);
        }

        String[] functionLabels = {
                "/", "*", "-", "+",
                "^", "%", "sin", "cos", "tan",
                "log", "sqrt", "exp", "!", "ln"
        };

        for (String label : functionLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            functionsPanel.add(button);
        }

        add(numbersPanel, BorderLayout.CENTER);
        add(functionsPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();
            processCommand(command);
        }
    }

    private void processCommand(String command) {
        switch (command) {
            case "=":
                calculate();
                break;
            case "/":
            case "*":
            case "-":
            case "+":
            case "^":
            case "%":
            case "log":
            case "sqrt":
            case "exp":
            case "!":
            case "ln":
                setOperator(command);
                break;
            case "C":
                clear();
                break;
            case "sin":
                applyTrigonometricFunction("sin");
                break;
            case "cos":
                applyTrigonometricFunction("cos");
                break;
            case "tan":
                applyTrigonometricFunction("tan");
                break;
            default:
                appendDigit(command);
                break;
        }
    }

    private void appendDigit(String digit) {
        String currentText = display.getText();
        display.setText(currentText + digit);
    }

    private void setOperator(String op) {
        operator = op;
        if (op.equals("log") || op.equals("sqrt") || op.equals("exp") || op.equals("!") || op.equals("ln")) {
            num1 = Double.parseDouble(display.getText());
            calculate();
        } else {
            num1 = Double.parseDouble(display.getText());
            display.setText("");
        }
    }

    private void calculate() {
        if (operator.equals("log")) {
            result = Math.log(num1);
        } else if (operator.equals("sqrt")) {
            result = Math.sqrt(num1);
        } else if (operator.equals("exp")) {
            result = Math.exp(num1);
        } else if (operator.equals("!")) {
            result = calculateFactorial(num1);
        } else if (operator.equals("ln")) {
            result = Math.log(num1);
        } else {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    break;
                case "%":
                    result = num1 % num2;
                    break;
            }
        }

        display.setText(String.valueOf(result));
    }

    private double calculateFactorial(double n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * calculateFactorial(n - 1);
        }
    }

    private void clear() {
        display.setText("");
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = null;
    }

    private void applyTrigonometricFunction(String function) {
        double value = Double.parseDouble(display.getText());

        switch (function) {
            case "sin":
                result = Math.sin(Math.toRadians(value));
                break;
            case "cos":
                result = Math.cos(Math.toRadians(value));
                break;
            case "tan":
                result = Math.tan(Math.toRadians(value));
                break;
        }

        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora());
    }
}
