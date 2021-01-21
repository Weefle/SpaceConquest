package fr.weefle;

public class Dock extends Sprite {

    int x, y;
    SpaceShip spaceShip;
    boolean took;


    public Dock(int x, int y, SpaceShip spaceShip){
        super(x, y);
        this.x = x;
        this.y = y;
        this.spaceShip = spaceShip;
        this.took = false;

        initDock();
    }

    private void initDock() {
        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    public SpaceShip getSpaceShip(){

        return this.spaceShip;

    }

    public void setSpaceShip(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
    }

    public void removeSpaceShip(SpaceShip spaceShip){
        this.spaceShip = null;
    }

}
