package fr.weefle;

import java.awt.*;
import java.awt.event.*;
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
    private int low = 25;
    private int high = 50;
    private int nb_aliens = new Random().nextInt(high - low) + low;
    private int nb_planets = new Random().nextInt(high/10 - low/10) + low/10;

    private final int[][] pos = new int[nb_aliens][2];
    private final int[][] pos2 = new int[nb_planets][2];

    public Board() {

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

        } else {

            drawGameOver(g);
            Main.player.stop();
            String audioFilePath = "src/resources/game_over.wav";
            AudioPlayer player = new AudioPlayer(audioFilePath);
            player.play();
        }

        Toolkit.getDefaultToolkit().sync();
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

        if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
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
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
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
                if(rand > 0) {
                    a.move();
                }
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

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            for(Planet planet : planets){
                Rectangle hitBox = planet.getBounds();
                if(hitBox.contains(e.getX(), e.getY())){
                    if(planet.addUfo(spaceship)){
                        if(spaceship.land(planet)){
                            spaceship.distance();
                        }
                    }
                    //System.exit(0);
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