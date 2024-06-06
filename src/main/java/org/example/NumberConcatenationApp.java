package chat6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberConcatenationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Number Concatenation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            JTextField numberField1 = new JTextField(10);
            JTextField numberField2 = new JTextField(10);
            JLabel resultLabel = new JLabel();

            JButton concatButton = new JButton("결과");
            concatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String num1 = numberField1.getText();
                    String num2 = numberField2.getText();
                    String result = num1 + num2;
                    resultLabel.setText("결과: " + result);
                }
            });

            panel.add(numberField1);
            panel.add(numberField2);
            panel.add(concatButton);
            panel.add(resultLabel);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}