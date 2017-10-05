package by.alt.DAO;

import java.util.ArrayList;

public class Position{
    private String name;
    private int id;

    Position(){}
    Position(int id,String name){
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString(){
        return String.valueOf(getId()) + "  " + getName();
    }
    //public int hashCode(){
   //     return this.getName().hashCode();
   // }
   /* public boolean equals(Object obj){
        if (!(obj instanceof Node))
            return false;
        Node entry = (Node) obj;
        return name.equals(entry.getName());
    }*/
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
