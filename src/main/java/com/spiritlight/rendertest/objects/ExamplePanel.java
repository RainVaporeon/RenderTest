package com.spiritlight.rendertest.objects;

import javax.swing.*;
import java.awt.*;

public class ExamplePanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
