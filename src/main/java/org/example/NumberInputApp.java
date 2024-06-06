package chat6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberInputApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Number Input");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            JButton helloButton = new JButton("음료구매");
            helloButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().removeAll();
                    frame.add(createNumberInputPanel());
                    frame.revalidate();
                    frame.repaint();
                }
            });
            panel.add(helloButton);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private static JPanel createNumberInputPanel() {
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

        return panel;
    }
}