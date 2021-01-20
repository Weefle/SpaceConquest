package fr.weefle;

public class Dock {

    int x, y, id;
    boolean took;


    public Dock(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.took = false;

    }

    public boolean isTook(){
        return this.took;
    }

    public void setTook(boolean took){
        this.took = took;
    }

}
