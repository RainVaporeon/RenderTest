package com.spiritlight.rendertest.objects;

import com.spiritlight.rendertest.Main;
import com.spiritlight.rendertest.math.Matrix;
import com.spiritlight.rendertest.math.Vertex;
import com.spiritlight.rendertest.utils.AutoScaler;
import com.spiritlight.rendertest.utils.MathHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExampleFrame extends JFrame {

    protected JSlider slider = new JSlider(0, 360, 180);

    protected JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);

    private final AtomicInteger yaw = new AtomicInteger();
    private final AtomicInteger pitch = new AtomicInteger();

    protected AutoScaler yawScale = new AutoScaler(yaw, 360, 0, 6, 50, TimeUnit.MILLISECONDS, this::repaint);
    protected AutoScaler pitchScale = new AutoScaler(pitch, 90, -90, 2, 50, TimeUnit.MILLISECONDS, this::repaint);

    public ExampleFrame() {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());

        // pane.add(slider, BorderLayout.SOUTH);

        // pane.add(pitchSlider, BorderLayout.EAST);

        pane.add(new ExamplePanel(), BorderLayout.CENTER);

        this.setSize(400, 400);

        // so the slider changes are seen
        // slider.addChangeListener(e -> repaint());
        // pitchSlider.addChangeListener(e -> repaint());

        this.yawScale.start();
        this.pitchScale.start();
    }

    private class ExamplePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            if (!(g instanceof Graphics2D)) return;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            // rendering
            double heading = Math.toRadians(yaw.get());
            Matrix headingTransform = Matrix.builder(3, 3)
                    .putRow(Math.cos(heading), 0, -Math.sin(heading))
                    .putRow(0, 1, 0)
                    .putRow(Math.sin(heading), 0, Math.cos(heading))
                    .build();

            double pitch = Math.toRadians(ExampleFrame.this.pitch.get());
            Matrix pitchTransform = Matrix.builder(3, 3)
                    .putRow(1, 0, 0)
                    .putRow(0, Math.cos(pitch), Math.sin(pitch))
                    .putRow(0, -Math.sin(pitch), Math.cos(pitch))
                    .build();

            Matrix transform = headingTransform.multiply(pitchTransform);

            g.setColor(Color.WHITE);

            BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Z-Buffer array
            double[] buffer = new double[image.getHeight() * image.getWidth()];
            Arrays.fill(buffer, Double.NEGATIVE_INFINITY);

            for (Triangle t : Main.list) {
                // transform the vertices and then translate
                Vertex v1 = t.getPoint1().transform(transform)
                        .add(this.getWidth() / 2d, this.getHeight() / 2d, 0);
                Vertex v2 = t.getPoint2().transform(transform)
                        .add(this.getWidth() / 2d, this.getHeight() / 2d, 0);
                Vertex v3 = t.getPoint3().transform(transform)
                        .add(this.getWidth() / 2d, this.getHeight() / 2d, 0);

                Vertex ab = v2.subtract(v1);
                Vertex ac = v3.subtract(v1);
                Vertex normal = Vertex.of(
                        ab.getY() * ac.getZ() - ab.getZ() * ac.getY(),
                        ab.getZ() * ac.getX() - ab.getX() * ac.getZ(),
                        ab.getX() * ac.getY() - ab.getY() * ac.getX()
                );

                normal = normal.scale(1.0 / normal.normalLength());

                double angle = Math.cos(normal.getZ());

                int minX = (int) Math.max(0, Math.ceil(MathHelper.min(v1.getX(), v2.getX(), v3.getX())));
                int maxX = (int) Math.min(this.getWidth() - 1, Math.floor(MathHelper.max(v1.getX(), v2.getX(), v3.getX())));

                int minY = (int) Math.max(0, Math.ceil(MathHelper.min(v1.getY(), v2.getY(), v3.getY())));
                int maxY = (int) Math.min(this.getHeight() - 1, Math.floor(MathHelper.max(v1.getY(), v2.getY(), v3.getY())));

                double triArea2D = (v1.getY() - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - v1.getX());

                // didn't really feel like typing out this hot mess so i just copied it over with modifications

                /* Start of the hot mess */
                // this just draws our picture btw
                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        double b1 =
                                ((y - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - x)) / triArea2D;
                        double b2 =
                                ((y - v1.getY()) * (v3.getX() - v1.getX()) + (v3.getY() - v1.getY()) * (v1.getX() - x)) / triArea2D;
                        double b3 =
                                ((y - v2.getY()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v2.getX() - x)) / triArea2D;
                        double depth = b1 * v1.getZ() + b2 * v2.getZ() + b3 * v3.getZ();

                        int zIndex = y * image.getWidth() + x;

                        if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                            if(buffer[zIndex] < depth) {
                                image.setRGB(x, y, t.getShade(angle).getRGB());
                                buffer[zIndex] = depth;
                            }
                        }
                    }
                }
            }
            /* End of that hot mess */

            g.drawImage(image, 0, 0, null);
        }
    }
}
