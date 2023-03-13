package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoOutput extends JPanel {

    private BufferedImage image;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
}

