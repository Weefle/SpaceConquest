package fr.weefle;

import java.util.ArrayList;
import java.util.Random;

public class Planet extends Sprite {

    ArrayList<Dock> docks = new ArrayList<Dock>() ;

    public Planet(int x, int y, int nbStation) {
        super(x, y);

        Dock dock1, dock2, dock3, dock4, dock5;

        if(nbStation<=5 && nbStation>0) {

            if (nbStation == 1) {
                dock1 = new Dock(x + 100, y + 100);
                this.docks.add(dock1);
            } else if (nbStation == 2) {
                dock1 = new Dock(x + 100, y + 100);
                dock2 = new Dock(x, y + 100);
                this.docks.add(dock1);
                this.docks.add(dock2);
            } else if (nbStation == 3) {
                dock1 = new Dock(x + 100, y + 100);
                dock2 = new Dock(x, y + 100);
                dock3 = new Dock(x + 100, y);
                this.docks.add(dock1);
                this.docks.add(dock2);
                this.docks.add(dock3);
            } else if (nbStation == 4) {
                dock1 = new Dock(x + 100, y + 100);
                dock2 = new Dock(x, y + 100);
                dock3 = new Dock(x + 100, y);
                dock4 = new Dock(x, y);
                this.docks.add(dock1);
                this.docks.add(dock2);
                this.docks.add(dock3);
                this.docks.add(dock4);
            } else if (nbStation == 5) {
                dock1 = new Dock(x + 100, y + 100);
                dock2 = new Dock(x, y + 100);
                dock3 = new Dock(x + 100, y);
                dock4 = new Dock(x, y);
                dock5 = new Dock(x + 50, y + 50);
                this.docks.add(dock1);
                this.docks.add(dock2);
                this.docks.add(dock3);
                this.docks.add(dock4);
                this.docks.add(dock5);
            }
        }else{
            dock1 = new Dock(x + 100, y + 100);
            dock2 = new Dock(x, y + 100);
            dock3 = new Dock(x + 100, y);
            this.docks.add(dock1);
            this.docks.add(dock2);
            this.docks.add(dock3);
        }

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
            if(dock.getUuid()==null || dock.getUuid().equals(spaceShip.getUuid())){
                dock.setUuid(spaceShip.getUuid());
                return true;
            }
        }

        return false;
    }

    public boolean removeUfo(SpaceShip spaceShip){

        for(Dock dock : docks){
            if(dock.getUuid()!=null && dock.getUuid().equals(spaceShip.getUuid())){
                dock.removeUuid(spaceShip.getUuid());
                return true;
            }
        }

        return false;
    }


}

