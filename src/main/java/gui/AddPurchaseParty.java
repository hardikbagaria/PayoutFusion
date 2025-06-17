package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.Processes;

public class AddPurchaseParty extends JPanel {
    private JTextField nameField;
    private JButton createPartyButton;

    public AddPurchaseParty() {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Add Purchase Party");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30);
        this.add(titleLabel);

        // Create labels and text fields
        JLabel NameLabel = new JLabel("Name:");
        NameLabel.setBounds(20, 60, 100, 30);
        this.add(NameLabel);
        
        nameField = createTextField(120, 60);

        // Create Party Button
        createPartyButton = new JButton("Create Party");
        createPartyButton.setBounds(920, 629, 150, 40);
        createPartyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        createPartyButton.setFocusPainted(false);
        createPartyButton.setEnabled(false); // Initially disabled

        createPartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameFieldData = nameField.getText().trim();
                if (!nameFieldData.isEmpty()) {
                    try {
                        boolean success = Processes.addPParty(nameFieldData);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Party added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            nameField.setText("");
                            createPartyButton.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Party already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (ClassNotFoundException | SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Error adding party: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                }
            }
        });

        this.add(createPartyButton);

        // Add key listener to enable/disable button and handle Enter key press
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                createPartyButton.setEnabled(!nameField.getText().trim().isEmpty());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && createPartyButton.isEnabled()) {
                    createPartyButton.doClick(); // Simulate button click
                }
            }
        });
    }

    // Helper method to create text fields
    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 350, 30);
        add(field);
        return field;
    }
}
