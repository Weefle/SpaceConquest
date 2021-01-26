package fr.weefle;

import java.util.UUID;

public class Dock extends Sprite {

    int x, y;
    private UUID uuid;
    //SpaceShip spaceShip;
    boolean took;


    public Dock(int x, int y){
        super(x, y);
        this.x = x;
        this.y = y;
        //this.spaceShip = spaceShip;
        this.took = false;
        this.uuid = null;

        initDock();
    }

    private void initDock() {
        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    /*public SpaceShip getSpaceShip(){

        return this.spaceShip;

    }*/

    public UUID getUuid(){
        return this.uuid;
    }

    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void removeUuid(UUID uuid){
        this.uuid = null;
    }

    /*public void setSpaceShip(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
    }*/

    /*public void removeSpaceShip(SpaceShip spaceShip){
        this.spaceShip = null;
    }*/

}
