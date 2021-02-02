package fr.weefle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceShip extends Sprite {

    private int dx, dx_2;
    private int dy, dy_2;
    private List<Rocket> rockets;
    Planet start, finish;
    boolean inSpace;
    private UUID uuid;

    public SpaceShip(int x, int y) {
        super(x, y);
        this.start = null;
        this.finish = null;
        this.inSpace = true;
        this.uuid = UUID.randomUUID();
        initCraft();
    }

    public int getDirX(){
        return this.dx;
    }

    public int getDirY(){
        return this.dy;
    }

    private void initCraft() {

        rockets = new ArrayList<>();
        loadImage("src/resources/ufo.png");
        getImageDimensions();
    }

    public boolean land(Planet planet) {

        if (this.inSpace){
            this.start = planet;
        this.finish = null;
        this.inSpace = false;
        return true;
    }
            return false;

    }

    public UUID getUuid(){
        return this.uuid;
    }

    public void takeOff(){

        if(this.start.removeUfo(this)){
            this.start = null;
            this.finish = null;
            this.inSpace = true;
        }

    }

    public void move() {


        if(Board.spaceship!=this) {

            if (dx_2 == 0 && dy_2 == 0) {
                randomDirection();
            }


            if (x >= 1920) {
                x = 1920;
                randomDirection();
            }
            if (x <= 1) {
                x = 1;
                randomDirection();
            }
            if (y >= 1080) {
                y = 1080;
                randomDirection();
            }
            if (y <= 1) {
                y = 1;
                randomDirection();
            }
            x += dx_2;
            y += dy_2;
        }else{
            x += dx;
            y += dy;
        }

    }
    public void randomDirection() {
        double speed = 4.0;
        double direction = Math.random()*2*Math.PI;
        dx_2 = (int) (speed * Math.cos(direction));
        dy_2 = (int) (speed * Math.sin(direction));
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public int distance(){

        int racine = (int) Math.sqrt(Math.pow(this.getX() - finish.x, 2) + Math.pow(this.getY() - finish.y, 2));

        return racine;
    }

    public void fire() {
        if(this.getDirX()!=0 || this.getDirY()!=0) {
            Rocket rocket = new Rocket(x + width / 2, y + height / 2);
            double speed = 5.0;
            int dx = (int) (speed * this.getDirX());
            int dy = (int) (speed * this.getDirY());
            rocket.setDirX(dx);
            rocket.setDirY(dy);
            rockets.add(rocket);
            String audioFilePath = "src/resources/laser.wav";
            AudioPlayer player = new AudioPlayer(audioFilePath);
            player.play();
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
