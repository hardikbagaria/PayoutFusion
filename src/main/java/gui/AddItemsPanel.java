package gui;

import javax.swing.*;
import javax.swing.text.*;
import database.Processes;
import extras.NumberOnlyTextField;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddItemsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField ItemNameField, HSNCodeField;
    private JButton addItemButton;
	public AddItemsPanel() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Add Items ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30); // x, y, width, height
        this.add(titleLabel);

        // Create labels and text fields
        JLabel ItemNameLabel = new JLabel("Name:");
        ItemNameLabel.setBounds(20, 60, 100, 30);
        this.add(ItemNameLabel);
        ItemNameField = createTextField(120, 60);
        
        JLabel HSNCodeLabel = new JLabel("HSN Code:");
        HSNCodeLabel.setBounds(20, 100, 100, 30);
        this.add(HSNCodeLabel);
        HSNCodeField = new NumberOnlyTextField();
        HSNCodeField.setBounds(120, 100, 350, 30);
        this.add(HSNCodeField);
        
        JLabel PerLabel = new JLabel("Type:");
        PerLabel.setBounds(20, 140, 100, 30); 
        this.add(PerLabel);
        String[] units = {"--Select Type--","ltr", "kg"};
        JComboBox<String> comboBox = new JComboBox<>(units);
        comboBox.setBounds(120, 140, 350, 30);
        this.add(comboBox);
        comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem().toString() != "--Select Type--") {
					addItemButton.setEnabled(true);
					
				}else {
					addItemButton.setEnabled(false);
					
				}
			}
        	
        	
        });
        // Create Update Party and Delete Party buttons
        addItemButton = new JButton("Add Item");
        addItemButton.setBounds(800, 300, 150, 40);
        addItemButton.setEnabled(false);
        addItemButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addItemButton.setFocusPainted(false); // Remove focus outline
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String ItemNameFieldData = ItemNameField.getText();
                String HSNCodeFieldData = HSNCodeField.getText();
                StringBuilder errorMessages = new StringBuilder();
                if(ItemNameFieldData.equals("")) {
                	errorMessages.append("Item Name is Required. \n");
                }
                if(HSNCodeFieldData.equals("")) {
                	errorMessages.append("HSN Code Is Required. \n");                	
                }
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int HSNValue = Integer.parseInt(HSNCodeFieldData);
                
                try {
					Processes.addItem(ItemNameFieldData, HSNValue, comboBox.getSelectedItem().toString());
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
                JOptionPane.showMessageDialog(null, "Item Added Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
				resetFields();
            }    
        });
        this.add(addItemButton);
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
    	ItemNameField.setText("");
    	HSNCodeField.setText("");
    }
}
