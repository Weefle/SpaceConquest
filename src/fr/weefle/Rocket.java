package fr.weefle;

public class Rocket extends Sprite {

    private final int BOARD_WIDTH = 1920;
    private final int ROCKET_SPEED = 5;

    public Rocket(int x, int y) {
        super(x, y);

        initRocket();
    }

    private void initRocket() {

        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    public void move() {

        x += ROCKET_SPEED;

        if (x > BOARD_WIDTH)
            visible = false;
    }
}
