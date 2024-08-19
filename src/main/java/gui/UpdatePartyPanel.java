package gui;

import javax.swing.*;
import javax.swing.text.*;
import database.Processes;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

public class UpdatePartyPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> dropdown;
    private JTextField address1Field, address2Field, address3Field, gstField, cntPersonField, phoneNoField, destinationField;
    private JButton updatePartyButton, deletePartyButton;

    public UpdatePartyPanel() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Update Party Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30); // x, y, width, height
        this.add(titleLabel);

        // Create and position the dropdown
        ArrayList<String> options = Processes.Names();
        Collections.sort(options);
        String[] optionsArray = new String[options.size()];
        options.toArray(optionsArray);

        dropdown = new JComboBox<>(optionsArray);
        String defaultValue = "--Select Party--";
        dropdown.insertItemAt(defaultValue, 0);
        dropdown.setSelectedIndex(0);
        dropdown.setBounds(20, 60, 300, 30);
        this.add(dropdown);

        // Create labels and text fields
        JLabel address1Label = new JLabel("Address 1:");
        address1Label.setBounds(20, 100, 100, 30);
        this.add(address1Label);
        address1Field = createTextField(120, 100);

        JLabel address2Label = new JLabel("Address 2:");
        address2Label.setBounds(20, 140, 100, 30);
        this.add(address2Label);
        address2Field = createTextField(120, 140);

        JLabel address3Label = new JLabel("Address 3:");
        address3Label.setBounds(20, 180, 100, 30);
        this.add(address3Label);
        address3Field = createTextField(120, 180);

        JLabel gstLabel = new JLabel("GST:");
        gstLabel.setBounds(20, 220, 100, 30);
        this.add(gstLabel);
        gstField = createTextField(120, 220);
        // Allow only numbers and uppercase letters in GST field
        gstField.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 1L;

			@Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str != null && str.matches("[A-Z0-9]+")) {
                    super.insertString(offs, str.toUpperCase(), a);
                }
            }
        });

        JLabel cntPersonLabel = new JLabel("Cnt Person:");
        cntPersonLabel.setBounds(20, 260, 100, 30);
        this.add(cntPersonLabel);
        cntPersonField = createTextField(120, 260);
        JLabel phoneNoLabel = new JLabel("Phone No:");
        phoneNoLabel.setBounds(20, 300, 100, 30);
        this.add(phoneNoLabel);
        phoneNoField = createTextField(120, 300);
        // Allow only numbers in Phone No field, and limit to 10 characters
        phoneNoField.setDocument(new PlainDocument() {
			private static final long serialVersionUID = 1L;

			@Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str != null && str.matches("\\d+")) {
                    if (phoneNoField.getText().length() + str.length() <= 10) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(20, 340, 100, 30);
        this.add(destinationLabel);
        destinationField = createTextField(120, 340);

        // Set up the dropdown action listener
        dropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) dropdown.getSelectedItem();
                if (!selectedItem.equals(defaultValue)) {
                    enableTextFields(true);
                    // Populate text fields
                    try {
                        address1Field.setText(Processes.getAddress1(selectedItem));
                        address2Field.setText(Processes.getAddress2(selectedItem));
                        address3Field.setText(Processes.getAddress3(selectedItem));
                        gstField.setText(Processes.getGST(selectedItem));
                        cntPersonField.setText(Processes.getCntPerson(selectedItem));
                        phoneNoField.setText(Processes.getPhoneNo(selectedItem));
                        destinationField.setText(Processes.getDestination(selectedItem));
                    } catch (ClassNotFoundException | SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    resetFields();
                }
            }
        });

        // Create Update Party and Delete Party buttons
        updatePartyButton = new JButton("Update Party");
        updatePartyButton.setBounds(920, 629, 150, 40);
        updatePartyButton.setBackground(Color.GREEN);
        updatePartyButton.setForeground(Color.WHITE);
        updatePartyButton.setFont(new Font("Arial", Font.BOLD, 16));
        updatePartyButton.setFocusPainted(false); // Remove focus outline
        this.add(updatePartyButton);

        deletePartyButton = new JButton("Delete Party");
        deletePartyButton.setBounds(760, 629, 150, 40);
        deletePartyButton.setBackground(Color.RED);
        deletePartyButton.setForeground(Color.WHITE);
        deletePartyButton.setFont(new Font("Arial", Font.BOLD, 16));
        deletePartyButton.setFocusPainted(false); // Remove focus outline
        this.add(deletePartyButton);
        resetFields(); // Disable text fields initially
    }

    // Helper method to create and disable text fields
    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 350, 30);
        field.setEnabled(false);
        add(field);
        return field;
    }

    // Helper method to enable or disable all text fields
    private void enableTextFields(boolean enable) {
        address1Field.setEnabled(enable);
        address2Field.setEnabled(enable);
        address3Field.setEnabled(enable);
        gstField.setEnabled(enable);
        cntPersonField.setEnabled(enable);
        phoneNoField.setEnabled(enable);
        destinationField.setEnabled(enable);
    }

    // Reset text fields and disable them
    private void resetFields() {
        address1Field.setText("");
        address2Field.setText("");
        address3Field.setText("");
        gstField.setText("");
        cntPersonField.setText("");
        phoneNoField.setText("");
        destinationField.setText("");
        enableTextFields(false);
    }
}