// Importing the necessary libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
// Due to GUI, the class DisplayPublishedArticles is inheriting from the JFrame
public class DisplayPublishedArticles extends JFrame{
    // Declaring fields from the JFrame class
    private JPanel articlesPage;
    private JButton signupBtn;
    // Initializing logger (i.e. Information to the programmer or developer)
    private static final Logger logger = Logger.getLogger(DisplayPublishedArticles.class.getName());
    // Creating constructor
    public DisplayPublishedArticles() {
        // Make the form fullscreen but retain window decorations
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
        loadArticles();
    }
    
    private void initComponents() {
        // Setting up Gradient background panel from GradientPanel
        // LightBlue to HotPink
        GradientPanel gradientPanel = new GradientPanel(new Color(135, 206, 250), new Color(255, 105, 180));
        setContentPane(gradientPanel);
        gradientPanel.setLayout(new BorderLayout());
        articlesPage = new JPanel();
        articlesPage.setLayout(new BoxLayout(articlesPage, BoxLayout.Y_AXIS));
        // Making the articles page transparent
        articlesPage.setOpaque(false);
        signupBtn = new JButton("Create Account");
        // Setting font for the signupBtn
        Font signupFont = new Font("Verdana", Font.PLAIN, 18);
        signupBtn.setFont(signupFont);
        // Setting the button color
        signupBtn.setBackground((new Color(33, 150, 243))); // Blue
        signupBtn.setForeground(Color.WHITE);
        
        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open signup form
                new SignUpForm().setVisible(true);
                // Close this form
                DisplayPublishedArticles.this.dispose();
            }
        });
        JScrollPane scrollPane = new JScrollPane(articlesPage);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        setTitle("Published Articles");
        setSize(800, 600);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        // Making the top panel transparent
        topPanel.setOpaque(false);
        topPanel.add(signupBtn);

        gradientPanel.add(topPanel, BorderLayout.NORTH);

        gradientPanel.add(scrollPane, BorderLayout.CENTER);

    }
    private void loadArticles() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        // Database info
        String url = "jdbc:mysql://localhost:3306/userdb";
        String user = "root";
        String password = "";
        // Connecting to database
        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM articlesdb";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            Font titleFont = new Font("Verdana", Font.BOLD, 18);
            Font contentFont = new Font("Verdana", Font.PLAIN, 14);
            Font metaFont = new Font("Verdana", Font.ITALIC, 12);
            
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                String content = resultSet.getString("Content");
                String author = resultSet.getString("Author");
                String createdAt = resultSet.getString("Created");
                
                JPanel articlePage = new JPanel();
                
                articlePage.setLayout(new BoxLayout(articlePage, BoxLayout.Y_AXIS));

                // Making the article page transparent
                articlePage.setOpaque(false);
                JLabel titleLabel = new JLabel("Title: " + title);
                titleLabel.setFont(titleFont);
                JLabel authorLabel = new JLabel("Author: " + author);
                authorLabel.setFont(metaFont);
                JLabel createdAtLabel = new JLabel("Published on: " + createdAt);
                createdAtLabel.setFont(metaFont);
                
                JTextArea contentArea = new JTextArea(content);
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);
                contentArea.setEditable(false);

                // Making the content area transparent
                contentArea.setOpaque(false);
                contentArea.setFont(contentFont);
                articlePage.add(titleLabel);
                articlePage.add(authorLabel);
                articlePage.add(createdAtLabel);
                
                articlePage.add(new JScrollPane(contentArea));
                
                articlePage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                
                articlesPage.add(articlePage);
                articlesPage.add(Box.createVerticalStrut(10));
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
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            }
            catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error Closing Resources", ex);
            }
        }
        articlesPage.revalidate();
        articlesPage.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplayPublishedArticles().setVisible(true);
            }
        });
    }
}
