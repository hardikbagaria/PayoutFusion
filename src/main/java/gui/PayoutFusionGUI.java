package gui;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
public class PayoutFusionGUI{
	private static JButton lastSelectedButton = null;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Create the frame
        JFrame frame = new JFrame("Payout Fusion");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Make the frame size unchangeable

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(66, 66, 68));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel logoLabel = new JLabel("Payout Fusion", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logoLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer

        // Bills section
        final JButton createBillButton = createStyledButton("Create Bills");
        final JButton updateBillButton = createStyledButton("Update Bills");
        final JButton deleteBillButton = createStyledButton("Delete Bills");
        
        // Party section
        final JButton createPartyButton = createStyledButton("Create Party");
        final JButton updatePartyButton = createStyledButton("Update Party");
        final JButton deletePartyButton = createStyledButton("Delete Party");

        // Print section
        final JButton printBillButton = createStyledButton("Print Bills");

        // Ledger section
        final JButton viewBillsButton = createStyledButton("View Bills");
        final JButton viewLedgerButton = createStyledButton("View Ledger");
        final JButton viewPaymentsButton = createStyledButton("View Payments");

        // Add buttons to sidebar with spacers
        sidebar.add(createBillButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(updateBillButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(deleteBillButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(createPartyButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(updatePartyButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(deletePartyButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(printBillButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(viewBillsButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(viewLedgerButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(viewPaymentsButton);

        // Create the content area with CardLayout
        final JPanel contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(Color.WHITE); // Set the background color to white

        // Create different panels for different functionalities
        JPanel initialPanel = new JPanel();
        initialPanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome to Payout Fusion");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(0x1ABC9C));
        initialPanel.add(welcomeLabel);
        
        BillPanel createBillPanel = new BillPanel();
        
        JPanel updateBillPanel = new UpdateBillPanel();

        JPanel deleteBillPanel = new JPanel();
        deleteBillPanel.setBackground(Color.WHITE);
        deleteBillPanel.add(new JLabel("Delete Bills Panel"));

        JPanel createPartyPanel = new JPanel();
        createPartyPanel.setBackground(Color.WHITE);
        createPartyPanel.add(new JLabel("Create Party Panel"));

        JPanel updatePartyPanel = new JPanel();
        updatePartyPanel.setBackground(Color.WHITE);
        updatePartyPanel.add(new JLabel("Update Party Panel"));

        JPanel deletePartyPanel = new JPanel();
        deletePartyPanel.setBackground(Color.WHITE);
        deletePartyPanel.add(new JLabel("Delete Party Panel"));

        JPanel printBillPanel = new JPanel();
        printBillPanel.setBackground(Color.WHITE);
        printBillPanel.add(new JLabel("Print Bills Panel"));

        JPanel viewBillsPanel = new JPanel();
        viewBillsPanel.setBackground(Color.WHITE);
        viewBillsPanel.add(new JLabel("View Bills Panel"));

        JPanel viewLedgerPanel = new JPanel();
        viewLedgerPanel.setBackground(Color.WHITE);
        viewLedgerPanel.add(new JLabel("View Ledger Panel"));

        JPanel viewPaymentsPanel = new JPanel();
        viewPaymentsPanel.setBackground(Color.WHITE);
        viewPaymentsPanel.add(new JLabel("View Payments Panel"));

        // Add panels to the content area
        contentArea.add(initialPanel, "Initial");
        contentArea.add(createBillPanel, "Create Bills");
        contentArea.add(updateBillPanel, "Update Bills");
        contentArea.add(deleteBillPanel, "Delete Bills");
        contentArea.add(createPartyPanel, "Create Party");
        contentArea.add(updatePartyPanel, "Update Party");
        contentArea.add(deletePartyPanel, "Delete Party");
        contentArea.add(printBillPanel, "Print Bills");
        contentArea.add(viewBillsPanel, "View Bills");
        contentArea.add(viewLedgerPanel, "View Ledger");
        contentArea.add(viewPaymentsPanel, "View Payments");

        // Add action listeners to buttons to switch panels
        createBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Create Bills");
                highlightButton(createBillButton);
            }
        });

        updateBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Update Bills");
                highlightButton(updateBillButton);
            }
        });

        deleteBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Delete Bills");
                highlightButton(deleteBillButton);
            }
        });

        createPartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Create Party");
                highlightButton(createPartyButton);
            }
        });

        updatePartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Update Party");
                highlightButton(updatePartyButton);
            }
        });

        deletePartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Delete Party");
                highlightButton(deletePartyButton);
            }
        });

        printBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "Print Bills");
                highlightButton(printBillButton);
            }
        });

        viewBillsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "View Bills");
                highlightButton(viewBillsButton);
            }
        });

        viewLedgerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "View Ledger");
                highlightButton(viewLedgerButton);
            }
        });

        viewPaymentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel(contentArea, "View Payments");
                highlightButton(viewPaymentsButton);
            }
        });

        // Add components to the main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Set the frame to be visible
        frame.setVisible(true);
    }

    private static void switchPanel(JPanel contentArea, String panelName) {
        CardLayout cl = (CardLayout) (contentArea.getLayout());
        cl.show(contentArea, panelName);
    }

    private static void highlightButton(JButton button) {
        if (lastSelectedButton != null) {
            lastSelectedButton.setBackground(new Color(0x2980B9));
        }
        button.setBackground(new Color(0x1ABC9C));
        lastSelectedButton = button;
    }

    private static JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setUI(new RoundedCornerButtonUI());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x2980B9));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                button.setBackground(new Color(0x1ABC9C));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != lastSelectedButton) {
                    button.setBackground(new Color(0x2980B9));
                }
            }
        });
        button.setToolTipText("Click to " + text.toLowerCase());
        return button;
    }
}

class RoundedCornerButtonUI extends BasicButtonUI {
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = c.getWidth();
        int height = c.getHeight();
        g2.setColor(c.getBackground());
        g2.fillRoundRect(0, 0, width, height, 30, 30);
        g2.dispose();
    }
}
