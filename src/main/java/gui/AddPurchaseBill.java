// File: src/main/java/gui/AddPurchaseBill.java

package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

import database.Processes;
import extras.NumberOnlyTextField;

public class AddPurchaseBill extends JPanel {
    private JComboBox<String> dropdown;
    private DefaultComboBoxModel<String> model;
    private JTextField amountField, remarkField;
    private JButton addButton;
    private JTextField editorComponent;
    private final String PLACEHOLDER_TEXT = "--Select Party--";

    public AddPurchaseBill() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1080, 680));

        // Title Label
        JLabel titleLabel = new JLabel("Add Purchase Bill");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30);
        this.add(titleLabel);

        // Dropdown with Editable Input
        model = new DefaultComboBoxModel<>(getPartyList());
        dropdown = new JComboBox<>(model);
        dropdown.setEditable(true);
        dropdown.setBounds(10, 50, 200, 30);
        this.add(dropdown);

        // Get Editor Component (TextField inside JComboBox)
        editorComponent = (JTextField) dropdown.getEditor().getEditorComponent();
        editorComponent.setForeground(Color.GRAY);
        editorComponent.setText(PLACEHOLDER_TEXT);

        // Placeholder Effect
        editorComponent.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (editorComponent.getText().equals(PLACEHOLDER_TEXT)) {
                    editorComponent.setText("");
                    editorComponent.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (editorComponent.getText().trim().isEmpty()) {
                    editorComponent.setText(PLACEHOLDER_TEXT);
                    editorComponent.setForeground(Color.GRAY);
                }
            }
        });

        // Amount Label
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        amountLabel.setBounds(810, 531, 100, 30);
        this.add(amountLabel);

        // Amount Field (Only Numbers)
        amountField = new NumberOnlyTextField();
        amountField.setBounds(920, 527, 150, 40);
        this.add(amountField);

        // Remark Label
        JLabel remarkLabel = new JLabel("Remark:");
        remarkLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        remarkLabel.setBounds(810, 582, 100, 30);
        this.add(remarkLabel);

        // Remark Field
        remarkField = new JTextField();
        remarkField.setBounds(920, 578, 150, 40);
        this.add(remarkField);

        // Add Button (Initially Disabled)
        addButton = new JButton("Add Bill");
        addButton.setBounds(920, 629, 150, 40);
        addButton.setEnabled(false);
        this.add(addButton);

        // Input Validation
        editorComponent.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                validateInput();
            }
        });

        dropdown.addActionListener(e -> validateInput());

        addButton.addActionListener(e -> handleAddButtonClick());
    }

    private String[] getPartyList() throws ClassNotFoundException, SQLException {
        ArrayList<String> options = Processes.getPParty();
        Collections.sort(options);
        return options.toArray(new String[0]);
    }

    private void validateInput() {
        String selectedParty = editorComponent.getText().trim();
        boolean isValid = !selectedParty.isEmpty() && !selectedParty.equals(PLACEHOLDER_TEXT);
        addButton.setEnabled(isValid);
    }

    private void handleAddButtonClick() {
        String selectedParty = editorComponent.getText().trim();

        if (!selectedParty.isEmpty() && !selectedParty.equals(PLACEHOLDER_TEXT) && model.getIndexOf(selectedParty) == -1) {
            try {
                Processes.addPParty(selectedParty); // Add to database
                model.addElement(selectedParty); // Add to dropdown
                dropdown.setSelectedItem(selectedParty);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Clear fields and reset dropdown
        amountField.setText("");
        remarkField.setText("");
        editorComponent.setText(PLACEHOLDER_TEXT);
        editorComponent.setForeground(Color.GRAY);
        validateInput();
    }
}
