// Importing the necessary libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
// Inheriting blogger from the JFrame class due to GUI
public class Blogger extends JFrame {
    // Declaring fields from the JFrame class
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton publishButton;
    private JButton backButton;
    private JButton imageButton;
    private JButton videoButton;
    private JButton linkButton;
    // Info. to the programmer or developer
    private static final Logger logger = Logger.getLogger(Blogger.class.getName());
    // Constructor
    public Blogger () {
        // Make the form fullscreen but retain window decorations
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
    }
    private void initComponents(){
        // Creating labels, text, fields, and buttons
        JLabel titleLabel = new JLabel("Title: ");
        JLabel contentLabel = new JLabel("Content: ");
        // Setting fonts to verdana and increasing the size
        Font labelFont = new Font("Verdana", Font.BOLD, 16);
        titleLabel.setFont(labelFont);
        contentLabel.setFont(labelFont);
        
        titleField = new JTextField();
        contentArea = new JTextArea(20, 50);

        // Setting fonts and increasing the size
        Font fieldFont = new Font("New Times Roman", Font.PLAIN, 14);
        titleField.setFont(fieldFont);
        contentArea.setFont(fieldFont);
        publishButton = new JButton("Publish");
        backButton = new JButton("back");
        imageButton = new JButton("Upload Image");
        videoButton = new JButton("Upload Video");
        linkButton = new JButton("Insert Link");
        
        // Using border layout for the main layout
        setLayout(new BorderLayout());
        // Panel for the form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        // Adding Padding
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        // Adding title label and text field
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        formPanel.add(titleLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(titleField, gridBagConstraints);
        
        // Adding content label and text area
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(contentLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(contentArea);
        formPanel.add(scrollPane, gridBagConstraints);
        
        // Adding panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(publishButton);
        buttonPanel.add(backButton);
        buttonPanel.add(imageButton);
        buttonPanel.add(videoButton);
        buttonPanel.add(linkButton);
        // Adding panels to the main layout
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        // Setting frame properties
        setTitle("Create Article");
        setSize(800, 600);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adding action listeners
        publishButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                publishArticle();
            }
        });
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Display published articles
                new DisplayPublishedArticles().setVisible(true);
                // Close the current form
                Blogger.this.dispose();
            }
        });
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile("image");
            }
        });
        videoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile("video");
            }
        });
        linkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertLink();
            }
        });
    }
    private void publishArticle() {
        String title = titleField.getText();
        String content = contentArea.getText();
        
        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields");
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // Database Info
        String url = "jdbc:mysql://localhost:3306/userdb";
        String user = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO articlesdb (Title, Content, Author) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, "K Bola");
            
            int rowInserted = preparedStatement.executeUpdate();
            if (rowInserted > 0) {
                JOptionPane.showMessageDialog(null, "Article Published Successfully");
                
                logger.info("Article Published: " + title);
                // Open display published articles
                new DisplayPublishedArticles().setVisible(true);
                // Close the blogger page
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
    
    private void uploadFile(String fileType) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter;
        if ("image".equals(fileType)) {
            filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif");
        }
        else {
            filter = new FileNameExtensionFilter("Video Files", "mp4", "avi", "mov");
        }
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            contentArea.append("\n" + fileType.toUpperCase() + ": " + filePath + "\n");
        }
    }
    private void insertLink() {
        String link = JOptionPane.showInputDialog(this, "Enter URL:");
        if (link != null && !link.isEmpty()) {
            contentArea.append("\nLINK: " + link + "\n");
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Blogger().setVisible(true);
            }
        });
    }
}

