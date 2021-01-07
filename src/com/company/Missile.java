package com.company;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 1920;
    private final int MISSILE_SPEED = 5;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }

    private void initMissile() {

        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    public void move() {

        x += MISSILE_SPEED;

        if (x > BOARD_WIDTH)
            visible = false;
    }
}
