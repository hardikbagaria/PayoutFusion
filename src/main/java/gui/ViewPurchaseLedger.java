package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewPurchaseLedger extends JPanel{
	public ViewPurchaseLedger() {
		// Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning
        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed
        // Title Label
        JLabel titleLabel = new JLabel("View Purchase Ledger");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30);
        this.add(titleLabel);

	}
}
