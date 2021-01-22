package fr.weefle;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ImageFollowingMouseTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ImageFollowingMousePanel());
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}

class ImageFollowingMousePanel extends JPanel implements MouseMotionListener {

    private final BufferedImage image;
    private Point imagePosition = new Point(150, 150);
    private Point mousePoint;
    private double imageAngleRad = 0;

    public ImageFollowingMousePanel() {
        BufferedImage i = null;
        try {
            i = ImageIO.read(getClass().getResource("/resources/ufo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = i;
        addMouseMotionListener(this);

        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mousePoint != null) {

                    int centerX = imagePosition.x + (image.getWidth() / 2);
                    int centerY = imagePosition.y + (image.getHeight() / 2);

                    if (mousePoint.x != centerX) {
                        imagePosition.x += mousePoint.x < centerX ? -1 : 1;
                    }
                    if (mousePoint.y != centerY) {
                        imagePosition.y += mousePoint.y < centerY ? -1 : 1;
                    }
                    repaint();
                }
            }
        });
        timer.start();
    }

    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        int cx = image.getWidth() / 2;
        int cy = image.getHeight() / 2;
        AffineTransform oldAT = g.getTransform();
        g.translate(cx + imagePosition.x, cy + imagePosition.y);
        g.rotate(imageAngleRad);
        g.translate(-cx, -cy);
        g.drawImage(image, 0, 0, null);
        g.setTransform(oldAT);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePoint = e.getPoint();
        double dx = e.getX() - imagePosition.getX();
        double dy = e.getY() - imagePosition.getY();
        imageAngleRad = Math.atan2(dy, dx);
        repaint();
    }

}
