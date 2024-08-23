package gui;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import pdf.create.BillGenGST;
import pdf.create.BillGenGSTD;
import pdf.create.BillGenIGST;
import pdf.create.BillGenIGSTD;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
public class BillPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private JTextField totalTaxableValueField;
    private JTextField gstField;
    private JTextField transportationField;
    private JTextField grandTotalField;
    public BillPanel() throws ClassNotFoundException, SQLException {
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Create Bills Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(10, 10, 300, 30); // x, y, width, height
        this.add(titleLabel);

        // Date
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setBounds(220, 51, dateLabel.getPreferredSize().width, 30);
        this.add(dateLabel);
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DatePicker datePicker = new DatePicker(dateSettings);
        datePicker.setBounds(257, 51, datePicker.getPreferredSize().width, 30);
        datePicker.setDateToToday();
        this.add(datePicker);
        
        // Bill No
        JLabel BillNoLabel = new JLabel("Bill No:");
        BillNoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        BillNoLabel.setBounds(402, 51, BillNoLabel.getPreferredSize().width, 30);
        this.add(BillNoLabel);
        JLabel BillNumberLabel = new JLabel();
        int billNo = Processes.getBillNo();
        BillNumberLabel.setText(String.valueOf(billNo));
        BillNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        BillNumberLabel.setBounds(455, 51, BillNumberLabel.getPreferredSize().width, 30);
        this.add(BillNumberLabel);
        
        //Vehicle details
        JLabel VLabel = new JLabel("Vehicle Details:");
        VLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        VLabel.setBounds(489, 53, VLabel.getPreferredSize().width, 26);
        add(VLabel);
        
        JTextField VField = new JTextField(null);
        VField.setBounds(596, 51, 100, 30);
        add(VField);

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
        // Dropdown Item Listener
        dropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                    	String selectedItem = (String) dropdown.getSelectedItem();
                        Address1L.setText(Processes.getAddress1(selectedItem));
                        Address2L.setText(Processes.getAddress2(selectedItem));
                        Address3L.setText(Processes.getAddress3(selectedItem));
                        GSTL.setText(Processes.getGST(selectedItem));
                        CntPersonL.setText(Processes.getCntPerson(selectedItem));
                        PhoneNoL.setText(Processes.getPhoneNo(selectedItem));
                        DestinationL.setText(Processes.getDestination(selectedItem));
                    } catch (ClassNotFoundException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

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
        transportationField = new JTextField("0");
        transportationField.setBounds(670, 591, 100, 30);
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
        // Table Model Listener to update totals
        tableModel.addTableModelListener(e -> updateTotals());
    
        // Creating Bill
        JButton createBillButton = new JButton("Create Bill");
        createBillButton.setBounds(920, 629, 150, 40);
        createBillButton.setBackground(Color.GREEN);
        createBillButton.setForeground(Color.WHITE);
        createBillButton.setFont(new Font("Arial", Font.BOLD, 16));
        createBillButton.setFocusPainted(false); // Remove focus outline
        createBillButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		updateTotals();
	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            StringBuilder errorMessages = new StringBuilder();
	            // Check if table is empty
	            if (model.getRowCount() == 0) {
	                errorMessages.append("No data items added. \n");
	            }
	
	            // Check if selected item is valid
	            String selectedItem = (String) dropdown.getSelectedItem();
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
	            	int Bno = Integer.parseInt(BillNumberLabel.getText());
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
	                Double Transportation = 0.0;
	                if (!transportationField.getText().isEmpty()) {
	                    Transportation = Double.parseDouble(transportationField.getText());
	                }
	                Processes.cBill(Bno, selectedItem, formattedDate, VDetails, TaxableAmount, gst, Transportation, Total);
	                JOptionPane.showMessageDialog(null, 
	                        "Bill created successfully!", 
	                        "Success", 
	                        JOptionPane.INFORMATION_MESSAGE);
	                String gstno =Processes.getGST(selectedItem);
	                model.setRowCount(0);
	                dropdown.setSelectedIndex(0);
	                datePicker.setDate(null);
	                int billNo = Processes.getBillNo();
	                BillNumberLabel.setText(String.valueOf(billNo));
	                transportationField.setText("0");
	                VField.setText(null);
	                if ("27".equals(gstno.substring(0, 2))) {
		            	BillGenGSTD.createBill(String.valueOf(Bno));
		            	BillGenGST.createBill(String.valueOf(Bno));
	                } else {
		            	BillGenIGSTD.createBill(String.valueOf(Bno));
		            	BillGenIGST.createBill(String.valueOf(Bno));
	                }
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
	            } catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
    });
    add(createBillButton);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setBounds(760, 629, 150, 40);
    cancelButton.setBackground(Color.RED);
    cancelButton.setForeground(Color.WHITE);
    cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
    cancelButton.setFocusPainted(false); // Remove focus outline
    this.add(cancelButton);
    cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            dropdown.setSelectedIndex(0);
            datePicker.setDate(null);
            transportationField.setText("0");
            VField.setText(null);
            itemsDropdown.setSelectedIndex(0);
            quantityTextField.setText("");
            rateTextField.setText("");
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
