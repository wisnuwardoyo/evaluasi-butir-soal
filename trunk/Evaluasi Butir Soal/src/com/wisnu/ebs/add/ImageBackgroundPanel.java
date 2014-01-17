package com.wisnu.ebs.add;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageBackgroundPanel extends JPanel {

    Image image;

    public ImageBackgroundPanel(Image image) {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
