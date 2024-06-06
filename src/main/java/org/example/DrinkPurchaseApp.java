import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrinkPurchaseApp {
    private JFrame frame;
    private JPanel panel;
    private JTextField inputField1;
    private JTextField inputField2;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DrinkPurchaseApp().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("음료 구매");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JButton purchaseButton = new JButton("음료 구매");
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 숫자 입력 화면으로 이동
                frame.getContentPane().removeAll();
                frame.add(panel);
                frame.revalidate();
                frame.repaint();
            }
        });

        JLabel label1 = new JLabel("숫자 1:");
        inputField1 = new JTextField();
        JLabel label2 = new JLabel("숫자 2:");
        inputField2 = new JTextField();

        JButton resultButton = new JButton("결과");
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 결과 화면으로 이동 (구매 완료 화면)
                JOptionPane.showMessageDialog(frame, "구매 완료!");
                // 홈 화면으로 이동 (음료 구매 화면)
                frame.getContentPane().removeAll();
                frame.add(panel);
                frame.revalidate();
                frame.repaint();
            }
        });

        panel.add(purchaseButton);
        panel.add(new JLabel()); // 빈 라벨
        panel.add(label1);
        panel.add(inputField1);
        panel.add(label2);
        panel.add(inputField2);
        panel.add(resultButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}