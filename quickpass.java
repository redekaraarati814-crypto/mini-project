import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class QuickPassApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Login Panel Components
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    // Generator Panel Components
    private JTextField txtName;
    private JTextField txtMobile;
    private JTextField txtEmail;
    private JComboBox<String> cbRoute;
    private JComboBox<String> cbPassType;
    private JComboBox<String> cbPayment;
    private JButton btnGenerate;
    private JEditorPane epResult;
    
    public QuickPassApp() {
        setTitle("QuickPass Bus Pass Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Setup Panels
        JPanel loginPanel = createLoginPanel();
        JPanel appPanel = createAppPanel();
        
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(appPanel, "APP");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(242, 242, 242));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblTitle = new JLabel("QuickPass Login", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);
        
        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 128, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);
        
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                if (username.equals("admin") && password.equals("1234")) {
                    JOptionPane.showMessageDialog(QuickPassApp.this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "APP");
                } else {
                    JOptionPane.showMessageDialog(QuickPassApp.this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }
    
    private JPanel createAppPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Input Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Passenger Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Passenger Name:"), gbc);
        txtName = new JTextField(18);
        gbc.gridx = 1;
        formPanel.add(txtName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Mobile Number:"), gbc);
        txtMobile = new JTextField(18);
        gbc.gridx = 1;
        formPanel.add(txtMobile, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email Address:"), gbc);
        txtEmail = new JTextField(18);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Route:"), gbc);
        cbRoute = new JComboBox<>(new String[]{"Sangli - Kolhapur", "Sangli - Pune"});
        gbc.gridx = 1;
        formPanel.add(cbRoute, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Pass Type:"), gbc);
        cbPassType = new JComboBox<>(new String[]{"Monthly - Rs.500", "Quarterly - Rs.1400", "Yearly - Rs.5000"});
        gbc.gridx = 1;
        formPanel.add(cbPassType, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Payment Method:"), gbc);
        cbPayment = new JComboBox<>(new String[]{"UPI", "Debit Card", "Credit Card"});
        gbc.gridx = 1;
        formPanel.add(cbPayment, gbc);
        
        btnGenerate = new JButton("Generate Pass");
        btnGenerate.setBackground(new Color(0, 128, 0));
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(btnGenerate, gbc);
        
        panel.add(formPanel, BorderLayout.WEST);
        
        // Result HTML Pane (with Scroll)
        epResult = new JEditorPane();
        epResult.setContentType("text/html");
        epResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(epResult);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Generated Pass"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePass();
            }
        });
        
        return panel;
    }
    
    private void generatePass() {
        String name = txtName.getText().trim();
        String mobile = txtMobile.getText().trim();
        String email = txtEmail.getText().trim();
        String route = (String) cbRoute.getSelectedItem();
        String passType = (String) cbPassType.getSelectedItem();
        String payment = (String) cbPayment.getSelectedItem();
        
        if (name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all details", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String busNumber = "";
        String departureTime = "";
        String seats = "";
        String amount = "";
        String validity = "";
        
        Random random = new Random();
        String passId = "QP" + (1000 + random.nextInt(9000));
        
        if ("Sangli - Kolhapur".equals(route)) {
            busNumber = "MH12AB1234";
            departureTime = "08:00 AM";
            seats = "25";
        } else {
            busNumber = "MH14XY5678";
            departureTime = "09:30 AM";
            seats = "18";
        }
        
        if ("Monthly - Rs.500".equals(passType)) {
            amount = "500";
            validity = "30 Days";
        } else if ("Quarterly - Rs.1400".equals(passType)) {
            amount = "1400";
            validity = "90 Days";
        } else {
            amount = "5000";
            validity = "365 Days";
        }
        
        String htmlResult = "<html>" +
                "<body style='font-family: Arial, sans-serif; padding: 10px;'>" +
                "<h2 style='color: green; text-align: center;'>🚌 DIGITAL BUS PASS</h2>" +
                "<hr>" +
                "<table style='width: 100%; border-collapse: collapse; font-size: 13px;'>" +
                "<tr><td><b>Pass ID :</b></td><td>" + passId + "</td></tr>" +
                "<tr><td colspan='2' style='text-align: center;'><img src='https://api.qrserver.com/v1/create-qr-code/?size=120x120&data=" + passId + "' width='120' height='120'></td></tr>" +
                "<tr><td><b>Passenger Name :</b></td><td>" + name + "</td></tr>" +
                "<tr><td><b>Mobile Number :</b></td><td>" + mobile + "</td></tr>" +
                "<tr><td><b>Email :</b></td><td>" + email + "</td></tr>" +
                "<tr><td><b>Bus Number :</b></td><td>" + busNumber + "</td></tr>" +
                "<tr><td><b>Route :</b></td><td>" + route + "</td></tr>" +
                "<tr><td><b>Departure Time :</b></td><td>" + departureTime + "</td></tr>" +
                "<tr><td><b>Available Seats :</b></td><td>" + seats + "</td></tr>" +
                "<tr><td><b>Pass Type :</b></td><td>" + passType + "</td></tr>" +
                "<tr><td><b>Pass Validity :</b></td><td>" + validity + "</td></tr>" +
                "<tr><td><b>Amount Paid :</b></td><td>Rs. " + amount + "</td></tr>" +
                "<tr><td><b>Payment Method :</b></td><td>" + payment + "</td></tr>" +
                "<tr><td><b>Booking Date :</b></td><td>15-06-2026</td></tr>" +
                "<tr><td><b>Expiry Date :</b></td><td>15-06-2027</td></tr>" +
                "<tr><td><b>Status :</b></td><td style='color: green;'><b>Approved ✅</b></td></tr>" +
                "<tr><td><b>Passenger Age :</b></td><td>21</td></tr>" +
                "<tr><td><b>Gender :</b></td><td>Female</td></tr>" +
                "<tr><td><b>Seat Number :</b></td><td>A12</td></tr>" +
                "<tr><td><b>Driver Name :</b></td><td>Amit Patil</td></tr>" +
                "<tr><td><b>Conductor Name :</b></td><td>Rajesh Jadhav</td></tr>" +
                "<tr><td><b>Bus Type :</b></td><td>AC Sleeper</td></tr>" +
                "<tr><td><b>Platform Number :</b></td><td>3</td></tr>" +
                "<tr><td><b>Journey Distance :</b></td><td>230 KM</td></tr>" +
                "<tr><td><b>Travel Duration :</b></td><td>4 Hours</td></tr>" +
                "<tr><td><b>Pass Category :</b></td><td>Student Pass</td></tr>" +
                "<tr><td><b>Booking Reference :</b></td><td>BK10245</td></tr>" +
                "<tr><td><b>Emergency Contact :</b></td><td>108</td></tr>" +
                "</table>" +
                "<hr>" +
                "<p style='font-size: 11px; text-align: center;'><b>Customer Care:</b> 1800-123-456 | <b>Email Support:</b> support@quickpass.com</p>" +
                "<h3 style='text-align: center; color: blue;'>Thank You For Using QuickPass</h3>" +
                "</body>" +
                "</html>";
        
        epResult.setText(htmlResult);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuickPassApp().setVisible(true);
            }
        });
    }
}