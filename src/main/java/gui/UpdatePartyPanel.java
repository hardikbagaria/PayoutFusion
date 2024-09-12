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
    private JTextField address1Field, address2Field, address3Field, gstField, cntPersonField, phoneNoField,emailField, destinationField;
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
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 340, 100, 30);
        this.add(emailLabel);
        emailField = createTextField(120,340);
        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(20, 380, 100, 30);
        this.add(destinationLabel);
        destinationField = createTextField(120, 380);

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
                        emailField.setText(Processes.getEmail(selectedItem));
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
        updatePartyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updatePartyButton.setFocusPainted(false); // Remove focus outline
        updatePartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String Name = (String) dropdown.getSelectedItem();
                String address1FieldData = address1Field.getText();
                String address2FieldData = address2Field.getText();
                String address3FieldData = address3Field.getText();
                String gstFieldData = gstField.getText();
                String cntPersonFieldData = cntPersonField.getText();
                String phoneNoFieldData = phoneNoField.getText();
                String emailFieldData = emailField.getText();
                String destinationFieldData =  destinationField.getText();
                StringBuilder errorMessages = new StringBuilder();
                if(Name.equals("--Select Party--")) {
                	errorMessages.append("Selected Party is Not Valid");
                }
                if(address1FieldData.equals("")) {
                	errorMessages.append("Address Line 1 is Required. \n");                	
                }
                if(address2FieldData.equals("")) {
                	errorMessages.append("Address Line 2 is Required. \n");                	
                }
                if(gstFieldData.equals("") || gstFieldData.length() != 15) {
                	errorMessages.append("GST Number is Required. \n");                	
                }
                if(phoneNoFieldData.length() != 10) {
                	errorMessages.append("Phone Number Not Valid");
                }
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //Proceed With Normal Updation
                try {
                	Processes.updateParty(Name, address1FieldData, address2FieldData, address3FieldData, gstFieldData, cntPersonFieldData, phoneNoFieldData, emailFieldData, destinationFieldData);
                	JOptionPane.showMessageDialog(null, "Party Updated Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                	resetFields();
                } catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
            }    
        });
        this.add(updatePartyButton);

        deletePartyButton = new JButton("Delete Party");
        deletePartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String Name = (String) dropdown.getSelectedItem();
            	try {
					Processes.delateParty(Name);
					dropdown.removeItem(Name);
					JOptionPane.showMessageDialog(null, "Party "+Name+" Delate Successfully.", "Delate Successful",JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
            	resetFields();
            }    
        });
        deletePartyButton.setBounds(760, 629, 150, 40);
        deletePartyButton.setFont(new Font("Arial", Font.PLAIN, 16));
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
        emailField.setEnabled(enable);
        destinationField.setEnabled(enable);
        updatePartyButton.setEnabled(enable);
        deletePartyButton.setEnabled(enable);
    }

    // Reset text fields and disable them
    private void resetFields() {
    	dropdown.setSelectedIndex(0);
        address1Field.setText("");
        address2Field.setText("");
        address3Field.setText("");
        gstField.setText("");
        cntPersonField.setText("");
        phoneNoField.setText("");
        emailField.setText("");
        destinationField.setText("");
        enableTextFields(false);
    }
}
