package fr.weefle;

import java.util.Random;

public class Alien extends Sprite {

    private int dx;
    private int dy;

    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("src/resources/ovni.png");
        getImageDimensions();
    }

    public void move() {

        x -= 2;

        int rand = new Random().nextInt(2);

        if(rand > 0){
            x += new Random().nextInt(2);
        }else{
            x -= new Random().nextInt(2);
        }

        if(rand > 0){
            y += new Random().nextInt(2);
        }else{
            y -= new Random().nextInt(2);
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
        x += dx;
        y += dy;
    }

    public void randomDirection() {
        double speed = 4.0;
        double direction = Math.random()*2*Math.PI;
        dx = (int) (speed * Math.cos(direction));
        dy = (int) (speed * Math.sin(direction));
    }
}
