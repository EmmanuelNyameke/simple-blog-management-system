import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginButton, backButton, signupButton;

    private static final Logger logger = Logger.getLogger(LogInForm.class.getName());

    public LogInForm() {
        // Disable resize, maximize and minimize buttons
        setResizable(false);
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });
        initComponents();
    }

    private void initComponents() {
        JLabel eMail = new JLabel("Email:");
        JLabel pass = new JLabel("Password:");

        // Setting font and increasing the size
        Font fontLabel = new Font("Verdana", Font.BOLD, 16);
        eMail.setFont(fontLabel);
        pass.setFont(fontLabel);

        emailField = new JTextField();
        passField = new JPasswordField();

        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        // Implementing "Don't have an account? sign up" feature
        signupButton = new JButton("Don't Have an Account? Signup");
        // Setting the fonts
        Font signupFont = new Font("Verdana", Font.PLAIN, 12);

        // Setting background color
        Color backgroundColor = new Color(60, 63, 65); // Dark Gray
        Color foregroundColor = Color.WHITE; // White text

        getContentPane().setBackground(backgroundColor);

        // Changing the label color from black to white
        eMail.setForeground(foregroundColor);
        pass.setForeground(foregroundColor);

        // Setting the button color
        loginButton.setBackground((new Color(33, 150, 243))); // Blue
        loginButton.setForeground(foregroundColor);
        backButton.setBackground(new Color(33, 150, 243));
        backButton.setForeground(foregroundColor);
        signupButton.setBackground(new Color(255, 255, 255));

        // Setting button font
        loginButton.setFont(fontLabel);
        backButton.setFont(fontLabel);
        signupButton.setFont(signupFont);

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 2, 25, 15);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to the layout
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        add(eMail, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.9;
        add(emailField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        add(pass, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.9;
        add(passField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor =GridBagConstraints.CENTER;
        add(loginButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        add(backButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        add(signupButton, gridBagConstraints);

        setTitle("Login Form");
        setSize(500, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DisplayPublishedArticles().setVisible(true);
                // Close the login form
                LogInForm.this.dispose();
            }
        });
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open signup form
                new SignUpForm().setVisible(true);
                JOptionPane.showMessageDialog(LogInForm.this, "Openning Signup Form");
                // Close the login form
                LogInForm.this.dispose();
            }
        });
    }

    // Creating the loginUser method
    private void loginUser() {
        String mail = emailField.getText();
        String passWord = new String(passField.getPassword());

        if (mail.isEmpty() || passWord.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return;
        }
        // Database info
        String url = "jdbc:mysql://localhost:3306/userdb";
        String user = "root";
        String password = "";
        // Connecting to database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT *FROM users WHERE Email = ? AND Password = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, passWord);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Login Successfully");
                logger.info("User Logged in: " + mail);
                // Open the News writer page
                new Blogger().setVisible(true);
                // Close the login form
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid Email or Password");
            }
        }
        catch (SQLException throwables) {
            logger.log(Level.SEVERE, "Database Connection Error", throwables);

            JOptionPane.showMessageDialog(null, "Database Connection Error: " + throwables.getMessage());
        }
        finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            }
            catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error Closing Resources", ex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LogInForm().setVisible(true);
            }
        });
    }
}
