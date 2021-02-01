package fr.weefle;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private JPopupMenu popupMenu;
    private Timer timer;
    public static SpaceShip spaceship;
    private List<SpaceShip> spaceships;
    private List<Alien> aliens;
    private List<Planet> planets;
    private Image bg;
    private final int B_WIDTH = 1920;
    private final int B_HEIGHT = 1080;
    private Rectangle playButton = new Rectangle(B_WIDTH/2-50,500,100,50);
    private Rectangle quitButton = new Rectangle(B_WIDTH/2-50,600,100,50);
    private final int DELAY = 1;
    private int low = 5;
    private int high = 10;
    private int low1 = 3;
    private int high1 = 6;
    private Point mousePoint;
    private Point mousePopup;
    private double imageAngleRad = 0;
    private boolean inGame = false;
    private int nb_aliens = new Random().nextInt(high - low) + low;
    private int nb_planets = new Random().nextInt(high1 - low1) + low1;
    private String distance="";
    public static AudioPlayer player = null;
    private final int[][] pos = new int[nb_aliens][2];
    private final int[][] pos2 = new int[nb_planets][2];

    private enum STATE{
        MENU,
        GAME,
        ENDGAME
    }

    private STATE State = STATE.MENU;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        initPlanets();
        initAliens();
        initSpaceShips();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {

        aliens = new ArrayList<>();

        for (int[] p : pos) {
            p[0] = new Random().nextInt(1920);
            p[1] = new Random().nextInt(1080);
        }

        for (int[] p : pos) {
            aliens.add(new Alien(p[0], p[1]));
        }
    }

    public void initSpaceShips() {

        spaceships = new ArrayList<>();

        for (int[] p : pos) {
            p[0] = new Random().nextInt(1920);
            p[1] = new Random().nextInt(1080);
        }

        for (int[] p : pos) {
            spaceships.add(new SpaceShip(p[0], p[1]));
        }
    }

    public void initPlanets() {

        planets = new ArrayList<>();

        for (int[] p : pos2) {
            p[0] = new Random().nextInt(1920);
            p[1] = new Random().nextInt(1080);
        }

        for (int[] p : pos2) {
            planets.add(new Planet(p[0], p[1]));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (State == STATE.GAME) {

            if(!inGame){
                createPopupMenu();
                inGame=true;
                player.stop();
                String audioFilePath = "src/resources/game_theme.wav";
                player = new AudioPlayer(audioFilePath);
                player.loop();
            }

            drawGame(g);

        } else if(State == STATE.ENDGAME){

            inGame=false;
            drawGameOver(g);
            player.stop();
            String audioFilePath = "src/resources/game_over.wav";
            AudioPlayer player = new AudioPlayer(audioFilePath);
            player.play();
        }else if(State == STATE.MENU){
            if(player==null) {
                String audioFilePath = "src/resources/menu.wav";
                player = new AudioPlayer(audioFilePath);
                player.loop();
            }
            drawMenu(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawMenu(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        ImageIcon j = new ImageIcon("src/resources/space.png");
        bg = j.getImage();
        g.drawImage(bg, 0, 0, null);
        String msg = "Space Conquest";
        Font small = new Font("Helvetica", Font.BOLD, 100);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, 400);
        Font font = new Font("arial", Font.BOLD, 30);
        g.setFont(font);
        g.drawString("Play", playButton.x+20, playButton.y+30);
        g.drawString("Quit", quitButton.x+20, quitButton.y+30);
        g2d.draw(playButton);
        g2d.draw(quitButton);
        for (SpaceShip spaceShip : spaceships) {
            if (spaceShip.isVisible()) {
                g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
            }
        }

    }

    private void drawGame(Graphics g) {

        ImageIcon j = new ImageIcon("src/resources/space.png");
        bg = j.getImage();
        g.drawImage(bg, 0, 0, null);

        for (Planet planet : planets) {
            if (planet.isVisible()) {
                g.drawImage(planet.getImage(), planet.getX(), planet.getY(), this);
                /*for (Dock dock : planet.docks) {
                    if (dock.isVisible()) {
                        g.drawImage(dock.getImage(), dock.getX(), dock.getY(), this);
                    }
                }*/

            }
        }

        if(spaceship!=null) {
            List<Rocket> ms = spaceship.getRockets();


            for (Rocket Rocket : ms) {
                if (Rocket.isVisible()) {
                    g.drawImage(Rocket.getImage(), Rocket.getX(), Rocket.getY(), this);
                }
            }
        }

        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }

        if (spaceship != null) {
            if (spaceship.isVisible()) {
                if (!spaceship.inSpace) {
                    g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
                } else {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(
                            RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);

                    int cx = spaceship.getImage().getWidth(null) / 2;
                    int cy = spaceship.getImage().getHeight(null) / 2;
                    AffineTransform oldAT = g2d.getTransform();
                    g2d.drawRect(spaceship.getX(), spaceship.getY(), spaceship.getImage().getWidth(null), spaceship.getImage().getHeight(null));
                    g2d.translate(cx + spaceship.x, cy + spaceship.y);
                    if(mousePoint==null){
                        imageAngleRad = Math.atan2(spaceship.getDirY(), spaceship.getDirX());
                    }
                    g2d.rotate(imageAngleRad);
                    g2d.translate(-cx, -cy);
                    g2d.drawImage(spaceship.getImage(), 0, 0, null);
                    g2d.setTransform(oldAT);
                    if(mousePoint!=null){
                        g2d.drawLine(spaceship.getX() + spaceship.getImage().getWidth(null)/2, spaceship.getY() + spaceship.getImage().getHeight(null)/2, (int) mousePoint.getX(), (int) mousePoint.getY());
                    }
                }
            }
        }

            for (SpaceShip spaceShip : spaceships) {
                    if (spaceShip.isVisible()) {
                        if(!spaceShip.equals(spaceship)) {
                            g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(), this);
                    }
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

        updateShips();
        updateRockets();
        updateAliens();

        checkCollisions();

        if (mousePoint != null) {
            if(spaceship!=null) {

                int centerX = spaceship.x + (spaceship.getImage().getWidth(null) / 2);
                int centerY = spaceship.y + (spaceship.getImage().getHeight(null) / 2);

                if (mousePoint.x != centerX) {
                    spaceship.x += mousePoint.x < centerX ? -5 : 5;
                }
                if (mousePoint.y != centerY) {
                    spaceship.y += mousePoint.y < centerY ? -5 : 5;
                }
            }
        }

        repaint();
    }

    private void inGame() {

        if (State==STATE.ENDGAME) {
            timer.stop();
        }
    }

    private void updateShips() {

        if(spaceship!=null){
            if(spaceship.finish==null) {
                if(spaceship.inSpace) {
                    if (spaceship.isVisible()) {
                            spaceship.move();
                    }
                }
            }
        }

        for (SpaceShip spaceShip: spaceships) {
            if (spaceShip.isVisible()) {
                if(spaceShip.inSpace) {
                    if (!spaceShip.equals(spaceship)) {
                        spaceShip.move();
                    }
                }

            }
        }
    }

    private void updateRockets() {

        if(spaceship!=null) {
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
    }

    private void updateAliens() {

        if (aliens.isEmpty()) {

            State = STATE.ENDGAME;
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

        if(spaceship!=null){

        Rectangle r3 = spaceship.getBounds();

        /*for (Alien alien : aliens) {

            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {

                spaceship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
            }
        }*/

        for (Planet planet : planets) {
            Rectangle r2 = planet.getBounds();

            if (r3.intersects(r2) && spaceship.finish != null && spaceship.finish.equals(planet) && spaceship.inSpace) {
                if (spaceship.land(planet)) {
                    for (Dock dock : planet.docks) {
                        if (dock.getUuid() != null && dock.getUuid().equals(spaceship.getUuid())) {
                            spaceship.x = dock.getX();
                            spaceship.y = dock.getY();
                            mousePoint = null;
                        }
                    }


                }
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
    }
    private void createPopupMenu() {

        popupMenu = new JPopupMenu();

        ImageIcon iconSpaceShip = new ImageIcon("src/resources/spaceship.png");
        ImageIcon iconPlanet = new ImageIcon("src/resources/planet.png");
        ImageIcon iconAlien = new ImageIcon("src/resources/alien.png");

        JMenuItem addSpaceShipMenuItem = new JMenuItem(new MenuItemAction("Add SpaceShip", iconSpaceShip));
        addSpaceShipMenuItem.addActionListener((e) -> {
            spaceships.add(new SpaceShip(mousePopup.x, mousePopup.y));
        });

        popupMenu.add(addSpaceShipMenuItem);

        JMenuItem addPlanetMenuItem = new JMenuItem(new MenuItemAction("Add Planet", iconPlanet));
        addPlanetMenuItem.addActionListener((e) -> {
            planets.add(new Planet(mousePopup.x, mousePopup.y));
        });

        popupMenu.add(addPlanetMenuItem);

        JMenuItem addAlienMenuItem = new JMenuItem(new MenuItemAction("Add Alien", iconAlien));
        addAlienMenuItem.addActionListener((e) -> {
            aliens.add(new Alien(mousePopup.x, mousePopup.y));
        });

        popupMenu.add(addAlienMenuItem);



        JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener((e) -> System.exit(0));

        popupMenu.add(quitMenuItem);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                mousePopup = e.getPoint();

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private class MenuItemAction extends AbstractAction {

        public MenuItemAction(String text, ImageIcon icon) {
            super(text);

            putValue(SMALL_ICON, icon);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            if(State==STATE.GAME){

            for(Planet planet : planets) {
                Rectangle hitBox = planet.getBounds();
                if (hitBox.contains(e.getX(), e.getY())) {
                    if (spaceship != null) {
                        if (spaceship.finish == null) {
                            if (spaceship.start != planet) {
                                if (planet.addUfo(spaceship)) {
                                    if (spaceship.start != null) {
                                        spaceship.takeOff();
                                    }
                                    spaceship.finish = planet;
                                    distance = spaceship.distance() + "";
                                    mousePoint = e.getPoint();
                                    double dx = e.getX() - spaceship.getX();
                                    double dy = e.getY() - spaceship.getY();
                                    imageAngleRad = Math.atan2(dy, dx);
                                    repaint();
                                }
                            }
                        }
                    }
                }

            }

            for(SpaceShip spaceShip: spaceships){
                Rectangle hitBox = spaceShip.getBounds();
                if(hitBox.contains(e.getX(), e.getY())) {
                    if(spaceship==null) {
                        spaceship = spaceShip;
                    }else if(spaceship.finish==null) {
                            spaceship = spaceShip;
                        }
                    }

                }



        } else if(State==STATE.MENU){

                if (playButton.contains(e.getX(), e.getY())) {

                    State = STATE.GAME;

                }else if(quitButton.contains(e.getX(), e.getY())){
                    System.exit(0);
                }

            }
        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            if(spaceship!=null) {
                spaceship.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ESCAPE) {
                if (spaceship != null) {
                    if (!spaceship.inSpace) {
                        spaceship.takeOff();
                    } else {
                        if(spaceship.finish!=null) {
                            spaceship.finish.removeUfo(spaceship);
                        }
                        spaceship.finish=null;
                        spaceship=null;
                    }
                    mousePoint=null;

                }else{
                        System.exit(0);

                }
            }
            if(spaceship!=null) {
                spaceship.keyPressed(e);
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
}