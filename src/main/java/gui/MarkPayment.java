package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import database.Processes;
import extras.NumberOnlyTextField;

public class MarkPayment extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextField paidAmtField, remarksField;
	public JButton button;
	public JLabel OAmt;
	public DatePicker datePicker;
	public MarkPayment() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Mark Payment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30); // x, y, width, height
        this.add(titleLabel);
        
        // Dropdown
        ArrayList<String> options = Processes.Names();
        Collections.sort(options);
        String[] optionsArray = new String[options.size()];
        options.toArray(optionsArray);
        JComboBox<String> dropdown = new JComboBox<>(optionsArray);
        String defaultValue = "--Select Party--";
        dropdown.insertItemAt(defaultValue, 0);
        dropdown.setSelectedIndex(0);
        dropdown.setBounds(10, 50, 200, 30); // x, y, width, height
        this.add(dropdown);
        
        dropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                	String name = dropdown.getSelectedItem().toString();
                	if(name == "--Select Party--") {
                		paidAmtField.setText("0");
                		OAmt.setText("0");
                		set(false);
                		
                		
                		
                		
                		
                		
                	}else {
                		try {
                			String amt = Processes.getOAmt(name).toString();
							paidAmtField.setText(amt);
							OAmt.setText(amt);
							set(true);
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
                		
                	}
                }
            }
        });
        JLabel OAmtLabel = new JLabel("Outstanding Amount:");
        OAmtLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        OAmtLabel.setForeground(Color.BLACK);
        OAmtLabel.setBounds(796, 539, OAmtLabel.getPreferredSize().width, OAmtLabel.getPreferredSize().height);
        this.add(OAmtLabel);
        OAmt = new JLabel("0");
        OAmt.setFont(new Font("Arial", Font.PLAIN, 18));
        OAmt.setForeground(Color.RED);
        OAmt.setBounds(973, 539, 100, 22);
        this.add(OAmt);
        
        JLabel paidAmtLabel = new JLabel("Amount Paid:");
        paidAmtLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        paidAmtLabel.setForeground(Color.BLACK);
        paidAmtLabel.setBounds(796, 572, paidAmtLabel.getPreferredSize().width, paidAmtLabel.getPreferredSize().height);
        this.add(paidAmtLabel);
        paidAmtField = new NumberOnlyTextField("");
        paidAmtField.setFont(new Font("Arial", Font.PLAIN, 18));
        paidAmtField.setForeground(Color.RED);
        paidAmtField.setBounds(970, 572, 100, 22);
        paidAmtField.setDisabledTextColor(new Color(255, 102, 102));
        paidAmtField.setEnabled(false);
        this.add(paidAmtField);
        
        JLabel remarksLabel = new JLabel("Remarks:");
        remarksLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        remarksLabel.setForeground(Color.BLACK);
        remarksLabel.setBounds(796, 605, remarksLabel.getPreferredSize().width, remarksLabel.getPreferredSize().height);
        this.add(remarksLabel);
        
        remarksField = new JTextField("");
        remarksField.setFont(new Font("Arial", Font.PLAIN, 18));
        remarksField.setForeground(Color.BLACK);
        remarksField.setBounds(970, 605, 100, 22);
        remarksField.setEnabled(false);
        this.add(remarksField);
        // Date
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setBounds(220, 51, dateLabel.getPreferredSize().width, 30);
        this.add(dateLabel);
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        datePicker = new DatePicker(dateSettings);
        datePicker.setBounds(257, 51, datePicker.getPreferredSize().width, 30);
        datePicker.getComponentDateTextField().setEnabled(false);
        datePicker.setDateToToday();
        this.add(datePicker);
        
        button = new JButton("Mark Payment");
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.BLACK);
        button.setBounds(923, 638, button.getPreferredSize().width, button.getPreferredSize().height);
        this.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder errorMessages = new StringBuilder();
                
                // Values
                String paidAmtText = paidAmtField.getText();
                String outstandingAmtText = OAmt.getText();
                String remarksText = remarksField.getText();
                // Check if the fields are empty
                if (paidAmtText.isEmpty()) {
                    errorMessages.append("Paid Amount cannot be empty.\n");
                }
                if (outstandingAmtText.isEmpty()) {
                    errorMessages.append("Outstanding Amount cannot be empty.\n");
                }

                // Only parse the amounts if the fields are not empty
                int amtPaid = 0;
                int outstandingAmt = 0;
                
                if (errorMessages.length() == 0) {
                    try {
                        amtPaid = Integer.parseInt(paidAmtText);
                        outstandingAmt = Integer.parseInt(outstandingAmtText);
                    } catch (NumberFormatException ex) {
                        errorMessages.append("Please enter valid numeric values for amounts.\n");
                    }
                }
                
                final LocalDate date = datePicker.getDate();
                final String selectedItem = (String) dropdown.getSelectedItem();
                
                // Validation
                if ("--Select Party--".equals(selectedItem)) {
                    errorMessages.append("Please select a valid Party.\n");
                } if (date == null) {
                    errorMessages.append("Please select a date.\n");
                } if (amtPaid > outstandingAmt) {
                    errorMessages.append("Amount Paid cannot be greater than Outstanding Amount.\n");
                } if (amtPaid == 0) {
                    errorMessages.append("Amount Paid cannot be 0.\n");
                }
                
                // If there are validation errors, show them in a single dialog
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Process the valid data
                try {
                	Processes.markPayment(selectedItem, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), amtPaid, remarksText);
                    Processes.OAmtUpdate(selectedItem, amtPaid);
                    dropdown.setSelectedIndex(0);
                    set(false);
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                            "An error occurred while processing your request.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }  
        });


	}
	public void set(Boolean value) {
		paidAmtField.setEnabled(value);
        remarksField.setEnabled(value);
        remarksField.setText("");
	}
}
