package com.company;

import java.util.Random;

public class Planet extends Sprite {

    public Planet(int x, int y) {
        super(x, y);

        initPlanet();
    }

    private void initPlanet() {

        int rand = new Random().nextInt(4);

        switch(rand){

            case 0: loadImage("src/resources/Baren.png");
                break;

            case 1: loadImage("src/resources/Ice.png");
                break;

            case 2: loadImage("src/resources/Lava.png");
                break;

            case 3: loadImage("src/resources/Terran.png");
                break;


        }
        getImageDimensions();
    }

    public void move() {

    }

}

