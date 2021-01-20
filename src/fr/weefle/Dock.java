package fr.weefle;

public class Dock extends Sprite {

    int x, y, id;
    boolean took;


    public Dock(int x, int y, int id){
        super(x, y);
        this.x = x;
        this.y = y;
        this.id = id;
        this.took = false;

        initDock();
    }

    private void initDock() {
        loadImage("src/resources/circle.png");
        getImageDimensions();
    }

    public int getId(){

        return this.id;

    }

    public boolean isTook(){
        return this.took;
    }

    public void setTook(boolean took){
        this.took = took;
    }

}
