package Main;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Hello World App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Create the label
        JLabel label = new JLabel("Hello, World!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        // Use a layout that centers the label
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make it visible
        frame.setVisible(true);
    }
}

