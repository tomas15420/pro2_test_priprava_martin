package Gui;

import java.awt.*;

public class MyCanvas extends Canvas {

    public MyCanvas() {
        this.setBackground(Color.BLACK);
        this.setSize(new Dimension(800,600));
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString("Fuck MEEE",this.getWidth() / 2 - 15 , this.getHeight() / 2 - 15);
    }
}
