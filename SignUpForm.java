// Importing the necessary libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

     // Inheriting SignupForm from JFrame in the 'javax.swing.*' for GUI
public class SignUpForm extends JFrame {
    // Declaring fields from the JFrame class
         private JTextField firstNameField, lastNameField, emailField, userNameField;
         private JPasswordField createPassField, confirmPassField;
         private JButton signUpButton, backButton, loginButton;

         // Initializing logger
         private static final Logger logger = Logger.getLogger(SignUpForm.class.getName());

         // Constructor
         public SignUpForm() {
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
         // Creating initComponents method
         private void initComponents() {
             // Creating the labels as identity for users to input the right information
             JLabel firstName = new JLabel("First Name:");
             JLabel lastName = new JLabel("Last Name:");
             JLabel userName = new JLabel("Username:");
             JLabel eMail = new JLabel("Email:");
             JLabel createPassWord = new JLabel("Create Password:");
             JLabel confirmPassWord = new JLabel("Confirm Password:");
             // Setting Fonts and increasing the size
             Font fontLabel = new Font("Verdana", Font.BOLD, 16);
             firstName.setFont(fontLabel);
             lastName.setFont(fontLabel);
             userName.setFont(fontLabel);
             eMail.setFont(fontLabel);
             createPassWord.setFont(fontLabel);
             confirmPassWord.setFont(fontLabel);
             // Setting background color
             Color backgroundColor = new Color(60, 63, 65); // Dark Gray
             Color foregroundColor = Color.WHITE; // White text

             getContentPane().setBackground(backgroundColor);

             // Changing the label color from black to white
             firstName.setForeground(foregroundColor);
             lastName.setForeground(foregroundColor);
             userName.setForeground(foregroundColor);
             eMail.setForeground(foregroundColor);
             createPassWord.setForeground(foregroundColor);
             confirmPassWord.setForeground(foregroundColor);
             // Initializing fields to enable users type their information
             firstNameField = new JTextField();
             lastNameField = new JTextField();
             userNameField = new JTextField();
             emailField = new JTextField();
             createPassField = new JPasswordField();
             confirmPassField = new JPasswordField();

             signUpButton = new JButton("SignUp");
             backButton = new JButton("Back");
             // Implementing "Already have an account? Login" in the Signup form
             loginButton = new JButton("Already Have an Account? Login");
             // Setting the font
             Font loginFont = new Font("Verdana", Font.PLAIN, 12);
             // Setting the button color
             signUpButton.setBackground((new Color(33, 150, 243))); // Blue
             signUpButton.setForeground(foregroundColor);
             backButton.setBackground(new Color(33, 150, 243));
             backButton.setForeground(foregroundColor);
             loginButton.setBackground(new Color(255, 255, 255));
             // Setting button font
             signUpButton.setFont(fontLabel);
             backButton.setFont(fontLabel);
             loginButton.setFont(loginFont);

             setLayout(new GridBagLayout());
             GridBagConstraints gridBagConstraints = new GridBagConstraints();
             // Adding padding
             gridBagConstraints.insets = new Insets(0, 10, 20, 15);
             gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
             // Adding components to the layout
             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 0;
             gridBagConstraints.weightx = 0.1;
             add(firstName, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 0;
             gridBagConstraints.weightx = 0.9;
             add(firstNameField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 1;
             gridBagConstraints.weightx = 0.1;
             add(lastName, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 1;
             gridBagConstraints.weightx = 0.9;
             add(lastNameField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 2;
             gridBagConstraints.weightx = 0.1;
             add(userName, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 2;
             gridBagConstraints.weightx = 0.9;
             add(userNameField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 3;
             gridBagConstraints.weightx = 0.1;
             add(eMail, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 3;
             gridBagConstraints.weightx = 0.9;
             add(emailField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 4;
             gridBagConstraints.weightx = 0.1;
             add(createPassWord, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 4;
             gridBagConstraints.weightx = 0.9;
             add(createPassField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 5;
             gridBagConstraints.weightx = 0.1;
             add(confirmPassWord, gridBagConstraints);
             gridBagConstraints.gridx = 1;
             gridBagConstraints.gridy = 5;
             gridBagConstraints.weightx = 0.9;
             add(confirmPassField, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 6;
             gridBagConstraints.gridwidth = 2;
             gridBagConstraints.anchor = GridBagConstraints.CENTER;
             add(signUpButton, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 7;
             gridBagConstraints.gridwidth = 2;
             gridBagConstraints.anchor = GridBagConstraints.CENTER;
             add(backButton, gridBagConstraints);

             gridBagConstraints.gridx = 0;
             gridBagConstraints.gridy = 8;
             gridBagConstraints.gridwidth = 4;
             gridBagConstraints.anchor = GridBagConstraints.WEST;
             add(loginButton, gridBagConstraints);

             setTitle("Create Account");
             setSize(600, 500);

             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

             // Adding Action Listeners
             signUpButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     signUpButton();
                 }
             });

             backButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     // Open display articles
                     new DisplayPublishedArticles().setVisible(true);
                     // Close the signup form
                     SignUpForm.this.dispose();
                 }
             });

             loginButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     // Open Login Form
                     new LogInForm().setVisible(true);
                     JOptionPane.showMessageDialog(SignUpForm.this, "Openning login form");
                     // close signup form
                     SignUpForm.this.dispose();
                 }
             });
         }

         // Creating the signUpButton method
         private void signUpButton() {
             // Getting user inputs
             String firstName = firstNameField.getText();
             String lastName = lastNameField.getText();
             String userName = userNameField.getText();
             String eMail = emailField.getText();
             String createPassWord = new String(createPassField.getPassword());
             String confirmPassWord = new String(confirmPassField.getPassword());
             // Validating user input so that some part of the form will not be left blank
             if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || eMail.isEmpty()
                     || createPassWord.isEmpty() || confirmPassWord.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please fill all fields");
                 return;
             }

             // Validating email and password
             if (!isValidEmail(eMail)) {
                 JOptionPane.showMessageDialog(null, "Invalid Email Format");
                 return;
             }

             if (!isValidPassword(createPassWord)) {
                 JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and include a mix of uppercase, lowercase, digits, and special charcters");
                 return;
             }

             // Validating create and confirm password
             if (!createPassWord.equals(confirmPassWord)) {
                 JOptionPane.showMessageDialog(null, "Passwords do not match");
                 return;
             }
             // Connecting to database and inserting user input into the database
             Connection connection = null;
             PreparedStatement preparedStatement = null;
             // Database info
             String url = "jdbc:mysql://localhost:3306/userdb";
             String user = "root";
             String pass = "";
             try {
                 connection = DriverManager.getConnection(url, user, pass);
                 String query = "INSERT INTO users (`First Name`, `Last Name`, Username, Email, Password) VALUES(?, ?, ?, ?, ?)";
                 preparedStatement = connection.prepareStatement(query);
                 preparedStatement.setString(1, firstName);
                 preparedStatement.setString(2, lastName);
                 preparedStatement.setString(3, userName);
                 preparedStatement.setString(4, eMail);
                 preparedStatement.setString(5, createPassWord);

                 int rowInserted = preparedStatement.executeUpdate();
                 if (rowInserted > 0) {
                     JOptionPane.showMessageDialog(null, "User Registered Successfully");
                     logger.info("User Registered: "  + userName);
                     // Open login page
                     new LogInForm().setVisible(true);
                     // Close the signup page
                     this.dispose();
                 }
             }
             catch (SQLException throwables) {
                 logger.log(Level.SEVERE, "Database Connection Error", throwables);

                 JOptionPane.showMessageDialog(null, "Database Connection Error: " + throwables.getMessage());
             }
             finally {
                 try {
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

         // Creating isValidEmail method
         private boolean isValidEmail(String eMail) {
             String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z]{2,7}$";
             Pattern pattern = Pattern.compile(emailRegex);
             Matcher matcher = pattern.matcher(eMail);
             return matcher.matches();
         }

         // Creating isValidPassword method
         private boolean isValidPassword(String createPassWord) {
             if (createPassWord.length() < 8)
                 return false;
             if (!createPassWord.matches(".*[A-Z].*"))
                 return false;
             if (!createPassWord.matches(".*[a-z].*"))
                 return false;
             if(!createPassWord.matches(".*\\d.*"))
                 return false;
             if (!createPassWord.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
                 return false;
             return true;
         }

         // Main method that wil make the signup form runnable
         public static void main(String[] args) {
             SwingUtilities.invokeLater(new Runnable() {
                 @Override
                 public void run() {
                     new SignUpForm().setVisible(true);
                 }
             });
         }
}
