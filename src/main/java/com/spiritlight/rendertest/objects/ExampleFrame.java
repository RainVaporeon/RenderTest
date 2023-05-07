package com.spiritlight.rendertest.objects;

import com.spiritlight.rendertest.Main;
import com.spiritlight.rendertest.math.Matrix;
import com.spiritlight.rendertest.math.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class ExampleFrame extends JFrame {

    protected JSlider slider = new JSlider(0, 360, 180);

    protected JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);

    public ExampleFrame() {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());


        pane.add(slider, BorderLayout.SOUTH);

        pane.add(pitchSlider, BorderLayout.EAST);

        pane.add(new ExamplePanel(), BorderLayout.CENTER);

        this.setSize(400, 400);
    }

    private class ExamplePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            if (!(g instanceof Graphics2D)) return;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            // rendering
            double heading = Math.toRadians(slider.getValue());
            Matrix transform = Matrix.builder(3, 3)
                    .putRow(Math.cos(heading), 0, -Math.sin(heading))
                    .putRow(0, 1, 0)
                    .putRow(Math.sin(heading), 0, Math.cos(heading))
                    .build();

            g.translate(this.getWidth() / 2, this.getHeight() / 2);
            g.setColor(Color.WHITE);

            for (Triangle t : Main.list) {
                Vertex v1 = t.getPoint1().transform(transform);
                Vertex v2 = t.getPoint2().transform(transform);
                Vertex v3 = t.getPoint3().transform(transform);

                Path2D path = new Path2D.Double();

                path.moveTo(v1.getX(), v1.getY());
                path.lineTo(v2.getX(), v2.getY());
                path.lineTo(v3.getX(), v3.getY());

                path.closePath();
                ((Graphics2D) g).draw(path);
            }
        }
    }
}
