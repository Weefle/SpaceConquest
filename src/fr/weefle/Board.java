package fr.weefle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
    private List<Alien> aliens;
    private List<Planet> planets;
    private Image bg;
    private boolean ingame;
    private final int ICRAFT_X = 960;
    private final int ICRAFT_Y = 200;
    private final int B_WIDTH = 1920;
    private final int B_HEIGHT = 1080;
    private final int DELAY = 1;
    private int low = 5;
    private int high = 10;
    private int low1 = 3;
    private int high1 = 6;
    private Point mousePoint;
    private double imageAngleRad = 0;
    private int nb_aliens = new Random().nextInt(high - low) + low;
    private int nb_planets = new Random().nextInt(high1 - low1) + low1;
    private String distance="";

    private final int[][] pos = new int[nb_aliens][2];
    private final int[][] pos2 = new int[nb_planets][2];

    public Board() {

        /*for (Point point : getPoints(new Point(0,0), new Point(10,10))) {
            System.out.println(point.getX() + " : " + point.getY());
         }*/
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        initPlanets();
        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {

        aliens = new ArrayList<>();

        for (int[] p : pos) {
            p[0] = new Random().nextInt(1920);
            p[1] = new Random().nextInt(1080 - ICRAFT_Y) + ICRAFT_Y;
        }

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    public void initPlanets() {

        planets = new ArrayList<>();

        for (int[] p : pos2) {
            p[0] = new Random().nextInt(1920);
            p[1] = new Random().nextInt(1080 - ICRAFT_Y) + ICRAFT_Y;
        }

        for (int[] p : pos2) {
            planets.add(new Planet(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {

            drawObjects(g);
            drawSpecial(g);

        } else {

            drawGameOver(g);
            Main.player.stop();
            String audioFilePath = "src/resources/game_over.wav";
            AudioPlayer player = new AudioPlayer(audioFilePath);
            player.play();
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawSpecial(Graphics g) {

        if (spaceship.isVisible()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);

            int cx = spaceship.getImage().getWidth(null) / 2;
            int cy = spaceship.getImage().getHeight(null) / 2;
            AffineTransform oldAT = g2d.getTransform();
            g2d.translate(cx + spaceship.x, cy + spaceship.y);
            g2d.rotate(imageAngleRad);
            g2d.translate(-cx, -cy);
            g2d.drawImage(spaceship.getImage(), 0, 0, null);
            g2d.setTransform(oldAT);
            //g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
        }

    }

    private void drawObjects(Graphics g) {

        ImageIcon j = new ImageIcon("src/resources/space.png");
        bg = j.getImage();
        g.drawImage(bg,0,0,null);

        for (Planet planet : planets) {
            if (planet.isVisible()) {
                g.drawImage(planet.getImage(), planet.getX(), planet.getY(), this);
                for(Dock dock : planet.docks){
                    if(dock.isVisible()) {
                        g.drawImage(dock.getImage(), dock.getX(), dock.getY(), this);
                    }
                }

            }
        }

        List<Rocket> ms = spaceship.getRockets();

        for (Rocket Rocket : ms) {
            if (Rocket.isVisible()) {
                g.drawImage(Rocket.getImage(), Rocket.getX(), Rocket.getY(), this);
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        g.drawString("Distance: " + distance, 100, 15);
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateShip();
        updateRockets();
        updateAliens();

        checkCollisions();

        if (mousePoint != null) {

            int centerX = spaceship.x + (spaceship.getImage().getWidth(null) / 2);
            int centerY = spaceship.y + (spaceship.getImage().getHeight(null) / 2);

            if (mousePoint.x != centerX) {
                spaceship.x += mousePoint.x < centerX ? -1 : 1;
            }
            if (mousePoint.y != centerY) {
                spaceship.y += mousePoint.y < centerY ? -1 : 1;
            }
        }

        repaint();
    }

    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {

        if (spaceship.isVisible()) {

            spaceship.move();
        }
    }

    private void updateRockets() {

        List<Rocket> ms = spaceship.getRockets();

        for (int i = 0; i < ms.size(); i++) {

            Rocket m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    private void updateAliens() {

        if (aliens.isEmpty()) {

            ingame = false;
            return;
        }

        for (int i = 0; i < aliens.size(); i++) {

            Alien a = aliens.get(i);

            if (a.isVisible()) {
                int rand = new Random().nextInt(2);
                //if(rand > 0) {
                    a.move();
                //}
            } else {
                aliens.remove(i);
            }
        }
    }

    public void checkCollisions() {

        Rectangle r3 = spaceship.getBounds();

        for (Alien alien : aliens) {

            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {

                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }

        List<Rocket> ms = spaceship.getRockets();

        for (Rocket m : ms) {

            Rectangle r1 = m.getBounds();

            for (Alien alien : aliens) {

                Rectangle r2 = alien.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    alien.setVisible(false);
                    String audioFilePath = "src/resources/explosion.wav";
                    AudioPlayer player = new AudioPlayer(audioFilePath);
                    player.play();
                }
            }
        }
    }

    /*public ArrayList<Point> getPoints(Point p1, Point p2)
    {
        ArrayList<Point> points = new ArrayList<Point>();

        // no slope (vertical line)
        if (p1.getX() == p2.getX())
        {
            for (double y = p1.getY(); y <= p2.getY(); y++)
            {
                Point p = new Point((int)p1.getX(), (int)y);
                points.add(p);
            }
        }
        else
        {
            // swap p1 and p2 if p2.X < p1.X
            if (p2.getX() < p1.getX())
            {
                Point temp = p1;
                p1 = p2;
                p2 = temp;
            }

            double deltaX = p2.getX() - p1.getX();
            double deltaY = p2.getY() - p1.getY();
            double error = -1.0f;
            double deltaErr = Math.abs(deltaY / deltaX);

            double y = p1.getY();
            for (double x = p1.getX(); x <= p2.getX(); x++)
            {
                Point p = new Point((int)x, (int)y);
                points.add(p);
                //System.out.println("Added Point: " + p.getX() + "," + p.getY());

                error += deltaErr;
                //System.out.println("Error is now: " + error);

                while (error >= 0.0f)
                {
                    //System.out.println("   Moving Y to " + y);
                    y++;
                    points.add(new Point((int)x, (int)y));
                    error -= 1.0f;
                }
            }

            if (!points.get(points.size()-1).equals(p2))
            {
                points.remove(points.get(points.size()-1));
            }
        }

        return points;
    }*/

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            for(Planet planet : planets){
                Rectangle hitBox = planet.getBounds();
                if(hitBox.contains(e.getX(), e.getY())){
                    //if(planet.addUfo(spaceship)){
                        if(spaceship.land(planet)){
                            distance = spaceship.distance()+"";
                            mousePoint = e.getPoint();
                            double dx = e.getX() - spaceship.getX();
                            double dy = e.getY() - spaceship.getY();
                            imageAngleRad = Math.atan2(dy, dx);
                            repaint();
                        }
                        //}
                }

            }

        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            spaceship.keyPressed(e);
        }
    }
}