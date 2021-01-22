package fr.weefle;

import java.util.Random;

public class Alien extends Sprite {

    private int dx;
    private int dy;
    private boolean isNavigating;
    private final int INITIAL_X = 1920;
    private final int INITIAL_Y = 1080;

    public Alien(int x, int y) {
        super(x, y);
        initAlien();
        this.isNavigating = false;
    }

    private void initAlien() {

        loadImage("src/resources/ovni.png");
        getImageDimensions();
    }

    public void move() {

       /* if (x < 0) {
            x = INITIAL_X;
        }

        if (y < 0) {
            y = INITIAL_Y;
        }

        x -= 2;

        int rand = new Random().nextInt(2);

        if(rand > 0){
            x += new Random().nextInt(4);
        }else{
            x -= new Random().nextInt(4);
        }

        if(rand > 0){
            y += new Random().nextInt(4);
        }else{
            y -= new Random().nextInt(4);
        }*/

        if(dx==0 && dy==0){
            randomDirection();
        }

        if(this.isNavigating){

        }else{
            x += dx;
            y += dy;
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
    }

    public boolean isNavigating(){
        return this.isNavigating;
    }

    public void setNavigating(boolean navigating) {
        this.isNavigating = navigating;
    }

    public void randomDirection() {
        double speed = 4.0;
        double direction = Math.random()*2*Math.PI;
        dx = (int) (speed * Math.cos(direction));
        dy = (int) (speed * Math.sin(direction));
    }
}
