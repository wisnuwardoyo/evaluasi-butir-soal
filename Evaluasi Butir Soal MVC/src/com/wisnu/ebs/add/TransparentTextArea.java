/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.add;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JTextArea;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */

public class TransparentTextArea extends JTextArea {

    public TransparentTextArea() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 0));
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        g.fillRect(x, y, width, height);
        super.paintComponent(g);
    }

}
