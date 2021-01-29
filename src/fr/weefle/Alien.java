package fr.weefle;

public class Alien extends Sprite {

    private int dx;
    private int dy;
    private final int INITIAL_X = 1920;
    private final int INITIAL_Y = 1080;

    public Alien(int x, int y) {
        super(x, y);
        initAlien();
    }

    private void initAlien() {

        loadImage("src/resources/ovni.png");
        getImageDimensions();
    }

    public void move() {

        if(dx==0 && dy==0){
            randomDirection();
        }

            x += dx;
            y += dy;


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

    public void randomDirection() {
        double speed = 4.0;
        double direction = Math.random()*2*Math.PI;
        dx = (int) (speed * Math.cos(direction));
        dy = (int) (speed * Math.sin(direction));
    }
}
