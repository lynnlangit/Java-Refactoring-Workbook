import java.awt.*;
import javax.swing.*;

public class Background extends JLayeredPane {
    private static final long serialVersionUID = 1L;
    
	int lineDistance;

    public Background(int width, int height, int lineDistance)
    {
        super();
        setPreferredSize(new Dimension(width, height));
        this.lineDistance = lineDistance;

        setOpaque(true);
        setBackground(Color.yellow);
        setForeground(Color.orange);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int height = getHeight();
        for (int i = 0; i < this.getWidth(); i+= lineDistance)
            g.drawLine(i, 0, i, height);
    }
}
