package fr.weefle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Planet extends Sprite {

    ArrayList<Dock> docks = new ArrayList<Dock>() ;

    public Planet(int x, int y) {
        super(x, y);
        Dock dock1 = new Dock(x+100, y+100 , null) ;
        Dock dock2 = new Dock(x, y+100 , null);
        Dock dock3 = new Dock(x+100, y , null);
        Dock dock4 = new Dock(x, y , null);
        this.docks.add(dock1);
        this.docks.add(dock2);
        this.docks.add(dock3);
        this.docks.add(dock4);

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
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean addUfo(SpaceShip spaceShip){


        for(Dock dock : docks){
            if(dock.getSpaceShip() == null || !dock.getSpaceShip().equals(spaceShip)){
                dock.setSpaceShip(spaceShip);
                return true;
            }
        }

        return false;
    }

    public boolean removeUfo(SpaceShip spaceShip){

        for(Dock dock : docks){
            if(dock.getSpaceShip() != null && dock.getSpaceShip().equals(spaceShip)){
                dock.removeSpaceShip(spaceShip);
                return true;
            }
        }

        return false;
    }


}

