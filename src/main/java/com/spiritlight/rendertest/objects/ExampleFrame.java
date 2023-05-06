package com.spiritlight.rendertest.objects;

import javax.swing.*;
import java.awt.*;

public class ExampleFrame extends JFrame {

    public ExampleFrame() {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());

        JSlider slider = new JSlider(0, 360, 180);
        pane.add(slider, BorderLayout.SOUTH);

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        pane.add(new ExamplePanel(), BorderLayout.CENTER);

        this.setSize(400, 400);
    }
}
