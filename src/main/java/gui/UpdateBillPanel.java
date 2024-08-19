package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import database.Processes;
import extras.NumberOnlyTextField;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class UpdateBillPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTextField totalTaxableValueField;
    private JTextField gstField;
    private JTextField transportationField;
    private JTextField grandTotalField;
	private static final long serialVersionUID = 1L;
	public UpdateBillPanel() throws ClassNotFoundException, SQLException {
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Update Bills Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(10, 10, 300, 30); // x, y, width, height
        this.add(titleLabel);
        
        //Bill dropdown
        ArrayList<String> allBills = Processes.BillNo();
        Collections.sort(allBills);
        String[] optionsArray = new String[allBills.size()];
        allBills.toArray(optionsArray);
        JComboBox<String> comboBox = new JComboBox<String>(optionsArray);
        String defaultValue = "--Select BillNo--";
        comboBox.insertItemAt(defaultValue, 0);
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(10, 50, 200, 30);
        add(comboBox);
        
        // Date
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setBounds(220, 51, dateLabel.getPreferredSize().width, 30);
        this.add(dateLabel);
        DatePickerSettings dateSettings = new DatePickerSettings();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateSettings.setFormatForDatesCommonEra(format);
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setBounds(257, 51, datePicker.getPreferredSize().width, 30);
        this.add(datePicker);
        
        // Party Name
        JLabel partyNameLabel = new JLabel("Party Name:");
        partyNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        partyNameLabel.setBounds(402, 51, partyNameLabel.getPreferredSize().width, 30);
        this.add(partyNameLabel);
        ArrayList<String> allParty = Processes.Names();
        Collections.sort(allParty);
        String[] PartyArray = new String[allParty.size()];
        allParty.toArray(PartyArray);
        JComboBox<String> PartyName = new JComboBox<String>(PartyArray);
        String defaultValueP = "--Select Party--";
        PartyName.insertItemAt(defaultValueP, 0);
        PartyName.setSelectedIndex(0);
        PartyName.setFont(new Font("Arial", Font.PLAIN, 14));
        PartyName.setBounds(489, 50, 150, 30);
        PartyName.setEnabled(false);
        this.add(PartyName);
        
        //Vehicle details
        JLabel VLabel = new JLabel("Vehicle Details:");
        VLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        VLabel.setBounds(673, 51, VLabel.getPreferredSize().width, 26);
        add(VLabel);
        
        JTextField VField = new JTextField(null);
        VField.setBounds(780, 50, 100, 30);
        add(VField);
        
        
        // Information Labels
        JLabel Address1L = new JLabel();
        JLabel Address2L = new JLabel();
        JLabel Address3L = new JLabel();
        JLabel GSTL = new JLabel();
        JLabel CntPersonL = new JLabel();
        JLabel PhoneNoL = new JLabel();
        JLabel DestinationL = new JLabel();
        
        // Style the labels
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = Color.DARK_GRAY;
        for (JLabel lblNewLabel : new JLabel[]{Address1L, Address2L, Address3L, GSTL, CntPersonL, PhoneNoL, DestinationL}) {
            lblNewLabel.setFont(labelFont);
            lblNewLabel.setForeground(labelColor);
        }
        // Add Labels to Panel with Descriptions
        String[] descriptions = {"Address 1:", "Address 2:", "Address 3:", "GST:", "Contact Person:", "Phone No:", "Destination:"};
        JLabel[] labels = {Address1L, Address2L, Address3L, GSTL, CntPersonL, PhoneNoL, DestinationL};
        for (int i = 0; i < descriptions.length; i++) {
            JLabel descLabel = new JLabel(descriptions[i]);
            descLabel.setFont(new Font("Arial", Font.BOLD, 14));
            descLabel.setForeground(Color.BLACK);
            descLabel.setBounds(10, 90 + i * 40, 150, 30); // x, y, width, height
            this.add(descLabel);

            labels[i].setBounds(170, 90 + i * 40, 300, 30); // x, y, width, height
            this.add(labels[i]);
        }
        
        // Items Dropdown
        ArrayList<String> itemsOptions = Processes.Items();
        Collections.sort(itemsOptions);
        String[] itemsOptionsArray = new String[itemsOptions.size()];
        itemsOptions.toArray(itemsOptionsArray);
        JComboBox<String> itemsDropdown = new JComboBox<>(itemsOptionsArray);
        String defaultValueItems = "--Select Item--";
        itemsDropdown.insertItemAt(defaultValueItems, 0);
        itemsDropdown.setSelectedIndex(0);
        itemsDropdown.setBounds(10, 370, 150, 30); // x, y, width, height
        this.add(itemsDropdown);

        // Quantity and Rate Fields
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(170, 370, quantityLabel.getPreferredSize().width, 30);
        this.add(quantityLabel);
        JTextField quantityTextField = new NumberOnlyTextField("");
        quantityTextField.setBounds(240, 370, 100, 30);
        this.add(quantityTextField);

        JLabel rateLabel = new JLabel("Rate:");
        rateLabel.setBounds(350, 370, rateLabel.getPreferredSize().width, 30);
        this.add(rateLabel);
        JTextField rateTextField = new NumberOnlyTextField("");
        rateTextField.setBounds(400, 370, 100, 30);
        this.add(rateTextField);

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setBounds(510, 370, 100, 30); // x, y, width, height
        addItemButton.setEnabled(false);
        this.add(addItemButton);

        // Table for Items	
        tableModel = new DefaultTableModel(new String[] {"Sr.No", "Item Name", "Quantity", "Rate", "Amount", "Remove"},0);
        JTable table = new JTable();
        table.getTableHeader().setResizingAllowed(false); 
        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                if (column != 5) {
                    table.editingStopped(null);
                    boolean isEditing = table.isEditing();
                    if (!isEditing) {
                        JOptionPane.showMessageDialog(null, "You can't edit here");
                    }
                }
            }
        });
        scrollPane.setBounds(10, 410, 1060, 170);
        this.add(scrollPane);

        // Add Button Renderer and Editor for Remove button
        table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
        table.getColumn("Remove").setCellEditor(new ButtonEditor(new JButton("Remove")));

        // Add Item Button Action Listener
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = (String) itemsDropdown.getSelectedItem();
                String quantityText = quantityTextField.getText();
                String rateText = rateTextField.getText();
                StringBuilder errorMessages = new StringBuilder();
                if(itemName == "--Select Item--") {
                	errorMessages.append("No Item Selected. \n");
                	
                }
                if(quantityText.isEmpty()) {
                    errorMessages.append("No Quantity Given. \n");
                	
                }
                if(rateText.isEmpty()) {
                	errorMessages.append("No Rate Given. \n");
                }
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        double rate = Double.parseDouble(rateText);
                        double amount = quantity * rate;

                        // Determine the next serial number
                        int nextSerialNumber = 1;
                        if (tableModel.getRowCount() > 0) {
                            int highestSerialNumber = 1;
                            for (int i = 0; i < tableModel.getRowCount(); i++) {
                                int currentSerialNumber = (Integer) tableModel.getValueAt(i, 0);
                                if (currentSerialNumber > highestSerialNumber) {
                                    highestSerialNumber = currentSerialNumber;
                                }
                            }
                            nextSerialNumber = highestSerialNumber + 1;
                        }

                        // Add row to table
                        tableModel.addRow(new Object[]{nextSerialNumber, itemName, quantity, rate, amount, "Remove"});

                        // Reset input fields
                        itemsDropdown.setSelectedIndex(0);
                        quantityTextField.setText("");
                        rateTextField.setText("");
                        // Update totals
                        updateTotals();
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace(); // Handle number format exception
                    }
                }
        });

        // Total Taxable Value
        JLabel totalTaxableValueLabel = new JLabel("Total Taxable Value:");
        totalTaxableValueLabel.setBounds(10, 591, 150, 30);
        this.add(totalTaxableValueLabel);
        totalTaxableValueField = new JTextField();
        totalTaxableValueField.setBounds(170, 591, 150, 30);
        totalTaxableValueField.setEditable(false);
        this.add(totalTaxableValueField);

        // GST
        JLabel gstLabel = new JLabel("GST@18%:");
        gstLabel.setBounds(330, 591, 100, 30);
        this.add(gstLabel);
        gstField = new JTextField();
        gstField.setBounds(430, 591, 100, 30);
        gstField.setEditable(false);
        this.add(gstField);

        // Transportation
        JLabel transportationLabel = new JLabel("Transportation:");
        transportationLabel.setBounds(540, 591, 120, 30);
        this.add(transportationLabel);
        transportationField = new JTextField("");
        transportationField.setBounds(670, 591, 100, 30);
        transportationField.setEditable(false);
        this.add(transportationField);

        // Grand Total
        JLabel grandTotalLabel = new JLabel("Grand Total:");
        grandTotalLabel.setBounds(780, 591, 100, 30);
        this.add(grandTotalLabel);
        grandTotalField = new JTextField();
        grandTotalField.setBounds(890, 591, 100, 30);
        grandTotalField.setEditable(false);
        this.add(grandTotalField);
        transportationField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotals();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotals();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotals();
            }
        });
        // Dropdown Bill Listener
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if (e.getStateChange() == ItemEvent.SELECTED) {
            		if((String) comboBox.getSelectedItem() != "--Select BillNo--") {
	            		 try {
	            			addItemButton.setEnabled(true);
	            	        transportationField.setEditable(true);
	                    	DefaultTableModel model = (DefaultTableModel) table.getModel();
	                        model.setRowCount(0);
	            			int selectedItem = Integer.parseInt((String) comboBox.getSelectedItem());
	            			PartyName.setEnabled(true);
	            			String name = Processes.getName(selectedItem);
							PartyName.setSelectedItem(name);
							String name1 = PartyName.getSelectedItem().toString();
							String date = Processes.getDate(selectedItem);
	            	        datePicker.setDate(LocalDate.parse(date,format));
	                        VField.setText(Processes.getVDetails(selectedItem));
							if(name1 != "--Select Party--") {
		            	        Address1L.setText(Processes.getAddress1(name1));
		                        Address2L.setText(Processes.getAddress2(name1));
		                        Address3L.setText(Processes.getAddress3(name1));
		                        GSTL.setText(Processes.getGST(name1));
		                        CntPersonL.setText(Processes.getCntPerson(name1));
		                        PhoneNoL.setText(Processes.getPhoneNo(name1));
		                        DestinationL.setText(Processes.getDestination(name1));
							}
	                        transportationField.setText(Processes.getTDetails(selectedItem));
	                        // Add row to table
	                        ResultSet rs = Processes.resultSet(selectedItem);
	                        while (rs.next()) {
	                        	int SrNo = rs.getInt("SrNo");
	                            String itemName = rs.getString("ItemName"); // Retrieve the ItemName column
	                            int quantity = (int) Math.round(rs.getDouble("Quantity"));       // Retrieve the Quantity column
	                            double rate = rs.getDouble("Rate");         // Retrieve the Rate column
	                            double amount = rs.getDouble("Amount");     // Retrieve the Amount column
	                            
	                            // Add a new row to the table model
	                            tableModel.addRow(new Object[]{SrNo, itemName, quantity, rate, amount, "Remove"});
	                        }
	            		 } catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}	 
            		}else {
            			addItemButton.setEnabled(false);
            	        transportationField.setEditable(false);
            	        PartyName.setEnabled(false);
            			datePicker.clear();
            			PartyName.setSelectedIndex(0);
            			VField.setText("");
            			Address1L.setText("");
            			Address2L.setText("");
            			Address3L.setText("");
            			GSTL.setText("");
            			CntPersonL.setText("");
            			PhoneNoL.setText("");
            			DestinationL.setText("");
            	        transportationField.setText("");
                    	DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0);
            		}
            	}
            }    
        });
        PartyName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				String name1 = PartyName.getSelectedItem().toString();
				if(name1 != "--Select Party--") {
        	        try {
						Address1L.setText(Processes.getAddress1(name1));
	                    Address2L.setText(Processes.getAddress2(name1));
	                    Address3L.setText(Processes.getAddress3(name1));
	                    GSTL.setText(Processes.getGST(name1));
	                    CntPersonL.setText(Processes.getCntPerson(name1));
	                    PhoneNoL.setText(Processes.getPhoneNo(name1));
	                    DestinationL.setText(Processes.getDestination(name1));
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
				}else {
        			Address1L.setText("");
        			Address2L.setText("");
        			Address3L.setText("");
        			GSTL.setText("");
        			CntPersonL.setText("");
        			PhoneNoL.setText("");
        			DestinationL.setText("");
				}
            	
            	
            }   
        });
        // Table Model Listener to update totals
        tableModel.addTableModelListener(e -> updateTotals());
        
        // Creating Bill
        JButton updateBillButton = new JButton("Update Bill");
        updateBillButton.setBounds(920, 629, 150, 40);
        updateBillButton.setBackground(Color.GREEN);
        updateBillButton.setForeground(Color.WHITE);
        updateBillButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateBillButton.setFocusPainted(false); // Remove focus outline
        this.add(updateBillButton);
        updateBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateTotals();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                StringBuilder errorMessages = new StringBuilder();
                // Check if table is empty
                if (model.getRowCount() == 0) {
                    errorMessages.append("No data items added. \n");
                }

                // Check if selected item is valid
                String selectedItem = (String) PartyName.getSelectedItem();
                if ("--Select Party--".equals(selectedItem)) {
                    errorMessages.append("Please select a valid Party.\n");
                }

                // Check if date is selected
                LocalDate date = datePicker.getDate();
                if (date == null) {
                    errorMessages.append("Please select a date.\n");
                }

                // If there are validation errors, show them in a single dialog
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Proceed with the normal processing if no errors
                try {
                	int Bno = Integer.parseInt(comboBox.getSelectedItem().toString());
                	Processes.removeBill(Bno);
                    for (int row = 0; row < model.getRowCount(); row++) {
                        int srNo = Integer.parseInt(model.getValueAt(row, 0).toString());
                        String itemName = model.getValueAt(row, 1).toString();
                        double quantity = Double.parseDouble(model.getValueAt(row, 2).toString());
                        double rate = Double.parseDouble(model.getValueAt(row, 3).toString());
                        double amount = Double.parseDouble(model.getValueAt(row, 4).toString());
                        Processes.createBill(Bno, srNo, itemName, quantity, rate, amount);
                    }
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String VDetails = VField.getText();
                    Double TaxableAmount = Double.parseDouble(totalTaxableValueField.getText());
                    Double gst = Double.parseDouble(gstField.getText());
                    Double Total = Double.parseDouble(grandTotalField.getText());
                    Double Transportation = Double.parseDouble(transportationField.getText());
                    Processes.cBill(Bno, selectedItem, formattedDate, VDetails, TaxableAmount, gst, Transportation, Total);

                    JOptionPane.showMessageDialog(null, 
                            "Bill Updated successfully!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    model.setRowCount(0);
                    PartyName.setSelectedIndex(0);
                    PartyName.setEnabled(false);
                    comboBox.setSelectedIndex(0);
                    datePicker.setDate(null);
                    transportationField.setText(null);
                    VField.setText(null);
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                            "An error occurred while creating the bill. Please try again.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, 
                            "Please ensure all numerical fields are correctly filled.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });        
        JButton delateButton = new JButton("Delate");
        delateButton.setBounds(760, 629, 150, 40);
        delateButton.setBackground(Color.RED);
        delateButton.setForeground(Color.WHITE);
        delateButton.setFont(new Font("Arial", Font.BOLD, 16));
        delateButton.setFocusPainted(false); // Remove focus outline
        this.add(delateButton);
        delateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox.getSelectedItem();
                StringBuilder errorMessages1 = new StringBuilder();
                if ("--Select BillNo--".equals(selectedItem)) {
                    errorMessages1.append("Please select a valid Bill.\n");
                }
                if (errorMessages1.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages1.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Display a confirmation dialog with "Yes" and "No" options
                int result = JOptionPane.showConfirmDialog(null, 
                                "Do you want to delete Bill No "+ selectedItem + "?", 
                                "Delete Confirmation", 
                                JOptionPane.YES_NO_OPTION, 
                                JOptionPane.QUESTION_MESSAGE);
                // Check user's choice
                if (result == JOptionPane.YES_OPTION) {
                	int Bno = Integer.parseInt(comboBox.getSelectedItem().toString());
                    try {
						Processes.removeBill(Bno);
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
                	DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    PartyName.setSelectedIndex(0);
                    PartyName.setEnabled(false);
                    Object item = comboBox.getSelectedItem();
                    comboBox.setSelectedItem(defaultValue);
                    comboBox.removeItem(item);
                    datePicker.setDate(null);
                    transportationField.setText(null);
                    VField.setText(null);
                    itemsDropdown.setSelectedIndex(0);
                    quantityTextField.setText("");
                    rateTextField.setText("");
                }
            }    
        });

	}
	private void updateTotals() {
        double totalTaxableValue = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double amount = (Double) tableModel.getValueAt(i, 4);
            totalTaxableValue += amount;
        }
        totalTaxableValueField.setText(String.format("%.2f", totalTaxableValue));

        double gst = totalTaxableValue * 0.18;
        gstField.setText(String.format("%.2f", gst));

        double transportation = 0;
        try {
            transportation = Double.parseDouble(transportationField.getText());
        } catch (NumberFormatException ex) {
            transportation = 0;
        }

        double grandTotal = totalTaxableValue + gst + transportation;
        grandTotalField.setText(String.format("%.2f", grandTotal));
    }

    // Button Renderer class
    class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 6068091560196771005L;

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Button Editor class
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private static final long serialVersionUID = 1L;
        private JButton button;
        private int selectedRow;
        private JTable table;

        public ButtonEditor(JButton button) {
            this.button = button;
            this.button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table; // Store the table reference
            this.selectedRow = row;
            this.button.setText((value == null) ? "" : value.toString());
            return this.button;
        }
        public Object getCellEditorValue() {
            return this.button.getText();
        }

        public void actionPerformed(ActionEvent e) {
        	if (table.isEditing()) {
        		table.getCellEditor().stopCellEditing();
        	}
            if (table != null) {
                tableModel.removeRow(this.selectedRow);

                // Update Sr.No values only if there are rows left
                if (tableModel.getRowCount() > 0) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        tableModel.setValueAt(i + 1, i, 0);
                    }
                }

                updateTotals();
            }
            fireEditingStopped();
        }
    }
}
