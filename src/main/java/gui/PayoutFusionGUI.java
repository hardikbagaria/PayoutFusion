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
import java.util.HashMap;
import java.util.Map;
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

public class PayoutFusionGUI {
    private static JButton lastSelectedButton = null;
    private static Map<String, JPanel> panelsMap = new HashMap<>();
    private static JPanel contentArea;

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Payout Fusion");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar setup
        JPanel sidebar = createSidebar();
        
        // Content area setup with CardLayout
        contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(Color.WHITE);

        // Preloading the initial panel
        JPanel initialPanel = createInitialPanel();
        contentArea.add(initialPanel, "Initial");
        switchPanel("Initial");

        // Add components to main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        // Finalize frame setup
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(66, 66, 68));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel logoLabel = new JLabel("Payout Fusion", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logoLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        // Define buttons and their corresponding panels
        String[] buttonNames = {
            "Create Bills", "Update Bills", "Create Party", "Update Party", "Print Bills", "View Bills", "View Ledger", "View Payments"
        };
        for (String buttonName : buttonNames) {
            JButton button = createStyledButton(buttonName);
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

            // Add ActionListener for dynamic reloading
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
						handlePanelLoading(buttonName, button);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        }

        return sidebar;
    }

    private static JPanel createInitialPanel() {
        JPanel initialPanel = new JPanel();
        initialPanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome to Payout Fusion");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(0x1ABC9C));
        initialPanel.add(welcomeLabel);
        return initialPanel;
    }

    private static void handlePanelLoading(String panelName, JButton button) throws ClassNotFoundException, SQLException {
        // Remove the old panel if it exists
        if (panelsMap.containsKey(panelName)) {
            contentArea.remove(panelsMap.get(panelName));
        }

        JPanel newPanel = null;
        switch (panelName) {
            case "Create Bills":
                newPanel = new BillPanel(); // Load BillPanel class
                break;
            case "Update Bills":
                newPanel = new UpdateBillPanel(); // Load UpdateBillPanel class
                break;
            // Add more cases for other panels
            case "Create Party":
                newPanel = new CreatePartyPanel(); // Load CreatePartyPanel class
                break;
            case "Update Party":
                newPanel = new UpdatePartyPanel(); // Load UpdatePartyPanel class
                break;
            case "Print Bills":
            	newPanel = new PrintBillsPanel();
            	break;
            case "View Bills":
            	newPanel = new ViewBillsPanel();
            	break;
            case "View Ledger":
            	newPanel = new ViewLedgerPanel();
            	break;
            // Default case for unhandled panel names
            default:
                newPanel = createNewPanel(panelName); // Fallback to default
                break;
        }

        panelsMap.put(panelName, newPanel);
        contentArea.add(newPanel, panelName);

        // Switch to the new panel
        switchPanel(panelName);

        // Refresh the layout
        contentArea.revalidate();
        contentArea.repaint();

        // Highlight the selected button
        highlightButton(button);
    }

    private static JPanel createNewPanel(String panelName) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(panelName + " Panel");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(label);
        return panel;
    }

    private static void switchPanel(String panelName) {
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
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x2980B9));
        button.setUI(new StyledButtonUI());
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Custom button UI for the sidebar buttons
    private static class StyledButtonUI extends BasicButtonUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            AbstractButton button = (AbstractButton) c;
            button.setOpaque(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            paintBackground(g, button, button.getModel().isPressed() ? 2 : 0);
            super.paint(g, c);
        }

        private void paintBackground(Graphics g, JComponent c, int yOffset) {
            Dimension size = c.getSize();
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(c.getBackground().darker());
            g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
            g.setColor(c.getBackground());
            g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
        }
    }
}
