package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import database.Processes;
import pdf.create.LedgerGen;

import javax.swing.JButton;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class ViewLedgerPanel extends JPanel {
    private String name;
    private LocalDate fromdate, todate;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final long serialVersionUID = 1L;

    public ViewLedgerPanel() throws ClassNotFoundException, SQLException {
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1080, 680));

        JLabel titleLabel = new JLabel("View Ledger");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(20, 20, 300, 30);
        this.add(titleLabel);
        
        JLabel partyNameLabel = new JLabel("Party Name:");
        partyNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        partyNameLabel.setBounds(20, 50, partyNameLabel.getPreferredSize().width, 30);
        this.add(partyNameLabel);
        
        ArrayList<String> allParty = Processes.Names();
        Collections.sort(allParty);
        String[] PartyArray = new String[allParty.size()];
        allParty.toArray(PartyArray);
        JComboBox<String> PartyName = new JComboBox<>(PartyArray);
        PartyName.insertItemAt("--Select Party--", 0);
        PartyName.setSelectedIndex(0);
        PartyName.setFont(new Font("Arial", Font.PLAIN, 14));
        PartyName.setBounds(107, 50, 200, 30);
        this.add(PartyName);
        
        JLabel fromdateLabel = new JLabel("From Date:");
        fromdateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        fromdateLabel.setBounds(317, 50, fromdateLabel.getPreferredSize().width, 30);
        this.add(fromdateLabel);
        
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra(format);
        dateSettings.setAllowKeyboardEditing(false);
        dateSettings.setVisibleClearButton(false);
        dateSettings.setVisibleNextYearButton(false);
        dateSettings.setVisiblePreviousYearButton(false);
        dateSettings.setAllowEmptyDates(false);
        DatePicker fromdatePicker = new DatePicker(dateSettings);
        fromdatePicker.setBounds(390, 50, fromdatePicker.getPreferredSize().width, 30);
        fromdatePicker.setDate(LocalDate.now().minusMonths(1));
        fromdatePicker.getComponentDateTextField().setEnabled(false);
        this.add(fromdatePicker);
        
        JLabel todateLabel = new JLabel("To Date:");
        todateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        todateLabel.setBounds(535, 50, todateLabel.getPreferredSize().width, 30);
        this.add(todateLabel);
        
        DatePickerSettings dateSettings1 = new DatePickerSettings();
        dateSettings1.setFormatForDatesCommonEra(format);
        dateSettings1.setAllowKeyboardEditing(false);
        dateSettings1.setVisibleClearButton(false);
        dateSettings1.setVisibleClearButton(false);
        dateSettings1.setVisibleNextYearButton(false);
        dateSettings1.setVisiblePreviousYearButton(false);
        dateSettings1.setAllowEmptyDates(false);
        DatePicker todatePicker = new DatePicker(dateSettings1);
        todatePicker.setBounds(600, 50, todatePicker.getPreferredSize().width, 30);
        todatePicker.setDate(LocalDate.now());
        todatePicker.getComponentDateTextField().setEnabled(false);
        this.add(todatePicker);
        
        String[] columnNames = {"Date", "Particulars", "Vch Type", "Vch No", "Vch Remark", "Debit", "Credit"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 91, 1050, 578);
        this.add(scrollPane);

        PartyName.addActionListener(e -> {
			try {
				fetchLedger(PartyName, fromdatePicker, todatePicker, tableModel, table);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        fromdatePicker.addDateChangeListener(e -> {
			try {
				fetchLedger(PartyName, fromdatePicker, todatePicker, tableModel, table);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        todatePicker.addDateChangeListener(e -> {
			try {
				fetchLedger(PartyName, fromdatePicker, todatePicker, tableModel, table);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        JButton printLedgerButton = new JButton("Print Ledger");
        printLedgerButton.setBounds(862, 50, 100, 30);
        add(printLedgerButton);
        printLedgerButton.addActionListener(e ->{
			try {
				int rowCount = table.getRowCount();
				int columnCount = table.getColumnCount();

				DefaultTableModel sortedModel = new DefaultTableModel();

				// Set column names manually
				for (int i = 0; i < columnCount; i++) {
				    sortedModel.addColumn(table.getColumnName(i));
				}

				// Add sorted rows from the table (which respects the row sorter)
				for (int i = 0; i < rowCount; i++) {
				    Object[] rowData = new Object[columnCount];
				    for (int j = 0; j < columnCount; j++) {
				        rowData[j] = table.getValueAt(i, j);
				    }
				    sortedModel.addRow(rowData);
				}

				LedgerGen.createLedger(name, fromdate, todate, sortedModel);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    }

    private void fetchLedger(JComboBox<String> PartyName, DatePicker fromdatePicker, DatePicker todatePicker, DefaultTableModel tableModel, JTable table) throws ClassNotFoundException {
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
            JOptionPane.showMessageDialog(null, errorMessages.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ResultSet rs = 
            Processes.viewLedger(name, fromdate.format(format), todate.format(format));
            tableModel.setRowCount(0);
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
            JOptionPane.showMessageDialog(null, "An error occurred while retrieving the ledger data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);

        // Set a custom comparator for the date column (index 0)
        sorter.setComparator(0, new Comparator<String>() {
         private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         @Override
         public int compare(String date1, String date2) {
             LocalDate d1 = LocalDate.parse(date1, formatter);
             LocalDate d2 = LocalDate.parse(date2, formatter);
             return d1.compareTo(d2);
         }
     });

     table.setRowSorter(sorter);
     sorter.toggleSortOrder(0); // Sort by the first column (date)

    }
}