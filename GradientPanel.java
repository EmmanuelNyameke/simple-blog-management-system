import javax.swing.*;
import java.awt.*;

// Inheriting GradientPanel from JPanel in the java swing library
public class GradientPanel extends JPanel {
    private Color lightBlue;
    private Color hotPink;

    // Constructor
    public GradientPanel(Color lightBlue, Color hotPink) {
        this.lightBlue = lightBlue;
        this.hotPink = hotPink;
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradientPaint = new GradientPaint(0, 0, lightBlue, width, height, hotPink);
    }
}
// Going to update the DisplayPublishedArticles to use the GradientPanel as its content pane