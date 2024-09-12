package gui;

import javax.swing.*;
import javax.swing.text.*;
import database.Processes;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class CreatePartyPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField nameField, address1Field, address2Field, address3Field, gstField, cntPersonField, phoneNoField,emailField, destinationField;
    private JButton createPartyButton, clearButton;

    public CreatePartyPanel() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Add Party ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30); // x, y, width, height
        this.add(titleLabel);

        // Create labels and text fields
        JLabel NameLabel = new JLabel("Name:");
        NameLabel.setBounds(20, 60, 100, 30);
        this.add(NameLabel);
        nameField = createTextField(120, 60);
        
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

        // Create Update Party and Delete Party buttons
        createPartyButton = new JButton("Create Party");
        createPartyButton.setBounds(920, 629, 150, 40);
        createPartyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        createPartyButton.setFocusPainted(false); // Remove focus outline
        createPartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nameFieldData = nameField.getText();
                String address1FieldData = address1Field.getText();
                String address2FieldData = address2Field.getText();
                String address3FieldData = address3Field.getText();
                String gstFieldData = gstField.getText();
                String cntPersonFieldData = cntPersonField.getText();
                String phoneNoFieldData = phoneNoField.getText();
                String emailFieldData = emailField.getText();
                String destinationFieldData =  destinationField.getText();
                StringBuilder errorMessages = new StringBuilder();
                if(nameFieldData.equals("")) {
                	errorMessages.append("Party Name is Required. \n");
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
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //Proceed With Normal Updation
                try {
                	Processes.addParty(nameFieldData, address1FieldData, address2FieldData, address3FieldData, gstFieldData, cntPersonFieldData, phoneNoFieldData, emailFieldData, destinationFieldData);
                	JOptionPane.showMessageDialog(null, "Party Added Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                	resetFields(); 
                } catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
            }    
        });
        this.add(createPartyButton);

        clearButton = new JButton("Clear Details");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {	
            	resetFields();
            }    
        });
        clearButton.setBounds(760, 629, 150, 40);
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.setFocusPainted(false); // Remove focus outline
        this.add(clearButton);
        }

    // Helper method to create and disable text fields
    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 350, 30);
        add(field);
        return field;
    }

    // Reset text fields and disable them
    private void resetFields() {
    	nameField.setText("");
        address1Field.setText("");
        address2Field.setText("");
        address3Field.setText("");
        gstField.setText("");
        cntPersonField.setText("");
        phoneNoField.setText("");
        emailField.setText("");
        destinationField.setText("");
    }
}
