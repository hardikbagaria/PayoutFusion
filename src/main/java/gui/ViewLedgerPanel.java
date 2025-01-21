package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import database.Processes;
import javax.swing.JButton;

public class ViewLedgerPanel extends JPanel {
	private String name;
	private LocalDate fromdate,todate;
	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public ViewLedgerPanel() throws ClassNotFoundException, SQLException {
        // Set background color and layout
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("View Ledger");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30); // x, y, width, height
        this.add(titleLabel);
        
        // Party Name
        JLabel partyNameLabel = new JLabel("Party Name:");
        partyNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        partyNameLabel.setBounds(20, 50, partyNameLabel.getPreferredSize().width, 30);
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
        PartyName.setBounds(107, 50, 200, 30);
        this.add(PartyName);
        
        // From Date
        JLabel fromdateLabel = new JLabel("From Date:");
        fromdateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        fromdateLabel.setBounds(317, 50, fromdateLabel.getPreferredSize().width, 30);
        this.add(fromdateLabel);
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra(format);
        DatePicker fromdatePicker = new DatePicker(dateSettings);
        fromdatePicker.setBounds(390, 50, fromdatePicker.getPreferredSize().width, 30);
        fromdatePicker.setDate(LocalDate.now().minusMonths(1));
        this.add(fromdatePicker);
        
        // To Date
        JLabel todateLabel = new JLabel("To Date:");
        todateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        todateLabel.setBounds(535, 50, todateLabel.getPreferredSize().width, 30);
        this.add(todateLabel);
        DatePickerSettings dateSettings1 = new DatePickerSettings();
        dateSettings1.setFormatForDatesCommonEra(format);
        DatePicker todatePicker = new DatePicker(dateSettings1);
        todatePicker.setBounds(600, 50, todatePicker.getPreferredSize().width, 30);
        todatePicker.setDate(LocalDate.now());
        this.add(todatePicker);
        
        // Table Setup
        String[] columnNames = {"Date", "Particulars", "Vch Type", "Vch No", "Vch Remark", "Debit", "Credit"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };
        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 91, 1050, 578);
        this.add(scrollPane);
        // Get Ledger Button
        JButton getLedgerButton = new JButton("Get Ledger");
        getLedgerButton.setBounds(752, 50, 100, 30);
        this.add(getLedgerButton);
        // Button Action Listener
        getLedgerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = PartyName.getSelectedItem().toString();
                fromdate = fromdatePicker.getDate();
                todate = todatePicker.getDate();
                
                StringBuilder errorMessages = new StringBuilder();
                if ("--Select Party--".equals(name)) {
                    errorMessages.append("Please select a valid Party.\n");
                }
                if (fromdate == null) {
                    errorMessages.append("Please select a valid from date.\n");
                }
                if (todate == null) {
                    errorMessages.append("Please select a valid to date.\n");
                }
                if (errorMessages.length() > 0) {
                    JOptionPane.showMessageDialog(null, 
                            errorMessages.toString(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ResultSet rs = Processes.viewLedger(name, fromdate.format(format), todate.format(format));
                    tableModel.setRowCount(0); // Clear the table before adding new rows
                    while (rs.next()) {
                        int billNo = rs.getInt("BillNo");
                        String date = rs.getString("Date");
                        double grandTotal = rs.getDouble("grandTotal");
                        tableModel.addRow(new Object[]{date, "PURCHASE GST", "PURCHASE", billNo, "", "", grandTotal});
                    }
                    ResultSet rs1 = Processes.viewPayments(name, fromdate.format(format), todate.format(format));
                    while (rs1.next()) {
                        String date = rs1.getString("date");
                        int amountPaid = rs1.getInt("amountPaid");
                        String remarks = rs1.getString("remarks");
                        tableModel.addRow(new Object[]{date, "Payment GST", "Payment", "", remarks, amountPaid, ""});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                            "An error occurred while retrieving the ledger data.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                            "An unexpected error occurred.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton printLedgerButton = new JButton("Print Ledger");
        printLedgerButton.setBounds(862, 50, 100, 30);
        add(printLedgerButton);
        printLedgerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String a = name;
            	String from_date = fromdate.format(format);
            	String to_date = todate.format(format);
            }    
        });
    }
}
