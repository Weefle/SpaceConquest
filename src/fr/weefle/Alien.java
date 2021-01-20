package fr.weefle;

import java.util.Random;

public class Alien extends Sprite {

    private final int INITIAL_X = 1920;

    public Alien(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("src/resources/ovni.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        int rand = new Random().nextInt(2);

        if(rand > 0){
            x += new Random().nextInt(2);
        }else{
            x -= new Random().nextInt(2);
        }

        x -= 2;

        if(rand > 0){
            y += new Random().nextInt(2);
        }else{
            y -= new Random().nextInt(2);
        }

    }
}
