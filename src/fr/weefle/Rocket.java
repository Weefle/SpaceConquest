package fr.weefle;

public class Rocket extends Sprite {

    private final int BOARD_WIDTH_X = 1920;
    private final int BOARD_WIDTH_Y = 1080;
    private  int ROCKET_SPEED_X;
    private int ROCKET_SPEED_Y;

    public Rocket(int x, int y) {
        super(x, y);

        initRocket();
    }

    public void setDirX(int dirX){
        this.ROCKET_SPEED_X = dirX;
    }

    public void setDirY(int dirY){
        this.ROCKET_SPEED_Y = dirY;
    }

    private void initRocket() {

        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    public void move() {

        x += ROCKET_SPEED_X;
        y += ROCKET_SPEED_Y;

        if (x > BOARD_WIDTH_X) {
            visible = false;
        }
        if (y > BOARD_WIDTH_Y) {
            visible = false;
        }
    }
}
