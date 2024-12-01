package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Processes;
import pdf.print.PDFPrinter;
import pdf.send.SendPdf;
public class PrintBillsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public PrintBillsPanel() throws ClassNotFoundException, SQLException {
        this.setBackground(Color.WHITE);
        this.setLayout(null); // Absolute layout for fixed positioning

        // Set fixed size for the panel
        this.setPreferredSize(new Dimension(1080, 680)); // Adjust size as needed

        // Title Label
        JLabel titleLabel = new JLabel("Send & Print Bills Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(10, 10, 300, 30); // x, y, width, height
        this.add(titleLabel);
        //Bill dropdown
        ArrayList<String> allBills = Processes.BillParty();
        Collections.sort(allBills);
        String[] optionsArray = new String[allBills.size()];
        allBills.toArray(optionsArray);
        JComboBox<String> comboBox = new JComboBox<String>(optionsArray);
        String defaultValue = "--Select Bill--";
        comboBox.insertItemAt(defaultValue, 0);
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(10, 50, 300, 30);
        add(comboBox);
        
        JButton sendBillButton = new JButton("Send Bill");
        sendBillButton.setBounds(920, 629, 150, 40);
        sendBillButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sendBillButton.setFocusPainted(false); // Remove focus outline
        this.add(sendBillButton);
        sendBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(comboBox.getSelectedItem() != "--Select Bill--") {
                	String def = (String) comboBox.getSelectedItem();
                	String orig = def + ".pdf";
                	try {
    					new SendPdf(orig);
    	                JOptionPane.showMessageDialog(null, 
    	                        "Bill Sent successfully!", 
    	                        "Success", 
    	                        JOptionPane.INFORMATION_MESSAGE);
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
            	}
            }   
        });
        JButton printBillButton = new JButton("Print Bill");
        printBillButton.setBounds(760, 629, 150, 40);
        printBillButton.setFont(new Font("Arial", Font.PLAIN, 16));
        printBillButton.setFocusPainted(false); // Remove focus outline
        this.add(printBillButton);
        printBillButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(comboBox.getSelectedItem() != "--Select Bill--") {
                	String def = (String) comboBox.getSelectedItem();
                	String orig = def + ".pdf";
                	String dupl = def + "(duplicate).pdf";
                	PDFPrinter.printPDF(orig);
                	PDFPrinter.printPDF(dupl);
        		}
        	}	
        });
        
	}
}
