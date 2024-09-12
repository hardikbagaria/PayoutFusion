package extras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileInputWithSystemFileChooser {
    public static void main(String[] args) {
        try {
            // Set the Look and Feel to the system's default (Windows in this case)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the main frame
        JFrame frame = new JFrame("File Input Dialog Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        
        // Get the user's home directory and set the default directory to Downloads
        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");

        // Create a button that will trigger the dialog
        JButton button = new JButton("Enter File Details");

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a JPanel to hold the input fields
                JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

                // Create labels and text fields for file path and file name
                JLabel pathLabel = new JLabel("File Path:");
                
                // Set default value to Downloads directory
                JTextField pathField = new JTextField(downloadsDir.getAbsolutePath(), 20);
                pathField.setEditable(false);  // File path shouldn't be editable manually

                // Create a button to open the file explorer
                JButton browseButton = new JButton("Browse...");
                browseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        
                        // Set the default directory to Downloads folder
                        fileChooser.setCurrentDirectory(downloadsDir);

                        // Set file chooser to select directories
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        int option = fileChooser.showOpenDialog(frame);

                        // If a directory is selected, set the pathField text
                        if (option == JFileChooser.APPROVE_OPTION) {
                            File selectedDirectory = fileChooser.getSelectedFile();
                            pathField.setText(selectedDirectory.getAbsolutePath());
                        }
                    }
                });

                JLabel nameLabel = new JLabel("File Name:");
                JTextField nameField = new JTextField(20);

                // Add components to the panel
                panel.add(pathLabel);
                panel.add(pathField);
                panel.add(new JLabel());  // Empty cell for spacing
                panel.add(browseButton);
                panel.add(nameLabel);
                panel.add(nameField);

                // Show the input dialog with the panel
                int result = JOptionPane.showConfirmDialog(
                        frame, panel, "Enter File Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // Check if the user clicked OK
                if (result == JOptionPane.OK_OPTION) {
                    String filePath = pathField.getText();
                    String fileName = nameField.getText();

                    if (!filePath.isEmpty() && !fileName.trim().isEmpty()) {
                        // Handle the input values
                        System.out.println("File Path: " + filePath);
                        System.out.println("File Name: " + fileName);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please provide both file path and name.");
                    }
                }
            }
        });

        // Add the button to the frame and make it visible
        frame.getContentPane().add(button, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
