package fr.weefle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Rocket> rockets;
    Planet start, finish;
    boolean inSpace;

    public SpaceShip(int x, int y) {
        super(x, y);
        this.start = null;
        this.finish = null;
        this.inSpace = true;
        initCraft();
    }

    private void initCraft() {

        rockets = new ArrayList<>();
        loadImage("src/resources/ufo.png");
        getImageDimensions();
    }

    public boolean land(Planet finish){

        if(finish.addUfo(this)){

            this.finish = finish;
            //this.start = finish;
            //this.inSpace = false;
            return true;

        }
        return false;
    }

    public void takeOff(){

        if(this.start.removeUfo(this)){
            this.finish.addUfo(this);
        }

    }

    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
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

        //int racine = (int) Math.sqrt(Math.pow(start.x - finish.x, 2) + Math.pow(start.y - finish.y, 2));
        int racine = (int) Math.sqrt(Math.pow(this.getX() - finish.x, 2) + Math.pow(this.getY() - finish.y, 2));

        System.out.println(racine);
        return racine;
    }

    public void fire() {
        rockets.add(new Rocket(x + width, y + height / 2));
        String audioFilePath = "src/resources/laser.wav";
        AudioPlayer player = new AudioPlayer(audioFilePath);
        player.play();
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
