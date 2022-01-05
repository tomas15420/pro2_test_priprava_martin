package Gui;

import ViewModel.SongsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SecondFrame extends JFrame {

    private JPanel content;
    private MyCanvas canvas;
    private Graphics g;

    public SecondFrame() throws HeadlessException {
        this.setPreferredSize(new Dimension(800,600));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.initComponents();

        this.pack();
        this.setResizable(false);

        draw();

    }

    private void initComponents() {
        content = new JPanel();
        content.setLayout(new BorderLayout());

        canvas = new MyCanvas();


        content.add(canvas,BorderLayout.CENTER);
        this.add(content);
        this.pack();
        g = canvas.getGraphics();


    }

    private void draw(){
        canvas.paint(g);
    }


}
